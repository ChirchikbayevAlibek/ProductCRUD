package com.example.productcrud.controllers;

import com.example.productcrud.dto.ProductDTO;
import com.example.productcrud.entity.Product;
import com.example.productcrud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductDTO product) {
        return productService.addProduct(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDetails) {
        return productService.updateProduct(id, productDetails);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProductsByFilter(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) String name) {
        List<Product> products = productService.filterProducts(category, price, name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);

        if (product != null) {
            return ResponseEntity.ok(product);
        }

        return ResponseEntity.notFound().build();
    }
}