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
            Group svgIcon = new Group( new SVGPath(){{setContent("M3 0 21 0C23.5 0 24 .5 24 3L24 13C24 15.5 23.5 16 21 16L3 16C.5 16 0 15.5 0 13L0 3C0 .5.5 0 3 0");}});
            this.getStyleClass().add("chips");
            this.getChildren().add(svgIcon);

        this.getChildren().add(txt);
    }
}
