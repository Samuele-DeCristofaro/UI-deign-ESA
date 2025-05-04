package com.esa.moviestar.movie_view;

import javafx.scene.Group;

import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FilmTagChips extends HBox {
    private static final Map<String, Group> groupMap = new HashMap<>();

    public FilmTagChips(String text){
        this.minHeight(64.0);
        this.minWidth(64.0);
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/esa/moviestar/styles/General.css")).toExternalForm());
        this.getStyleClass().addAll("surface-dim-border","surface-transparent", "small-item");
        this.getStyleClass().add("film-tag-chip");
        Text txt = new Text(text);
        txt.getStyleClass().addAll("on-primary","small-text");
        if(groupMap.containsKey(text)) {
            Group svgIcon = groupMap.get(text);
            this.getStyleClass().add("chips");
            this.getChildren().add(svgIcon);
        }
        this.getChildren().add(txt);
    }
}
