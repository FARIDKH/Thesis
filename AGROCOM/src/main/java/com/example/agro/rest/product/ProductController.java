package com.example.agro.rest.product;


import com.example.agro.message.request.ProductRequest;
import com.example.agro.message.response.ProductMinAndMaxResponse;
import com.example.agro.models.Product;
import com.example.agro.models.User;
import com.example.agro.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

import javax.annotation.Resource;
import javax.servlet.MultipartConfigElement;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/public/getAll")
    @ResponseBody
    public List<Product> getAllProducts(){
        return this.productService.getAllProducts();
    }

    @GetMapping("/public/productId/{productId}")
    @ResponseBody
    public Optional<Product> getProductById(
            @PathVariable Long productId
    ){
        return this.productService.getProduct(productId);
    }

    @GetMapping(value = "/public/getProductImage/{productId}")
    public @ResponseBody byte[] getProductImage(
            @PathVariable Long productId
    ) throws IOException {
        byte[] bytes =  this.productService.getProductImageBy(productId);

        return bytes;
    }

    @GetMapping("/public/productCategoryID/{productCategoryID}/getAll")
    @ResponseBody
    public List<Product> getProductByProductCategory(@PathVariable Long productCategoryID){
        return this.productService.getProductByProductCategoryID(productCategoryID);
    }


    @GetMapping("/public/minPrice/{minPrice}/maxPrice/{maxPrice}/getAll")
    @ResponseBody
    public List<Product> getProductByPriceBetween(
            @PathVariable Integer minPrice,
            @PathVariable Integer maxPrice){
        return this.productService.getProductBetween(minPrice,maxPrice);
    }


    @GetMapping("/public/getMinAndMax")
    public ProductMinAndMaxResponse getMinAndMaxPrice(){
        return this.productService.getMinAndMaxPricesProducts();
    }

    @GetMapping("/user/{user_id}/getAll")
    @ResponseBody
    public List<Product> getUserProducts(@PathVariable Long user_id){
        return this.productService.getUserProducts(user_id);
    }



    @PostMapping("/create/userID/{userId}")
    @ResponseBody
    public Product createProduct(
            @PathVariable Long userId,
            @ModelAttribute ProductRequest product)
    {
        Product newProd = this.productService.createProduct(userId,product);
        return newProd;
    }

    @PutMapping("/update/{productId}/userId/{userId}/productCategory/{productCategoryId}")
    @ResponseBody
    public Product updateProduct(
            @PathVariable Long userId,
            @PathVariable Long productCategoryId,
            @PathVariable Long productId,
            @Valid @RequestBody Product product)
    {
        Product prod = this.productService.getProduct(productId).get();
        if(prod.getSeller().getId() == userId){
            Product newProd = this.productService.updateProduct(productId,productCategoryId,product);
            return newProd;
        }
        return null;
    }

    @DeleteMapping("/delete/{product_id}/userId/{user_id}/productCategory/{product_category_id}/{product_id}")
    @ResponseBody
    public Boolean deleteProduct(@PathVariable Long user_id,
                                 @PathVariable Long product_category_id,
                                 @PathVariable Long product_id){
        return this.productService.deleteProduct(product_id);
    }





}
