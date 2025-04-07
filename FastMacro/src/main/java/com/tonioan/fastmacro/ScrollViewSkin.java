package com.tonioan.fastmacro;

import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

public class ScrollViewSkin extends SkinBase<ScrollView> {
    // UI Components
    // header
    private HBox titleBox = new HBox();
    private Button titleLabel = new Button();
    private Line separator = new Line();
    private Button arrowButton = new Button();
    // footer
    private ScrollPane scrollPane = new ScrollPane();
    private HBox container = new HBox();
    private Button leftButton = new Button();
    private Button rightButton = new Button();
    private AnchorPane sliderContainer; // Reference
    private Pane leftGradient; // overlay
    private Pane rightGradient; // overlay
    // Animation
    private Timeline scrollAnimation;
    private final double ARROW_SCALE_PROPERTY = 0.06;

    // Listeners
    private final ChangeListener<Number> scrollListener;
    private boolean isHovering = false; // Track hover state

    /**
     * Constructor for the ScrollViewSkin.
     *
     * @param control   The ScrollView control this skin is for.
     * @param foreColor The default foreground color (can be overridden by control).
     */
    public ScrollViewSkin(ScrollView control, Color foreColor) {
        super(control);
        control.getStyleClass().add("scroll-view");

        if (foreColor != null && control.getForeColor() == null) {
            control.setForeColor(foreColor);
        }

        titleBox = createTitleBox(control.getForeColor());
        sliderContainer = createSlider();

        // Setup hover
        setupHoverBehavior();

        // Main layout
        VBox mainLayout = new VBox(5);
        mainLayout.getChildren().addAll(titleBox, sliderContainer);

        // Setup bindings
        setupBindings();

        // Apply the initial foreground color to all elements
        updateForeColor(control.getForeColor());

        // Initialize scroll position listener
        scrollListener = (observable, oldValue, newValue) -> updateButtonVisibility(newValue.doubleValue());
        scrollPane.hvalueProperty().addListener(scrollListener);

        // Initialize buttons to be hidden
        leftButton.setOpacity(0);
        rightButton.setOpacity(0);

        // Add to scene graph
        getChildren().add(mainLayout);
    }
    /**
     * Sets up the mouse enter/exit listeners on the slider container
     * to control the visibility of the navigation buttons.
     */
    private void setupHoverBehavior() {
        // Show buttons when mouse enters the container
        sliderContainer.setOnMouseEntered(e -> {
            isHovering = true;
            updateButtonVisibility(scrollPane.getHvalue());
        });

        // Hide buttons when mouse exits the container
        sliderContainer.setOnMouseExited(e -> {
            isHovering = false;
            hideButton(leftButton, true);
            hideButton(rightButton, false);
        });
    }

    /**
     * Creates the header section containing the title, separator line, and action button.
     *
     * @param foreColor The color to use for text and icons.
     * @return The HBox containing the header elements.
     */
    private HBox createTitleBox(Color foreColor) {
        // Create title box container
        HBox box = new HBox();
        box.setPadding(new Insets(0,50,0,50));
        box.setAlignment(Pos.CENTER_LEFT);
        // Create and configure the label
        titleLabel = new Button();
        titleLabel.setTextFill(foreColor);
        titleLabel.getStyleClass().add("title-label");
        // Create the separator line
        separator = new Line();
        separator.setStrokeWidth(1);
        separator.setStroke(Color.BLACK); // Use provided color or default

        arrowButton = new Button();
        arrowButton.getStyleClass().add("arrow-button");


        // Create a StackPane to hold the line
        StackPane lineContainer = new StackPane(separator);

        lineContainer.setAlignment(Pos.CENTER);
        HBox.setHgrow(lineContainer, Priority.ALWAYS);
//      second solution
//      SVGPath svgPath = new SVGPath();
//      svgPath.setContent(getSkinnable().getArrowIcon());
//      svgPath.setFill(Color.WHITE);  // Explicitly set to white for visibility
//       svgPath.setScaleX(ARROW_SCALE_PROPERTY);
//       svgPath.setScaleY(ARROW_SCALE_PROPERTY);
//      arrowButton.setGraphic(svgPath);
        arrowButton.setMinHeight(40);
        arrowButton.setPrefHeight(40);
        arrowButton.setPrefWidth(90);
        arrowButton.setMinWidth(90);
        // Add all components to the container
        box.getChildren().addAll(titleLabel, lineContainer,arrowButton);

        // Bind the line width to the container width
        separator.startXProperty().set(60);
        separator.endXProperty().bind(lineContainer.widthProperty().subtract(60));

        return box;
    }

