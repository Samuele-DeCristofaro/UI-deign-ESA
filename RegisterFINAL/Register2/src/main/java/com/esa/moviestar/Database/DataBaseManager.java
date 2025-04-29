package com.esa.moviestar.Database;  // Dichiara il package in cui risiede questa classe

import java.sql.Connection; // Importa l’interfaccia Connection per gestire la connessione al database
import java.sql.DriverManager; // Importa DriverManager per ottenere la connessione JDBC
import java.sql.SQLException; // Importa SQLException per gestire gli errori SQL

public class DataBaseManager {
    private static final String URL = "jdbc:sqlite:C:\\com.esa.moviestar.Users\\ssamu\\IdeaProjects\\UI-deign-ESA\\RegisterFINAL\\Register2\\src\\main\\resources\\com\\example\\register2\\DatabaseProjectUID.db";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void main(String[] args) {
        // Definisce l’URL di connessione: indica il driver JDBC e il percorso del file .db
        String url = "jdbc:sqlite:C:\\com.esa.moviestar.Users\\ssamu\\IdeaProjects\\UI-deign-ESA\\RegisterFINAL\\Register2\\src\\main\\resources\\com\\example\\register2\\DatabaseProjectUID.db";

        // Prova ad aprire la connessione; try-with-resources chiude automaticamente conn al termine
        try (Connection conn = DriverManager.getConnection(url)) {
            // Se conn non è null, significa che la connessione è riuscita
            if (conn != null) {
                System.out.println("Connesso al database");
                // Messaggio di conferma per indicare che la connessione è attiva
            }
        } catch (SQLException e) {
            // Viene eseguito se si verifica un errore durante l’apertura della connessione
            System.out.println("Connessione al database fallita: " + e.getMessage());
            // Stampa un messaggio di errore con il dettaglio fornito dall’eccezione
        }
        // Fine del metodo main; la connessione è già chiusa dal try-with-resources
    }
}
