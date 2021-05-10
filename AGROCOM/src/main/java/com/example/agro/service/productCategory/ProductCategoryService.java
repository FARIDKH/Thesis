package com.example.agro.service.productCategory;


import com.example.agro.exceptions.ResourceNotFoundException;
import com.example.agro.models.ProductCategory;
import com.example.agro.repositories.productCategory.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductCategoryService {


    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public Iterable<ProductCategory> getAllProductCategories(){
        return this.productCategoryRepository.findAll();
    }

    public Optional<ProductCategory> getProductCategoryById(Long id){
        return this.productCategoryRepository.findById(id);
    }

    public void create(ProductCategory productCategory){
        this.productCategoryRepository.save(productCategory);
    }

    public ProductCategory update(Long id, ProductCategory productCategory){
        if(!productCategoryRepository.existsById(id)){
            throw new ResourceNotFoundException(productCategory.getTitle() + " not found!");
        }

        return productCategoryRepository.findById(id).map((productCategory1) -> {
            productCategory1.setTitle(productCategory.getTitle());
            if(productCategory.getProductCategory() != null)
                productCategory1.setProductCategory(productCategory.getProductCategory());
            return productCategoryRepository.save(productCategory1);
        }).orElseThrow(() -> new ResourceNotFoundException("Product category with id" + id + " not found!"));

    }


}
