package com.example.waterdeliveryapp;

import java.io.Serializable;

public class SelectedProduct implements Serializable {
    private String name;
    private Integer count;


    public SelectedProduct(String name, Integer count) {
        this.count = count;
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
