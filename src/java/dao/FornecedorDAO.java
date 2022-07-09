package dao;
import model.Fornecedor;
import java.util.ArrayList;

import java.sql.*;

public class FornecedorDAO {

    private static final String BUSCAR_POR_ID = "SELECT * FROM fornecedores WHERE id = ?";
    private static final String BUSCAR_POR_CNPJ = "SELECT * FROM fornecedores WHERE cnpj = ?";
    private static final String LISTAR  = "SELECT * FROM fornecedores ORDER BY id ASC";
    private static final String ALTERAR = "UPDATE fornecedores SET razao_social = ?, cnpj = ?, endereco = ?, bairro = ?, cidade = ?, uf = ?, cep = ?, telefone = ?, email = ? WHERE id = ?;";
    private static final String INSERIR = "INSERT INTO fornecedores (razao_social, cnpj, endereco, bairro, cidade, uf, cep, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETAR = "DELETE FROM fornecedores WHERE id = ?";

    /* ---------------------------------------------------------------------- */

    private static Connection conexao = null;

    public FornecedorDAO() { FornecedorDAO.conexao = Conexao.iniciarConexao(); }

    /* ---------------------------------------------------------------------- */

    public static void encerrarConexao() throws SQLException {

        FornecedorDAO.conexao.close();

    }

    /* ---------------------------------------------------------------------- */

    public static Fornecedor buscarPorId(int id) throws SQLException {

        Fornecedor forn = null;

        PreparedStatement pstmt = FornecedorDAO.conexao.prepareStatement(BUSCAR_POR_ID);
            pstmt.setInt(1, id);
        ResultSet resultado = pstmt.executeQuery();
        
        if ( resultado.isBeforeFirst() && resultado.next() ) {

            forn = new Fornecedor();
            
            forn.setId(resultado.getInt("id"));
            forn.setRazaoSocial(resultado.getString("razao_social"));
            forn.setCnpj(resultado.getString("cnpj"));
            forn.setEndereco(resultado.getString("endereco"));
            forn.setBairro(resultado.getString("bairro"));
            forn.setCidade(resultado.getString("cidade"));
            forn.setUf(resultado.getString("uf"));
            forn.setCep(resultado.getString("cep"));
            forn.setTelefone(resultado.getString("telefone"));
            forn.setEmail(resultado.getString("email"));

        }

        pstmt.close();

        return forn;

    }

    /* ---------------------------------------------------------------------- */

    public static Fornecedor buscarPorCnpj(String cnpj) throws SQLException {

        Fornecedor forn = null;

        PreparedStatement pstmt = FornecedorDAO.conexao.prepareStatement(BUSCAR_POR_CNPJ);
            pstmt.setString(1, cnpj);
        ResultSet resultado = pstmt.executeQuery();
        
        if ( resultado.isBeforeFirst() && resultado.next() ) {

            forn = new Fornecedor();
            
            forn.setId(resultado.getInt("id"));
            forn.setRazaoSocial(resultado.getString("razao_social"));
            forn.setCnpj(resultado.getString("cnpj"));
            forn.setEndereco(resultado.getString("endereco"));
            forn.setBairro(resultado.getString("bairro"));
            forn.setCidade(resultado.getString("cidade"));
            forn.setUf(resultado.getString("uf"));
            forn.setCep(resultado.getString("cep"));
            forn.setTelefone(resultado.getString("telefone"));
            forn.setEmail(resultado.getString("email"));

        }

        pstmt.close();

        return forn;

    }

    /* ---------------------------------------------------------------------- */

    public static ArrayList<Fornecedor> listar() throws SQLException {

        ArrayList<Fornecedor> lista = new ArrayList<>();

        PreparedStatement pstmt = FornecedorDAO.conexao.prepareStatement(LISTAR);
        ResultSet resultado = pstmt.executeQuery();

        while ( resultado.next() ) {

            Fornecedor forn = new Fornecedor();

            forn.setId(resultado.getInt("id"));
            forn.setRazaoSocial(resultado.getString("razao_social"));
            forn.setCnpj(resultado.getString("cnpj"));
            forn.setEndereco(resultado.getString("endereco"));
            forn.setBairro(resultado.getString("bairro"));
            forn.setCidade(resultado.getString("cidade"));
            forn.setUf(resultado.getString("uf"));
            forn.setCep(resultado.getString("cep"));
            forn.setTelefone(resultado.getString("telefone"));
            forn.setEmail(resultado.getString("email"));

            lista.add(forn);

        }

        pstmt.close();

        return lista;

    }

    /* ---------------------------------------------------------------------- */

    public static boolean inserir(Fornecedor forn) throws SQLException {

        int linhasAfetadas = 0;

        PreparedStatement pstmt = FornecedorDAO.conexao.prepareStatement(INSERIR);
            pstmt.setString(1, forn.getRazaoSocial()    );
            pstmt.setString(2, forn.getCnpj()           );
            pstmt.setString(3, forn.getEndereco()       );
            pstmt.setString(4, forn.getBairro()         );
            pstmt.setString(5, forn.getCidade()         );
            pstmt.setString(6, forn.getUf()             );
            pstmt.setString(7, forn.getCep()            );
            pstmt.setString(8, forn.getTelefone()       );
            pstmt.setString(9, forn.getEmail()          );
        linhasAfetadas = pstmt.executeUpdate();
        pstmt.close();

        if (linhasAfetadas > 0) { return true; }
        else { return false; }

    }

    /* ---------------------------------------------------------------------- */

    public static boolean alterar(Fornecedor forn) throws SQLException {

        int linhasAfetadas = 0;

        PreparedStatement pstmt = FornecedorDAO.conexao.prepareStatement(ALTERAR);
            pstmt.setString(1, forn.getRazaoSocial()    );
            pstmt.setString(2, forn.getCnpj()           );
            pstmt.setString(3, forn.getEndereco()       );
            pstmt.setString(4, forn.getBairro()         );
            pstmt.setString(5, forn.getCidade()         );
            pstmt.setString(6, forn.getUf()             );
            pstmt.setString(7, forn.getCep()            );
            pstmt.setString(8, forn.getTelefone()       );
            pstmt.setString(9, forn.getEmail()          );
            pstmt.setInt(  10, forn.getId()             );
        linhasAfetadas = pstmt.executeUpdate();
        pstmt.close();

        if (linhasAfetadas > 0) { return true; }
        else { return false; }

    }

    /* ---------------------------------------------------------------------- */

    public static boolean deletar(int id) throws SQLException {

        int linhasAfetadas = 0;

        PreparedStatement pstmt = FornecedorDAO.conexao.prepareStatement(DELETAR);
            pstmt.setInt(1, id);
        linhasAfetadas = pstmt.executeUpdate();
        pstmt.close();

        if (linhasAfetadas > 0) { return true; }
        else { return false; }

    }

}