    /**
     * Creates the main slider area including the ScrollPane, item container,
     * navigation buttons, and gradient overlays.
     *
     * @return An AnchorPane containing the slider components.
     */
    private AnchorPane createSlider() {
        ScrollView control = getSkinnable();

        // Create scroll pane and container for items
        scrollPane = new ScrollPane();

        // Configure the
        container = new HBox(control.getSpacing());
        container.setAlignment(Pos.CENTER_LEFT);
        container.getStyleClass().add("cards-container");
        container.setSpacing(control.getSpacing());
        container.spacingProperty().bind(control.spacingProperty());
        // Configure ScrollPane
        scrollPane.setContent(container);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPadding(new Insets(0,30,0,30));
        scrollPane.setPannable(true);
        scrollPane.setFitToHeight(true); // Ensure content fits the height
        HBox.setHgrow(scrollPane, Priority.ALWAYS); // Make scroll pane take up all available space

        // Create navigation buttons
        leftButton = createNavButton(true);
        rightButton = createNavButton(false);

        // Create gradient overlays
        leftGradient = new Pane();
        leftGradient.setPrefWidth(80);
        leftGradient.setPrefHeight(1);

        rightGradient = new Pane();
        rightGradient.setPrefWidth(80);
        rightGradient.setPrefHeight(1);

        leftGradient.setStyle("-fx-background-color: linear-gradient( to right, "
                + toCssCode(control.getBackgroundColor()) +
                ", "
                + toCssCode(control.getBackgroundColor()) +
                " 40% , transparent);");
        rightGradient.setStyle("-fx-background-color: linear-gradient( to left, "
                + toCssCode(control.getBackgroundColor()) +
                ", "
                + toCssCode(control.getBackgroundColor()) +
                " 40% , transparent);");

        // Create Anchor to place buttons and gradients on the sides of ScrollPane
        AnchorPane containerStack = new AnchorPane();
        //containerStack.setAlignment(Pos.CENTER);
        final double distance =0.0;
        // Set alignment for all components
        AnchorPane.setBottomAnchor(scrollPane,distance);
        AnchorPane.setTopAnchor(scrollPane,distance);
        AnchorPane.setLeftAnchor(scrollPane,distance);
        AnchorPane.setRightAnchor(scrollPane,distance);

        AnchorPane.setBottomAnchor(rightButton,distance);
        AnchorPane.setTopAnchor(rightButton,distance);
        AnchorPane.setRightAnchor(rightButton,distance);

        AnchorPane.setBottomAnchor(leftButton,distance);
        AnchorPane.setTopAnchor(leftButton,distance);
        AnchorPane.setLeftAnchor(leftButton,distance);

        AnchorPane.setBottomAnchor(leftGradient,distance);
        AnchorPane.setTopAnchor(leftGradient,distance);
        AnchorPane.setLeftAnchor(leftGradient,distance);

        AnchorPane.setBottomAnchor(rightGradient,distance);
        AnchorPane.setTopAnchor(rightGradient,distance);
        AnchorPane.setRightAnchor(rightGradient,distance);

        containerStack.getChildren().addAll(scrollPane,rightGradient,leftGradient, leftButton, rightButton);
        // Set button alignment


        // Initialize gradient visibility
        return containerStack;
    }



