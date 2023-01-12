package com.example.movieplay;
import com.example.movieplay.Movie;
import com.example.movieplay.MovieDao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDaoImpl implements MovieDao {


    private Connection con;  // CONNECTS TO DATABASE

    public MovieDaoImpl() {
        try { con = DriverManager.getConnection("jdbc:sqlserver://LASSE:1433;databaseName=Filmaficionado;userName=sa;password=123456;encrypt=true;trustServerCertificate=true");
        } catch (SQLException e) {
            System.err.println("cannot create connection" + e.getMessage());
        }

        System.out.println("MovieDaoImpl connected to the database... ");
    }

    //Sørger for vi kan gemme en film i databasen
    public void saveMovie(String Name, String Rating, String RatingIMDB, String Filelink, String Lastview) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO MOVIE VALUES(?,?,?,?,?);");
            ps.setString(1, Name);
            ps.setDouble(2, Double.parseDouble(Rating));
            ps.setDouble(3, Double.parseDouble(RatingIMDB));
            ps.setString(4, Filelink);
            ps.setString(5, Lastview);


            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("cannot insert record (saveMovie)");
        }
    }

    //Sørger for vi kan slette en film fra databasen
    public void deleteMovie(Movie movie) {
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM CatMovie WHERE MovieId = ?");
            ps.setInt(1, (movie.getMovieId()));
            ps.executeUpdate();

            PreparedStatement pr = con.prepareStatement("DELETE FROM Movie WHERE MovieId = ?;");
            pr.setInt(1, (movie.getMovieId()));
            pr.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Cannot delete movie");
        }
    }

    //Sørger for vi kan se alle vores film i film listviewet
    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Movie;");
            ResultSet rs = ps.executeQuery();

            Movie movie;
            while (rs.next()) {
                int MovieId = rs.getInt(1);
                String Name = rs.getString(2);
                double Rating = rs.getDouble(3);
                double RatingIMDB = rs.getDouble(4);
                String Filelink = rs.getString(5);
                String Lastview = rs.getString(6);


                movie = new Movie(MovieId, Name, Rating, RatingIMDB, Filelink, Lastview);
                movies.add(movie);
            }

        } catch (SQLException e) {
            System.err.println("cannot access records (MovieDaoImpl)");
        }
        return movies;
    }

    //Sørger for vi kan se hvilke film der ikke er set i mere end 2 år eller har en rating lavere end 3 i vores uønsketLV
        public List<Movie> getOldMovies() {
        List<Movie> movies = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Movie WHERE Rating < 3 OR Lastview < DATEADD(year, -2, GETDATE());");
            ResultSet rs = ps.executeQuery();

            Movie movie;
            while (rs.next()) {
                int MovieId = rs.getInt(1);
                String Name = rs.getString(2);
                double Rating = rs.getDouble(3);
                double RatingIMDB = rs.getDouble(4);
                String Filelink = rs.getString(5);
                String Lastview = rs.getString(6);


                movie = new Movie(MovieId, Name, Rating, RatingIMDB, Filelink, Lastview);
                movies.add(movie);
            }

        } catch (SQLException e) {
            System.err.println("cannot access records (MovieDaoImpl)");
        }
        return movies;
    }

    //Opdaterer vores dato hver gang vi ser en film
    @Override
    public void opdaterDato(int MovieId) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE Movie SET lastview = getDate() WHERE movieID = ?;");

            ps.setInt(1, MovieId);


            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            System.err.println("Kan ikke ændre sidst set dato på denne film " + e.getMessage());
        }
    }

    //Sørger for vi kan sorterer vores navn på film i alfabetisk orden fra a
    public List<Movie> SorterTitelMovieAsc() {
        List<Movie> movies = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Movie ORDER BY Name ASC;");
            ResultSet rs = ps.executeQuery();

            Movie movie;
            while (rs.next()) {
                int MovieId = rs.getInt(1);
                String Name = rs.getString(2);
                double Rating = rs.getDouble(3);
                double RatingIMDB = rs.getDouble(4);
                String Filelink = rs.getString(5);
                String Lastview = rs.getString(6);


                movie = new Movie(MovieId, Name, Rating, RatingIMDB, Filelink, Lastview);
                movies.add(movie);
            }

        } catch (SQLException e) {
            System.err.println("cannot access records (MovieDaoImpl)");
        }
        return movies;
    }

    //Sørger for at vi kan sorterer vores navn på film i alfabetisk orden fra å
    public List<Movie> SorterTitelMovieDesc() {
        List<Movie> movies = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Movie ORDER BY Name DESC;");
            ResultSet rs = ps.executeQuery();

            Movie movie;
            while (rs.next()) {
                int MovieId = rs.getInt(1);
                String Name = rs.getString(2);
                double Rating = rs.getDouble(3);
                double RatingIMDB = rs.getDouble(4);
                String Filelink = rs.getString(5);
                String Lastview = rs.getString(6);


                movie = new Movie(MovieId, Name, Rating, RatingIMDB, Filelink, Lastview);
                movies.add(movie);
            }

        } catch (SQLException e) {
            System.err.println("cannot access records (MovieDaoImpl)");
        }
        return movies;
    }

    //Sorterer vores rating på film fra 1-10
    public List<Movie> SorterRatingMovieAsc() {
        List<Movie> movies = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Movie ORDER BY Rating ASC;");
            ResultSet rs = ps.executeQuery();

            Movie movie;
            while (rs.next()) {
                int MovieId = rs.getInt(1);
                String Name = rs.getString(2);
                double Rating = rs.getDouble(3);
                double RatingIMDB = rs.getDouble(4);
                String Filelink = rs.getString(5);
                String Lastview = rs.getString(6);


                movie = new Movie(MovieId, Name, Rating, RatingIMDB, Filelink, Lastview);
                movies.add(movie);
            }

        } catch (SQLException e) {
            System.err.println("cannot access records (MovieDaoImpl)");
        }
        return movies;
    }

    //Sorterer vores rating på film fra 10-1
    public List<Movie> SorterRatingMovieDesc() {
        List<Movie> movies = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Movie ORDER BY Rating DESC;");
            ResultSet rs = ps.executeQuery();

            Movie movie;
            while (rs.next()) {
                int MovieId = rs.getInt(1);
                String Name = rs.getString(2);
                double Rating = rs.getDouble(3);
                double RatingIMDB = rs.getDouble(4);
                String Filelink = rs.getString(5);
                String Lastview = rs.getString(6);


                movie = new Movie(MovieId, Name, Rating, RatingIMDB, Filelink, Lastview);
                movies.add(movie);
            }

        } catch (SQLException e) {
            System.err.println("cannot access records (MovieDaoImpl)");
        }
        return movies;
    }

    //Sørger for at vi kan søge på en film titel
    public List<Movie> SøgMovie(String movie) {
        List<Movie> movies = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Movie WHERE Name LIKE ?;");
            ps.setString(1, movie);
            ResultSet rs = ps.executeQuery();

            Movie m;
            while (rs.next()) {
                int MovieId = rs.getInt(1);
                String Name = rs.getString(2);
                double Rating = rs.getDouble(3);
                double RatingIMDB = rs.getDouble(4);
                String Filelink = rs.getString(5);
                String Lastview = rs.getString(6);


                m = new Movie(MovieId, Name, Rating, RatingIMDB, Filelink, Lastview);
                movies.add(m);
            }

        } catch (SQLException e) {
            System.err.println("cannot access records (MovieDaoImpl)");
        }
        return movies;
    }

}