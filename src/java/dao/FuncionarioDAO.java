package dao;
import model.Funcionario;
import java.util.ArrayList;

import java.sql.*;

public class FuncionarioDAO {

    private static final String BUSCAR_POR_ID  = "SELECT * FROM funcionarios WHERE id = ?";
    private static final String BUSCAR_POR_CREDENCIAIS  = "SELECT * FROM funcionarios WHERE cpf = ? and senha = ?";
    private static final String LISTAR  = "SELECT * FROM funcionarios";
    private static final String ALTERAR = "UPDATE funcionarios SET nome = ?, cpf = ?, senha = ?, papel = ? WHERE id = ?;";
    private static final String INSERIR = "INSERT INTO funcionarios (id, nome, cpf, senha, papel) VALUES (?, ?, ?, ?, ?)";
    private static final String DELETAR = "DELETE FROM funcionarios WHERE id = ?";

    /* ---------------------------------------------------------------------- */

    private static Connection conexao = null;

    public FuncionarioDAO() { FuncionarioDAO.conexao = Conexao.iniciarConexao(); }

    /* ---------------------------------------------------------------------- */

    public static void encerrarConexao() throws SQLException {

        FuncionarioDAO.conexao.close();

    }

    /* ---------------------------------------------------------------------- */

    public static Funcionario buscarPorId(int id) throws SQLException {

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

    /* ---------------------------------------------------------------------- */

    public static ArrayList<Funcionario> listar() throws SQLException {

        ArrayList<Funcionario> lista = new ArrayList<>();

        PreparedStatement pstmt = FuncionarioDAO.conexao.prepareStatement(LISTAR);
        ResultSet resultado = pstmt.executeQuery();

        while ( resultado.next() ) {

            Funcionario func = new Funcionario();
            
            func.setId(resultado.getInt("id"));
            func.setNome(resultado.getString("nome"));
            func.setCpf(resultado.getString("cpf"));
            func.setSenha(resultado.getString("senha"));
            func.setPapel(Funcionario.Papel.values()[Integer.parseInt(resultado.getString("papel"))]);

            lista.add(func);

        }

        pstmt.close();

        return lista;

    }

    /* ---------------------------------------------------------------------- */

    public static boolean inserir(Funcionario func) throws SQLException {

        int linhasAfetadas = 0;

        PreparedStatement pstmt = FuncionarioDAO.conexao.prepareStatement(INSERIR);
            pstmt.setInt(1, func.getId());
            pstmt.setString(2, func.getNome());
            pstmt.setString(3, func.getCpf());
            pstmt.setString(4, func.getSenha());
            pstmt.setInt(5, func.getPapel().ordinal());
        linhasAfetadas = pstmt.executeUpdate();
        pstmt.close();

        if (linhasAfetadas > 0) { return true; }
        else { return false; }

    }

    /* ---------------------------------------------------------------------- */

    public static boolean alterar(Funcionario func) throws SQLException {

        int linhasAfetadas = 0;

        PreparedStatement pstmt = FuncionarioDAO.conexao.prepareStatement(ALTERAR);
            pstmt.setString(1, func.getNome());
            pstmt.setString(2, func.getCpf());
            pstmt.setString(3, func.getSenha());
            pstmt.setInt(4, func.getPapel().ordinal());
            pstmt.setInt(5, func.getId());
        linhasAfetadas = pstmt.executeUpdate();
        pstmt.close();

        if (linhasAfetadas > 0) { return true; }
        else { return false; }

    }

    /* ---------------------------------------------------------------------- */

    public static boolean deletar(int id) throws SQLException {

        int linhasAfetadas = 0;

        PreparedStatement pstmt = FuncionarioDAO.conexao.prepareStatement(DELETAR);
            pstmt.setInt(1, id);
        linhasAfetadas = pstmt.executeUpdate();
        pstmt.close();

        if (linhasAfetadas > 0) { return true; }
        else { return false; }

    }

}