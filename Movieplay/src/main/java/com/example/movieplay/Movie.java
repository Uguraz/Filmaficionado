package com.example.movieplay;

    //Movie klassen gør at vi kan få oprettet vores film objekter.
    //Indeholder samtidig informationer som vi kan hente om vores film.
public class Movie {
    //Movie attributter
    private int MovieId;
    private String Name;
    private String Rating;

    private String RatingIMDB;
    private String FileLink;
    private String Lastview;


    //Tager de ting vi gerne vil have vist i vores filmLV
    public String toString() { return Name + "          " + Rating + "          " + RatingIMDB + "          " + FileLink + "          " + Lastview;}

    //Konstruktøren
    public Movie(int MovieId, String Name, String Rating, String RatingIMDB, String Filelink, String Lastview) {
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
    public String getRating() { return this.Rating;
    }
    //Giver os IMDB rating til en film
    public String getRatingIMDB() { return this.RatingIMDB;
    }
    //Giver os filelinket til en film
    public String getFileLink() { return this.FileLink;
    }
    //Giver os lastview til en film
    public String getLastview() { return this.Lastview;
    }
    }