    /**
     * Creates a navigation button (left or right).
     *
     * @param isLeft True to create the left button, false for the right.
     * @return The configured Button.
     */
    private Button createNavButton(boolean isLeft) {
        Button button = new Button();
        button.getStyleClass().add("nav-button");

        // Create SVG icon with white fill to ensure visibility
        SVGPath svgPath = new SVGPath();
        svgPath.setContent(getSkinnable().getArrowIcon());
        svgPath.setFill(Color.WHITE);

        // Scale and direction of arrow icon
        if (isLeft) {
            svgPath.setScaleX(-ARROW_SCALE_PROPERTY);
            svgPath.setScaleY(ARROW_SCALE_PROPERTY);
        } else {
            svgPath.setScaleX(ARROW_SCALE_PROPERTY);
            svgPath.setScaleY(ARROW_SCALE_PROPERTY);
        }

        button.setGraphic(svgPath);

        // --- Button Action ---
        button.setOnAction(event -> {
            double currentHValue = scrollPane.getHvalue();
            double visibleItems = calculateVisibleItems();
            int itemCount = getSkinnable().getItems().size();

            // Calculate the *change* in hvalue needed to scroll (visibleItems - 1) items
            double hValueChange = calculateHValueChange(visibleItems, itemCount);

            double targetValue;
            if (isLeft) {
                targetValue = Math.max(0.0, currentHValue - hValueChange); // Scroll left, clamp at 0
            } else {
                targetValue = Math.min(1.0, currentHValue + hValueChange); // Scroll right, clamp at 1
            }

            // Animate the scroll if the target is different from the current value
            if (Math.abs(targetValue - currentHValue) > 0.001) {
                animateScroll(targetValue);
            }
        });


        // Set proper dimensions
        button.setMinHeight(80);
        button.setPrefWidth(40);
        button.setMinWidth(40);

        return button;
    }

    private void updateButtonStyle(Button button, boolean isLeft) {
        ScrollView control = getSkinnable();
        String baseStyle = isLeft ? "-fx-background-radius: 20 0 0 20;" : "-fx-background-radius: 0 20 20 0;";
        leftGradient.setStyle("-fx-background-color: linear-gradient( to right, "
                + toCssCode(control.getBackgroundColor()) +
                ", "
                + toCssCode(control.getBackgroundColor()) +
                " 40% , transparent);");
        rightGradient.setStyle("-fx-background-color: linear-gradient( to left, "
                + toCssCode(control.getBackgroundColor()) +
                ", "
                + toCssCode(control.getBackgroundColor()) +
                " 40% , transparent);");
        button.setStyle(baseStyle);
    }

    /**
     * Estimates the width of a single item in the ScrollView.
     * Assumes all items have roughly the same width. Uses the first item.
     *
     * @return The estimated width of an item, or 0 if no items exist.
     */
    private double estimateItemWidth() {
        if (!getSkinnable().getItems().isEmpty()) {
            Node firstItem = getSkinnable().getItems().getFirst();
            // Use layout bounds for more accurate width after layout
            return firstItem.getLayoutBounds().getWidth();
        }
        return 0; // Default width if no items
    }

    /**
     * Calculates the approximate number of items that are fully visible within the ScrollPane's viewport.
     *
     * @return The number of visible items (at least 1 if the viewport has width).
     */
    private double calculateVisibleItems() {
        double viewportWidth = scrollPane.getViewportBounds().getWidth();
        if (viewportWidth <= 0) return 0; // No space

        double itemWidth = estimateItemWidth();
        if (itemWidth <= 0) return 0; // No item width

        double spacing = getSkinnable().getSpacing();
        double itemWidthWithSpacing = itemWidth + spacing; // Total space one item takes

        if (itemWidthWithSpacing <= 0) {
            // Avoid division by zero/infinite loops if spacing is negative and cancels width
            return itemWidth > 0 ? 1 : 0;
        }

        return Math.max(1.0, Math.floor(viewportWidth / itemWidthWithSpacing));
    }


