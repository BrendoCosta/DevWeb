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

                    } else { throw new Exception("Não foi possível localizar o fornecedor com o ID informado!"); }

                }

            }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmRazaoSocial") != null
                && !request.getParameter("prmRazaoSocial").isEmpty() ) {

                if ( request.getParameter("prmRazaoSocial").length() <= 50 ) {

                    forn.setRazaoSocial(request.getParameter("prmRazaoSocial"));

                } else { throw new Exception("Razaõ social é inválida!"); }

            } else { throw new Exception("Razaõ social não foi informada!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmCnpj") != null
                && !request.getParameter("prmCnpj").isEmpty() ) {

                if ( request.getParameter("prmCnpj").length() <= 18 ) {

                    forn.setCnpj(request.getParameter("prmCnpj"));

                } else { throw new Exception("CNPJ é inválido!"); }

            } else { throw new Exception("CNPJ não foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmEndereco") != null
                && !request.getParameter("prmEndereco").isEmpty() ) {

                if ( request.getParameter("prmEndereco").length() <= 50 ) {

                    forn.setEndereco(request.getParameter("prmEndereco"));

                } else { throw new Exception("Endereço é inválido!"); }

            } else { throw new Exception("Endereço não foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmBairro") != null
                && !request.getParameter("prmBairro").isEmpty() ) {

                if ( request.getParameter("prmBairro").length() <= 50 ) {

                    forn.setBairro(request.getParameter("prmBairro"));

                } else { throw new Exception("Bairro é inválido!"); }

            } else { throw new Exception("Bairro não foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmCidade") != null
                && !request.getParameter("prmCidade").isEmpty() ) {

                if ( request.getParameter("prmCidade").length() <= 50 ) {

                    forn.setCidade(request.getParameter("prmCidade"));

                } else { throw new Exception("Cidade é inválida!"); }

            } else { throw new Exception("Cidade não foi informada!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmUf") != null
                && !request.getParameter("prmUf").isEmpty() ) {

                if ( request.getParameter("prmUf").length() == 2 ) {

                    forn.setUf(request.getParameter("prmUf"));

                } else { throw new Exception("UF é inválida!"); }

            } else { throw new Exception("UF não foi informada!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmCep") != null
                && !request.getParameter("prmCep").isEmpty() ) {

                if ( request.getParameter("prmCep").length() == 9 ) {

                    forn.setCep(request.getParameter("prmCep"));

                } else { throw new Exception("CEP é inválido!"); }

            } else { throw new Exception("CEP não foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmTelefone") != null
                && !request.getParameter("prmTelefone").isEmpty() ) {

                if ( request.getParameter("prmTelefone").length() <= 20 ) {

                    forn.setTelefone(request.getParameter("prmTelefone"));

                } else { throw new Exception("Telefone é inválido!"); }

            } else { throw new Exception("Telefone não foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmEmail") != null
                && !request.getParameter("prmEmail").isEmpty() ) {

                if ( request.getParameter("prmEmail").length() <= 50 ) {

                    forn.setEmail(request.getParameter("prmEmail"));

                } else { throw new Exception("E-mail é inválido!"); }

            } else { throw new Exception("E-mail não foi informado!"); }

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

                if ( fornDAO.alterar(forn) ) {

                    Mensagem resMsg = new Mensagem("Fornecedor alterado!", Mensagem.Tipo.SUCESSO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                } else { throw new Exception("Não foi possível alterar os dados do fornecedor!"); }

            } else {

                if ( fornDAO.inserir(forn) ) {

                    Mensagem resMsg = new Mensagem("Fornecedor cadastrado!", Mensagem.Tipo.SUCESSO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                } else { throw new Exception("Não foi possível cadastrar o fornecedor!"); }

            }

            fornDAO.encerrarConexao();

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
                        
                    } else { throw new Exception("Não foi possível localizar o fornecedor informado!"); }

                    fornDAO.encerrarConexao();

                } else { throw new Exception("ID do fornecedor informado não é numérico!"); }

            } else { throw new Exception("ID do fornecedor não foi informado!"); }

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados!",
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
                                        "Fornecedor excluído!",
                                        Mensagem.Tipo.SUCESSO,
                                        request,
                                        response
                                    );

                                } else { throw new Exception("Não foi possível excluir o fornecedor informado!"); }

                            } else { throw new Exception("Não foi possível excluir o fornecedor informado pois há compras cadastradas para ele!"); }
                            
                        } else { throw new Exception("Apenas compradores podem excluir fornecedores!"); }

                    } else { throw new Exception("Não foi possível localizar o fornecedor informado!"); }

                    fornDAO.encerrarConexao();
                    compDAO.encerrarConexao();

                } else { throw new Exception("ID do fornecedor informado não é numérico!"); }

            } else { throw new Exception("ID do fornecedor não foi informado!"); }

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados!",
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