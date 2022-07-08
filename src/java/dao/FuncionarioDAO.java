package dao;
import model.Funcionario;
import java.util.ArrayList;

import java.sql.*;

public class FuncionarioDAO {

    private static final String BUSCAR_POR_ID  = "SELECT * FROM funcionarios WHERE id = ?";
    private static final String BUSCAR_POR_CREDENCIAIS  = "SELECT * FROM funcionarios WHERE cpf = ? and senha = ?";
    private static final String LISTAR  = "SELECT * FROM funcionarios";
    private static final String ALTERAR = "UPDATE funcionarios SET nome = ?, cpf = ?, senha = ?, papel = ? WHERE id = ?;";
    private static final String INSERIR = "INSERT INTO funcionarios (nome, cpf, senha, papel) VALUES (?, ?, ?, ?)";
    private static final String DELETAR = "DELETE FROM funcionarios WHERE id = ?";

    /* ---------------------------------------------------------------------- */

    private static Connection conexao = null;

    public FuncionarioDAO() { FuncionarioDAO.conexao = Conexao.iniciarConexao(); }

    /* ---------------------------------------------------------------------- */

    public static Funcionario buscar(int id) throws SQLException {

        Funcionario func = null;

        PreparedStatement pstmt = FuncionarioDAO.conexao.prepareStatement(BUSCAR_POR_ID);
            pstmt.setInt(1, id);
        ResultSet resultado = pstmt.executeQuery();
        
        if ( resultado.isBeforeFirst() && resultado.next() ) {

            func = new Funcionario();
            
            func.setId(resultado.getInt("id"));
            func.setNome(resultado.getString("nome"));
            func.setCpf(resultado.getString("cpf"));
            func.setSenha(resultado.getString("senha"));
            func.setPapel(Funcionario.Papel.values()[resultado.getInt("papel")]);

        }

        pstmt.close();

        return func;

    }

    /* ---------------------------------------------------------------------- */

    public static Funcionario buscarPorCredenciais(String cpf, String senha) throws SQLException {    

        Funcionario func = null;

        PreparedStatement pstmt = FuncionarioDAO.conexao.prepareStatement(BUSCAR_POR_CREDENCIAIS);
            pstmt.setString(1, cpf);
            pstmt.setString(2, senha);
        ResultSet resultado = pstmt.executeQuery();

        if ( resultado.isBeforeFirst() && resultado.next() ) {

            func = new Funcionario();
            
            func.setId(resultado.getInt("id"));
            func.setNome(resultado.getString("nome"));
            func.setCpf(resultado.getString("cpf"));
            func.setSenha(resultado.getString("senha"));
            func.setPapel(Funcionario.Papel.values()[resultado.getInt("papel")]);

        }

        pstmt.close();

        return func;

    }

}