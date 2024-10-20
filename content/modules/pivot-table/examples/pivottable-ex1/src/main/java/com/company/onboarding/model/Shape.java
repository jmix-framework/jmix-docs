package com.company.onboarding.model;

// tag::shape[]
public class Shape {

    private Long id;
    private String shape;
    private String color;
    private String size;

    public Shape(Long id, String shape, String color, String size) {
        this.id = id;
        this.shape = shape;
        this.color = color;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
// end::shape[]
