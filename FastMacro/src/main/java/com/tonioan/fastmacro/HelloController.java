package com.tonioan.fastmacro;


import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.*;

public class HelloController {
    @FXML
    VBox body;
    @FXML
    ScrollPane root;
   @FXML
    VBox scrollViewContainer;
    public void initialize(){
        List<Pane> listc0 = new Vector<>();
        for(int i = 0; i<5;i++){
            FilmCard t = new FilmCard( 1,new Image((Objects.requireNonNull(getClass().getResource("/images/untitled.png"))).toExternalForm()),"Titolo mediamente lungo",i,350,250);
            t.setOnMouseClicked(mouseEvent -> {cardClicked(t.getCardId());});
            listc0.add(t);
        }
        Carousel carousel = new Carousel();
        carousel.getItems().addAll(listc0);
        carousel.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/Carousel.css")).toExternalForm());
        body.getChildren().addFirst(carousel);
        List<Pane> listc = new Vector<>();
        for(int i = 0; i<10;i++){
            FilmCard t = new FilmCard( 1,new Image((Objects.requireNonNull(getClass().getResource("/images/untitled.png"))).toExternalForm()),"Titolo mediamente lungo",4);
            t.setOnMouseClicked(mouseEvent -> {cardClicked(t.getCardId());});
            listc.add(t);
        }
        ScrollView scrollView = new ScrollView("hello");

        scrollView.setBackgroundColor(Color.rgb(15,15,15));
        scrollView.setContent(listc);
        scrollView.setForeColor(Color.WHITE);
        scrollView.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/ScrollView.css")).toExternalForm());


        List<Pane> listc2 = new Vector<>();
        for(int i = 0; i<10;i++){
            FilmCard t = new FilmCard( 1,new Image((Objects.requireNonNull(getClass().getResource("/images/untitled.png"))).toExternalForm()),"Titolo mediamente lungo",4);
            t.setOnMouseClicked(mouseEvent -> {cardClicked(t.getCardId());});
            listc2.add(t);
        }
        ScrollView scrollView2 = new ScrollView("Novita");
        scrollView2.setBackgroundColor(Color.rgb(247,157,7,1));
        scrollView2.setContent(listc2);
        scrollView2.setForeColor(Color.BLACK);
        scrollView2.setArrowButtonAction(event ->{System.out.println("ssw");});
        scrollView2.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/ScrollView.css")).toExternalForm());
        List<Pane> listc3 = new Vector<>();
        for(int i = 0; i<10;i++){
            FilmCard t = new FilmCard( 1,new Image((Objects.requireNonNull(getClass().getResource("/images/untitled.png"))).toExternalForm()),"Titolo mediamente lungo",4);
            t.setOnMouseClicked(mouseEvent -> {cardClicked(t.getCardId());});
            listc3.add(t);
        }
        ScrollView scrollView3 = new ScrollView("hello");

        scrollView3.setBackgroundColor(Color.rgb(15,15,15));
        scrollView3.setContent(listc3);
        scrollView3.setForeColor(Color.WHITE);
        scrollView3.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/ScrollView.css")).toExternalForm());
        List<Pane> listc4 = new Vector<>();
        for(int i = 0; i<10;i++){
            FilmCard t = new FilmCard( 1,new Image((Objects.requireNonNull(getClass().getResource("/images/untitled.png"))).toExternalForm()),"Titolo mediamente lungo",4);
            t.setOnMouseClicked(mouseEvent -> {cardClicked(t.getCardId());});
            listc4.add(t);
        }
        ScrollView scrollView4 = new ScrollView("hello");

        scrollView4.setBackgroundColor(Color.rgb(15,15,15));
        scrollView4.setContent(listc4);
        scrollView4.setForeColor(Color.WHITE);
        scrollView4.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/ScrollView.css")).toExternalForm());
        List<Pane> listc5 = new Vector<>();
        for(int i = 0; i<10;i++){
            FilmCard t = new FilmCard( 1,new Image((Objects.requireNonNull(getClass().getResource("/images/untitled.png"))).toExternalForm()),"Titolo mediamente lungo",4);
            t.setOnMouseClicked(mouseEvent -> {cardClicked(t.getCardId());});
            listc5.add(t);
        }
        ScrollView scrollView5 = new ScrollView("hello");

        scrollView5.setBackgroundColor(Color.rgb(31,31,31));
        scrollView5.setContent(listc5);
        scrollView5.setForeColor(Color.WHITE);
        scrollView5.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/ScrollView.css")).toExternalForm());

        scrollViewContainer.getChildren().addAll(scrollView,scrollView2,scrollView3,scrollView4,scrollView5);
        root.widthProperty().addListener((observable, oldValue, newValue) -> {



        });

    }

    public void categoryButtonClicked(){
        System.out.println("hello");
    }
    public void cardClicked(int idFilm){
        System.out.println("hellofromcard");
    }

}