package com.example.movieplay;

import java.util.List;

public interface CatMovieDao {

    public void addCatMovie(int Favourite, int MovieId, int CategoryId); //Tilføjer en film til en kategori


    public void deleteCatMovie(Movie movie); //Sletter en film fra en kategori


    List<Movie> getAllCatMovies(Category category); //Sørger for at vi kan se alle vores film i kategorierne


}
