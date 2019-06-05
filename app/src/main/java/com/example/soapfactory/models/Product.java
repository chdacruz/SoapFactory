package com.example.soapfactory.models;

public class Product {

    private String name, price, type, description;

    public Product(){ } // Need for firebase?

    public Product(String name, String price, String type, String description) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.description = description;
    }

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }


}
