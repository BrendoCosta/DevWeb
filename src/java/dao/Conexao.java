package dao;

import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

public class Conexao {

    public static Connection iniciarConexao() {

        try {

            // Carrega o driver para o MySQL
        
            Class.forName("com.mysql.jdbc.Driver");
            
            // Inicia conexão com o banco de dados

            Connection cnx = null;

            cnx = java.sql.DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/estoque",
                "root",
                ""
            );

            return cnx;

        } catch (ClassNotFoundException e) {

            System.out.println("Não foi possível encontrar o driver JDBC!");
            e.printStackTrace();

        } catch (SQLException e) {

            System.out.println("Não foi possível conectar ao banco!");
            e.printStackTrace();

        }

        return null;

    }

}