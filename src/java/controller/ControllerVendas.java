package controller;
import app.ServletUtils;
import app.Mensagem;
import model.Venda;
import dao.VendaDAO;
import model.Produto;
import dao.ProdutoDAO;
import model.Cliente;
import dao.ClienteDAO;
import java.util.ArrayList;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.sql.SQLException;

@WebServlet(name = "ControllerVendas", urlPatterns = {"/ControllerVendas"})
public class ControllerVendas extends HttpServlet {

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

        Venda ven = new Venda();
        VendaDAO venDAO = new VendaDAO();
        
        Produto prod = null;
        ProdutoDAO prodDAO = new ProdutoDAO();

        Cliente clnt = null;
        ClienteDAO clntDAO = new ClienteDAO();

        // Valida parâmetros

        boolean alteracao = false;

        try {

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmId") != null ) {

                if ( request.getParameter("prmId").matches("\\d+")
                    && request.getParameter("prmId").length() <= 11 ) {

                    ven = venDAO.buscarPorId(Integer.parseInt(request.getParameter("prmId")));
                    
                    if (ven != null) {

                        if (ven.getIdFuncionario() == (int) request.getSession().getAttribute("usuarioID")) {

                            alteracao = true;

                        } else { throw new Exception("Venda informada não pertence ao funcionário atual!"); }

                    } else { throw new Exception("Não foi possível localizar a venda com o ID informado!"); }

                } else { throw new Exception("ID da venda é inválido!"); }

            }

            // ---------------------------------------------------------------------

            ven.setIdFuncionario((int) request.getSession().getAttribute("usuarioID"));

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmValorVenda") != null ) {

