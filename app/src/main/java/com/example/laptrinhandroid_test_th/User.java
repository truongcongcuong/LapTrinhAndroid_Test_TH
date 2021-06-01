package com.example.laptrinhandroid_test_th;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String fullName;
    private int age;

    public User() {
    }

    public User(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
