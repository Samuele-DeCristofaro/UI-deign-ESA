package com.example.register2.DAO;

import com.example.register2.model.Utente;

import java.sql.*;

public class UtenteDao {
    private Connection connection;

    public UtenteDao(Connection connection){  //Costruttore
        this.connection=connection;
    }

    //Operazioni Crud

    //Metodo per inserire un Utente
    public void inserisciUtente(Utente utente) throws SQLException {
        String query = "INSERT INTO utente (codUtente,nome,dataNascita,codPiano,dataRegistrazione,email) VALUES (?,?,?,?,?,?);";

        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1,utente.getCodUtente());
            stmt.setString(2,utente.getNome());
            stmt.setString(3,utente.getDataNascita());
            stmt.setInt(4,utente.getCodPiano());
            stmt.setString(5,utente.getDataRegistrazione());
            stmt.setString(6,utente.getEmail());
            stmt.execute();

        }catch(SQLException e){
            e.printStackTrace();
            throw new SQLException("Errore nell'inserire l'utente", e);
        }
    }

    //Metodo per rimuovere un Utente dal codUtente
    public void rimuoviUtente(int codUtente) throws SQLException {
        String sql = "DELETE FROM utente WHERE codUtente = ?;";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,codUtente);
            int rowsAffected=stmt.executeUpdate();
            if (rowsAffected==0) {
                throw new SQLException("Nessun utente trovato con codice utente = " + codUtente);
            }
        }catch(SQLException e ){
            e.printStackTrace();
            throw  new SQLException("Errore nell'eliminazione dell'utente",e);
        }

    }

    //Metodo per cercare un utente dal codiceUtente

    public Utente cercaUtente(int codUtente) throws SQLException{

        String query = "SELECT * FROM utente WHERE codUtente=?;";

        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1,codUtente);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Risultato : ");
            if(rs.next()){
              return new Utente(
                      rs.getInt("codUtente"),
                      rs.getString("nome"),
                      rs.getString("dataNascita"),
                      rs.getInt("codPiano"),
                      rs.getString("dataRegistrazione"),
                      rs.getString("email")
              );
            }else {
                return null;
            }
        }catch (SQLException e ) {
            e.printStackTrace();
            throw new SQLException("Errore nel cercare l'utente", e);
        }
    }


}
