package com.esa.moviestar.Database;

import com.esa.moviestar.model.Account;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDao {

    private static Connection connection;


    public AccountDao(){
        try{
            this.connection = DataBaseManager.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    //Metodo per inserire un Account
    public boolean inserisciAccount(Account account) throws SQLException {
        String sql = "INSERT INTO account (email,password) Values (?,?);";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, account.getEmail());
            stmt.setString(2, account.getPassword());
            stmt.execute();
            return true;
        }catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                // Email già esistente
                return false;
            } else {
                e.printStackTrace();
                return false;
            }
        }
    }

    //Metodo per eliminare l'account
    public void rimuoviAccount(String email) throws SQLException{
        String query = "DELETE FROM account WHERE email = ?;";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1,email);
            int rowsAffected=stmt.executeUpdate();
            if (rowsAffected==0) {
                throw new SQLException("Nessun account trovato con email = " + email);
            }
        }catch(SQLException e ){
            e.printStackTrace();
            throw  new SQLException("Errore nell'eliminazione dell'account",e);
        }
    }

    //Metodo per cercare l'account dall'email
    public  Account cercaAccount(String email) throws SQLException {
        String query = "SELECT * FROM account WHERE email = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Account(
                        rs.getString("email"),
                        rs.getString("password")
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
