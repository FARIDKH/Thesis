package com.example.agro.service.product;


import com.example.agro.message.request.ProductRequest;
import com.example.agro.message.response.ProductMinAndMaxResponse;
import com.example.agro.models.Constant;
import com.example.agro.models.Product;
import com.example.agro.models.ProductCategory;
import com.example.agro.models.User;
import com.example.agro.repositories.product.ProductRepository;
import com.example.agro.service.FileUploadService;
import com.example.agro.service.productCategory.ProductCategoryService;
import com.example.agro.service.user.UserService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductCategoryService productCategoryService;


    public Optional<Product> getProduct(Long id){
        return this.productRepository.findById(id);
    }


    public byte[] getProductImageBy(Long productId) throws IOException {
        if(getProduct(productId).get().getImg_path() != null){
            var url = new FileSystemResource("product-images/" + getProduct(productId).get().getImg_path());
            byte[] bytes = StreamUtils.copyToByteArray(url.getInputStream());
            return bytes;
        }
        return null;

    }

    public List<Product> getUserProducts(Long user_id){
        return this.productRepository.findAllBySellerId(user_id);
    }

    public List<Product> getAllProducts(){
        return this.productRepository.findAll();
    }



    public List<Product> getProductByProductCategoryID(Long productCategoryId){
        ProductCategory pc = this.productCategoryService.getProductCategoryById(productCategoryId).get();
        return this.productRepository.findAllByProductCategory(pc);
    }

    public List<Product> getProductBetween(Integer fromPrice, Integer toPrice){
        return this.productRepository.findAllByPriceBetween(fromPrice,toPrice);
    }

    public ProductMinAndMaxResponse getMinAndMaxPricesProducts(){
        return this.productRepository.getMinAndMaxValue();
    }


    public Product createProduct(Long user_id, ProductRequest productRequest){
        User seller = this.userService.getUserByID(user_id).get();
        ProductCategory productCategory = this.productCategoryService.getProductCategoryById(productRequest.getProductCategoryId()).get();
        Product product = new Product();
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        product.setProductCategory(productCategory);
        product.setSeller(seller);
        product.setCreatedDate(new Date(System.currentTimeMillis()));
        product.setDescription(productRequest.getDescription());
        product.setTitle(productRequest.getTitle());
        product.setConstant(productRequest.getConstant());
        product.setDescription(productRequest.getDescription());
        Product p = this.productRepository.save(product);

        if (productRequest.getProductImage() != null) {
            String uploadDir = "product-images/" ;
            String fileName = "product#" + p.getId() + StringUtils.cleanPath(productRequest.getProductImage().getOriginalFilename());
            p.setImg_path(fileName);
            try {
                FileUploadService.saveFile(uploadDir,fileName,productRequest.getProductImage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        this.productRepository.save(p);
        return p;

    }

    public Product updateProduct(Long id, Long product_category_id,Product product){
        Product repoProd = this.productRepository.findById(id).get();
        repoProd.setDescription(product.getDescription());
        repoProd.setProductCategory(this.productCategoryService.getProductCategoryById(product_category_id).get());
        repoProd.setPrice(product.getPrice());
        repoProd.setStock(product.getStock());
        repoProd.setTitle(product.getTitle());
        repoProd.setImg_path(product.getImg_path());
        this.productRepository.save(repoProd);
        return repoProd;
    }

    public Boolean deleteProduct(Long id){
        this.productRepository.deleteById(id);
        return !this.productRepository.existsById(id);
    }





}
