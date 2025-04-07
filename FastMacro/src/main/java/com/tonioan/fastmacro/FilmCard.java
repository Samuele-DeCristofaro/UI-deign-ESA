package com.tonioan.fastmacro;

import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

import java.util.Objects;

public class FilmCard extends StackPane {

    private int id;
    private String title;
    private double rating;
    private Image posterImage;

    private double width = 300;
    private double height = 200;

    // UI components
    private StackPane root = new StackPane();
    private ImageView posterView;
    private Label titleLabel;
    private Label ratingLabel;
    private Button starView;

    // Skin control
    private FilmCardSkin skin;

    /**
     * Creates a basic FilmCard with default dimensions
     *
     * @param id Unique identifier for the card
     * @param posterImage The movie/content poster image
     * @param title The title of the content
     * @param rating Rating from 0 to 5
     */
    public FilmCard(int id, Image posterImage, String title, double rating) {
        this.id = id;
        this.posterImage = posterImage;
        this.title = title;
        this.rating = rating;
        initialize();
    }

    /**
     * Creates a FilmCard with custom dimensions
     *
     * @param id Unique identifier for the card
     * @param posterImage The movie/content poster image
     * @param title The title of the content
     * @param rating Rating from 0 to 5
     * @param width Width of card
     * @param height Height of the card
     */
    public FilmCard(int id, Image posterImage, String title, double rating,  int width, int height) {
        this.id = id;
        this.posterImage = posterImage;
        this.title = title;
        this.rating = rating;
        this.width = width;
        this.height = height;
        initialize();
    }

    /**
     * Initialize the card components and skin
     */
    private void initialize() {
        this.setStyle("    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 0);" +
                "    -fx-fit-to-height: true;" +
                "    -fx-fit-to-width: true;");
        // Create and apply skin
        skin = new FilmCardSkin(this);
        skin.applySkin();

        // Add the root container to this StackPane
        this.getChildren().add(root);

        // Set alignment
        StackPane.setAlignment(root, Pos.CENTER);
    }

    /**
     * Sets the click handler for the entire card
     *
     * @param handler The event handler to execute when the card is clicked
     */
    public void setOnCardClicked(EventHandler<MouseEvent> handler) {
        this.setOnMouseClicked(handler);
    }

    // Getters and Setters
    public int getCardId() {
        return id;
    }

