package dao;
import model.Cliente;
import java.util.ArrayList;

import java.sql.*;

public class ClienteDAO {

    private static final String BUSCAR_POR_ID  = "SELECT * FROM clientes WHERE id = ?";
    private static final String BUSCAR_POR_CPF = "SELECT * FROM clientes WHERE cpf = ?";
    private static final String LISTAR  = "SELECT * FROM clientes";
    private static final String ALTERAR = "UPDATE clientes SET nome = ?, cpf = ?, endereco = ?, bairro = ?, cidade = ?, uf = ?, cep = ?, telefone = ?, email = ? WHERE id = ?;";
    private static final String INSERIR = "INSERT INTO clientes (nome, cpf, endereco, bairro, cidade, uf, cep, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETAR = "DELETE FROM clientes WHERE id = ?";

    /* ---------------------------------------------------------------------- */

    private static Connection conexao = null;

    public ClienteDAO() { ClienteDAO.conexao = Conexao.iniciarConexao(); }

    /* ---------------------------------------------------------------------- */

    public static Cliente buscarPorId(int id) throws SQLException {

        Cliente clnt = new Cliente();

        PreparedStatement pstmt = ClienteDAO.conexao.prepareStatement(BUSCAR_POR_ID);
            pstmt.setInt(1, id);
        ResultSet resultado = pstmt.executeQuery();
        
        if ( resultado.isBeforeFirst() && resultado.next() ) {
            
            clnt.setId(resultado.getInt("id"));
            clnt.setNome(resultado.getString("nome"));
            clnt.setCpf(resultado.getString("cpf"));
            clnt.setEndereco(resultado.getString("endereco"));
            clnt.setBairro(resultado.getString("bairro"));
            clnt.setCidade(resultado.getString("cidade"));
            clnt.setUf(resultado.getString("uf"));
            clnt.setCep(resultado.getString("cep"));
            clnt.setTelefone(resultado.getString("telefone"));
            clnt.setEmail(resultado.getString("email"));

        }

        pstmt.close();

        return clnt;

    }

    /* ---------------------------------------------------------------------- */

    public static Cliente buscarPorCpf(String cpf) throws SQLException {

        Cliente clnt = null;

        PreparedStatement pstmt = ClienteDAO.conexao.prepareStatement(BUSCAR_POR_CPF);
            pstmt.setString(1, cpf);
        ResultSet resultado = pstmt.executeQuery();
        
        if ( resultado.isBeforeFirst() && resultado.next() ) {
            
            clnt = new Cliente();

            clnt.setId(resultado.getInt("id"));
            clnt.setNome(resultado.getString("nome"));
            clnt.setCpf(resultado.getString("cpf"));
            clnt.setEndereco(resultado.getString("endereco"));
            clnt.setBairro(resultado.getString("bairro"));
            clnt.setCidade(resultado.getString("cidade"));
            clnt.setUf(resultado.getString("uf"));
            clnt.setCep(resultado.getString("cep"));
            clnt.setTelefone(resultado.getString("telefone"));
            clnt.setEmail(resultado.getString("email"));

        }

        pstmt.close();

        return clnt;

    }

    /* ---------------------------------------------------------------------- */

    public static ArrayList<Cliente> listar() throws SQLException {

        ArrayList<Cliente> lista = new ArrayList<>();

        PreparedStatement pstmt = ClienteDAO.conexao.prepareStatement(LISTAR);
        ResultSet resultado = pstmt.executeQuery();

        while ( resultado.next() ) {

            Cliente clnt = new Cliente();
            
            clnt.setId(resultado.getInt("id"));
            clnt.setNome(resultado.getString("nome"));
            clnt.setCpf(resultado.getString("cpf"));
            clnt.setEndereco(resultado.getString("endereco"));
            clnt.setBairro(resultado.getString("bairro"));
            clnt.setCidade(resultado.getString("cidade"));
            clnt.setUf(resultado.getString("uf"));
            clnt.setCep(resultado.getString("cep"));
            clnt.setTelefone(resultado.getString("telefone"));
            clnt.setEmail(resultado.getString("email"));

            lista.add(clnt);

        }

        pstmt.close();

        return lista;

    }

    /* ---------------------------------------------------------------------- */

    public static boolean inserir(Cliente clnt) throws SQLException {

        int linhasAfetadas = 0;

        PreparedStatement pstmt = ClienteDAO.conexao.prepareStatement(INSERIR);
            pstmt.setString(1, clnt.getNome());
            pstmt.setString(2, clnt.getCpf());
            pstmt.setString(3, clnt.getEndereco());
            pstmt.setString(4, clnt.getBairro());
            pstmt.setString(5, clnt.getCidade());
            pstmt.setString(6, clnt.getUf());
            pstmt.setString(7, clnt.getCep());
            pstmt.setString(8, clnt.getTelefone());
            pstmt.setString(9, clnt.getEmail());
        linhasAfetadas = pstmt.executeUpdate();
        pstmt.close();

        if (linhasAfetadas > 0) { return true; }
        else { return false; }

    }

    /* ---------------------------------------------------------------------- */

    public static boolean alterar(Cliente clnt) throws SQLException {

        int linhasAfetadas = 0;

        PreparedStatement pstmt = ClienteDAO.conexao.prepareStatement(ALTERAR);
            pstmt.setString(1, clnt.getNome());
            pstmt.setString(2, clnt.getCpf());
            pstmt.setString(3, clnt.getEndereco());
            pstmt.setString(4, clnt.getBairro());
            pstmt.setString(5, clnt.getCidade());
            pstmt.setString(6, clnt.getUf());
            pstmt.setString(7, clnt.getCep());
            pstmt.setString(8, clnt.getTelefone());
            pstmt.setString(9, clnt.getEmail());
            pstmt.setInt(10, clnt.getId());
        linhasAfetadas = pstmt.executeUpdate();
        pstmt.close();

        if (linhasAfetadas > 0) { return true; }
        else { return false; }

    }

    /* ---------------------------------------------------------------------- */

    public static boolean deletar(int id) throws SQLException {

        int linhasAfetadas = 0;

        PreparedStatement pstmt = ClienteDAO.conexao.prepareStatement(DELETAR);
            pstmt.setInt(1, id);
        linhasAfetadas = pstmt.executeUpdate();
        pstmt.close();

        if (linhasAfetadas > 0) { return true; }
        else { return false; }

    }

}