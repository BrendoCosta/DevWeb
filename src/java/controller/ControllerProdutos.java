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

@WebServlet(name = "ControllerProdutos", urlPatterns = {"/ControllerProdutos"})
public class ControllerProdutos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        if (!ServletUtils.validaSessao(request, response)) { return; }

        String acao = null;

        if (((String) request.getParameter("acao") == null)
            || ((String) request.getParameter("acao")).isEmpty()) {

            acao = "listar";

        } else {

            acao = (String) request.getParameter("acao");

        }

        switch (acao) {

            case "alterar":

                alterar(request, response);
                break;

            default:

                listar(request, response);
                break;

        }

    }

    /* ---------------------------------------------------------------------- */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        if (!ServletUtils.validaSessao(request, response)) { return; }

        Produto prod = new Produto(); 
        boolean alteracao = false;

        // Verifica se o POST se refere a uma alteração

        if (request.getParameter("prmId") != null) {

            if (!((String)request.getParameter("prmId")).isEmpty()
                && Integer.parseInt(request.getParameter("prmId")) >= 0) {

                alteracao = true;
                prod.setId(Integer.parseInt(request.getParameter("prmId")));

            }

        }

        // Dados do produto

        prod.setLiberadoVenda( Produto.Liberado.valueOf((String) request.getParameter("prmLiberadoVenda")) );

        if (alteracao) {

            // Altera produto

            try {

                ProdutoDAO prodDAO = new ProdutoDAO();
                Produto aux = prodDAO.buscarPorId(prod.getId());

                if ( aux != null ) {

                    aux.setLiberadoVenda(prod.getLiberadoVenda());

                    if ( prodDAO.alterar(aux) ) {

                        Mensagem resMsg = new Mensagem("Dados do produto alterados!", Mensagem.Tipo.SUCESSO);
                        request.setAttribute("resMensagem", resMsg);
                        this.doGet(request, response);

                    } else {

                        Mensagem resMsg = new Mensagem("Falha ao alterar os dados do produto!", Mensagem.Tipo.ERRO);
                        request.setAttribute("resMensagem", resMsg);
                        this.doGet(request, response);

                    }

                } else {

                    Mensagem resMsg = new Mensagem("Não foi possível localizar o produto com o ID especificado!", Mensagem.Tipo.ERRO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                }

            } catch (SQLException excecao) {

                ServletUtils.mensagemErroFatal(
                    "Não foi possível alterar os dados do produto no banco de dados!",
                    excecao,
                    request,
                    response
                );

            }

        } else {

            Mensagem resMsg = new Mensagem("ID do produto a ser alterado não foi especificado!", Mensagem.Tipo.ERRO);
            request.setAttribute("resMensagem", resMsg);
            this.doGet(request, response);

        }

    }

    /* ---------------------------------------------------------------------- */

    private void alterar(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        request.setAttribute("acao", "alterar");
        
        Integer id = null;
        
        if (!((String) request.getParameter("id") == null)) {

            id = Integer.parseInt( (String) request.getParameter("id") );

        }

        if (id != null) {

            try {

                Produto prod = null;
                ProdutoDAO prodDAO = new ProdutoDAO();
                
                prod = prodDAO.buscarPorId(id);

                if (prod != null) {
                    
                    request.setAttribute("produto", prod);

                    RequestDispatcher rd = request.getRequestDispatcher("/ControllerProdutos.jsp");  
                    rd.forward(request, response);
                    
                } else {

                    ServletUtils.mensagem(
                        "/ControllerProdutos?acao=listar",
                        "Não foi possível localizar o produto com o ID informado!",
                        Mensagem.Tipo.ERRO,
                        request,
                        response
                    );

                }

            } catch (SQLException excecao) {

                ServletUtils.mensagemErroFatal(
                    "Não foi possível consultar o banco de dados!",
                    excecao,
                    request,
                    response
                );

            }

        } else {

            Mensagem resMsg = new Mensagem("ID do produto a ser alterado não foi informado!", Mensagem.Tipo.ERRO);
            request.setAttribute("resMensagem", resMsg);
            this.doGet(request, response);

        }

    }

    /* ---------------------------------------------------------------------- */

    private void listar(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        request.setAttribute("acao", "listar");

        ArrayList<Produto> lista = null;
        ProdutoDAO prodDAO = null;

        try {

            prodDAO = new ProdutoDAO();
            lista = prodDAO.listar();

        } catch (SQLException excecao) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados para listar os produtos!",
                excecao,
                request,
                response
            );

        }

        request.setAttribute("lista", lista);

        RequestDispatcher rd = request.getRequestDispatcher("/ControllerProdutos.jsp");  
        rd.forward(request, response);

    }

}