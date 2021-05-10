package com.example.agro.rest.productCategory;

import com.example.agro.models.Product;
import com.example.agro.models.ProductCategory;
import com.example.agro.service.productCategory.ProductCategoryService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;


    @GetMapping("/public/productCategory/getAll")
    public Iterable<ProductCategory> getAllProductCategories(){
        return this.productCategoryService.getAllProductCategories();
    }

    @GetMapping("/public/productCategory/find/{id}")
    public ProductCategory getProductCategoryById(@PathVariable Long id){
        return this.productCategoryService.getProductCategoryById(id).get();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = {"/admin/productCategory/create/{parent_id}"
            ,"/admin/productCategory/create"},method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createProductCategory(
            @PathVariable(required = false) Long parent_id,
            @Valid @RequestBody ProductCategory productCategory
    ){
        if (parent_id != null){
            ProductCategory parentProductCategory = this.productCategoryService.getProductCategoryById(parent_id).get();
            productCategory.setProductCategory(parentProductCategory);
        }
        this.productCategoryService.create(productCategory);
        return ResponseEntity.status(200).body(productCategory);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/productCategory/update/{id}")
    @ResponseBody
    public String updateProductCategory(
            @PathVariable Long id,
            @Valid @RequestBody ProductCategory newProductCategory
    ){
        ProductCategory productCategory = this.productCategoryService.update(id,newProductCategory);
        return "(newly created pc : )" + productCategory.toString();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/productCategory/delete/{id}")
    @ResponseBody
    public String deleteProductCategory(
            @PathVariable Long id,
            @Valid @RequestBody ProductCategory newProductCategory
    ){
        ProductCategory productCategory = this.productCategoryService.update(id,newProductCategory);
        return "(newly created pc : )" + productCategory.toString();
    }


}
