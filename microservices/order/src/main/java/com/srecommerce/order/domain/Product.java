package com.srecommerce.order.domain;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private String description;
    private double price;
    private boolean available;
    private int quantity;
}
