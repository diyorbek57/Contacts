package com.b12.contacts.models;

import java.io.Serializable;

public class Contact implements Serializable {
    private String name, number;
    private int image, viewType;

    public Contact() {

    }

    public Contact(String name, String number, int image, int viewType) {
        this.name = name;
        this.number = number;
        this.image = image;
        this.viewType = viewType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
