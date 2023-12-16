package com.amazon.inventorymanagementsystem.model;

import org.springframework.context.ApplicationEvent;

public class AddProductEvent extends ApplicationEvent {
    private final String productName;

    public String getProductName() {
        return productName;
    }

    public AddProductEvent(Object source, String productName) {
        super(source);
        this.productName = productName;
    }
}
