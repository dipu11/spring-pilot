package com.example.module.model;

import lombok.Data;

/**
 * Created by DIPU on 8/13/20
 */


@Data
public class TestModel {

    private String name;
    private int age;

    public TestModel()
    {
        this.name="default-value";
        this.age=15;
    }
    public TestModel(String name, int age)
    {
        this.name=name;
        this.age=age;
    }

}
