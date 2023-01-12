package com.example.movieplay;

import com.example.movieplay.Category;

import java.util.List;

public interface CategoryDao {

    public void saveCategory(String Name); //Tilføjer en ny kategori
    public void deleteCategory(Category category); //Sletter en kategori

    public List<Category> getAllCategories(); //Indlæser alle kategorier

    // TODO the other CRUD methods are placed here

}