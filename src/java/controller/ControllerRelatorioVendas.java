package controller;

import app.ServletUtils;
import app.Mensagem;
import model.Venda;
import dao.VendaDAO;
import model.TotalVendas;
import dao.TotalVendasDAO;
import model.Funcionario;
import java.util.ArrayList;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.sql.SQLException;

@WebServlet(name = "ControllerRelatorioVendas", urlPatterns = {"/ControllerRelatorioVendas"})
public class ControllerRelatorioVendas extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        if (!ServletUtils.validaSessao(request, response)) { return; }

        try {

            if (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.ADMIN) {

                VendaDAO venDAO = new VendaDAO();
                ArrayList<Venda> listaVendas = venDAO.listar();
                venDAO.encerrarConexao();

                TotalVendasDAO totvDAO = new TotalVendasDAO();
                ArrayList<TotalVendas> listaTotalVendas = totvDAO.listar();
                totvDAO.encerrarConexao();

                request.setAttribute("listaVendas", listaVendas);
                request.setAttribute("listaTotalVendas", listaTotalVendas);

                RequestDispatcher rd = request.getRequestDispatcher("/ControllerRelatorioVendas.jsp");  
                rd.forward(request, response);

            } else { throw new Exception("Apenas administradores podem gerar relatórios!"); }

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados!",
                e,
                request,
                response
            );

        } catch (Exception e) {

            ServletUtils.mensagem(
                "/Area",
                e.getMessage(),
                Mensagem.Tipo.ERRO,
                request,
                response
            );

        }

    }

}