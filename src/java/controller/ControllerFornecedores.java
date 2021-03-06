package controller;
import app.ServletUtils;
import app.Mensagem;
import model.Fornecedor;
import dao.FornecedorDAO;
import model.Funcionario;
import dao.CompraDAO;
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

        try {

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmId") != null ) {

                if ( request.getParameter("prmId").matches("\\d+")
                    && request.getParameter("prmId").length() <= 11 ) {

                    forn = fornDAO.buscarPorId(Integer.parseInt(request.getParameter("prmId")));
                    
                    if (forn != null) {

                        if (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.COMPRADOR) {

                            alteracao = true;
                            forn.setId(Integer.parseInt(request.getParameter("prmId")));

                        } else { throw new Exception("Apenas compradores podem alterar fornecedores!"); }

                    } else { throw new Exception("N??o foi poss??vel localizar o fornecedor com o ID informado!"); }

                }

            }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmRazaoSocial") != null
                && !request.getParameter("prmRazaoSocial").isEmpty() ) {

                if ( request.getParameter("prmRazaoSocial").length() <= 50 ) {

                    forn.setRazaoSocial(request.getParameter("prmRazaoSocial"));

                } else { throw new Exception("Raza?? social ?? inv??lida!"); }

            } else { throw new Exception("Raza?? social n??o foi informada!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmCnpj") != null
                && !request.getParameter("prmCnpj").isEmpty() ) {

                if ( request.getParameter("prmCnpj").length() <= 18 ) {

                    forn.setCnpj(request.getParameter("prmCnpj"));

                } else { throw new Exception("CNPJ ?? inv??lido!"); }

            } else { throw new Exception("CNPJ n??o foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmEndereco") != null
                && !request.getParameter("prmEndereco").isEmpty() ) {

                if ( request.getParameter("prmEndereco").length() <= 50 ) {

                    forn.setEndereco(request.getParameter("prmEndereco"));

                } else { throw new Exception("Endere??o ?? inv??lido!"); }

            } else { throw new Exception("Endere??o n??o foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmBairro") != null
                && !request.getParameter("prmBairro").isEmpty() ) {

                if ( request.getParameter("prmBairro").length() <= 50 ) {

                    forn.setBairro(request.getParameter("prmBairro"));

                } else { throw new Exception("Bairro ?? inv??lido!"); }

            } else { throw new Exception("Bairro n??o foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmCidade") != null
                && !request.getParameter("prmCidade").isEmpty() ) {

                if ( request.getParameter("prmCidade").length() <= 50 ) {

                    forn.setCidade(request.getParameter("prmCidade"));

                } else { throw new Exception("Cidade ?? inv??lida!"); }

            } else { throw new Exception("Cidade n??o foi informada!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmUf") != null
                && !request.getParameter("prmUf").isEmpty() ) {

                if ( request.getParameter("prmUf").length() == 2 ) {

                    forn.setUf(request.getParameter("prmUf"));

                } else { throw new Exception("UF ?? inv??lida!"); }

            } else { throw new Exception("UF n??o foi informada!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmCep") != null
                && !request.getParameter("prmCep").isEmpty() ) {

                if ( request.getParameter("prmCep").length() == 9 ) {

                    forn.setCep(request.getParameter("prmCep"));

                } else { throw new Exception("CEP ?? inv??lido!"); }

            } else { throw new Exception("CEP n??o foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmTelefone") != null
                && !request.getParameter("prmTelefone").isEmpty() ) {

                if ( request.getParameter("prmTelefone").length() <= 20 ) {

                    forn.setTelefone(request.getParameter("prmTelefone"));

                } else { throw new Exception("Telefone ?? inv??lido!"); }

            } else { throw new Exception("Telefone n??o foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmEmail") != null
                && !request.getParameter("prmEmail").isEmpty() ) {

                if ( request.getParameter("prmEmail").length() <= 50 ) {

                    forn.setEmail(request.getParameter("prmEmail"));

                } else { throw new Exception("E-mail ?? inv??lido!"); }

            } else { throw new Exception("E-mail n??o foi informado!"); }

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "N??o foi poss??vel consultar o banco de dados!",
                e,
                request,
                response
            );

        } catch (Exception e) {

            Mensagem resMsg = new Mensagem(e.getMessage(), Mensagem.Tipo.ERRO);
            request.setAttribute("resMensagem", resMsg);
            this.doGet(request, response);

        }

        // Inclus??o / Altera????o

        try {

            if (alteracao) {

                if ( fornDAO.alterar(forn) ) {

                    Mensagem resMsg = new Mensagem("Fornecedor alterado!", Mensagem.Tipo.SUCESSO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                } else { throw new Exception("N??o foi poss??vel alterar os dados do fornecedor!"); }

            } else {

                if ( fornDAO.inserir(forn) ) {

                    Mensagem resMsg = new Mensagem("Fornecedor cadastrado!", Mensagem.Tipo.SUCESSO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                } else { throw new Exception("N??o foi poss??vel cadastrar o fornecedor!"); }

            }

            fornDAO.encerrarConexao();

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "N??o foi poss??vel consultar o banco de dados!",
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

        RequestDispatcher rd = request.getRequestDispatcher("/ControllerFornecedores.jsp");  
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

                    FornecedorDAO fornDAO = new FornecedorDAO();
                    Fornecedor forn = fornDAO.buscarPorId(Integer.parseInt(request.getParameter("id")));

                    if (forn != null) {

                        if (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.COMPRADOR) {

                            request.setAttribute("fornecedor", forn);

                            RequestDispatcher rd = request.getRequestDispatcher("/ControllerFornecedores.jsp");  
                            rd.forward(request, response);

                        } else { throw new Exception("Apenas compradores podem alterar fornecedores!"); }
                        
                    } else { throw new Exception("N??o foi poss??vel localizar o fornecedor informado!"); }

                    fornDAO.encerrarConexao();

                } else { throw new Exception("ID do fornecedor informado n??o ?? num??rico!"); }

            } else { throw new Exception("ID do fornecedor n??o foi informado!"); }

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "N??o foi poss??vel consultar o banco de dados!",
                e,
                request,
                response
            );

        } catch (Exception e) {

            ServletUtils.mensagem(
                "/ControllerFornecedores?acao=listar",
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

            if ( request.getParameter("id") != null ) {

                if ( request.getParameter("id").matches("\\d+") ) {

                    int id = Integer.parseInt(request.getParameter("id"));
                    FornecedorDAO fornDAO = new FornecedorDAO();
                    Fornecedor forn = fornDAO.buscarPorId(id);
                    CompraDAO compDAO = new CompraDAO();
                    
                    if (forn != null) {

                        if (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.COMPRADOR) {

                            if (compDAO.listarPorFornecedor(forn.getId()).size() == 0) {

                                if ( fornDAO.deletar(id) ) {

                                    ServletUtils.mensagem(
                                        "/ControllerFornecedores?acao=listar",
                                        "Fornecedor exclu??do!",
                                        Mensagem.Tipo.SUCESSO,
                                        request,
                                        response
                                    );

                                } else { throw new Exception("N??o foi poss??vel excluir o fornecedor informado!"); }

                            } else { throw new Exception("N??o foi poss??vel excluir o fornecedor informado pois h?? compras cadastradas para ele!"); }
                            
                        } else { throw new Exception("Apenas compradores podem excluir fornecedores!"); }

                    } else { throw new Exception("N??o foi poss??vel localizar o fornecedor informado!"); }

                    fornDAO.encerrarConexao();
                    compDAO.encerrarConexao();

                } else { throw new Exception("ID do fornecedor informado n??o ?? num??rico!"); }

            } else { throw new Exception("ID do fornecedor n??o foi informado!"); }

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "N??o foi poss??vel consultar o banco de dados!",
                e,
                request,
                response
            );

        } catch (Exception e) {

            ServletUtils.mensagem(
                "/ControllerFornecedores?acao=listar",
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

        FornecedorDAO fornDAO = null;
        ArrayList<Fornecedor> lista = null;

        try {

            fornDAO = new FornecedorDAO();
            lista = fornDAO.listar();
            fornDAO.encerrarConexao();

        } catch (SQLException excecao) {

            ServletUtils.mensagemErroFatal(
                "N??o foi poss??vel consultar o banco de dados para listar os fornecedores!",
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