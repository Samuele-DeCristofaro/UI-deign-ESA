package com.esa.moviestar.Database;

import com.esa.moviestar.model.Utente;

import java.sql.*;

public class UtenteDao {
    private Connection connection;

    public UtenteDao() {
        try {
            this.connection = DataBaseManager.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Inserimento di un nuovo utente
    public void inserisciUtente(Utente utente) throws SQLException {
        String query = "INSERT INTO Utente (Nome, Gusti, idImmagine, email) VALUES (?, ?, ?, ?);";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, utente.getNome());
            stmt.setString(2, utente.getGusti());
            stmt.setInt(3, utente.getIdImmagine());
            stmt.setString(4, utente.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Errore nell'inserimento dell'utente", e);
        }
    }

    // Rimozione utente tramite codice
    public void rimuoviUtente(int codUtente) throws SQLException {
        String sql = "DELETE FROM Utente WHERE CodUtente = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, codUtente);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Nessun utente trovato con codice utente = " + codUtente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Errore nell'eliminazione dell'utente", e);
        }
    }

    // Ricerca utente tramite codice
    public Utente cercaUtente(int codUtente) throws SQLException {
        String query = "SELECT * FROM Utente WHERE CodUtente = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, codUtente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Utente(
                        rs.getString("Nome"),
                        rs.getInt("idImmagine"),
                        rs.getString("Gusti"),
                        rs.getString("email")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Errore nel cercare l'utente", e);
        }
    }
}