    public void setCardId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        if (titleLabel != null) {
            titleLabel.setText(title);
        }
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
        if (ratingLabel != null) {
            ratingLabel.setText(String.format("%.0f/5", rating));
        }
    }

    public Image getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(Image posterImage) {
        this.posterImage = posterImage;
        if (posterView != null) {
            posterView.setImage(posterImage);
        }
    }

    public double getCardWidth() {
        return width;
    }

    public void setCardWidth(double width) {
        this.width = width;
        skin.updateDimensions();
    }

    public double getCardHeight() {
        return height;
    }

    public void setCardHeight(double height) {
        this.height = height;
        skin.updateDimensions();
    }



    // Inner class for handling the visual aspects of the FilmCard
    private class FilmCardSkin {
        private final FilmCard card;

        // Material Design constants
        private final Duration TRANSITION_DURATION = Duration.millis(200);
        private final double HOVER_SCALE = 1.03; // 3% scale increase on hover
        private final double REST_SCALE = 1.0;
        private final double FOOTER_HEIGHT = 60;

        private ScaleTransition growTransition;
        private ScaleTransition shrinkTransition;
        private DropShadow defaultShadow;
        private DropShadow hoverShadow;

        public FilmCardSkin(FilmCard card) {
            this.card = card;
        }

        /**
         * Apply the skin to the card
         */
        public void applySkin() {
            setupShadowEffects();
            setupTransitions();
            setupLayout();
            setupCardInteraction();
        }

        /**
         * Update the card dimensions
         */
        public void updateDimensions() {
            double padding = card.getCardWidth() / 20;
            card.setPrefSize(card.getCardWidth() + padding * 2, card.getCardHeight() + padding * 2);
            card.setMinSize(card.getCardWidth() + padding * 2, card.getCardHeight() + padding * 2);
            card.setMaxSize(card.getCardWidth() + padding * 2, card.getCardHeight() + padding * 2);

            if (posterView != null) {
                posterView.setFitWidth(card.getCardWidth());
                posterView.setFitHeight(card.getCardHeight());
            }

            // Update clip rectangle
            Rectangle clip = new Rectangle(card.getCardWidth(), card.getCardHeight());
            clip.setArcWidth(card.getCardWidth() / 10);
            clip.setArcHeight(card.getCardWidth() / 10);
            root.setClip(clip);
        }

        /**
         * Sets up shadow effects for normal and hover states
         */
        private void setupShadowEffects() {
            // Default shadow (elevation 1)
            defaultShadow = new DropShadow();
            defaultShadow.setColor(Color.rgb(0, 0, 0, 0.2));  // 20% opacity
            defaultShadow.setOffsetY(1);
            defaultShadow.setOffsetX(0);
            defaultShadow.setRadius(3);
            defaultShadow.setSpread(0.1);

            // Hover shadow (elevation 2-3)
            hoverShadow = new DropShadow();
            hoverShadow.setColor(Color.rgb(0, 0, 0, 0.3));  // 30% opacity
            hoverShadow.setOffsetY(3);
            hoverShadow.setOffsetX(0);
            hoverShadow.setRadius(8);
            hoverShadow.setSpread(0.2);

            root.setEffect(defaultShadow);
        }

        /**
         * Sets up scale transitions for hover effect
         */
        private void setupTransitions() {
            // Grow transition for mouse enter
            growTransition = new ScaleTransition(TRANSITION_DURATION, root);
            growTransition.setToX(HOVER_SCALE);
            growTransition.setToY(HOVER_SCALE);

            // Shrink transition for mouse exit
            shrinkTransition = new ScaleTransition(TRANSITION_DURATION, root);
            shrinkTransition.setToX(REST_SCALE);
            shrinkTransition.setToY(REST_SCALE);
        }

        /**
         * Sets up the internal layout of the card
         */
        private void setupLayout() {
            double padding = card.getCardWidth() / 20;
            card.setPadding(new Insets(padding));
            card.setPrefSize(card.getCardWidth() + padding * 2, card.getCardHeight() + padding * 2);
            card.setMinSize(card.getCardWidth() + padding * 2, card.getCardHeight() + padding * 2);
            card.setMaxSize(card.getCardWidth() + padding * 2, card.getCardHeight() + padding * 2);

            // Poster image
            posterView = new ImageView(card.getPosterImage());
            posterView.setFitWidth(card.getCardWidth());
            posterView.setFitHeight(card.getCardHeight());
            posterView.setPreserveRatio(false);

            root.getChildren().add(posterView);
            root.setStyle("-fx-background-radius: 20; -fx-border-radius: 20; -fx-border-color: black;");

            Rectangle clip = new Rectangle(card.getCardWidth(), card.getCardHeight());
            clip.setArcWidth(card.getCardWidth() / 10); // 2x the border radius
            clip.setArcHeight(card.getCardWidth() / 10); // 2x the border radius
            root.setClip(clip);

            // Footer container (title and rating)
            HBox footerContainer = new HBox();
            footerContainer.setPrefHeight(FOOTER_HEIGHT);
            footerContainer.setMinHeight(FOOTER_HEIGHT);
            footerContainer.setMaxHeight(FOOTER_HEIGHT);
            footerContainer.setAlignment(Pos.CENTER_LEFT);
            footerContainer.setPadding(new Insets(0, 10, 0, 10));
            footerContainer.setStyle("-fx-background-color: rgba(240, 240, 240, 0.9);   -fx-background-radius: 20;" +
                    "    -fx-padding: 10 10;"); // Semi-transparent footer

            // Title
            titleLabel = new Label(card.getTitle());
            titleLabel.setStyle("-fx-text-fill: #313131; -fx-font-size: 15px; -fx-font-weight: bold; -fx-wrap-text: true;");
            HBox.setHgrow(titleLabel, Priority.ALWAYS);
            titleLabel.setTextOverrun(OverrunStyle.CLIP);

            // Rating container
            HBox ratingContainer = new HBox();
            ratingContainer.setStyle("-fx-alignment: CENTER_RIGHT; -fx-spacing: 4;");
            ratingContainer.setAlignment(Pos.CENTER_RIGHT);

            // Rating text
            ratingLabel = new Label(String.format("%.0f/5", card.getRating()));
            ratingLabel.setStyle("    -fx-text-fill: #313131;" +
                    "    -fx-font-size: 14px;" +
                    "    -fx-font-weight: bold;");
            ratingLabel.setTextOverrun(OverrunStyle.CLIP);

            // Star icon
            starView = new Button();
            starView.setStyle("    -fx-background-color: transparent;" +
                    "    -fx-min-width:24;" +
                    "    -fx-min-height:24;" +
                    "    -fx-pref-width:24;" +
                    "    -fx-pref-height:24;" +
                    "    -fx-max-width:24;" +
                    "    -fx-max-height:24;" +
                    "    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 1, 0, 0, 0);");
            String starPath = "M 47.755 3.765 l 11.525 23.353 c 0.448 0.907 1.313 1.535 2.314 1.681 l 25.772 3.745 c 2.52 0.366 3.527 3.463 1.703 5.241 L 70.42 55.962 c -0.724 0.706 -1.055 1.723 -0.884 2.72 l 4.402 25.667 c 0.431 2.51 -2.204 4.424 -4.458 3.239 L 46.43 75.47 c -0.895 -0.471 -1.965 -0.471 -2.86 0 L 20.519 87.588 c -2.254 1.185 -4.889 -0.729 -4.458 -3.239 l 4.402 -25.667 c 0.171 -0.997 -0.16 -2.014 -0.884 -2.72 L 0.931 37.784 c -1.824 -1.778 -0.817 -4.875 1.703 -5.241 l 25.772 -3.745 c 1.001 -0.145 1.866 -0.774 2.314 -1.681 L 42.245 3.765 C 43.372 1.481 46.628 1.481 47.755 3.765 z";
            SVGPath svgPath = new SVGPath();
            svgPath.setContent(starPath);
            starView.setGraphic(svgPath);
            svgPath.setScaleX(0.25);
            svgPath.setScaleY(0.25);
            svgPath.setFill(Color.GOLD);

            ratingContainer.getChildren().addAll(ratingLabel, starView);

            // Create a spacer to push the rating to the right
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            footerContainer.getChildren().addAll(titleLabel, spacer, ratingContainer);
            StackPane.setAlignment(footerContainer, Pos.BOTTOM_CENTER);

            // Add footer to main container
            root.getChildren().add(footerContainer);
        }

        /**
         * Sets up mouse interaction for the card
         */
        private void setupCardInteraction() {
            // Mouse enter: grow and increase shadow
            card.setOnMouseEntered(e -> {
                shrinkTransition.stop();
                growTransition.play();
                card.setEffect(hoverShadow);
            });

            // Mouse exit: shrink and reduce shadow
            card.setOnMouseExited(e -> {
                growTransition.stop();
                shrinkTransition.play();
                card.setEffect(defaultShadow);
            });

            // Mouse pressed: slightly smaller scale for "pressed" effect
            card.setOnMousePressed(e -> {
                card.setScaleX(HOVER_SCALE * 0.98);
                card.setScaleY(HOVER_SCALE * 0.98);
            });

            // Mouse released: back to hover scale
            card.setOnMouseReleased(e -> {
                card.setScaleX(HOVER_SCALE);
                card.setScaleY(HOVER_SCALE);
            });
        }
    }
}