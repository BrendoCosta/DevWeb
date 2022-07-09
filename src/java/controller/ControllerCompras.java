package controller;
import app.ServletUtils;
import app.Mensagem;
import model.Compra;
import dao.CompraDAO;
import model.Produto;
import dao.ProdutoDAO;
import model.Fornecedor;
import dao.FornecedorDAO;
import java.util.ArrayList;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.sql.SQLException;

@WebServlet(name = "ControllerCompras", urlPatterns = {"/ControllerCompras"})
public class ControllerCompras extends HttpServlet {

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

        Compra comp = new Compra();
        CompraDAO compDAO = new CompraDAO();
        
        Produto prod = null;
        ProdutoDAO prodDAO = new ProdutoDAO();

        FornecedorDAO fornDAO = new FornecedorDAO();

        // Valida parâmetros

        boolean alteracao = false;

        comp.setIdFuncionario((int) request.getSession().getAttribute("usuarioID"));

        try {

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmId") != null
                && !request.getParameter("prmId").isEmpty() ) {

                if ( request.getParameter("prmId").matches("\\d+")
                    && request.getParameter("prmId").length() <= 11 ) {

                    comp = compDAO.buscarPorId(Integer.parseInt(request.getParameter("prmId")));
                    
                    if (comp != null) {

                        alteracao = true;

                    } else { throw new Exception("Não foi possível localizar a compra com o ID informado!"); }

                } else { throw new Exception("ID da compra é inválido!"); }

            }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmIdProduto") != null
                && !request.getParameter("prmIdProduto").isEmpty() ) {

                if ( request.getParameter("prmIdProduto").matches("\\d+")
                    && request.getParameter("prmIdProduto").length() <= 11 ) {

                    comp.setIdProduto(Integer.parseInt(request.getParameter("prmIdProduto")));

                } else { throw new Exception("ID do produto é inválido!"); }

            } else { throw new Exception("ID do produto não foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmValorCompra") != null
                && !request.getParameter("prmValorCompra").isEmpty() ) {

                if ( request.getParameter("prmValorCompra").matches("\\d+")
                    && request.getParameter("prmValorCompra").length() <= 11 ) {

                    comp.setValorCompra(Integer.parseInt(request.getParameter("prmValorCompra")));

                } else { throw new Exception("Valor informado é inválido!"); }

            } else { throw new Exception("Valor não foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmQuantidadeCompra") != null
                && !request.getParameter("prmQuantidadeCompra").isEmpty() ) {

                if ( request.getParameter("prmQuantidadeCompra").matches("\\d+")
                    && request.getParameter("prmQuantidadeCompra").length() <= 11 ) {

                    comp.setQuantidadeCompra(Integer.parseInt(request.getParameter("prmQuantidadeCompra")));

                } else { throw new Exception("Quantidade informada é inválida!"); }

            } else { throw new Exception("Quantidade não foi informada!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmDataCompra") != null
                && !request.getParameter("prmDataCompra").isEmpty() ) {

                if ( request.getParameter("prmDataCompra").length() == 16 ) {

                    comp.setDataCompra(request.getParameter("prmDataCompra"));

                } else { throw new Exception("Data informada é inválida!"); }

            } else { throw new Exception("Data não foi informada!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmIdFornecedor") != null
                && !request.getParameter("prmIdFornecedor").isEmpty() ) {

                if ( request.getParameter("prmIdFornecedor").matches("\\d+")
                    && request.getParameter("prmIdFornecedor").length() <= 11 ) {

                    comp.setIdFornecedor(Integer.parseInt(request.getParameter("prmIdFornecedor")));

                } else { throw new Exception("ID do fornecedor informado é inválido!"); }

            } else { throw new Exception("ID do fornecedor não foi informado!"); }

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

            // Verifica se fornecedor está cadastrado

            if (fornDAO.buscarPorId(comp.getIdFornecedor()) == null) {

                throw new Exception("Fornecedor informado não foi localizado!");

            }

            // Verifica se o produto está cadastrado

            if (prodDAO.buscarPorId(comp.getIdProduto()) == null) {

                throw new Exception("Produto informado não foi localizado!");

            }

            if (alteracao) {

                if ( compDAO.alterar(comp) ) {

                    Mensagem resMsg = new Mensagem("Dados da compra alterados!", Mensagem.Tipo.SUCESSO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                } else { throw new Exception("Não foi possível alterar os dados da compra!"); }

            } else {

                if ( compDAO.inserir(comp) ) {

                    Mensagem resMsg = new Mensagem("Dados da compra incluídos!", Mensagem.Tipo.SUCESSO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                } else { throw new Exception("Não foi possível incluir os dados da compra!"); }

            }

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

        // Lógica da compra

        try {

            if (!alteracao) {

                prod = prodDAO.buscarPorId(comp.getIdProduto());

                if (prod == null) {

                    throw new Exception("Produto informado não foi localizado!");

                } else {

                    prod.setPrecoCompra(comp.getValorCompra());
                    prod.setQuantidadeDisponivel(prod.getQuantidadeDisponivel() + comp.getQuantidadeCompra());
                    
                    if ( !(prodDAO.alterar(prod)) ) {

                        throw new Exception("Não foi possível atualizar os dados do produto!");

                    }

                }

            }

        } catch (SQLException excecao) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados!",
                excecao,
                request,
                response
            );

        } catch (Exception e) {

            Mensagem resMsg = new Mensagem(e.getMessage(), Mensagem.Tipo.ERRO);
            request.setAttribute("resMensagem", resMsg);
            this.doGet(request, response);

        }

        // Fecha conexão

        try {

            prodDAO.encerrarConexao();
            fornDAO.encerrarConexao();
            compDAO.encerrarConexao();

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível fechar a conexão com o banco de dados!",
                e,
                request,
                response
            );

        }

    }

    /* ---------------------------------------------------------------------- */

    private void incluir(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        request.setAttribute("acao", "incluir");

        try {

            ProdutoDAO prodDAO = new ProdutoDAO();
            request.setAttribute("produtos", prodDAO.listar());

            RequestDispatcher rd = request.getRequestDispatcher("/ControllerCompras.jsp");  
            rd.forward(request, response);

        } catch (SQLException excecao) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados!",
                excecao,
                request,
                response
            );

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

                    CompraDAO compDAO = new CompraDAO();
                    ProdutoDAO prodDAO = new ProdutoDAO();
                    Compra comp = compDAO.buscarPorId(Integer.parseInt(request.getParameter("id")));
                    ArrayList<Produto> lista = prodDAO.listar();
                    
                    if (comp != null) {

                        if (comp.getIdFuncionario() == (int) request.getSession().getAttribute("usuarioID")) {

                            request.setAttribute("compra", comp);
                            request.setAttribute("produtos", lista);

                            RequestDispatcher rd = request.getRequestDispatcher("/ControllerCompras.jsp");  
                            rd.forward(request, response);

                        } else { throw new Exception("Compra informada não pertence ao usuário atual!"); }

                    } else { throw new Exception("Não foi possível localizar a compra informada!"); }

                    compDAO.encerrarConexao();
                    prodDAO.encerrarConexao();

                } else { throw new Exception("ID da compra informado não é numérico!"); }

            } else { throw new Exception("ID da compra não foi informado!"); }

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados!",
                e,
                request,
                response
            );

        } catch (Exception e) {

            ServletUtils.mensagem(
                "/ControllerCompras?acao=listar",
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
                    CompraDAO compDAO = new CompraDAO();
                    Compra comp = compDAO.buscarPorId(id);
                    
                    if (comp != null) {

                        if (comp.getIdFuncionario() == (int) request.getSession().getAttribute("usuarioID")) {

                            if ( compDAO.deletar(id) ) {

                                ServletUtils.mensagem(
                                    "/ControllerCompras?acao=listar",
                                    "Compra excluída!",
                                    Mensagem.Tipo.SUCESSO,
                                    request,
                                    response
                                );

                            } else { throw new Exception("Não foi possível excluir a compra informada!"); }
                            
                        } else { throw new Exception("Compra informada não pertence ao usuário atual!"); }

                    } else { throw new Exception("Não foi possível localizar a compra informada!"); }

                } else { throw new Exception("ID da compra informado não é numérico!"); }

            } else { throw new Exception("ID da compra não foi informado!"); }

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados!",
                e,
                request,
                response
            );

        } catch (Exception e) {

            ServletUtils.mensagem(
                "/ControllerCompras?acao=listar",
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

        CompraDAO compDAO = null;
        ArrayList<Compra> lista = null;

        try {

            compDAO = new CompraDAO();
            lista = compDAO.listar();
            compDAO.encerrarConexao();

        } catch (SQLException excecao) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados!",
                excecao,
                request,
                response
            );

        }

        request.setAttribute("lista", lista);

        RequestDispatcher rd = request.getRequestDispatcher("/ControllerCompras.jsp");  
        rd.forward(request, response);

    }

}