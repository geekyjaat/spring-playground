package com.example.demo.model;

public class AreaRequest {
    private String type;
    private String radius;
    private String width;
    private String height;

    public AreaRequest() {
    }

    public AreaRequest(String type, String radius, String width, String height) {
        this.type = type;
        this.radius = radius;
        this.width = width;
        this.height = height;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
