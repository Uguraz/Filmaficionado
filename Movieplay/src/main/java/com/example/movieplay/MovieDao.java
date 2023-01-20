package com.example.movieplay;

import java.util.List;

public interface MovieDao {

    public void saveMovie(String Name, String Rating, String RatingIMDB, String Filelink, String Lastview); //Laver en film i databasen

    public List<Movie> getAllMovies(); // Indlæs film fra databasen

    public void deleteMovie(Movie movie); //Sletter film fra database

    public  List<Movie> getOldMovies(); //Finder alle de film der ikke er blevet set i 2 år eller har rating på laverer end 3

    public void opdaterDato (int MovieId); //Opdaterer dato når man har set en film, til current dato

    public List<Movie> SorterTitelMovieAsc(); //Sorterer titel i vores database alfabetisk oppefra

    public List<Movie> SorterTitelMovieDesc(); //Sorterer titel i vores database alfabetisk nedefra

    public List<Movie> SorterRatingMovieAsc(); //Sorterer rating i vores database fra 1 til 10

    public List<Movie> SorterRatingMovieDesc(); // Sorterer rating i vores database fra 10 til 1

    public List<Movie> SøgMovie(String movie); //Man kan søge efter en film titel i søgefeltet

    public void redigerMovie(int MovieId, String Rating); // Sørger for vi kan redigerer en rating på en film
}

