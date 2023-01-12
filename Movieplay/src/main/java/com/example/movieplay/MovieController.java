package com.example.movieplay;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.control.Label;


import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class MovieController {


    @FXML
    private Label Playing;

    @FXML
    private ListView<Category> catLV;

    @FXML
    private ListView<Movie> fcLV;

    @FXML
    private ListView<Movie> filmLV;
    @FXML
    private TextField søgeFelt;

    @FXML
    private ListView<Movie> uønsketLV;
    @FXML
    private MediaView visFilm;


    private boolean afspiller = false;

    private MediaPlayer mediaPlayer;
    Media film;
    private MediaView mediaView;
    private MovieDao MovieDao = new MovieDaoImpl();
    private CategoryDao CategoryDao = new CategoryDaoImpl();
    private CatMovieDao CatMovieDao = new CatMovieDaoImpl();



    @FXML
    public void initialize() {
        refreshMovieLV();
        refreshCategoryLV();
        tilføjUnønsket();




    }

    // UPLOADS MOVIES TO MOVIE LISTVIEW FROM START OF PROGRAM
    private void refreshMovieLV() {
        filmLV.getItems().clear();
        System.out.println(MovieDao.getAllMovies());
        List<Movie> movies = MovieDao.getAllMovies();
        for (Movie movie : movies) {
            filmLV.getItems().add(movie);
        }
    }

    // UPLOADS CATEGORIES TO CATEGORY LISTVIEW FROM START OF PROGRAM
   private void refreshCategoryLV() {
       catLV.getItems().clear();
       System.out.println(CategoryDao.getAllCategories());
       List<Category> categories = CategoryDao.getAllCategories();
       for (Category category : categories) {
           catLV.getItems().add(category);
       }
   }

    public void showMovies(javafx.scene.input.MouseEvent mouseEvent) {
        System.out.println("fcLV mouse event works");
        fcLV.getItems().clear();
        Category category = (Category) catLV.getSelectionModel().getSelectedItem();
        List<Movie> Movies = CatMovieDao.getAllCatMovies(category);
        for (Movie movie : Movies) {
            fcLV.getItems().add(movie);
        }
    }

        @FXML
        void SøgMovie(ActionEvent event) {
        filmLV.getItems().clear();
        System.out.println(MovieDao.SøgMovie(søgeFelt.getText()));
        List<Movie> movies = MovieDao.SøgMovie(søgeFelt.getText());
        for (Movie movie : movies) {
            filmLV.getItems().add(movie);
        }
    }



    @FXML
    void fjernCat(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("delete category");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Label confirm = new Label("Deleting category will not delete any movies");
        dialog.getDialogPane().setContent(confirm);
        Optional<ButtonType> button = dialog.showAndWait();

        if (button.get() == ButtonType.OK)
            try {
                ObservableList<Integer> chosenIndex = catLV.getSelectionModel().getSelectedIndices();
                if (chosenIndex.size() == 0)
                    System.out.println("Choose a movie");
                else
                    for (Object index : chosenIndex) {
                        System.out.println("You chose " + catLV.getSelectionModel().getSelectedItem());
                        Category c = (Category) catLV.getItems().get((int) index);
                        catLV.getItems().remove(catLV.getSelectionModel().getSelectedItem());

                        CategoryDao.deleteCategory(c);
                    }

            } catch (Exception e) {
                System.out.println("Something went wrong");
            }
    }

    @FXML
    void fjernFC(ActionEvent event) {
        ObservableList<Integer> chosenIndex = fcLV.getSelectionModel().getSelectedIndices();
        if (chosenIndex.size() != 0) {
            for (Object index : chosenIndex) {
                Movie m = (Movie) fcLV.getItems().get((int) index);
                Category category = (Category) catLV.getSelectionModel().getSelectedItem();
                CatMovieDao.deleteCatMovie(m);
                List<Movie> movies = CatMovieDao.getAllCatMovies(category);
                fcLV.getItems().remove(m);
            }
        }
    }


    @FXML
    void fjernFilm(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Delete Movie");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Label confirm = new Label("Are you sure you want to delete?");
        dialog.getDialogPane().setContent(confirm);
        Optional<ButtonType> button = dialog.showAndWait();

        if (button.get() == ButtonType.OK)
            try {
                ObservableList<Integer> chosenIndex = filmLV.getSelectionModel().getSelectedIndices();
                if (chosenIndex.size() == 0)
                    System.out.println("Choose a movie");
                else
                    for (Object index : chosenIndex) {
                        System.out.println("You chose " + filmLV.getSelectionModel().getSelectedItem());
                        Movie m = (Movie) filmLV.getItems().get((int) index);
                        filmLV.getItems().remove(filmLV.getSelectionModel().getSelectedItem());

                        MovieDao.deleteMovie(m);
                    }

            } catch (Exception e) {
                System.out.println("Something went wrong");
            }
    }

    @FXML
    void tilføjUnønsket() {
        uønsketLV.getItems().clear();
        System.out.println(MovieDao.getOldMovies());
        List<Movie> movies = MovieDao.getOldMovies();
        for (Movie movie : movies) {
            uønsketLV.getItems().add(movie);
        }
    }


    @FXML
    void fjernUønsket(ActionEvent event) {
        try {
            ObservableList<Integer> chosenIndex = uønsketLV.getSelectionModel().getSelectedIndices();
            if (chosenIndex.size() == 0)
                System.out.println("Choose a movie");
            else
                for (Object index : chosenIndex) {
                    System.out.println("You chose " + uønsketLV.getSelectionModel().getSelectedItem());
                    Movie m = (Movie) uønsketLV.getItems().get((int) index);
                    uønsketLV.getItems().remove(uønsketLV.getSelectionModel().getSelectedItem());

                    MovieDao.deleteMovie(m);
                    refreshMovieLV();
                }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }

    private String getSelectedFileLink() {
        ObservableList<Integer> chosenIndex1 = filmLV.getSelectionModel().getSelectedIndices();
        if (chosenIndex1.size() != 0) {
            for (Object index : chosenIndex1) {
                Movie m = (Movie) filmLV.getItems().get((int) index);
                return m.getFileLink();
            }
        }
        ObservableList<Integer> chosenIndex2 = fcLV.getSelectionModel().getSelectedIndices();
        if (chosenIndex2.size() != 0) {
            for (Object index : chosenIndex2) {
                Movie m1 = (Movie) fcLV.getItems().get((int) index);
                return m1.getFileLink();
            }
        }
        return null;
    }


    private String getSelectedName() {
        ObservableList<Integer> chosenIndex1 = filmLV.getSelectionModel().getSelectedIndices();
        if (chosenIndex1.size() != 0) {
            for (Object index : chosenIndex1) {
                Movie m = (Movie) filmLV.getItems().get((int) index);
                return m.getName();
            }
        }
        ObservableList<Integer> chosenIndex2 = fcLV.getSelectionModel().getSelectedIndices();
        if (chosenIndex2.size() != 0) {
            for (Object index : chosenIndex2) {
                Movie Name = (Movie) fcLV.getItems().get((int) index);
                return Movie.class.getName();
            }
        }
        return null;
    }

    @FXML
    void navnAsc(ActionEvent event) {
        filmLV.getItems().clear();
        System.out.println(MovieDao.SorterTitelMovieAsc());
        List<Movie> movies = MovieDao.SorterTitelMovieAsc();
        for (Movie movie : movies) {
            filmLV.getItems().add(movie);
        }
    }
    @FXML
    void navnDesc(ActionEvent event) {
        filmLV.getItems().clear();
        System.out.println(MovieDao.SorterTitelMovieDesc());
        List<Movie> movies = MovieDao.SorterTitelMovieDesc();
        for (Movie movie : movies) {
            filmLV.getItems().add(movie);
        }
    }
    @FXML
    void ratingAsc(ActionEvent event) {
        filmLV.getItems().clear();
        System.out.println(MovieDao.SorterRatingMovieAsc());
        List<Movie> movies = MovieDao.SorterRatingMovieAsc();
        for (Movie movie : movies) {
            filmLV.getItems().add(movie);
        }
    }

    @FXML
    void ratingDesc(ActionEvent event) {
        filmLV.getItems().clear();
        System.out.println(MovieDao.SorterRatingMovieDesc());
        List<Movie> movies = MovieDao.SorterRatingMovieDesc();
        for (Movie movie : movies) {
            filmLV.getItems().add(movie);
        }
    }

    @FXML
    void playKnap(ActionEvent event) throws IOException {

            film = new Media(String.valueOf(getClass().getResource(getSelectedFileLink())));
            mediaPlayer = new MediaPlayer(film);
            mediaView = new MediaView(mediaPlayer);
            visFilm.setMediaPlayer(mediaPlayer);
            mediaPlayer.seek(mediaPlayer.getStartTime());
            Playing.setText(getSelectedName());
            afspiller = true;
            mediaPlayer.play();
        }


    @FXML
    void stopAfspil(ActionEvent event) throws IOException {
        if (afspiller)
            mediaPlayer.stop();
            afspiller = false;
    }



    @FXML
    void tilføjCat(ActionEvent event) throws IOException{
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.setTitle("new category dialog");
        dialog.setHeaderText("New Category");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField categoryTF = new TextField();
        Label nameLabel = new Label();
        nameLabel.setText("Enter category name:");
        VBox box = new VBox(nameLabel, categoryTF);
        dialog.getDialogPane().setContent(box);

        Optional<ButtonType> ok = dialog.showAndWait();
        if (ok.get() == ButtonType.OK)
            System.out.println("Category name = " + categoryTF.getText());


        CategoryDao.saveCategory(categoryTF.getText());
        refreshCategoryLV();

        categoryTF.clear();
    }

    @FXML
    void tilføjFC(ActionEvent event) {
        ObservableList<Integer> chosenIndex = filmLV.getSelectionModel().getSelectedIndices();
        if (chosenIndex.size() != 0) {
            ObservableList<Integer> chosenIndex1 = catLV.getSelectionModel().getSelectedIndices();
            if (chosenIndex1.size() == 0)
                System.out.println("Choose a category");
            else
                for (Object index : chosenIndex) {
                    System.out.println("You chose " + filmLV.getSelectionModel().getSelectedItem());
                    Movie m = (Movie) filmLV.getItems().get((int) index);
                    Category category = (Category) catLV.getSelectionModel().getSelectedItem();
                    CatMovieDao.addCatMovie(0, m.getMovieId(), category.getCategoryId());
                    List<Movie> movies = CatMovieDao.getAllCatMovies(category);
                    fcLV.getItems().clear();
                    for (Movie movie : movies){
                        fcLV.getItems().add(movie);

                    }
                }
        }
        else  System.out.println("Choose a Movie");
    }


    @FXML
    void tilføjFilm(ActionEvent event)  throws IOException {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.setTitle("new movie dialog");
        dialog.setHeaderText("Add a new Movie");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField NameTF = new TextField();
        TextField RatingTF = new TextField();
        TextField RatingIMDBTF = new TextField();
        TextField FilelinkTF = new TextField();
        TextField LastviewTF = new TextField();


        Label NameLabel = new Label();
        NameLabel.setText("Enter Movie name:");
        Label RatingLabel = new Label();
        RatingLabel.setText("Enter Rating:");
        Label RatingIMDBLabel = new Label();
        RatingIMDBLabel.setText("Enter RatingIMDB:");
        Label FilelinkLabel = new Label();
        FilelinkLabel.setText("Enter FileLink:");
        Label LastviewLabel = new Label();
        LastviewLabel.setText("Enter Lastview:");



        VBox box = new VBox(NameLabel, NameTF, RatingLabel, RatingTF, RatingIMDBLabel, RatingIMDBTF, FilelinkLabel, FilelinkTF, LastviewLabel, LastviewTF);
        dialog.getDialogPane().setContent(box);

        Optional<ButtonType> ok = dialog.showAndWait();
        if (ok.get() == ButtonType.OK)
            System.out.println("Name = " + NameTF.getText() + " Rating = " + RatingTF.getText() + " RatingIMDB = "
                    + RatingIMDBTF.getText() + " Filelink = " + FilelinkTF.getText() + " Lastview = " + LastviewTF.getText());

        MovieDao.saveMovie(NameTF.getText(), RatingTF.getText(), RatingIMDBTF.getText(), FilelinkTF.getText(), LastviewTF.getText());
        refreshMovieLV();
        tilføjUnønsket();

        NameTF.clear();
        RatingTF.clear();
        RatingIMDBTF.clear();
        FilelinkTF.clear();
        LastviewTF.clear();


    }
    }


