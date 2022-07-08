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

        boolean alteracao = false;

        // Verifica se o POST se refere a uma alteração

        if (request.getParameter("prmId") != null) {

            if (!((String)request.getParameter("prmId")).isEmpty()
                && Integer.parseInt(request.getParameter("prmId")) >= 0) {

                alteracao = true;
                ven.setId(Integer.parseInt(request.getParameter("prmId")));

            }

        }

        // Busca o produto especificado no sistema

        Produto prod = null;
        ProdutoDAO prodDAO = null;

        try {

            prodDAO = new ProdutoDAO();
            prod = prodDAO.buscarPorId( Integer.parseInt(request.getParameter("prmIdProduto")) );

            if (prod == null) {

                Mensagem resMsg = new Mensagem("Não foi possível localizar o produto especificado!", Mensagem.Tipo.ERRO);
                request.setAttribute("resMensagem", resMsg);
                this.doGet(request, response);

            } else {

                if (prod.getLiberadoVenda() == Produto.Liberado.N) {

                    Mensagem resMsg = new Mensagem("Produto especificado não está liberado para venda!", Mensagem.Tipo.ERRO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                }

                if ( !(prod.getQuantidadeDisponivel() >= 1) ) {

                    Mensagem resMsg = new Mensagem("Produto especificado está esgotado!", Mensagem.Tipo.ERRO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                }

            }

        } catch (SQLException excecao) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados para consultar os produtos!",
                excecao,
                request,
                response
            );

        }

        // Busca o cliente no sistema

        Cliente clnt = null;
        ClienteDAO clntDAO = null;

        try {

            clntDAO = new ClienteDAO();
            clnt = clntDAO.buscarPorCpf((String) request.getParameter("prmCpfCliente"));

            if (clnt == null) {

                Mensagem resMsg = new Mensagem("Não foi possível localizar o cliente especificado!", Mensagem.Tipo.ERRO);
                request.setAttribute("resMensagem", resMsg);
                this.doGet(request, response);

            }

        } catch (SQLException excecao) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados para consultar os clientes!",
                excecao,
                request,
                response
            );

        }

        // Dados da venda

        ven.setDataVenda( (String) request.getParameter("prmDataVenda") );
        ven.setQuantidadeVenda( Integer.parseInt(request.getParameter("prmQuantidadeVenda")) );
        ven.setValorVenda((float) (prod.getPrecoVenda() * Integer.parseInt(request.getParameter("prmQuantidadeVenda"))));
        ven.setIdCliente( clnt.getId() );
        ven.setIdProduto( prod.getId() );
        ven.setIdFuncionario( (Integer) (request.getSession()).getAttribute("usuarioID") );

        if (alteracao) {

            // Altera venda

            try {

                Venda venAux = venDAO.buscarPorId(ven.getId());

                if ( venAux.getIdFuncionario() == (Integer) (request.getSession()).getAttribute("usuarioID") ) {

                    venDAO.alterar(ven);

                    Mensagem resMsg = new Mensagem("Venda alterada!", Mensagem.Tipo.SUCESSO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                } else {

                    Mensagem resMsg = new Mensagem("Venda selecionada para alteração não pertence ao usuário atual!", Mensagem.Tipo.ERRO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                }

            } catch (SQLException excecao) {

                ServletUtils.mensagemErroFatal(
                    "Não foi possível altera os dados da venda no banco de dados!",
                    excecao,
                    request,
                    response
                );

            }

        } else {

            // Vende produto

            try {

                prodDAO.vender(prod.getId(), Integer.parseInt(request.getParameter("prmQuantidadeVenda")));

            } catch (SQLException excecao) {

                ServletUtils.mensagemErroFatal(
                    "Não foi possível cadastrar a venda do produto no banco de dados!",
                    excecao,
                    request,
                    response
                );

            }

            // Cadastra venda

            try {

                venDAO.inserir(ven);

                Mensagem resMsg = new Mensagem("Venda realizada!", Mensagem.Tipo.SUCESSO);
                request.setAttribute("resMensagem", resMsg);
                this.doGet(request, response);

            } catch (SQLException excecao) {

                ServletUtils.mensagemErroFatal(
                    "Não foi possível cadastrar a venda para o cliente no banco de dados!",
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
        
        Integer id = null;
        
        if (!((String) request.getParameter("id") == null)) {

            id = Integer.parseInt( (String) request.getParameter("id") );

        }

        if (id != null) {

            Venda ven          = null;
            Produto prod       = null;
            Cliente clnt       = null;
            ArrayList<Produto> lista = null;
            VendaDAO venDAO    = new VendaDAO();
            ProdutoDAO prodDAO = new ProdutoDAO();
            ClienteDAO clntDAO = new ClienteDAO();

            try {

                ven   = venDAO.buscarPorId(id);
                clnt  = clntDAO.buscarPorId(ven.getIdCliente());
                prod  = prodDAO.buscarPorId(ven.getIdProduto());
                lista = prodDAO.listar();

                if (!(ven == null)) {

                    request.setAttribute("venda", ven);

                } else {

                    ServletUtils.mensagem(
                        "/ControllerVendas?acao=listar",
                        "Não foi possível encontrar a venda com o ID informado!",
                        Mensagem.Tipo.ERRO,
                        request,
                        response
                    );

                }

                if (!(clnt == null)) {

                    request.setAttribute("cliente", clnt);

                } else {

                    ServletUtils.mensagem(
                        "/ControllerVendas?acao=listar",
                        "Não foi possível encontrar o cliente associado a venda!",
                        Mensagem.Tipo.ERRO,
                        request,
                        response
                    );

                }

                if (!(prod == null)) {

                    request.setAttribute("produto", prod);

                } else {

                    ServletUtils.mensagem(
                        "/ControllerVendas?acao=listar",
                        "Não foi possível encontrar o produto associado a venda!",
                        Mensagem.Tipo.ERRO,
                        request,
                        response
                    );

                }

                if (!(lista == null)) {

                    request.setAttribute("produtosDisponiveis", lista);

                } else {

                    ServletUtils.mensagem(
                        "/ControllerVendas?acao=listar",
                        "Não foi possível listar os produto cadastrados!",
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

        }

        RequestDispatcher rd = request.getRequestDispatcher("/ControllerVendas.jsp");  
        rd.forward(request, response);

    }

    /* ---------------------------------------------------------------------- */

    private void excluir(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        int id = -1;

        if (request.getParameter("id") != null) {

            id = Integer.parseInt(request.getParameter("id"));

        }

        if (id != -1) {

            Venda ven = null;
            VendaDAO venDAO = null;

            try {

                venDAO = new VendaDAO();
                ven = venDAO.buscarPorId(id);

                if ( ven.getIdFuncionario() == (Integer) (request.getSession()).getAttribute("usuarioID") ) {

                    if ( venDAO.deletar(id) ) {

                        ServletUtils.mensagem(
                            "/ControllerVendas?acao=listar",
                            "Venda excluída!",
                            Mensagem.Tipo.SUCESSO,
                            request,
                            response
                        );

                    } else {

                        ServletUtils.mensagem(
                            "/ControllerVendas?acao=listar",
                            "Não foi possível excluir a venda selecionado!",
                            Mensagem.Tipo.ERRO,
                            request,
                            response
                        );

                    }

                } else {

                    ServletUtils.mensagem(
                        "/ControllerVendas?acao=listar",
                        "Venda selecionada para exclusão não pertence ao usuário atual!",
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
                "/ControllerClientes?acao=listar",
                "ID da venda não informado!",
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