package com.esa.moviestar.movie_view;

import javafx.beans.property.*;
import javafx.scene.image.Image;

public class Film {
    // Use JavaFX properties for automatic UI updates and binding.
    private final IntegerProperty id;
    private final StringProperty title;
    private final StringProperty subtitle;
    private final BooleanProperty series;
    private final StringProperty time;
    private final DoubleProperty rating;
    private final ObjectProperty<Image> image;
    // Constructor
    public Film(int id, String title, String subtitle,boolean series, String time, double rating, Image image) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.subtitle = new SimpleStringProperty(subtitle);
        this.series = new SimpleBooleanProperty(series);
        this.time = new SimpleStringProperty(time);
        this.rating = new SimpleDoubleProperty(rating);
        this.image = new SimpleObjectProperty<>(image);
    }

    // Getters and Setters for properties
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getSubtitle() {
        return subtitle.get();
    }

    public StringProperty subtitleProperty() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle.set(subtitle);
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public double getRating() {
        return rating.get();
    }

    public DoubleProperty ratingProperty() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating.set(rating);
    }

    public Image getImage() {
        return image.get();
    }

    public ObjectProperty<Image> imageProperty() {
        return image;
    }

    public void setImage(Image image) {
        this.image.set(image);
    }

    public boolean isSeries() {
        return series.get();
    }

    public BooleanProperty isSeriesProperty() {
        return series;
    }
}
