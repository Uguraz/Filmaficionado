package com.example.movieplay;
import com.example.movieplay.Category;
import com.example.movieplay.CategoryDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao
{


    private Connection con; // CONNECTS TO DATABASE

    public CategoryDaoImpl() {
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://LASSE:1433;databaseName=Filmaficionado;userName=sa;password=123456;encrypt=true;trustServerCertificate=true");
        } catch (SQLException e) {
            System.err.println("cannot create connection (CategoryDaoImpl)" + e.getMessage());
        }

        System.out.println("CategoryDaoImpl connected to the database... ");
    }

    //Gemmer en kategori i vores database
    public void saveCategory(String Name) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO CATEGORY VALUES(?);");
            ps.setString(1, Name);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("cannot insert record: " + e.getMessage());
        }
    }

    //Sletter en kategori fra databasen
    public void deleteCategory(Category category) {
        try {
            PreparedStatement pr = con.prepareStatement("DELETE FROM CatMovie WHERE categoryId = ?;");
            pr.setInt(1, (category.getCategoryId()));
            pr.executeUpdate();

            PreparedStatement ps = con.prepareStatement("DELETE FROM Category WHERE categoryId = ?;");
            ps.setInt(1, (category.getCategoryId()));
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("cannot delete category");
        }
    }

    //Sørger for at vi kan få alle vores kategorier op i listview
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Category;");
            ResultSet rs = ps.executeQuery();

            Category category;
            while (rs.next()) {
                int categoryId = rs.getInt(1);
                String Name = rs.getString(2);

                category = new Category(categoryId, Name);
                categories.add(category);
            }

        } catch (SQLException e) {
            System.err.println("cannot access records (CategoryDaoImpl)");

        }
        return categories;
    }
}

