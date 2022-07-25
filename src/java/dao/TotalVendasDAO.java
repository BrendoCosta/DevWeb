package dao;
import model.TotalVendas;
import java.util.ArrayList;

import java.sql.*;

public class TotalVendasDAO {

    private static final String LISTAR = "SELECT DISTINCT v.data_venda, (SELECT COUNT(*) FROM vendas WHERE data_venda = v.data_venda) AS quantidadeDataVenda FROM vendas v ORDER BY v.data_venda DESC";

    /* ---------------------------------------------------------------------- */

    private static Connection conexao = null;

    public TotalVendasDAO() { TotalVendasDAO.conexao = Conexao.iniciarConexao(); }

    /* ---------------------------------------------------------------------- */

    public static void encerrarConexao() throws SQLException {

        TotalVendasDAO.conexao.close();

    }

    /* ---------------------------------------------------------------------- */

    public static ArrayList<TotalVendas> listar() throws SQLException {

        ArrayList<TotalVendas> lista = new ArrayList<>();

        PreparedStatement pstmt = TotalVendasDAO.conexao.prepareStatement(LISTAR);
        ResultSet resultado = pstmt.executeQuery();

        while ( resultado.next() ) {

            TotalVendas totv = new TotalVendas();

            totv.setDataVenda(resultado.getString("data_venda"));
            totv.setQuantidadeDataVenda(resultado.getInt("quantidadeDataVenda"));

            lista.add(totv);

        }

        pstmt.close();

        return lista;

    }

}