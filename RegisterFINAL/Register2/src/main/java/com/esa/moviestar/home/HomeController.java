package com.esa.moviestar.home;

import com.esa.moviestar.movie_view.Film;
import com.esa.moviestar.movie_view.FilmCardController;
import com.esa.moviestar.movie_view.WindowCardController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Vector;

public class HomeController {
    @FXML
    private VBox body;
    @FXML
    private ScrollPane root;
    @FXML
    private VBox scrollViewContainer;
    public ScrollView scrollView3;
    private Carousel carousel;
    private final ResourceBundle resourceBundle =ResourceBundle.getBundle("com.esa.moviestar.images.svg-paths.general-svg");
    private final Color foreColor = Color.rgb(240, 240, 240);
    private final Color backgroundColor = Color.rgb(15, 15, 15);

    public void initialize() {
        List<Node> listc0 = new Vector<>();
        try {
        for (int i = 0; i < 5; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esa/moviestar/movie_view/WindowCard.fxml"),resourceBundle);
            Node body = loader.load();
            WindowCardController windowCardController = loader.getController();
            windowCardController.setContent( new Film(1, "ciao","ma sai che forse non è male come ti ho strutturato", true , "",4.5,new Image(Objects.requireNonNull(getClass().getResource("/com/esa/moviestar/images/untitled.png")).toExternalForm())));
            windowCardController.getPlayButton().setOnMouseClicked(e->cardClicked(windowCardController.getCardId()));
            windowCardController.getInfoButton().setOnMouseClicked(e->cardClicked(windowCardController.getCardId()));
            listc0.add(body);

        }
            carousel = new Carousel();
            carousel.getItems().addAll(listc0);
            carousel.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/esa/moviestar/styles/Carousel.css")).toExternalForm());
            body.getChildren().add(1, carousel);
    } catch (IOException e) {
        System.err.println(e);
    }
        List<Node> listc2 = new Vector<>();
        try {
            for (int i = 0; i < 10; i++) {
                FXMLLoader fxmlLoader= new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/esa/moviestar/movie_view/FilmCard_Vertical.fxml")),resourceBundle);
                Node n = fxmlLoader.load();
                FilmCardController filmCardController = fxmlLoader.getController();
                filmCardController.setContent(new Film(i,"Il tmapiderma","un ragazzo punto da un ragno autistico",true,"1:32",4.5,new Image(Objects.requireNonNull(getClass().getResource("/com/esa/moviestar/images/Gemini_Generated.jpeg")).toExternalForm())));
                n.setOnMouseClicked(e->cardClicked(filmCardController.getCardId()));
                listc2.add(n);
            }
            ScrollView scrollView2 = new ScrollView("hello", Color.TRANSPARENT, foreColor,backgroundColor);
            scrollView2.setContent(listc2);
            scrollView2.setSpacing(16);
            scrollView2.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/esa/moviestar/styles/ScrollView.css")).toExternalForm());


        List<Node> listc3 = new Vector<>();
        for (int i = 0; i < 10; i++) {
            FXMLLoader fxmlLoader= new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/esa/moviestar/movie_view/FilmCard_Horizontal.fxml")),resourceBundle);
            Node n = fxmlLoader.load();
            FilmCardController filmCardController = fxmlLoader.getController();
            filmCardController.setContent(new Film(1,"Il tmapiderma","un ragazzo punto da un ragno autistico",false,"1:32",4.5,new Image(Objects.requireNonNull(getClass().getResource("/com/esa/moviestar/images/untitled.png")).toExternalForm())));
            n.setOnMouseClicked(e->cardClicked(filmCardController.getCardId()));
            listc3.add(n);
        }
        scrollView3 = new ScrollView("hello", Color.TRANSPARENT, foreColor,backgroundColor);
        scrollView3.setContent(listc3);
            scrollView3.setSpacing(16);
        scrollView3.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/esa/moviestar/styles/ScrollView.css")).toExternalForm());

            scrollViewContainer.getChildren().addAll(scrollView2,scrollView3);//scrollView,,,,scrollView4,scrollView5);
    }catch (IOException e){
            System.err.println("oh fuck");
        }

    }

    public void load(List<List<Node>> content){
        carousel = new Carousel();
        carousel.getItems().addAll(content.getFirst());
        carousel.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/esa/moviestar/styles/Carousel.css")).toExternalForm());
        body.getChildren().add(1, carousel);
    }

    public void cardClicked(int idFilm){
        System.out.println("hellofromcard"+idFilm);
    }


}
