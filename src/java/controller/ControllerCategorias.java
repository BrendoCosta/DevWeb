package controller;
import app.ServletUtils;
import app.Mensagem;
import model.Categoria;
import dao.CategoriaDAO;
import model.Funcionario;
import java.util.ArrayList;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.sql.SQLException;

@WebServlet(name = "ControllerCategorias", urlPatterns = {"/ControllerCategorias"})
public class ControllerCategorias extends HttpServlet {

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

            case "incluir":

                incluir(request, response);
                break;

            case "alterar":

                alterar(request, response);
                break;
            
            case "excluir":

                excluir(request, response);
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

        Categoria cat = new Categoria();
        CategoriaDAO catDAO = new CategoriaDAO();

        boolean alteracao = false;

        try {

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmId") != null 
                && !request.getParameter("prmId").isEmpty() ) {

                if ( request.getParameter("prmId").matches("\\d+")
                    && request.getParameter("prmId").length() <= 11 ) {

                    if (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.COMPRADOR) {

                        cat = catDAO.buscarPorId(Integer.parseInt(request.getParameter("prmId")));
                        
                        if (cat != null) {

                            alteracao = true;
                            cat.setId(Integer.parseInt(request.getParameter("prmId")));

                        } else { throw new Exception("Não foi possível localizar a categoria com o ID informado!"); }

                    } else { throw new Exception("Apenas compradores podem incluir ou alterar categorias!"); }

                } else { throw new Exception("ID da categoria informado não é numérico!"); }

            }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmNomeCategoria") != null
                && !request.getParameter("prmNomeCategoria").isEmpty() ) {

                if ( request.getParameter("prmNomeCategoria").length() <= 50 ) {

                    cat.setNomeCategoria(request.getParameter("prmNomeCategoria"));

                } else { throw new Exception("Nome da categoria é inválido!"); }

            } else { throw new Exception("Nome da categoria não foi informado!"); }

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

        // Inclusão / Alteração

        try {

            if (alteracao) {

                if ( catDAO.alterar(cat) ) {

                    Mensagem resMsg = new Mensagem("Dados da categoria alterados!", Mensagem.Tipo.SUCESSO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                } else { throw new Exception("Não foi possível alterar os dados da categoria!"); }

            } else {

                if ( catDAO.inserir(cat) ) {

                    Mensagem resMsg = new Mensagem("Categoria cadastrada!", Mensagem.Tipo.SUCESSO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                } else { throw new Exception("Não foi possível cadastrar a categoria!"); }

            }
            
            catDAO.encerrarConexao();

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

    private void incluir(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        request.setAttribute("acao", "incluir");

        RequestDispatcher rd = request.getRequestDispatcher("/ControllerCategorias.jsp");  
        rd.forward(request, response);

    }

    /* ---------------------------------------------------------------------- */

    private void alterar(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        request.setAttribute("acao", "alterar");

        try {

            if ( request.getParameter("id") != null
                && !request.getParameter("id").isEmpty() ) {

                if ( request.getParameter("id").matches("\\d+") ) {

                    CategoriaDAO catDAO = new CategoriaDAO();
                    Categoria cat = catDAO.buscarPorId(Integer.parseInt(request.getParameter("id")));
                    catDAO.encerrarConexao();

                    if (cat != null) {

                        if (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.COMPRADOR) {

                            request.setAttribute("categoria", cat);

                            RequestDispatcher rd = request.getRequestDispatcher("/ControllerCategorias.jsp");  
                            rd.forward(request, response);

                        } else { throw new Exception("Apenas compradores podem alterar categorias!"); }
                        
                    } else { throw new Exception("Não foi possível localizar a categoria informado!"); }

                } else { throw new Exception("ID da categoria  informado não é numérico!"); }

            } else { throw new Exception("ID da categoria  não foi informado!"); }

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

    /* ---------------------------------------------------------------------- */

    private void excluir(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        request.setAttribute("acao", "excluir");

        try {

            if ( request.getParameter("id") != null
                && !request.getParameter("id").isEmpty() ) {

                if ( request.getParameter("id").matches("\\d+") ) {

                    if (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.COMPRADOR) {

                        int id = Integer.parseInt(request.getParameter("id"));
                        CategoriaDAO catDAO = new CategoriaDAO();
                        Categoria cat = catDAO.buscarPorId(id);
                        
                        if (cat != null) {

                            if ( catDAO.deletar(id) ) {

                                catDAO.encerrarConexao();

                                ServletUtils.mensagem(
                                    "/ControllerCategorias?acao=listar",
                                    "Categoria excluída!",
                                    Mensagem.Tipo.SUCESSO,
                                    request,
                                    response
                                );

                            } else { throw new Exception("Não foi possível excluir a categoria informada!"); }

                        } else { throw new Exception("Não foi possível localizar a categoria informada!"); }
                        
                    } else { throw new Exception("Apenas compradores podem excluir categorias!"); }

                } else { throw new Exception("ID da categoria informado não é numérico!"); }

            } else { throw new Exception("ID da categoria não foi informado!"); }

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

    /* ---------------------------------------------------------------------- */

    private void listar(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        request.setAttribute("acao", "listar");

        ArrayList<Categoria> lista = null;
        CategoriaDAO catDAO = null;

        try {

            catDAO = new CategoriaDAO();
            lista = catDAO.listar();
            catDAO.encerrarConexao();

        } catch (SQLException excecao) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados para listar as categorias!",
                excecao,
                request,
                response
            );

        }

        request.setAttribute("lista", lista);

        RequestDispatcher rd = request.getRequestDispatcher("/ControllerCategorias.jsp");  
        rd.forward(request, response);

    }

}