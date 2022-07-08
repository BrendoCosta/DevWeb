package dao;
import model.Produto;
import java.util.ArrayList;

import java.sql.*;

public class ProdutoDAO {

    private static final String BUSCAR_POR_ID = "SELECT * FROM produtos WHERE id = ? ORDER BY id ASC";
    private static final String LISTAR  = "SELECT * FROM produtos ORDER BY id ASC";
    private static final String LISTAR_DISPONIVEIS  = "SELECT * FROM produtos WHERE quantidade_disponível > 0 AND liberado_venda = 'S' ORDER BY id ASC";
    private static final String ALTERAR = "UPDATE produtos SET nome_produto = ?, descricao = ?, preco_compra = ?, preco_venda = ?, quantidade_disponível = ?, liberado_venda = ?, id_categoria = ? WHERE id = ?;";
    private static final String INSERIR = "INSERT INTO produtos (nome_produto, descricao, preco_compra, preco_venda, quantidade_disponível, liberado_venda, id_categoria) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String REALIZAR_VENDA = "UPDATE produtos SET quantidade_disponível = quantidade_disponível - ? WHERE id = ?";
    private static final String DELETAR = "DELETE FROM produtos WHERE id = ?";

    /* ---------------------------------------------------------------------- */

    private static Connection conexao = null;

    public ProdutoDAO() { ProdutoDAO.conexao = Conexao.iniciarConexao(); }

    /* ---------------------------------------------------------------------- */

    public static Produto buscarPorId(int idProduto) throws SQLException {

        Produto prod = null;

        PreparedStatement pstmt = ProdutoDAO.conexao.prepareStatement(BUSCAR_POR_ID);
            pstmt.setInt(1, idProduto);
        ResultSet resultado = pstmt.executeQuery();
        
        if ( resultado.isBeforeFirst() && resultado.next() ) {

            prod = new Produto();
            
            prod.setId(resultado.getInt("id"));
            prod.setNomeProduto(resultado.getString("nome_produto"));
            prod.setDescricao(resultado.getString("descricao"));
            prod.setPrecoCompra(resultado.getDouble("preco_compra"));
            prod.setPrecoVenda(resultado.getDouble("preco_venda"));
            prod.setQuantidadeDisponivel(resultado.getInt("quantidade_disponível"));
            prod.setLiberadoVenda(Produto.Liberado.valueOf(resultado.getString("liberado_venda")));
            prod.setIdCategoria(resultado.getInt("id_categoria"));

        }

        pstmt.close();

        return prod;

    }

    /* ---------------------------------------------------------------------- */

    public static ArrayList<Produto> listar() throws SQLException {

        return listar(false);

    }

    public static ArrayList<Produto> listar(boolean apenasDisponiveis) throws SQLException {

        ArrayList<Produto> lista = new ArrayList<>();

        PreparedStatement pstmt = apenasDisponiveis ? ProdutoDAO.conexao.prepareStatement(LISTAR_DISPONIVEIS) : ProdutoDAO.conexao.prepareStatement(LISTAR);
        ResultSet resultado = pstmt.executeQuery();

        while ( resultado.next() ) {

            Produto prod = new Produto();

            prod.setId(resultado.getInt("id"));
            prod.setNomeProduto(resultado.getString("nome_produto"));
            prod.setDescricao(resultado.getString("descricao"));
            prod.setPrecoCompra(resultado.getDouble("preco_compra"));
            prod.setPrecoVenda(resultado.getDouble("preco_venda"));
            prod.setQuantidadeDisponivel(resultado.getInt("quantidade_disponível"));
            prod.setLiberadoVenda(Produto.Liberado.valueOf(resultado.getString("liberado_venda")));
            prod.setIdCategoria(resultado.getInt("id_categoria"));

            lista.add(prod);

        }

        pstmt.close();

        return lista;

    }

    /* ---------------------------------------------------------------------- */

    public static boolean inserir(Produto prod) throws SQLException {

        int linhasAfetadas = 0;

        PreparedStatement pstmt = ProdutoDAO.conexao.prepareStatement(INSERIR);  
            pstmt.setString(1, prod.getNomeProduto());
            pstmt.setString(2, prod.getDescricao());
            pstmt.setDouble(3, prod.getPrecoCompra());
            pstmt.setDouble(4, prod.getPrecoVenda());
            pstmt.setInt(5, prod.getQuantidadeDisponivel());
            pstmt.setString(6, prod.getLiberadoVenda().toString());
            pstmt.setInt(7, prod.getIdCategoria());
        linhasAfetadas = pstmt.executeUpdate();
        pstmt.close();

        if (linhasAfetadas > 0) { return true; }
        else { return false; }

    }

    /* ---------------------------------------------------------------------- */

    public static boolean alterar(Produto prod) throws SQLException {

        int linhasAfetadas = 0;

        PreparedStatement pstmt = ProdutoDAO.conexao.prepareStatement(ALTERAR);  
            pstmt.setString(1, prod.getNomeProduto());
            pstmt.setString(2, prod.getDescricao());
            pstmt.setDouble(3, prod.getPrecoCompra());
            pstmt.setDouble(4, prod.getPrecoVenda());
            pstmt.setInt(5, prod.getQuantidadeDisponivel());
            pstmt.setString(6, prod.getLiberadoVenda().toString());
            pstmt.setInt(7, prod.getIdCategoria());
            pstmt.setInt(8, prod.getId());
        linhasAfetadas = pstmt.executeUpdate();
        pstmt.close();

        if (linhasAfetadas > 0) { return true; }
        else { return false; }

    }

    /* ---------------------------------------------------------------------- */

    public static boolean vender(int idProduto, int quantidade) throws SQLException {

        Produto prod = buscarPorId(idProduto);

        if ( ( quantidade >= 1 ) && ( quantidade <= prod.getQuantidadeDisponivel() ) ) {

            prod.setQuantidadeDisponivel(prod.getQuantidadeDisponivel() - quantidade);

            if (prod.getQuantidadeDisponivel() == 0) {

                prod.setLiberadoVenda(Produto.Liberado.N);

            }

            return alterar(prod);

        } else {

            return false;

        }

    }

    /* ---------------------------------------------------------------------- */

    public static boolean deletar(int idProduto) throws SQLException {

        int linhasAfetadas = 0;

        PreparedStatement pstmt = ProdutoDAO.conexao.prepareStatement(DELETAR);
            pstmt.setInt(1, idProduto);
        linhasAfetadas = pstmt.executeUpdate();
        pstmt.close();

        if (linhasAfetadas > 0) { return true; }
        else { return false; }

    }

}