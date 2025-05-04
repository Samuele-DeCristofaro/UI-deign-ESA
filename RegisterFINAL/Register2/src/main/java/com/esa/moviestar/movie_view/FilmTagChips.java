package com.esa.moviestar.movie_view;

import javafx.scene.Group;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

public class FilmTagChips extends HBox {
    private static final Map<String, Group> groupMap = new HashMap<>();

    public FilmTagChips(String text){
        this.minHeight(64.0);
        this.minWidth(64.0);
        this.getStyleClass().addAll("surface-border-dim","surface-opaque", "small-item");
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
