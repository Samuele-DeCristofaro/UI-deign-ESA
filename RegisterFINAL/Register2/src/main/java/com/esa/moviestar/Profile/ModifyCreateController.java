package com.esa.moviestar.Profile;

import java.io.IOException;

import com.esa.moviestar.Login.AnimationUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class ModifyCreateController {
    @FXML
    HBox pageContainer;
    @FXML
    VBox svgContainer;
    @FXML
    VBox elementContainer;
    @FXML
    Pane imagPane;
    @FXML
    Label creationTitle;
    @FXML
    TextField textName;
    @FXML
    HBox imageScroll1;
    @FXML
    HBox imageScroll2;
    @FXML
    HBox imageScroll3;
    @FXML
    VBox scrollContainer;
    @FXML
    Button saveButton;
    @FXML
    Button cancelButton;
    @FXML
    Label warningText;
    @FXML
    private Label errorText;


    public void initialize() {


        errorText.setText("");
        imagPane.setMaxSize(300,300);  //Immagine principale

        imagPane.setStyle("-fx-background-color:red;");//modifica di stile dell'immagine principale

        creationTitle.setText("Crea il nome utente:"); //label per sopra il textfield per farci capire che stiamo creando un nuovo utente

        textName.setPromptText("Nome");// text field dove inserire il nome, (con all'interno trasparente la scritta "inserisci nome")



        elementContainer.setSpacing(30);

        pageContainer.setSpacing(100); //utilizzo per avere spazio tra ogni elemento



        //metto lo spazio tra ogni scroll di immagini
        imageScroll1.setSpacing(20);
        imageScroll2.setSpacing(20);
        imageScroll3.setSpacing(20);

        //metto lo stile per ogni scroll di immagini

        scrollContainer.setSpacing(30);

        saveButton.setText("Salva"); //setting del bottone di salvataggio

        cancelButton.setText("Annulla"); //setting del bottone di annullamento

        cancelButton.setOnMouseClicked(e -> {//Se cliccato è un evento irreversibile e ritorna alla pagina iniziale di scelta dei profili
            System.out.println("Annullato");

        });
        saveButton.setOnMouseClicked(event -> {  //Se clicco sul bottone di salvataggio / dovrà poi ritornare alla pagina di scelta dei profili con il profilo creato

            if (!textName.getText().isEmpty() && !textName.getText().contains(" ")) {  //se ho messo un nome nel textfield e l'ho salvato allora ritorno alla pagina principale dei profili / oppure potrei far direttamente loggare / (modifiche da fare : controllare che abbia scelto anche un immagine, oppure se non l'ha scelta dare quella di default)
                //questo metodo cosi fa ritornare alla pagina dei profili, aggiungere poi il fatto che io abbia creato il panel nuovo con tutte le modifiche
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esa/moviestar/hello-view-profile.fxml"));
                    Parent profileContent = loader.load();
                    pageContainer.getChildren().clear();
                    pageContainer.getChildren().add(profileContent);
                } catch (IOException e) {
                    warningText.setText("Errore durante il caricamento della pagina di modifica: " + e.getMessage());
                    e.printStackTrace(); // oppure usa un logger
                }
                System.out.println("Ritorni alla pagina dei profili");
            } else {
                errorText.setText("Nessun nome inserito"); //se non ho inserito nessun nome mi da errore perchè per forza va settato un nome , oppure potrei dare il nome di default tipo utente 1
                AnimationUtils.shake(errorText);
            }

        });


        for (int i = 0; i < 5; i++) {  // for che crea e inserisce ogni pannello nel suo apposito scroll di immagine e setta lo stile
            Pane p1 = new Pane();
            p1.setPrefSize(100, 100);
            p1.setStyle("-fx-background-color:purple;");
            imageScroll1.getChildren().addAll(p1);

            Pane p2 = new Pane();
            p2.setPrefSize(100, 100);
            p2.setStyle("-fx-background-color:green;");
            imageScroll2.getChildren().addAll(p2);

            Pane p3 = new Pane();
            p3.setPrefSize(100, 100);
            p3.setStyle("-fx-background-color:blue;");
            imageScroll3.getChildren().addAll(p3);
        }
        // Inizializza tutti gli HBox di immagini
        setupImageProfile(imageScroll1);
        setupImageProfile(imageScroll2);
        setupImageProfile(imageScroll3);
    }

    private void setupImageProfile(HBox imageScroll) {
        // Cicla attraverso tutti gli elementi dell'HBox (le immagini)
        for (int i = 0; i < imageScroll.getChildren().size(); i++) {
            // prendo un'immagine che ce all'interno dello scrollImage
            Node scrollImage = imageScroll.getChildren().get(i);

            // ogni volta che clicco un immagine all'interno di un imageScroll allora succede qualcosa
            scrollImage.setOnMouseClicked(event -> {

                // Converte il nodo generico in un Pane specifico per poter accedere alle sue proprietà
                Pane originalPane = (Pane) scrollImage;

                // Crea un nuovo Pane che farà da clone dell'originale
                Pane clonedPane = new Pane();

                // Copia le dimensioni dal pane originale al clone
                clonedPane.setPrefSize(imagPane.getPrefWidth(), imagPane.getPrefHeight());

                // Copia lo stile (colore di sfondo, bordi, ecc.) dal pane originale al clone
                clonedPane.setStyle(originalPane.getStyle());

                // Ottiene l'immagine profilo attualmente visualizzata (primo elemento dell'elementContainer)
                Node currentImageProfile = elementContainer.getChildren().get(0);

                // Rimuove l'immagine profilo corrente dall'elementContainer
                elementContainer.getChildren().remove(currentImageProfile);

                // Aggiunge il clone dell'immagine selezionata come nuova immagine profilo
                // La posizione 0 garantisce che sia sempre il primo elemento dell'elementContainer
                elementContainer.getChildren().add(0, clonedPane);
            });
        }
    }
}