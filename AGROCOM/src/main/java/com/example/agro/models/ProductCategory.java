package com.example.agro.models;


import com.sun.istack.Nullable;

import javax.persistence.*;

@Entity
// fruits, meats, vegetables
public class ProductCategory {




    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;

    @OneToOne
    @JoinColumn(name = "product_category_id")
    @Nullable
    private ProductCategory productCategory;

    public ProductCategory() {}



    public void setTitle(String title) {
        this.title = title;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", productCategory=" + productCategory +
                '}';
    }



}
