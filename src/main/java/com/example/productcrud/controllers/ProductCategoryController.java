package com.example.productcrud.controllers;

import com.example.productcrud.enums.ProductCategory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/product-category")
public class ProductCategoryController {

    @GetMapping
    public List<String> getProductCategories() {
        return Arrays.stream(ProductCategory.values())
                     .map(Enum::name)
                     .collect(Collectors.toList());
    }
}