                if ( request.getParameter("prmValorVenda").matches("[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)") ) {

                    ven.setValorVenda(Float.parseFloat(request.getParameter("prmValorVenda")));

                } else { throw new Exception("Valor informado é inválido!"); }

            } else { throw new Exception("Valor não foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmQuantidadeVenda") != null ) {

                if ( request.getParameter("prmQuantidadeVenda").matches("\\d+")
                    && request.getParameter("prmQuantidadeVenda").length() <= 11 ) {

                    if ( Integer.parseInt(request.getParameter("prmQuantidadeVenda")) > 0) {

                        ven.setQuantidadeVenda(Integer.parseInt(request.getParameter("prmQuantidadeVenda")));

                    } else { throw new Exception("Quantidade informada não pode ser nula!"); }

                } else { throw new Exception("Quantidade informada é inválida!"); }

            } else { throw new Exception("Quantidade não foi informada!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmIdProduto") != null ) {

                if ( request.getParameter("prmIdProduto").matches("\\d+")
                    && request.getParameter("prmIdProduto").length() <= 11 ) {

                    prod = prodDAO.buscarPorId(Integer.parseInt(request.getParameter("prmIdProduto")));

                    if (prod != null) {

                        if (prod.getLiberadoVenda() == Produto.Liberado.S) {

                            if (prod.getQuantidadeDisponivel() >= ven.getQuantidadeVenda()) {

                                ven.setIdProduto(prod.getId());

                            } else { throw new Exception("Produto informado está esgotado!"); }

                        } else { throw new Exception("Produto informado não está liberado para venda!"); }

                    } else { throw new Exception("Não foi possível localizar o produto com o ID informado!"); }

                } else { throw new Exception("ID do produto é inválido!"); }

            } else { throw new Exception("ID do produto não foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmDataVenda") != null ) {

                if ( request.getParameter("prmDataVenda").length() == 16 ) {

                    ven.setDataVenda(request.getParameter("prmDataVenda"));

                } else { throw new Exception("Data informada é inválida!"); }

            } else { throw new Exception("Data não foi informada!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmIdCliente") != null ) {

                if ( request.getParameter("prmIdCliente").matches("\\d+")
                    && request.getParameter("prmIdCliente").length() <= 11 ) {

                    clnt = clntDAO.buscarPorId(Integer.parseInt(request.getParameter("prmIdCliente")));

                    if (clnt != null) {

                        ven.setIdCliente(clnt.getId());

                    } else { throw new Exception("Não foi possível localizar o cliente com o ID informado!"); }

                } else { throw new Exception("ID do cliente informado é inválido!"); }

            } else { throw new Exception("ID do cliente não foi informado!"); }

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

                if ( venDAO.alterar(ven) ) {

                    Mensagem resMsg = new Mensagem("Dados da venda alterados!", Mensagem.Tipo.SUCESSO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                } else { throw new Exception("Não foi possível alterar os dados da venda!"); }

            } else {

                if ( venDAO.inserir(ven) && prodDAO.vender(prod.getId(), ven.getQuantidadeVenda()) ) {

                    Mensagem resMsg = new Mensagem("Dados da venda incluídos!", Mensagem.Tipo.SUCESSO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                } else { throw new Exception("Não foi possível incluir os dados da venda!"); }

            }

            venDAO.encerrarConexao();
            prodDAO.encerrarConexao();
            clntDAO.encerrarConexao();

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

        try {

            ProdutoDAO prodDAO = new ProdutoDAO();
            request.setAttribute("produtosDisponiveis", prodDAO.listar(true));

            RequestDispatcher rd = request.getRequestDispatcher("/ControllerVendas.jsp");  
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

                    VendaDAO venDAO    = new VendaDAO();
                    ProdutoDAO prodDAO = new ProdutoDAO();
                    ClienteDAO clntDAO = new ClienteDAO();
                    Venda ven          = null;
                    Produto prod       = null;
                    Cliente clnt       = null;
                    ArrayList<Produto> lista = null;

                    ven   = venDAO.buscarPorId(Integer.parseInt(request.getParameter("id")));
                    prod  = prodDAO.buscarPorId(ven.getIdProduto());
                    clnt  = clntDAO.buscarPorId(ven.getIdCliente());
                    lista = prodDAO.listar();
                    
                    if (ven != null) {

                        if (ven.getIdFuncionario() == (int) request.getSession().getAttribute("usuarioID")) {

                            if (prod != null ) {

                                if (clnt != null ) {

                                    request.setAttribute("venda", ven);
                                    request.setAttribute("cliente", clnt);
                                    request.setAttribute("produto", prod);
                                    request.setAttribute("produtosDisponiveis", lista);

                                    RequestDispatcher rd = request.getRequestDispatcher("/ControllerVendas.jsp");  
                                    rd.forward(request, response);

                                } else { throw new Exception("Não foi possível localizar o cliente associado a venda informada!"); }

                            } else { throw new Exception("Não foi possível localizar o produto associado a venda informada!"); }

                        } else { throw new Exception("Venda informada não pertence ao usuário atual!"); }

                    } else { throw new Exception("Não foi possível localizar a venda informada!"); }

                    venDAO.encerrarConexao();
                    prodDAO.encerrarConexao();
                    clntDAO.encerrarConexao();

                } else { throw new Exception("ID da venda informado não é numérico!"); }

            } else { throw new Exception("ID da venda não foi informado!"); }

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados!",
                e,
                request,
                response
            );

        } catch (Exception e) {

            ServletUtils.mensagem(
                "/ControllerVendas?acao=listar",
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
                    VendaDAO venDAO = new VendaDAO();
                    Venda ven = venDAO.buscarPorId(id);
                    
                    if (ven != null) {

                        if (ven.getIdFuncionario() == (int) request.getSession().getAttribute("usuarioID")) {

                            if ( venDAO.deletar(id) ) {

                                ServletUtils.mensagem(
                                    "/ControllerVendas?acao=listar",
                                    "Venda excluída!",
                                    Mensagem.Tipo.SUCESSO,
                                    request,
                                    response
                                );

                            } else { throw new Exception("Não foi possível excluir a venda informada!"); }
                            
                        } else { throw new Exception("Venda informada não pertence ao usuário atual!"); }

                    } else { throw new Exception("Não foi possível localizar a venda informada!"); }

                    venDAO.encerrarConexao();

                } else { throw new Exception("ID da venda informado não é numérico!"); }

            } else { throw new Exception("ID da venda não foi informado!"); }

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados!",
                e,
                request,
                response
            );

        } catch (Exception e) {

            ServletUtils.mensagem(
                "/ControllerVendas?acao=listar",
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

        VendaDAO venDAO = null;
        ArrayList<Venda> lista = null;

        try {

            venDAO = new VendaDAO();
            lista = venDAO.listar();
            venDAO.encerrarConexao();

        } catch (SQLException excecao) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados!",
                excecao,
                request,
                response
            );

        }

        request.setAttribute("lista", lista);

        RequestDispatcher rd = request.getRequestDispatcher("/ControllerVendas.jsp");  
        rd.forward(request, response);

    }

}