package dao;
import model.Categoria;
import java.util.ArrayList;

import java.sql.*;

public class CategoriaDAO {

    private static final String BUSCAR_POR_ID = "SELECT * FROM categorias WHERE id = ?";
    private static final String LISTAR  = "SELECT * FROM categorias ORDER BY id ASC";
    private static final String ALTERAR = "UPDATE categorias SET nome_categoria = ? WHERE id = ?;";
    private static final String INSERIR = "INSERT INTO categorias (nome_categoria) VALUES (?)";
    private static final String DELETAR = "DELETE FROM categorias WHERE id = ?";

    /* ---------------------------------------------------------------------- */

    private static Connection conexao = null;

    public CategoriaDAO() { CategoriaDAO.conexao = Conexao.iniciarConexao(); }

    /* ---------------------------------------------------------------------- */

    public static Categoria buscarPorId(int id) throws SQLException {

        Categoria cat = null;

        PreparedStatement pstmt = CategoriaDAO.conexao.prepareStatement(BUSCAR_POR_ID);
            pstmt.setInt(1, id);
        ResultSet resultado = pstmt.executeQuery();
        
        if ( resultado.isBeforeFirst() && resultado.next() ) {

            cat = new Categoria();
            
            cat.setId(resultado.getInt("id"));
            cat.setNomeCategoria(resultado.getString("nome_categoria"));

        }

        pstmt.close();

        return cat;

    }

    /* ---------------------------------------------------------------------- */

    public static ArrayList<Categoria> listar() throws SQLException {

        ArrayList<Categoria> lista = new ArrayList<>();

        PreparedStatement pstmt = CategoriaDAO.conexao.prepareStatement(LISTAR);
        ResultSet resultado = pstmt.executeQuery();

        while ( resultado.next() ) {

            Categoria cat = new Categoria();

            cat.setId(resultado.getInt("id"));
            cat.setNomeCategoria(resultado.getString("nome_categoria"));

            lista.add(cat);

        }

        pstmt.close();

        return lista;

    }

    /* ---------------------------------------------------------------------- */

    public static boolean inserir(Categoria cat) throws SQLException {

        int linhasAfetadas = 0;

        PreparedStatement pstmt = CategoriaDAO.conexao.prepareStatement(INSERIR);
            pstmt.setString(1, cat.getNomeCategoria()    );
        linhasAfetadas = pstmt.executeUpdate();
        pstmt.close();

        if (linhasAfetadas > 0) { return true; }
        else { return false; }

    }

    /* ---------------------------------------------------------------------- */

    public static boolean alterar(Categoria cat) throws SQLException {

        int linhasAfetadas = 0;

        PreparedStatement pstmt = CategoriaDAO.conexao.prepareStatement(ALTERAR);
            pstmt.setString(1, cat.getNomeCategoria()    );
            pstmt.setInt(2, cat.getId()    );
        linhasAfetadas = pstmt.executeUpdate();
        pstmt.close();

        if (linhasAfetadas > 0) { return true; }
        else { return false; }

    }

    /* ---------------------------------------------------------------------- */

    public static boolean deletar(int id) throws SQLException {

        int linhasAfetadas = 0;

        PreparedStatement pstmt = CategoriaDAO.conexao.prepareStatement(DELETAR);
            pstmt.setInt(1, id);
        linhasAfetadas = pstmt.executeUpdate();
        pstmt.close();

        if (linhasAfetadas > 0) { return true; }
        else { return false; }

    }

}