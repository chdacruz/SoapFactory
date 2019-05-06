package com.example.soapfactory;

public class Users {

    private String email, id, permission;

    public Users() {
    }


    public Users(String email, String id, String permission) {
        this.email = email;
        this.id = id;
        this.permission = permission;
    }

    public Users(String email, String id) {
        this.email = email;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
