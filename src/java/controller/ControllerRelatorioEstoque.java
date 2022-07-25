package controller;

import app.ServletUtils;
import app.Mensagem;
import model.Produto;
import dao.ProdutoDAO;
import model.Funcionario;
import java.util.ArrayList;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.sql.SQLException;

@WebServlet(name = "ControllerRelatorioEstoque", urlPatterns = {"/ControllerRelatorioEstoque"})
public class ControllerRelatorioEstoque extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        if (!ServletUtils.validaSessao(request, response)) { return; }

        try {

            if (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.ADMIN) {

                ProdutoDAO prodDAO = new ProdutoDAO();
                ArrayList<Produto> lista = prodDAO.listar();
                prodDAO.encerrarConexao();

                request.setAttribute("lista", lista);

                RequestDispatcher rd = request.getRequestDispatcher("/ControllerRelatorioEstoque.jsp");  
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