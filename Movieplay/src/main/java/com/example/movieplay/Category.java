package com.example.movieplay;

public class Category {
    private int CategoryId;
    private String Name;


    public Category(int CategoryId, String Name){
        this.CategoryId = CategoryId;
        this.Name = Name;
    }

    public String toString() { return Name;}

    public int getCategoryId() { return CategoryId; }

    public String getName() { return Name;  }
}

