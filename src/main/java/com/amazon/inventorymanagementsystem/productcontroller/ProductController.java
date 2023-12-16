package com.amazon.inventorymanagementsystem.productcontroller;

import com.amazon.inventorymanagementsystem.model.AddProductEvent;
import com.amazon.inventorymanagementsystem.model.Product;
import com.amazon.inventorymanagementsystem.model.ProductResponse;
import com.amazon.inventorymanagementsystem.model.SearchProductsRequest;
import com.amazon.inventorymanagementsystem.productservice.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
    private final ProductService productService;
    private final ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    public ProductController(ProductService productService, ApplicationEventPublisher applicationEventPublisher) {
        this.productService = productService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @RequestMapping(method = RequestMethod.POST, path="/add")
    public ResponseEntity<ProductResponse> addProductsToInventory(@RequestBody Product product) {
        ProductResponse productResponse = productService.addProducts(product);
        applicationEventPublisher.publishEvent(new AddProductEvent(this, product.getProductName()));
        return new ResponseEntity<>(productResponse, productResponse.getSuccessful()?HttpStatus.OK:HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.POST, path="/delete/{id}")
    public ResponseEntity<ProductResponse> deleteProductsFromInventory(@PathVariable("id") int productId) {
        ProductResponse productResponse = productService.deleteProduct(productId);
        return new ResponseEntity<>(productResponse, productResponse.getSuccessful()?HttpStatus.OK:HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST, path="/update")
    public ResponseEntity<ProductResponse> updateProductsToInventory(@RequestBody Product product) {
        ProductResponse productResponse = productService.updateProduct(product);
        return new ResponseEntity<>(productResponse, productResponse.getSuccessful()?HttpStatus.OK:HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST, path="/search")
    public ResponseEntity<ProductResponse> searchProducts(@RequestBody List<SearchProductsRequest> searchProducts) {
        ProductResponse productResponse = productService.searchProducts(searchProducts);
        return new ResponseEntity<>(productResponse, productResponse.getSuccessful()?HttpStatus.OK:HttpStatus.NOT_FOUND);
    }
}
