package com.example.movieplay;

//Category klassen gør at vi kan få oprettet vores kategori objekter.
//Indeholder samtidig informationer som vi kan hente om vores kategorier.
public class Category {
    //Category attributer
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

