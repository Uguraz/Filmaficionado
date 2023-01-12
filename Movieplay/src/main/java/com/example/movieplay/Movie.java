package com.example.movieplay;

import java.util.Date;

public class Movie {

    private int MovieId;
    private String Name;
    private Double Rating;

    private Double RatingIMDB;
    private String FileLink;
    private String Lastview;



    public String toString() { return Name + "          " + Rating + "          " + RatingIMDB + "          " + FileLink + "          " + Lastview;}


    public Movie(int MovieId, String Name, Double Rating, Double RatingIMDB, String Filelink, String Lastview) {
        this.MovieId = MovieId;
        this.Name = Name;
        this.Rating = Rating;
        this.RatingIMDB = RatingIMDB;
        this.FileLink = Filelink;
        this.Lastview = Lastview;

    }

    public int getMovieId() { return this.MovieId;
    }

    public String getName() { return this.Name;
    }

    public Double getRating() { return this.Rating;
    }

    public Double getRatingIMDB() { return this.RatingIMDB;
    }


    public String getFileLink() { return this.FileLink;
    }

    public String getLastview() { return this.Lastview;
    }

    }


