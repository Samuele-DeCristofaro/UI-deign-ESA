package com.esa.moviestar.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Content or TV series in the recommendation system.
 */
public class Content {
    private long id;
    private String title;
    private String subtitle;
    private String time;
    private double rating;
    private String imageUrl;
    private long click;
    private String dataPubblicazione;
    private boolean serie;
    private List<Integer> categorie;

    // Default constructor
    public Content() {
        this.categorie = new ArrayList<>();
    }

    // Constructor with all fields
    public Content(long id, String title,String subtitle, String time, double rating, String imageUrl, long click, String dataPubblicazione, boolean serie, List<Integer> categorie) {
        this.id = id;
        this.title= title;
        this.subtitle = subtitle;
        this.time = time;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.click = click;
        this.dataPubblicazione = dataPubblicazione;
        this.serie = serie;
        this.categorie = categorie != null ? categorie : new ArrayList<>();
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getClick() {
        return click;
    }

    public void setClick(long click) {
        this.click = click;
    }

    public String getDataPubblicazione() {
        return dataPubblicazione;
    }

    public void setDataPubblicazione(String dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }



    public boolean isSeries() {
        return serie;
    }

    public void setSeries(boolean serie) {
        this.serie = serie;
    }

    public List<Integer> getCategorie() {
        return categorie;
    }

    public void setCategorie(List<Integer> categorie) {
        this.categorie = categorie != null ? categorie : new ArrayList<>();
    }
    @Override
    public String toString(){
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
