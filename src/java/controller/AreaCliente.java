package controller;

import app.ServletUtils;
import app.Mensagem;
import model.Produto;
import dao.ProdutoDAO;
import java.util.ArrayList;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.sql.SQLException;

@WebServlet(name = "AreaCliente", urlPatterns = {"/AreaCliente"})
public class AreaCliente extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        try {

            ProdutoDAO prodDAO = new ProdutoDAO();
            ArrayList<Produto> lista = prodDAO.listar(true);
            prodDAO.encerrarConexao();

            request.setAttribute("lista", lista);

            RequestDispatcher rd = request.getRequestDispatcher("/AreaCliente.jsp");  
            rd.forward(request, response);

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados!",
                e,
                request,
                response
            );

        }

    }

}