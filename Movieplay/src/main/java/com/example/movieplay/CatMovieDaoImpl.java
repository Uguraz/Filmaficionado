package com.example.movieplay;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CatMovieDaoImpl implements CatMovieDao {



    private Connection con; // CONNECTS TO DATABASE

    public CatMovieDaoImpl() {
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://LASSE:1433;databaseName=Filmaficionado;userName=sa;password=123456;encrypt=true;trustServerCertificate=true");
        } catch (SQLException e) {
            System.err.println("cannot create connection (CatMovieDaoImpl)" + e.getMessage());
        }

        System.out.println("CatMovieDaoImpl connected to the database... ");
    }

    //Sørger for at vi kan tilføje en film til en kategori
    public void addCatMovie(int Favourite, int MovieId, int categoryId) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO CATMOVIE VALUES(?,?,?);");
            ps.setInt(1, Favourite);
            ps.setInt(2, MovieId);
            ps.setInt(3, categoryId);


            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("cannot insert record (saveMovie)");
        }
    }

    //Sørger for at vi kan slette en film fra en kategori
    public void deleteCatMovie(Movie movie) {
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM CATMOVIE WHERE MovieId = ?;");
            ps.setInt(1, (movie.getMovieId()));

            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Cannot delete movie");
        }
    }

    //Sørger for vi kan få alle vores film på en valgt kategori frem i listview
    @Override
    public List<Movie> getAllCatMovies(Category category) {
        List<Movie> CatMovie = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Movie, Category, CatMovie WHERE " +
                    "Category.categoryId = CatMovie.categoryId AND CatMovie.MovieId = Movie.MovieId AND " +
                    "Category.categoryId = ?;");
            ps.setInt(1, category.getCategoryId());
            ResultSet rs = ps.executeQuery();
            Movie movie;
            while (rs.next()) {
                int MovieId = rs.getInt(1);
                String Name = rs.getString(2);
                Double Rating = rs.getDouble(3);
                Double RatingIMDB = rs.getDouble(4);
                String FileLink = rs.getString(5);
                String Lastview = rs.getString(6);


                movie = new Movie(MovieId, Name, Rating, RatingIMDB, FileLink,  Lastview);
                CatMovie.add(movie);
            }

        } catch (SQLException e) {
            System.err.println("cannot access records (SongDaoImpl)");
        }
        return CatMovie;
    }


}

