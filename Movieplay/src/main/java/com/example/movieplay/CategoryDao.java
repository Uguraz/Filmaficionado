package com.example.movieplay;


import java.util.List;

public interface CategoryDao {

    public void saveCategory(String Name); //Tilføjer en ny kategori til databasen
    public void deleteCategory(Category category); //Sletter en kategori fra databasen

    public List<Category> getAllCategories(); //Indlæser alle kategorier fra databasen


}