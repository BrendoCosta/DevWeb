package controller;
import app.ServletUtils;
import app.Mensagem;
import model.Fornecedor;
import dao.FornecedorDAO;
import java.util.ArrayList;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.sql.SQLException;

@WebServlet(name = "ControllerFornecedores", urlPatterns = {"/ControllerFornecedores"})
public class ControllerFornecedores extends HttpServlet {

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

        Fornecedor forn = new Fornecedor();
        FornecedorDAO fornDAO = new FornecedorDAO();

        boolean alteracao = false;

        // Verifica se o POST se refere a uma alteração

        if (request.getParameter("prmId") != null) {

            if (!((String)request.getParameter("prmId")).isEmpty()
                && Integer.parseInt(request.getParameter("prmId")) >= 0) {

                alteracao = true;
                forn.setId(Integer.parseInt(request.getParameter("prmId")));

            }

        }

        // Dados do fornecedor

        forn.setRazaoSocial( (String) request.getParameter("prmRazaoSocial") );
        forn.setCnpj( (String) request.getParameter("prmCnpj") );
        forn.setEndereco( (String) request.getParameter("prmEndereco") );
        forn.setBairro( (String) request.getParameter("prmBairro") );
        forn.setCidade( (String) request.getParameter("prmCidade") );
        forn.setUf( (String) request.getParameter("prmUf") );
        forn.setCep( (String) request.getParameter("prmCep") );
        forn.setTelefone( (String) request.getParameter("prmTelefone") );
        forn.setEmail( (String) request.getParameter("prmEmail") );

        if (alteracao) {

            // Altera fornecedor

            try {

                if ( fornDAO.alterar(forn) ) {

                    Mensagem resMsg = new Mensagem("Dados do fornecedor alterados!", Mensagem.Tipo.SUCESSO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                } else {

                    Mensagem resMsg = new Mensagem("Falha ao alterar os dados do fornecedor!", Mensagem.Tipo.ERRO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                }

            } catch (SQLException excecao) {

                ServletUtils.mensagemErroFatal(
                    "Não foi possível alterar os dados do fornecedor no banco de dados!",
                    excecao,
                    request,
                    response
                );

            }

        } else {

            // Inclui fornecedor

            try {

                if ( fornDAO.inserir(forn) ) {

                    Mensagem resMsg = new Mensagem("Dados do fornecedor incluídos!", Mensagem.Tipo.SUCESSO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                } else {

                    Mensagem resMsg = new Mensagem("Falha ao incluir os dados do fornecedor!", Mensagem.Tipo.ERRO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                }

            } catch (SQLException excecao) {

                ServletUtils.mensagemErroFatal(
                    "Não foi possível incluir os dados do fornecedor no banco de dados!",
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

        RequestDispatcher rd = request.getRequestDispatcher("/ControllerFornecedores.jsp");  
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

                Fornecedor forn = null;
                FornecedorDAO fornDAO = new FornecedorDAO();
                
                forn = fornDAO.buscarPorId(id);

                if (forn != null) {
                    
                    request.setAttribute("fornecedor", forn);

                    RequestDispatcher rd = request.getRequestDispatcher("/ControllerFornecedores.jsp");  
                    rd.forward(request, response);
                    
                } else {

                    ServletUtils.mensagem(
                        "/ControllerVendas?acao=listar",
                        "Não foi possível localizar o fornecedor com o ID informado!",
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

            Mensagem resMsg = new Mensagem("ID do fornecedor a ser alterado não foi informado!", Mensagem.Tipo.ERRO);
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

            Fornecedor forn = null;
            FornecedorDAO fornDAO = null;

            try {

                fornDAO = new FornecedorDAO();
                forn = fornDAO.buscarPorId(id);

                if ( forn != null ) {

                    if ( fornDAO.deletar(id) ) {

                        ServletUtils.mensagem(
                            "/ControllerFornecedores?acao=listar",
                            "Fornecedor excluído!",
                            Mensagem.Tipo.SUCESSO,
                            request,
                            response
                        );

                    } else {

                        ServletUtils.mensagem(
                            "/ControllerFornecedores?acao=listar",
                            "Não foi possível excluir o fornecedor!",
                            Mensagem.Tipo.ERRO,
                            request,
                            response
                        );

                    }

                } else {

                    ServletUtils.mensagem(
                        "/ControllerFornecedores?acao=listar",
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
                "/ControllerFornecedores?acao=listar",
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

        FornecedorDAO fornDAO = null;
        ArrayList<Fornecedor> lista = null;

        try {

            fornDAO = new FornecedorDAO();
            lista = fornDAO.listar();

        } catch (SQLException excecao) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados para listar os fornecedores!",
                excecao,
                request,
                response
            );

        }

        request.setAttribute("lista", lista);

        RequestDispatcher rd = request.getRequestDispatcher("/ControllerFornecedores.jsp");  
        rd.forward(request, response);

    }

}