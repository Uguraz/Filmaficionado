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
    private Media film;

    private MovieDao MovieDao = new MovieDaoImpl();
    private CategoryDao CategoryDao = new CategoryDaoImpl();
    private CatMovieDao CatMovieDao = new CatMovieDaoImpl();



    //Sørger for at vores listviews bliver opdateret med vores objekter når vi starter programmet
    @FXML
    public void initialize() {
        refreshMovieLV();
        refreshCategoryLV();
        tilføjUnønsket();
    }

    //Opdaterer vores listview for film
    private void refreshMovieLV() {
        filmLV.getItems().clear();
        System.out.println(MovieDao.getAllMovies());
        List<Movie> movies = MovieDao.getAllMovies();
        for (Movie movie : movies) {
            filmLV.getItems().add(movie);
        }
    }

    //Opdaterer vores listview for kategorier
    private void refreshCategoryLV() {
       catLV.getItems().clear();
       System.out.println(CategoryDao.getAllCategories());
       List<Category> categories = CategoryDao.getAllCategories();
       for (Category category : categories) {
           catLV.getItems().add(category);
       }
   }

    //Gør så vi kan se film der er i kategorier når vi klikker på en kategori
    public void showMovies(javafx.scene.input.MouseEvent mouseEvent) {
        fcLV.getItems().clear();
        Category category = (Category) catLV.getSelectionModel().getSelectedItem();
        List<Movie> Movies = CatMovieDao.getAllCatMovies(category);
        for (Movie movie : Movies) {
            fcLV.getItems().add(movie);
        }
    }

    //Gør så vi kan søge på en film efter navn
    @FXML
    void SøgMovie(ActionEvent event) {

        filmLV.getItems().clear();
        System.out.println(MovieDao.SøgMovie(søgeFelt.getText()));
        List<Movie> movies = MovieDao.SøgMovie(søgeFelt.getText());
        for (Movie movie : movies) {
            filmLV.getItems().add(movie);
        }
    }

    //Sørger for vi kan slette en kategori og opdaterer databasen
    @FXML
    void fjernCat(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Delete category");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Label confirm = new Label("Deleting category will not delete any movies");
        dialog.getDialogPane().setContent(confirm);
        Optional<ButtonType> button = dialog.showAndWait();

        if (button.get() == ButtonType.OK)
            try {
                ObservableList<Integer> chosenIndex = catLV.getSelectionModel().getSelectedIndices();
                if (chosenIndex.size() == 0)
                    System.out.println("Deleted");
                    for (Object index : chosenIndex) {
                        Category c = (Category) catLV.getItems().get((int) index);
                        catLV.getItems().remove(catLV.getSelectionModel().getSelectedItem());

                        CategoryDao.deleteCategory(c);
                        refreshCategoryLV();

                    }

            } catch (Exception e) {
                System.out.println("Something went wrong");
            }
    }

    //Gør så vi kan fjerne en film fra en kategori og opdaterer databasen
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

    //Gør så vi kan fjerne en film og opdaterer databasen
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
                        Movie m = (Movie) filmLV.getItems().get((int) index);
                        filmLV.getItems().remove(filmLV.getSelectionModel().getSelectedItem());

                        MovieDao.deleteMovie(m);
                        refreshMovieLV();
                    }

            } catch (Exception e) {
                System.out.println("Something went wrong");
            }
    }

    //Gør så de film der ikke er set i 2 år eller har rating på mindre end 3 kommer ned i uønsketLV
    @FXML
    void tilføjUnønsket() {
        uønsketLV.getItems().clear();
        System.out.println(MovieDao.getOldMovies());
        List<Movie> movies = MovieDao.getOldMovies();
        for (Movie movie : movies) {
            uønsketLV.getItems().add(movie);
        }
    }

    //Gør så vi kan slette en film fra uønsket listen og opdaterer i database
    @FXML
    void fjernUønsket(ActionEvent event) {
        try {
            ObservableList<Integer> chosenIndex = uønsketLV.getSelectionModel().getSelectedIndices();
            if (chosenIndex.size() == 0)
                System.out.println("Choose a movie");
            else
                for (Object index : chosenIndex) {
                    Movie m = (Movie) uønsketLV.getItems().get((int) index);
                    uønsketLV.getItems().remove(uønsketLV.getSelectionModel().getSelectedItem());

                    MovieDao.deleteMovie(m);
                    refreshMovieLV();
                }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }

    //Gør så vi kan få fat i filelink til en film
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

    //Gør så vi kan få fat i et navn til film
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

    //Gør så vi kan få fat i vores ID til film
    private int getSelectedMovieId() {
        ObservableList<Integer> chosenIndex1 = filmLV.getSelectionModel().getSelectedIndices();
        if (chosenIndex1.size() != 0) {
            for (Object index : chosenIndex1) {
                Movie m = (Movie) filmLV.getItems().get((int) index);
                return m.getMovieId();
            }
        }
        return 0;
    }

    //Redigerer en rating for en film
    @FXML
    void RedRating(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.setTitle("Rediger rating dialog");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField RatingTF = new TextField();


        Label RatingLabel = new Label();
        RatingLabel.setText("Enter Rating:");


        VBox box = new VBox(RatingLabel, RatingTF);
        dialog.getDialogPane().setContent(box);


        Movie selectedMovie = filmLV.getSelectionModel().getSelectedItem();
        RatingTF.setText(selectedMovie.getRating().toString());

        Optional<ButtonType> ok = dialog.showAndWait();
        if (ok.get() == ButtonType.OK)
        MovieDao.redigerMovie(getSelectedMovieId(), RatingTF.getText());
        refreshMovieLV();
        tilføjUnønsket();

        RatingTF.clear();
    }


    //Sorterer vores navne på film i en alfabetisk orden hvor vi starter fra a
    @FXML
    void navnAsc(ActionEvent event) {
        filmLV.getItems().clear();
        System.out.println(MovieDao.SorterTitelMovieAsc());
        List<Movie> movies = MovieDao.SorterTitelMovieAsc();
        for (Movie movie : movies) {
            filmLV.getItems().add(movie);
        }
    }

    //Sorterer vores navne på film i en alfabetisk orden hvor vi starter fra å
    @FXML
    void navnDesc(ActionEvent event) {
        filmLV.getItems().clear();
        System.out.println(MovieDao.SorterTitelMovieDesc());
        List<Movie> movies = MovieDao.SorterTitelMovieDesc();
        for (Movie movie : movies) {
            filmLV.getItems().add(movie);
        }
    }

    //Sorterer vores rating på film fra 1-10
    @FXML
    void ratingAsc(ActionEvent event) {
        filmLV.getItems().clear();
        System.out.println(MovieDao.SorterRatingMovieAsc());
        List<Movie> movies = MovieDao.SorterRatingMovieAsc();
        for (Movie movie : movies) {
            filmLV.getItems().add(movie);
        }
    }

    // Sorterer vores rating på film fra 10-1
    @FXML
    void ratingDesc(ActionEvent event) {
        filmLV.getItems().clear();
        System.out.println(MovieDao.SorterRatingMovieDesc());
        List<Movie> movies = MovieDao.SorterRatingMovieDesc();
        for (Movie movie : movies) {
            filmLV.getItems().add(movie);
        }
    }

    //Sætter vores media op så vi kan afspille med mediaplayer i vores mediaview og opdaterer diverse listviews
    @FXML
    void playKnap(ActionEvent event) throws IOException {


            film = new Media(String.valueOf(getClass().getResource(getSelectedFileLink())));
            mediaPlayer = new MediaPlayer(film);
            visFilm.setMediaPlayer(mediaPlayer);
            mediaPlayer.seek(mediaPlayer.getStartTime());
            Playing.setText(getSelectedName());
            afspiller = true;
            mediaPlayer.play();
            MovieDao.opdaterDato(getSelectedMovieId());
            tilføjUnønsket();
            refreshMovieLV();
        }

    //Sørger for vi kan stoppe med at afspille og lukker mediaplayeren
    @FXML
    void stopAfspil(ActionEvent event) throws IOException {
        if (afspiller)
            mediaPlayer.stop();
            afspiller = false;
            mediaPlayer.dispose();
    }

    //Sætter dialog op hvor vi kan oprette en kategori og gemmer i database
    @FXML
    void tilføjCat(ActionEvent event) throws IOException{
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.setTitle("New category dialog");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField categoryTF = new TextField();
        Label nameLabel = new Label();
        nameLabel.setText("Enter category name:");
        VBox box = new VBox(nameLabel, categoryTF);
        dialog.getDialogPane().setContent(box);

        Optional<ButtonType> ok = dialog.showAndWait();
        if (ok.get() == ButtonType.OK)

        CategoryDao.saveCategory(categoryTF.getText());
        refreshCategoryLV();

        categoryTF.clear();
    }

    //Tilføjer en film til en kategori og opdaterer i database
    @FXML
    void tilføjFC(ActionEvent event) {
        ObservableList<Integer> chosenIndex = filmLV.getSelectionModel().getSelectedIndices();
        if (chosenIndex.size() != 0) {
            ObservableList<Integer> chosenIndex1 = catLV.getSelectionModel().getSelectedIndices();
            if (chosenIndex1.size() == 0)
                System.out.println("Choose a category");
            else
                for (Object index : chosenIndex) {
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

    //Tilføjer en film via dialog og opdaterer i database
    @FXML
    void tilføjFilm(ActionEvent event)  throws IOException {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.setTitle("New movie dialog");
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


