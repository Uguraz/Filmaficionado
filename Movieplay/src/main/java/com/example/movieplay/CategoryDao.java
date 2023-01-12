package com.example.movieplay;

import com.example.movieplay.Category;

import java.util.List;

public interface CategoryDao {

    public void saveCategory(String Name);
    public void deleteCategory(Category category);

    public List<Category> getAllCategories(); // Read all playlists

    // TODO the other CRUD methods are placed here

}