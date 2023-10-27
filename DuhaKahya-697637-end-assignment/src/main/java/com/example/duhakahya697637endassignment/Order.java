package com.example.duhakahya697637endassignment;

import java.util.List;

public class Order {
    private String date;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private List<Product> products;

    public Order(String date, String firstName, String lastName, String email, String phoneNumber, List<Product> products) {
        this.date = date;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.products = products;
    }


    public List<Product> getOrderedProducts() {
        return products;
    }

}
