package com.example.soapfactory;

public class PostData {

    private String name, price, type, description;

    public PostData(){

    }

    public PostData(String name, String price, String type, String description) {
        this.name = name;
        this.price = price;
        this.type = type;
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
