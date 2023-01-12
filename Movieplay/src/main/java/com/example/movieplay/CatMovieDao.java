package com.example.movieplay;

import java.util.List;

public interface CatMovieDao {

    public void addCatMovie(int Favourite, int MovieId, int CategoryId);


    public void deleteCatMovie(Movie movie);


    List<Movie> getAllCatMovies(Category category);


}
