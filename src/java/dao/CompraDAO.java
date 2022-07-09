package dao;
import model.Compra;
import java.util.ArrayList;

import java.sql.*;

public class CompraDAO {

    private static final String BUSCAR_POR_ID = "SELECT * FROM compras WHERE id = ?";
    private static final String LISTAR_POR_FORNECEDOR  = "SELECT * FROM compras WHERE id_fornecedor = ? ORDER BY data_compra DESC";
    private static final String LISTAR  = "SELECT * FROM compras ORDER BY data_compra DESC";
    private static final String ALTERAR = "UPDATE compras SET quantidade_compra = ?, data_compra = ?, valor_compra = ?, id_fornecedor = ?, id_produto = ?, id_funcionario = ? WHERE id = ?;";
    private static final String INSERIR = "INSERT INTO compras (quantidade_compra, data_compra, valor_compra, id_fornecedor, id_produto, id_funcionario) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETAR = "DELETE FROM compras WHERE id = ?";

    /* ---------------------------------------------------------------------- */

    private static Connection conexao = null;

    public CompraDAO() { CompraDAO.conexao = Conexao.iniciarConexao(); }

    /* ---------------------------------------------------------------------- */

    public static void encerrarConexao() throws SQLException {

        CompraDAO.conexao.close();

    }

    /* ---------------------------------------------------------------------- */

    public static Compra buscarPorId(int id) throws SQLException {

        Compra comp = null;

        PreparedStatement pstmt = CompraDAO.conexao.prepareStatement(BUSCAR_POR_ID);
            pstmt.setInt(1, id);
        ResultSet resultado = pstmt.executeQuery();
        
        if ( resultado.isBeforeFirst() && resultado.next() ) {

            comp = new Compra();
            
            comp.setId(resultado.getInt("id"));
            comp.setQuantidadeCompra(resultado.getInt("quantidade_compra"));
            comp.setDataCompra(resultado.getString("data_compra"));
            comp.setValorCompra(resultado.getInt("valor_compra"));
            comp.setIdFornecedor(resultado.getInt("id_fornecedor"));
            comp.setIdProduto(resultado.getInt("id_produto"));
            comp.setIdFuncionario(resultado.getInt("id_funcionario"));

        }

        pstmt.close();

        return comp;

    }

    /* ---------------------------------------------------------------------- */

    public static ArrayList<Compra> listarPorFornecedor(int idFornecedor) throws SQLException {

        ArrayList<Compra> lista = new ArrayList<>();

        PreparedStatement pstmt = CompraDAO.conexao.prepareStatement(LISTAR_POR_FORNECEDOR);
            pstmt.setInt(1, idFornecedor);
        ResultSet resultado = pstmt.executeQuery();

        while ( resultado.next() ) {

            Compra comp = new Compra();

            comp.setId(resultado.getInt("id"));
            comp.setQuantidadeCompra(resultado.getInt("quantidade_compra"));
            comp.setDataCompra(resultado.getString("data_compra"));
            comp.setValorCompra(resultado.getInt("valor_compra"));
            comp.setIdFornecedor(resultado.getInt("id_fornecedor"));
            comp.setIdProduto(resultado.getInt("id_produto"));
            comp.setIdFuncionario(resultado.getInt("id_funcionario"));

            lista.add(comp);

        }

        pstmt.close();

        return lista;

    }

    /* ---------------------------------------------------------------------- */

    public static ArrayList<Compra> listar() throws SQLException {

        ArrayList<Compra> lista = new ArrayList<>();

        PreparedStatement pstmt = CompraDAO.conexao.prepareStatement(LISTAR);
        ResultSet resultado = pstmt.executeQuery();

        while ( resultado.next() ) {

            Compra comp = new Compra();

            comp.setId(resultado.getInt("id"));
            comp.setQuantidadeCompra(resultado.getInt("quantidade_compra"));
            comp.setDataCompra(resultado.getString("data_compra"));
            comp.setValorCompra(resultado.getInt("valor_compra"));
            comp.setIdFornecedor(resultado.getInt("id_fornecedor"));
            comp.setIdProduto(resultado.getInt("id_produto"));
            comp.setIdFuncionario(resultado.getInt("id_funcionario"));

            lista.add(comp);

        }

        pstmt.close();

        return lista;

    }

    /* ---------------------------------------------------------------------- */

    public static boolean inserir(Compra comp) throws SQLException {

        int linhasAfetadas = 0;

        PreparedStatement pstmt = CompraDAO.conexao.prepareStatement(INSERIR);
            pstmt.setInt(1, comp.getQuantidadeCompra()  );
         pstmt.setString(2, comp.getDataCompra()        );
            pstmt.setInt(3, comp.getValorCompra()       );
            pstmt.setInt(4, comp.getIdFornecedor()      );
            pstmt.setInt(5, comp.getIdProduto()         );
            pstmt.setInt(6, comp.getIdFuncionario()     );
        linhasAfetadas = pstmt.executeUpdate();
        pstmt.close();

        if (linhasAfetadas > 0) { return true; }
        else { return false; }

    }

    /* ---------------------------------------------------------------------- */

    public static boolean alterar(Compra comp) throws SQLException {

        int linhasAfetadas = 0;

        PreparedStatement pstmt = CompraDAO.conexao.prepareStatement(ALTERAR);
            pstmt.setInt(1, comp.getQuantidadeCompra()  );
         pstmt.setString(2, comp.getDataCompra()        );
            pstmt.setInt(3, comp.getValorCompra()       );
            pstmt.setInt(4, comp.getIdFornecedor()      );
            pstmt.setInt(5, comp.getIdProduto()         );
            pstmt.setInt(6, comp.getIdFuncionario()     );
            pstmt.setInt(7, comp.getId()                );
        linhasAfetadas = pstmt.executeUpdate();
        pstmt.close();

        if (linhasAfetadas > 0) { return true; }
        else { return false; }

    }

    /* ---------------------------------------------------------------------- */

    public static boolean deletar(int id) throws SQLException {

        int linhasAfetadas = 0;

        PreparedStatement pstmt = CompraDAO.conexao.prepareStatement(DELETAR);
            pstmt.setInt(1, id);
        linhasAfetadas = pstmt.executeUpdate();
        pstmt.close();

        if (linhasAfetadas > 0) { return true; }
        else { return false; }

    }

}