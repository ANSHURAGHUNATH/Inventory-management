package com.amazon.inventorymanagementsystem.productservice;

import com.amazon.inventorymanagementsystem.model.Product;
import com.amazon.inventorymanagementsystem.model.ProductResponse;
import com.amazon.inventorymanagementsystem.model.SearchProductsRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    private Map<Integer, Product> productMap = new HashMap<>();
    private ProductResponse productResponse;

    @Override
    public ProductResponse addProducts(Product product) {
        if (productMap.containsKey(product.getProductId())) {
            productResponse = new ProductResponse();
            productResponse.setMessage("Product Already Exists");
            productResponse.setSuccessful(false);
            return productResponse;
        } else {
            productMap.put(product.getProductId(), product);
            productResponse = new ProductResponse();
            productResponse.setProduct(List.of(product));
            productResponse.setMessage("Product Added Successfully");
            productResponse.setSuccessful(true);
            return productResponse;
        }
    }

    @Override
    public ProductResponse deleteProduct(int productId) {
        if (!productMap.containsKey(productId)) {
            productResponse = new ProductResponse();
            productResponse.setSuccessful(false);
            productResponse.setMessage("Product Does Not Exist. Deletion Failed");
            return productResponse;
        } else {
            Product deletedProduct = productMap.remove(productId);
            productResponse = new ProductResponse();
            productResponse.setProduct(List.of(deletedProduct));
            productResponse.setSuccessful(true);
            productResponse.setMessage("Deleted Successfully");
            return productResponse;
        }
    }

    @Override
    public ProductResponse updateProduct(Product product) {
        if (!productMap.containsKey(product.getProductId())) {
            productResponse = new ProductResponse();
            productResponse.setSuccessful(false);
            productResponse.setMessage("Product Does Not Exist. Updation Failed");
            return productResponse;
        } else {
            Product updatedProduct = productMap.replace(product.getProductId(), product);
            productResponse = new ProductResponse();
            productResponse.setProduct(List.of(updatedProduct));
            productResponse.setSuccessful(true);
            productResponse.setMessage("Updated Successfully");
            return productResponse;
        }
    }

    @Override
    public ProductResponse searchProducts(List<SearchProductsRequest> searchProductsRequest) {
        Set<Product> finalRetrievedProductSet = new HashSet<>();
        for (SearchProductsRequest request : searchProductsRequest) {
            List<Product> retrievedProducts;
            if ("name".equalsIgnoreCase(request.getSearchCriteria())) {
                retrievedProducts = productMap.values().stream().filter(product -> product.getProductName().equalsIgnoreCase(request.getSearchValue())).toList();
            } else if ("category".equalsIgnoreCase(request.getSearchCriteria())) {
                retrievedProducts = productMap.values().stream().filter(product -> product.getProductCategory().equalsIgnoreCase(request.getSearchValue())).toList();
            } else if ("brand".equalsIgnoreCase(request.getSearchCriteria())) {
                retrievedProducts = productMap.values().stream().filter(product -> product.getProductBrand().equalsIgnoreCase(request.getSearchValue())).toList();
            } else {
                productResponse = new ProductResponse();
                productResponse.setMessage("Invalid Search Criteria.");
                productResponse.setSuccessful(false);
                return productResponse;
            }
            finalRetrievedProductSet.addAll(retrievedProducts);
        }

        productResponse = new ProductResponse();
        productResponse.setMessage(finalRetrievedProductSet.size()>0?"Product Search Successful.":"No Products found");
        productResponse.setSuccessful(finalRetrievedProductSet.size()>0?true:false);
        productResponse.setProduct(finalRetrievedProductSet.stream().toList());
        return productResponse;
    }
}
