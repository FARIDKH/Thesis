package com.example.agro.repositories.product;

import com.example.agro.message.response.ProductMinAndMaxResponse;
import com.example.agro.models.Product;
import com.example.agro.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllBySellerId(Long seller_id);
    List<Product> findAllByProductCategory(ProductCategory productCategory);
    List<Product> findAllByPriceBetween(Integer minPrice, Integer maxPrice);
    @Query(value = "select new com.example.agro.message.response.ProductMinAndMaxResponse(" +
            "min(p.price), max(p.price) )  from Product p")
    ProductMinAndMaxResponse getMinAndMaxValue();

}
