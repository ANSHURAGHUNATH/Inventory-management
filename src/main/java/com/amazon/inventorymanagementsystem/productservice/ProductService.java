package com.amazon.inventorymanagementsystem.productservice;

import com.amazon.inventorymanagementsystem.model.Product;
import com.amazon.inventorymanagementsystem.model.ProductResponse;
import com.amazon.inventorymanagementsystem.model.SearchProductsRequest;

import java.util.List;

public interface ProductService {
  public ProductResponse addProducts(Product product);
  //public String getProducts(SearchProduct searchProduct);
  public ProductResponse deleteProduct(int productId);
  public ProductResponse updateProduct(Product product);
  public ProductResponse searchProducts(List<SearchProductsRequest> searchProductsRequest);
}
