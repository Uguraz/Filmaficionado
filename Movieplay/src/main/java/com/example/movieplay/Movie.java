package com.example.movieplay;

import java.util.Date;

public class Movie {

    private int MovieId;
    private String Name;
    private Double Rating;

    private Double RatingIMDB;
    private String FileLink;
    private String Lastview;


    //Tager de ting vi gerne vil have vist i vores filmLV
    public String toString() { return Name + "          " + Rating + "          " + RatingIMDB + "          " + FileLink + "          " + Lastview;}

    //Konstruktøren
    public Movie(int MovieId, String Name, Double Rating, Double RatingIMDB, String Filelink, String Lastview) {
        this.MovieId = MovieId;
        this.Name = Name;
        this.Rating = Rating;
        this.RatingIMDB = RatingIMDB;
        this.FileLink = Filelink;
        this.Lastview = Lastview;

    }
    //Giver os id til en film
    public int getMovieId() { return this.MovieId;
    }
    //Giver os navn til en film
    public String getName() { return this.Name;
    }
    //Giver os rating til en film
    public Double getRating() { return this.Rating;
    }
    //Giver os IMDB rating til en film
    public Double getRatingIMDB() { return this.RatingIMDB;
    }
    //Giver os filelinket til en film
    public String getFileLink() { return this.FileLink;
    }
    //Giver os lastview til en film
    public String getLastview() { return this.Lastview;
    }
    }


