package com.example.productcrud.service;

import com.example.productcrud.dto.ProductDTO;
import com.example.productcrud.entity.Product;
import com.example.productcrud.enums.ProductCategory;
import com.example.productcrud.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<Product> addProduct(ProductDTO product) {
        List<String> categories = Arrays.stream(ProductCategory.values())
                .map(Enum::name)
                .toList();

        if (!categories.contains(product.getCategory())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Product newProduct = new Product(product.getCategory(), product.getName(), product.getPrice());
        productRepository.save(newProduct);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public ResponseEntity<Product> updateProduct(Long id, ProductDTO productDetails) {

        List<String> categories = Arrays.stream(ProductCategory.values())
                .map(Enum::name)
                .toList();

        if (!categories.contains(productDetails.getCategory())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product existingProduct = product.get();
            existingProduct.setCategory(productDetails.getCategory());
            existingProduct.setName(productDetails.getName());
            existingProduct.setPrice(productDetails.getPrice());
            productRepository.save(existingProduct);

            return ResponseEntity.ok(existingProduct);
        }

        return ResponseEntity.notFound().build();
    }

    public ProductDTO getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);

        return product.map(p -> new ProductDTO(p.getId(), p.getCategory(), p.getName(), p.getPrice())).orElse(null);
    }

    public List<Product> filterProducts(String category, Double price, String name) {
        return productRepository.filterProducts(category, price, name);
    }
}