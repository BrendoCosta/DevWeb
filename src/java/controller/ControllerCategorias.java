package controller;
import app.ServletUtils;
import app.Mensagem;
import model.Categoria;
import dao.CategoriaDAO;
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

        // Verifica se o POST se refere a uma alteração

        if (request.getParameter("prmId") != null) {

            if (!((String)request.getParameter("prmId")).isEmpty()
                && Integer.parseInt(request.getParameter("prmId")) >= 0) {

                alteracao = true;
                cat.setId(Integer.parseInt(request.getParameter("prmId")));

            }

        }

        // Dados da categoria

        cat.setNomeCategoria( (String) request.getParameter("prmNomeCategoria") );

        if (alteracao) {

            // Altera categoria

            try {

                if ( catDAO.alterar(cat) ) {

                    Mensagem resMsg = new Mensagem("Dados da categoria alterados!", Mensagem.Tipo.SUCESSO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                } else {

                    Mensagem resMsg = new Mensagem("Falha ao alterar os dados da categoria!", Mensagem.Tipo.ERRO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                }

            } catch (SQLException excecao) {

                ServletUtils.mensagemErroFatal(
                    "Não foi possível alterar os dados da categoria no banco de dados!",
                    excecao,
                    request,
                    response
                );

            }

        } else {

            // Inclui categoria

            try {

                if ( catDAO.inserir(cat) ) {

                    Mensagem resMsg = new Mensagem("Dados da categoria incluídos!", Mensagem.Tipo.SUCESSO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                } else {

                    Mensagem resMsg = new Mensagem("Falha ao incluir os dados da categoria!", Mensagem.Tipo.ERRO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                }

            } catch (SQLException excecao) {

                ServletUtils.mensagemErroFatal(
                    "Não foi possível incluir os dados da categoria no banco de dados!",
                    excecao,
                    request,
                    response
                );

            }

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
        
        Integer id = null;
        
        if (!((String) request.getParameter("id") == null)) {

            id = Integer.parseInt( (String) request.getParameter("id") );

        }

        if (id != null) {

            try {

                Categoria cat = null;
                CategoriaDAO catDAO = new CategoriaDAO();
                
                cat = catDAO.buscarPorId(id);

                if (cat != null) {
                    
                    request.setAttribute("categoria", cat);

                    RequestDispatcher rd = request.getRequestDispatcher("/ControllerCategorias.jsp");  
                    rd.forward(request, response);
                    
                } else {

                    ServletUtils.mensagem(
                        "/ControllerVendas?acao=listar",
                        "Não foi possível localizar a categoria com o ID informado!",
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

            Mensagem resMsg = new Mensagem("ID da categoria a ser alterada não foi informado!", Mensagem.Tipo.ERRO);
            request.setAttribute("resMensagem", resMsg);
            this.doGet(request, response);

        }

    }

    /* ---------------------------------------------------------------------- */

    private void excluir(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        int id = -1;

        if (request.getParameter("id") != null) {

            id = Integer.parseInt(request.getParameter("id"));

        }

        if (id != -1) {

            Categoria cat = null;
            CategoriaDAO catDAO = null;

            try {

                catDAO = new CategoriaDAO();
                cat = catDAO.buscarPorId(id);

                if ( cat != null ) {

                    if ( catDAO.deletar(id) ) {

                        ServletUtils.mensagem(
                            "/ControllerCategorias?acao=listar",
                            "Categoria excluída!",
                            Mensagem.Tipo.SUCESSO,
                            request,
                            response
                        );

                    } else {

                        ServletUtils.mensagem(
                            "/ControllerCategorias?acao=listar",
                            "Não foi possível excluir o fornecedor!",
                            Mensagem.Tipo.ERRO,
                            request,
                            response
                        );

                    }

                } else {

                    ServletUtils.mensagem(
                        "/ControllerCategorias?acao=listar",
                        "Fornecedor inexistente!",
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

            ServletUtils.mensagem(
                "/ControllerCategorias?acao=listar",
                "ID do fornecedor a ser excluído não foi informado!",
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