package com.example.Product_Clover_API.controller;

import com.example.Product_Clover_API.entity.Product;
import com.example.Product_Clover_API.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    // Create Product
    @PostMapping("/creates")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        logger.info("Creating new product: {}", product);
        Product createdProduct = productService.createProduct(product);
        logger.debug("Created product: {}", createdProduct);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // Get all Products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        logger.info("Fetching all products...");
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()){
            logger.warn("No products found!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            logger.debug("Products retrieved: {}", products.size());
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }

    // Get Product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        logger.info("Fetching product with ID: {}", id);
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            logger.debug("Product found: {}", product.get());
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            logger.error("Product with ID {} not found!", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update Product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product){
        logger.info("Updating product with ID: {}", id);
        Product updatedProduct = productService.updateProduct(id, product);
        logger.debug("Updated product: {}", updatedProduct);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // Delete Product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        logger.info("Deleting product with ID: {}", id);
        productService.deleteProduct(id);
        logger.debug("Product with ID {} deleted", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
