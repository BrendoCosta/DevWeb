package dao;
import model.Venda;
import java.util.ArrayList;

import java.sql.*;

public class VendaDAO {

    private static final String BUSCAR_POR_ID = "SELECT * FROM vendas WHERE id = ?";
    private static final String BUSCAR_POR_VENDEDOR = "SELECT * FROM vendas WHERE id_funcionario = ? ORDER BY data_venda DESC";
    private static final String LISTAR_POR_CLIENTE = "SELECT * FROM vendas WHERE id_cliente = ? ORDER BY data_venda DESC";
    private static final String LISTAR  = "SELECT * FROM vendas ORDER BY data_venda DESC";
    private static final String ALTERAR = "UPDATE vendas SET quantidade_venda = ?, data_venda = ?, valor_venda = ?, id_cliente = ?, id_produto = ?, id_funcionario = ? WHERE id = ?;";
    private static final String INSERIR = "INSERT INTO vendas (quantidade_venda, data_venda, valor_venda, id_cliente, id_produto, id_funcionario) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETAR = "DELETE FROM vendas WHERE id = ?";

    /* ---------------------------------------------------------------------- */

    private static Connection conexao = null;

    public VendaDAO() { VendaDAO.conexao = Conexao.iniciarConexao(); }

    /* ---------------------------------------------------------------------- */

    public static void encerrarConexao() throws SQLException {

        VendaDAO.conexao.close();

    }

    /* ---------------------------------------------------------------------- */

    public static Venda buscarPorId(int idVenda) throws SQLException {

        Venda ven = null;

        PreparedStatement pstmt = VendaDAO.conexao.prepareStatement(BUSCAR_POR_ID);
            pstmt.setInt(1, idVenda);
        ResultSet resultado = pstmt.executeQuery();
        
        if ( resultado.isBeforeFirst() && resultado.next() ) {

            ven = new Venda();
            
            ven.setId(resultado.getInt("id"));
            ven.setQuantidadeVenda(resultado.getInt("quantidade_venda"));
            ven.setDataVenda(resultado.getString("data_venda"));
            ven.setValorVenda(resultado.getFloat("valor_venda"));
            ven.setIdCliente(resultado.getInt("id_cliente"));
            ven.setIdProduto(resultado.getInt("id_produto"));
            ven.setIdFuncionario(resultado.getInt("id_funcionario"));

        }

        pstmt.close();

        return ven;

    }

    /* ---------------------------------------------------------------------- */

    public static Venda buscarPorVendedor(int idVendedor) throws SQLException {

        Venda ven = new Venda();

        PreparedStatement pstmt = VendaDAO.conexao.prepareStatement(BUSCAR_POR_VENDEDOR);
            pstmt.setInt(1, idVendedor);
        ResultSet resultado = pstmt.executeQuery();
        
        if ( resultado.isBeforeFirst() && resultado.next() ) {
            
            ven.setId(resultado.getInt("id"));
            ven.setQuantidadeVenda(resultado.getInt("quantidade_venda"));
            ven.setDataVenda(resultado.getString("data_venda"));
            ven.setValorVenda(resultado.getFloat("valor_venda"));
            ven.setIdCliente(resultado.getInt("id_cliente"));
            ven.setIdProduto(resultado.getInt("id_produto"));
            ven.setIdFuncionario(resultado.getInt("id_funcionario"));

        }

        pstmt.close();

        return ven;

    }

    /* ---------------------------------------------------------------------- */

    public static ArrayList<Venda> listarPorCliente(int idCliente) throws SQLException {

        ArrayList<Venda> lista = new ArrayList<>();

        PreparedStatement pstmt = VendaDAO.conexao.prepareStatement(LISTAR_POR_CLIENTE);
            pstmt.setInt(1, idCliente);
        ResultSet resultado = pstmt.executeQuery();

        while ( resultado.next() ) {

            Venda ven = new Venda();

            ven.setId(resultado.getInt("id"));
            ven.setQuantidadeVenda(resultado.getInt("quantidade_venda"));
            ven.setDataVenda(resultado.getString("data_venda"));
            ven.setValorVenda(resultado.getFloat("valor_venda"));
            ven.setIdCliente(resultado.getInt("id_cliente"));
            ven.setIdProduto(resultado.getInt("id_produto"));
            ven.setIdFuncionario(resultado.getInt("id_funcionario"));

            lista.add(ven);

        }

        pstmt.close();

        return lista;

    }

    /* ---------------------------------------------------------------------- */

    public static ArrayList<Venda> listar() throws SQLException {

        ArrayList<Venda> lista = new ArrayList<>();

        PreparedStatement pstmt = VendaDAO.conexao.prepareStatement(LISTAR);
        ResultSet resultado = pstmt.executeQuery();

        while ( resultado.next() ) {

            Venda ven = new Venda();

            ven.setId(resultado.getInt("id"));
            ven.setQuantidadeVenda(resultado.getInt("quantidade_venda"));
            ven.setDataVenda(resultado.getString("data_venda"));
            ven.setValorVenda(resultado.getFloat("valor_venda"));
            ven.setIdCliente(resultado.getInt("id_cliente"));
            ven.setIdProduto(resultado.getInt("id_produto"));
            ven.setIdFuncionario(resultado.getInt("id_funcionario"));

            lista.add(ven);

        }

        pstmt.close();

        return lista;

    }

    /* ---------------------------------------------------------------------- */

    public static boolean inserir(Venda ven) throws SQLException {

        int linhasAfetadas = 0;

        PreparedStatement pstmt = VendaDAO.conexao.prepareStatement(INSERIR);
            pstmt.setInt(1, ven.getQuantidadeVenda());
            pstmt.setString(2, ven.getDataVenda());
            pstmt.setFloat(3, ven.getValorVenda());
            pstmt.setInt(4, ven.getIdCliente());
            pstmt.setInt(5, ven.getIdProduto());
            pstmt.setInt(6, ven.getIdFuncionario());
        linhasAfetadas = pstmt.executeUpdate();
        pstmt.close();

        if (linhasAfetadas > 0) { return true; }
        else { return false; }

    }

    /* ---------------------------------------------------------------------- */

    public static boolean alterar(Venda ven) throws SQLException {

        int linhasAfetadas = 0;

        PreparedStatement pstmt = VendaDAO.conexao.prepareStatement(ALTERAR);
            pstmt.setInt(1, ven.getQuantidadeVenda());
            pstmt.setString(2, ven.getDataVenda());
            pstmt.setFloat(3, ven.getValorVenda());
            pstmt.setInt(4, ven.getIdCliente());
            pstmt.setInt(5, ven.getIdProduto());
            pstmt.setInt(6, ven.getIdFuncionario());
            pstmt.setInt(7, ven.getId());
        linhasAfetadas = pstmt.executeUpdate();
        pstmt.close();

        if (linhasAfetadas > 0) { return true; }
        else { return false; }

    }

    /* ---------------------------------------------------------------------- */

    public static boolean deletar(int idVenda) throws SQLException {

        int linhasAfetadas = 0;

        PreparedStatement pstmt = VendaDAO.conexao.prepareStatement(DELETAR);
            pstmt.setInt(1, idVenda);
        linhasAfetadas = pstmt.executeUpdate();
        pstmt.close();

        if (linhasAfetadas > 0) { return true; }
        else { return false; }

    }

}