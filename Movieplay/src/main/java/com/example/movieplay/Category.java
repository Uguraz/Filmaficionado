package com.example.movieplay;

public class Category {
    private int CategoryId;
    private String Name;


    //Konstruktøren
    public Category(int CategoryId, String Name){
        this.CategoryId = CategoryId;
        this.Name = Name;
    }

    //Sørger for vi kun ser navn i kategori listview
    public String toString() { return Name;}

    //Giver os en ID for kategorien
    public int getCategoryId() { return CategoryId; }

    //Giver os et navn for kategorien
    public String getName() { return Name;  }
}

