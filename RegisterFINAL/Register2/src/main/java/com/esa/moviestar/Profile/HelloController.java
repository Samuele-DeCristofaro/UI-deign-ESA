package com.esa.moviestar.Profile;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

import java.io.IOException;

public class HelloController {

    @FXML   // Collegamento alla label del testo
    Label testo;

    @FXML   // HBox per la griglia che contiene gli utenti
    HBox griglia;

    @FXML   // StackPane che contiene l'intera schermata
    StackPane ContenitorePadre;

    @FXML   // Label per eventuali messaggi di errore o avviso
    Label warningText;

    // Metodo di inizializzazione che viene eseguito subito all'avvio
    public void initialize() {
        testo.setText("Chi vuole guardare Moviestar ?");  // Impostazione del testo della label iniziale
        griglia.setSpacing(40);  // Impostazione della spaziatura tra gli elementi nella griglia

        // Creazione del "pannello di aggiunta" che si visualizzerà come un'area cliccabile
        Pane creazione = new Pane();
        creazione.setPrefHeight(100);  // Altezza del pannello
        creazione.setPrefWidth(100);   // Larghezza del pannello
        creazione.setStyle("-fx-background-color:yellow;" +  // Colore di sfondo del pannello
                "-fx-background-radius:30;" +   // Arrotondamento degli angoli
                "-fx-border-radius: 20;" +      // Border radius per il bordo
                "-fx-border-color: black; " +   // Colore del bordo
                "-fx-border-radius: 30;");      // Altra impostazione di border radius

        // Creazione della VBox che contiene il pannello di aggiunta e la label "Aggiungi"
        VBox creazioneUtente = new VBox();
        Label nomeCreazione = new Label("Aggiungi");  // Etichetta "Aggiungi"
        nomeCreazione.setStyle("-fx-font-size: 20px;" +    // Impostazione del font
                "-fx-font-family: Arial;" +    // Impostazione della famiglia di font
                "-fx-text-fill: white;");      // Colore del testo
        creazioneUtente.getChildren().addAll(creazione, nomeCreazione);  // Aggiungi il pannello e la label alla VBox
        griglia.getChildren().add(creazioneUtente);  // Aggiungi la VBox alla griglia
        creazioneUtente.setAlignment(Pos.CENTER);  // Centra il contenuto all'interno della VBox

        // Aggiungi il listener al pannello di creazione, quando cliccato si esegue tastoAggiungi()
        creazione.setOnMouseClicked(e -> tastoAggiungi());
    }

    // Metodo che si attiva quando l'utente clicca sul pannello "Aggiungi"
    private void tastoAggiungi() {
        if (griglia.getChildren().size() < 4) {  // Se ci sono meno di 4 elementi, posso aggiungerne uno nuovo
            paginaCreazioneUtente();  // Carica la pagina di creazione
            griglia.getChildren().add(creazioneDellUtente("Nome Utente", null));  // Aggiungi un nuovo utente alla griglia
        } else if (griglia.getChildren().size() == 4) {  // Se la griglia ha già 4 elementi
            griglia.getChildren().remove(0);  // Rimuovi il primo utente della griglia (per far spazio al nuovo)
            griglia.getChildren().add(creazioneDellUtente("Nome Utente", null));  // Aggiungi il nuovo utente
        }
    }

    // Metodo per simulare il passaggio alla pagina Home
    private void paginaHome() {
        System.out.println("SEI NELLA PAGINA HOME ");  // Stampa un messaggio per debugging
    }

    // Metodo che gestisce il passaggio alla pagina di modifica
    private void paginaModifica(String nome, String colore) {
        if (griglia.getChildren().size() > 1) {  // Verifica che ci sia almeno un utente nella griglia
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("modify-create-view.fxml"));  // Carica il FXML per la modifica
                Parent modifyContent = loader.load();  // Carica la vista della pagina
                ContenitorePadre.getChildren().clear();  // Svuota il contenitore principale
                ContenitorePadre.getChildren().add(modifyContent);  // Aggiungi la nuova vista al contenitore principale
            } catch (IOException e) {
                warningText.setText("Errore durante il caricamento della pagina di modifica: " + e.getMessage());  // Gestione errore
                e.printStackTrace();  // Stampa il trace dell'errore
            }
        } else {
            warningText.setText("Nessun elemento selezionato da modificare.");  // Se non c'è nessun utente, mostra avviso
        }
    }

    // Metodo che simula il passaggio alla pagina di creazione utente
    private void paginaCreazioneUtente() {
        System.out.println("SEI NELLA PAGINA DI CREAZIONE");  // Stampa un messaggio per il debug
    }

    // Metodo che crea e restituisce un nuovo utente con il nome e l'immagine specificati
    private VBox creazioneDellUtente(String nome, String immagine) {
        VBox utente = new VBox();  // Crea una VBox per contenere l'utente
        utente.setSpacing(10);  // Imposta lo spazio tra gli elementi all'interno della VBox
        utente.setPadding(new Insets(10));  // Aggiungi un po' di padding attorno agli elementi

        // Creazione dell'immagine dell'utente
        Image image = new Image(getClass().getResource("/image/pngtree-avatar-icon-profile-icon-member-login-vector-isolated-png-image_1978396.jpg").toExternalForm());  // Carica l'immagine dell'utente
        ImageView imageView = new ImageView(image);  // Crea un ImageView per visualizzare l'immagine
        imageView.setFitWidth(100);  // Imposta la larghezza dell'immagine
        imageView.setFitHeight(100);  // Imposta l'altezza dell'immagine
        imageView.setPreserveRatio(true);  // Mantiene il rapporto di aspetto dell'immagine

        // Crea un'etichetta con il nome dell'utente
        Label l = new Label(nome);
        l.setStyle("-fx-font-size: 18px; -fx-font-family: Arial; -fx-text-fill: white;");  // Imposta lo stile del testo

        // Crea un bottone "Modifica" per l'utente
        Button modifica = new Button("Modifica");
        modifica.setPrefWidth(100);  // Imposta la larghezza del bottone

        // Aggiungi i listener per gli eventi di click
        imageView.setOnMouseClicked(e -> paginaHome());  // Cliccando sull'immagine si va alla pagina Home
        modifica.setOnMouseClicked(e -> paginaModifica(nome, immagine));  // Cliccando sul bottone si va alla pagina di modifica

        // Aggiungi tutti gli elementi (immagine, nome, bottone) alla VBox
        utente.getChildren().addAll(imageView, l, modifica);

        return utente;  // Ritorna la VBox che rappresenta l'utente
    }
}