    /**
     * Calculates the required change in the ScrollPane's hvalue to scroll
     * by approximately (visibleItems - 1) items.
     *
     * @param visibleItems The number of items currently visible (can be fractional).
     * @param itemCount    The total number of items in the ScrollView.
     * @return The proportional change (0.0 to 1.0) in hvalue needed for the scroll.
     */
    private double calculateHValueChange(double visibleItems, int itemCount) {
        double viewportWidth = scrollPane.getViewportBounds().getWidth();
        double itemWidth = estimateItemWidth();
        double spacing = getSkinnable().getSpacing();

        // Basic checks for valid calculation
        if (itemCount <= 0 || itemWidth <= 0 || viewportWidth <= 0 || visibleItems <= 0) {
            return 0.0;
        }

        // Calculate total width of all items and spacing
        double totalContentWidth = (itemCount * itemWidth) + (Math.max(0, itemCount - 1) * spacing);

        // Calculate the total scrollable width (content width - viewport width)
        double totalScrollableWidth = Math.max(1.0, totalContentWidth - viewportWidth); // Use 1.0 to avoid division by zero

        // If content fits within viewport, no scrolling is needed
        if (totalScrollableWidth <= 1.0) { // Use a small threshold
            return 0.0;
        }

        // Determine the number of items to scroll by (at least 1)
        // Use floor to ensure we use the count of fully visible items for deciding scroll distance
        double itemsToScroll = Math.max(1.0, Math.floor(visibleItems) - 1);

        // Calculate the pixel distance to scroll
        double itemWidthWithSpacing = itemWidth + spacing;
        double pixelScrollDistance = itemsToScroll * itemWidthWithSpacing;

        // Calculate the proportional change in hvalue
        double hValueChange = pixelScrollDistance / totalScrollableWidth;

        // Clamp the result between 0 and 1, though it should naturally fall in this range
        return Math.max(0.0, Math.min(1.0, hValueChange));
    }
    /**
     * Animates the ScrollPane's horizontal scroll position (hvalue) to a target value.
     *
     * @param targetValue The target hvalue (between 0.0 and 1.0).
     */
    private void animateScroll(double targetValue) {
        // If there's an animation in progress, stop it
        if (scrollAnimation != null && scrollAnimation.getStatus() == Timeline.Status.RUNNING) {
            scrollAnimation.stop();
        }

        // Create new animation with interpolator for smooth movement
        scrollAnimation = new Timeline();

        // Add initial and final keyframe for smooth animation
        KeyValue keyValue = new KeyValue(
                scrollPane.hvalueProperty(),
                targetValue,
                Interpolator.SPLINE(0.4, 0.0, 0.2, 1.0) // Easing curve for natural movement
        );

        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), keyValue);
        scrollAnimation.getKeyFrames().add(keyFrame);

        // Start animation
        scrollAnimation.play();
    }
    /**
     * Updates the visibility (opacity) of the navigation buttons based on the current
     * scroll position (hvalue) and whether the mouse is hovering over the slider area.
     *
     * @param scrollValue The current hvalue of the ScrollPane (0.0 to 1.0).
     */
    private void updateButtonVisibility(double scrollValue) {
        // Only handle button visibility if hovering
        if (!isHovering) {
            hideButton(leftButton, true);
            hideButton(rightButton, false);
            return;
        }

        // If at the beginning, hide left button
        if (scrollValue <= 0.001) {
            hideButton(leftButton, true);
        } else {
            showButton(leftButton, true);
        }

        // If at the end, hide right button
        if (scrollValue >= 0.999) {
            hideButton(rightButton, false);

        } else {
            showButton(rightButton, false);
        }
    }
    /**
     * Animates a button to become visible (fade in and slide in).
     *
     * @param button The button to show.
     * @param isLeft True if it's the left button (affects slide direction).
     */
    private void showButton(Node button, boolean isLeft) {
        // Define animation duration and easing
        Duration duration = Duration.millis(250); // Smoother duration
        Interpolator easing = Interpolator.EASE_OUT; // Easing out for appearance

        // Make the button visible and managed before starting animation
        // Start transparent and slightly offset
        button.setOpacity(0.0);
        button.setOpacity(1);

        // Create Fade Transition
        FadeTransition fadeIn = new FadeTransition(duration, button);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0); // Fade to fully opaque
        fadeIn.setInterpolator(easing);

        // Create Translate Transition
        TranslateTransition slideIn = new TranslateTransition(duration, button);
        slideIn.setFromX(isLeft ? -10 : 10); // Start from the offset position
        slideIn.setToX(0);                   // Slide to final position (0 offset)
        slideIn.setInterpolator(easing);

        // Play transitions in parallel
        ParallelTransition showTransition = new ParallelTransition(button, fadeIn, slideIn);
        showTransition.play();
    }

    /**
     * Animates a button to become hidden (fade out and slide out).
     * Makes the button unmanaged after the animation finishes.
     *
     * @param button The object to hide.
     * @param isLeft True if it's the left button (affects slide direction).
     */
    private void hideButton(Node button, boolean isLeft) {
        // Only animate if the button is currently visible and managed
        if (button.isVisible() && button.isManaged()) {
            // Define animation duration and easing
            Duration duration = Duration.millis(200); // Slightly faster for disappearance
            ParallelTransition hideTransition = getParallelTransition(button, isLeft, duration);

            // After animation, make the button invisible and unmanaged
            hideTransition.setOnFinished(event -> {
                button.setOpacity(0); // Reset opacity for next time
            });

            hideTransition.play();
        } else {
            // If already hidden or unmanaged, ensure state is correct
            button.setOpacity(0);
        }
    }

    private static ParallelTransition getParallelTransition(Node button, boolean isLeft, Duration duration) {
        Interpolator easing = Interpolator.EASE_IN; // Easing in for disappearance

        // Create Fade Transition
        FadeTransition fadeOut = new FadeTransition(duration, button);
        fadeOut.setFromValue(button.getOpacity()); // Start from current opacity
        fadeOut.setToValue(0.0);                 // Fade to fully transparent
        fadeOut.setInterpolator(easing);

        // Create Translate Transition
        TranslateTransition slideOut = new TranslateTransition(duration, button);
        slideOut.setFromX(0);                      // Start from current position
        slideOut.setToX(isLeft ? -10 : 10);        // Slide slightly off-screen
        slideOut.setInterpolator(easing);

        // Play transitions in parallel
        return new ParallelTransition(button, fadeOut, slideOut);
    }

    private void setupBindings() {
        ScrollView control = getSkinnable();

        // Bind title label text
        titleLabel.textProperty().bind(control.titleProperty());
        arrowButton.textProperty().bind(control.buttonTextProperty());
        arrowButton.setPrefWidth(control.getWidth()/10);
        // Initialize SVG paths
        updateSVGContent(leftButton, control.getArrowIcon());
        updateSVGContent(rightButton, control.getArrowIcon());
        leftButton.prefHeightProperty().bind(container.heightProperty());
        rightButton.prefHeightProperty().bind(container.heightProperty());

        // Bind foreground color
        control.foreColorProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                updateForeColor(newVal);
            }
        });

        // Bind background color
        control.backgroundColorProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                String colorStyle = "-fx-background-color: " + toCssCode(newVal) + ";";
                getSkinnable().setStyle(colorStyle);
                updateButtonStyle(leftButton, true);
                updateButtonStyle(rightButton, false);
            }
        });
        control.spacingProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                container.setSpacing((Double) newVal);
            }
        });

        arrowButton.setOnMouseClicked(event -> {
            if (control.getArrowButtonAction() != null) {
                control.getArrowButtonAction().handle(event);
            }
        });

        control.arrowButtonActionProperty().addListener((observable, oldValue, newValue) -> {});
        String colorStyle = "-fx-background-color: " + toCssCode(control.getBackgroundColor()) + ";";
        getSkinnable().setStyle(colorStyle);


        // Bind items
        Bindings.bindContent(container.getChildren(), control.getItems());

        control.getItems().addListener((javafx.collections.ListChangeListener<Node>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    // Calculate maximum height of all items
                    double maxHeight = 0;
                    for (Node item : control.getItems()) {
                        // Get the preferred height of each item
                        double itemHeight = item.prefHeight(-1);
                        if (itemHeight > maxHeight) {
                            maxHeight = itemHeight;
                        }
                    }

                    // Set container height to max item height + padding
                    if (maxHeight > 0) {
                        container.setMinHeight(maxHeight + 20); // 10px padding on top and bottom
                        container.setPrefHeight(maxHeight + 20);
                    }
                }
            }
        });
        control.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            if (newWidth.doubleValue() > 0) {
               //aiuto
            }
        });


    }
    /**
     * Updates the SVG content and fill color for a button's graphic.
     * Assumes the graphic is an SVGPath.
     *
     * @param button     The button whose graphic needs updating.
     * @param svgContent The new SVG path data string.
     */
    private void updateSVGContent(Button button, String svgContent) {
        if (button == null || button.getGraphic() == null) return;

        SVGPath svgPath = (SVGPath) button.getGraphic();
        svgPath.setContent(svgContent);
        svgPath.setFill(this.getSkinnable().getForeColor());
//        if (button == arrowButton) {
//            svgPath.setScaleX(ARROW_SCALE_PROPERTY);
//            svgPath.setScaleY(ARROW_SCALE_PROPERTY);
//        }
        if (button == leftButton) {
            svgPath.setScaleX(-ARROW_SCALE_PROPERTY);
            svgPath.setScaleY(ARROW_SCALE_PROPERTY);
        } else if (button == rightButton) {
            svgPath.setScaleX(ARROW_SCALE_PROPERTY);
            svgPath.setScaleY(ARROW_SCALE_PROPERTY);
        }
    }

    /**
     * Applies the foreground color to all relevant UI elements in the skin.
     *
     * @param color The new foreground color.
     */
    public void updateForeColor(Color color) {
        if (color == null) return;
        titleLabel.setTextFill(color);
        separator.setStroke(color);
        arrowButton.setStyle("-fx-text-fill: " + toCssCode(color) + "; -fx-border-color:" + toCssCode(color) + ";");

        // Navigation Button Icons (assuming they exist and are SVG)
        updateSVGContent(leftButton, getSkinnable().getArrowIcon());
        updateSVGContent(rightButton, getSkinnable().getArrowIcon());

    }
    /**
     * Converts a JavaFX Color object to a CSS rgba() string.
     * Handles null color by returning transparent.
     *
     * @param color The JavaFX Color.
     * @return A CSS rgba() string like "rgba(255, 0, 100, 0.8)".
     */
    public static String toCssCode(Color color) {
        return "rgba("+ (color.getRed() * 255)+", "+ (color.getGreen() * 255)+", "+ (color.getBlue() * 255)+", "+ color.getOpacity() +" )";
    }
    /**
     * Cleans up resources, listeners, and bindings when the skin is disposed.
     */
    @Override
    public void dispose() {
        // Clean up bindings and listeners
        titleLabel.textProperty().unbind();
        arrowButton.textProperty().unbind();
        container.spacingProperty().unbind();
        if (scrollListener != null) {
            scrollPane.hvalueProperty().removeListener(scrollListener);
        }
        // Remove mouse event handlers
        if (sliderContainer != null) {
            sliderContainer.setOnMouseEntered(null);
            sliderContainer.setOnMouseExited(null);
        }
        //Nullify references (optional, helps GC)
        titleBox = null;
        titleLabel = null;
        separator = null;
        arrowButton = null;
        scrollPane = null;
        container = null;
        leftButton = null;
        rightButton = null;
        sliderContainer = null;
        leftGradient = null;
        rightGradient = null;
        scrollAnimation = null;

        super.dispose();
    }
}