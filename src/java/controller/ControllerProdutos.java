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

        ProdutoDAO prodDAO = new ProdutoDAO();
        Produto prod = new Produto();

        boolean alteracao = false;

        try {

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmId") != null ) {

                if ( request.getParameter("prmId").matches("\\d+")
                    && request.getParameter("prmId").length() <= 11 ) {

                    prod = prodDAO.buscarPorId(Integer.parseInt(request.getParameter("prmId")));
                    
                    if (prod != null) {

                        if (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.COMPRADOR) {

                            alteracao = true;
                            prod.setId(Integer.parseInt(request.getParameter("prmId")));

                        } else { throw new Exception("Apenas compradores podem alterar produtos!"); }

                    } else { throw new Exception("Não foi possível localizar o produto com o ID informado!"); }

                } else { throw new Exception("ID do produto é inválido!"); }

            } else { throw new Exception("É obrigatório informar o ID do produto!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmLiberadoVenda") != null ) {

                if ( request.getParameter("prmLiberadoVenda").equals("S") 
                    || request.getParameter("prmLiberadoVenda").equals("N") ) {

                    prod.setLiberadoVenda(Produto.Liberado.valueOf(request.getParameter("prmLiberadoVenda")));

                } else { throw new Exception("Opção de liberação de venda é inválida!"); }

            } else { throw new Exception("Opção de liberação de venda não foi informada!"); }

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados!",
                e,
                request,
                response
            );

        } catch (Exception e) {

            Mensagem resMsg = new Mensagem(e.getMessage(), Mensagem.Tipo.ERRO);
            request.setAttribute("resMensagem", resMsg);
            this.doGet(request, response);

        }

        // Atleração

        try {

            if ( prodDAO.alterar(prod) ) {

                Mensagem resMsg = new Mensagem("Dados do produto alterados!", Mensagem.Tipo.SUCESSO);
                request.setAttribute("resMensagem", resMsg);
                this.doGet(request, response);

            } else { throw new Exception("Não foi possível alterar os dados do produto!"); }

            prodDAO.encerrarConexao();

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados!",
                e,
                request,
                response
            );

        } catch (Exception e) {

            Mensagem resMsg = new Mensagem(e.getMessage(), Mensagem.Tipo.ERRO);
            request.setAttribute("resMensagem", resMsg);
            this.doGet(request, response);

        }

    }

    /* ---------------------------------------------------------------------- */

    private void alterar(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        request.setAttribute("acao", "alterar");

        try {

            if ( request.getParameter("id") != null
                && !request.getParameter("id").isEmpty() ) {

                if ( request.getParameter("id").matches("\\d+") ) {

                    ProdutoDAO prodDAO = new ProdutoDAO();
                    Produto prod = prodDAO.buscarPorId(Integer.parseInt(request.getParameter("id")));
                    prodDAO.encerrarConexao();

                    if (prod != null) {
                        
                        request.setAttribute("produto", prod);

                        RequestDispatcher rd = request.getRequestDispatcher("/ControllerProdutos.jsp");  
                        rd.forward(request, response);
                        
                    } else { throw new Exception("Não foi possível localizar o produto informado!"); }

                } else { throw new Exception("ID do produto informado não é numérico!"); }

            } else { throw new Exception("ID do produto não foi informado!"); }

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados!",
                e,
                request,
                response
            );

        } catch (Exception e) {

            ServletUtils.mensagem(
                "/ControllerProdutos?acao=listar",
                e.getMessage(),
                Mensagem.Tipo.ERRO,
                request,
                response
            );

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
            prodDAO.encerrarConexao();

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