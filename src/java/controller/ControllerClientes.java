package controller;
import app.ServletUtils;
import app.Mensagem;
import model.Cliente;
import dao.ClienteDAO;
import dao.VendaDAO;
import model.Funcionario;
import java.util.ArrayList;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.sql.SQLException;

@WebServlet(name = "ControllerClientes", urlPatterns = {"/ControllerClientes"})
public class ControllerClientes extends HttpServlet {

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

        Cliente clnt = new Cliente();
        ClienteDAO clntDAO = new ClienteDAO();

        boolean alteracao = false;

        try {

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmId") != null
                && !request.getParameter("prmId").isEmpty() ) {

                if ( request.getParameter("prmId").matches("\\d+")
                    && request.getParameter("prmId").length() <= 11 ) {

                    if (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.VENDEDOR) {

                        clnt = clntDAO.buscarPorId(Integer.parseInt(request.getParameter("prmId")));
                        
                        if (clnt != null) {

                            alteracao = true;
                            clnt.setId(Integer.parseInt(request.getParameter("prmId")));

                        } else { throw new Exception("N??o foi poss??vel localizar o cliente com o ID informado!"); }

                    } else { throw new Exception("Apenas vendedores podem incluir ou alterar clientes!"); }

                } else { throw new Exception("ID do cliente informado n??o ?? num??rico!"); }

            }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmNome") != null
                && !request.getParameter("prmNome").isEmpty() ) {

                if ( request.getParameter("prmNome").length() <= 50 ) {

                    clnt.setNome(request.getParameter("prmNome"));

                } else { throw new Exception("Nome ?? inv??lido!"); }

            } else { throw new Exception("Nome n??o foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmCpf") != null
                && !request.getParameter("prmCpf").isEmpty() ) {

                if ( request.getParameter("prmCpf").length() == 14 ) {

                    clnt.setCpf(request.getParameter("prmCpf"));

                } else { throw new Exception("CPF ?? inv??lido!"); }

            } else { throw new Exception("CPF n??o foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmEndereco") != null
                && !request.getParameter("prmEndereco").isEmpty() ) {

                if ( request.getParameter("prmEndereco").length() <= 50 ) {

                    clnt.setEndereco(request.getParameter("prmEndereco"));

                } else { throw new Exception("Endere??o ?? inv??lido!"); }

            } else { throw new Exception("Endere??o n??o foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmBairro") != null
                && !request.getParameter("prmBairro").isEmpty() ) {

                if ( request.getParameter("prmBairro").length() <= 50 ) {

                    clnt.setBairro(request.getParameter("prmBairro"));

                } else { throw new Exception("Bairro ?? inv??lido!"); }

            } else { throw new Exception("Bairro n??o foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmCidade") != null
                && !request.getParameter("prmCidade").isEmpty() ) {

                if ( request.getParameter("prmCidade").length() <= 50 ) {

                    clnt.setCidade(request.getParameter("prmCidade"));

                } else { throw new Exception("Cidade ?? inv??lida!"); }

            } else { throw new Exception("Cidade n??o foi informada!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmUf") != null
                && !request.getParameter("prmUf").isEmpty() ) {

                if ( request.getParameter("prmUf").length() == 2 ) {

                    clnt.setUf(request.getParameter("prmUf"));

                } else { throw new Exception("UF ?? inv??lida!"); }

            } else { throw new Exception("UF n??o foi informada!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmCep") != null
                && !request.getParameter("prmCep").isEmpty() ) {

                if ( request.getParameter("prmCep").length() == 8 ) {

                    clnt.setCep(request.getParameter("prmCep"));

                } else { throw new Exception("CEP ?? inv??lido!"); }

            } else { throw new Exception("CEP n??o foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmTelefone") != null
                && !request.getParameter("prmTelefone").isEmpty() ) {

                if ( request.getParameter("prmTelefone").length() <= 20 ) {

                    clnt.setTelefone(request.getParameter("prmTelefone"));

                } else { throw new Exception("Telefone ?? inv??lido!"); }

            } else { throw new Exception("Telefone n??o foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmEmail") != null
                && !request.getParameter("prmEmail").isEmpty() ) {

                if ( request.getParameter("prmEmail").length() <= 50 ) {

                    clnt.setEmail(request.getParameter("prmEmail"));

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

                if ( clntDAO.alterar(clnt) ) {

                    Mensagem resMsg = new Mensagem("Cliente alterado!", Mensagem.Tipo.SUCESSO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                } else { throw new Exception("N??o foi poss??vel alterar os dados do cliente!"); }

            } else {

                if ( clntDAO.inserir(clnt) ) {

                    Mensagem resMsg = new Mensagem("Cliente cadastrado!", Mensagem.Tipo.SUCESSO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                } else { throw new Exception("N??o foi poss??vel cadastrar o cliente!"); }

            }

            clntDAO.encerrarConexao();

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

        RequestDispatcher rd = request.getRequestDispatcher("/ControllerClientes.jsp");  
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

                    ClienteDAO clntDAO = new ClienteDAO();
                    Cliente clnt = clntDAO.buscarPorId(Integer.parseInt(request.getParameter("id")));
                    clntDAO.encerrarConexao();

                    if (clnt != null) {

                        if (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.VENDEDOR) {

                            request.setAttribute("cliente", clnt);

                            RequestDispatcher rd = request.getRequestDispatcher("/ControllerClientes.jsp");  
                            rd.forward(request, response);

                        } else { throw new Exception("Apenas vendedores podem alterar clientes!"); }
                        
                    } else { throw new Exception("N??o foi poss??vel localizar o cliente informado!"); }

                } else { throw new Exception("ID do cliente informado n??o ?? num??rico!"); }

            } else { throw new Exception("ID do cliente n??o foi informado!"); }

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "N??o foi poss??vel consultar o banco de dados!",
                e,
                request,
                response
            );

        } catch (Exception e) {

            ServletUtils.mensagem(
                "/ControllerClientes?acao=listar",
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

                    if (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.VENDEDOR) {

                        int id = Integer.parseInt(request.getParameter("id"));
                        ClienteDAO clntDAO = new ClienteDAO();
                        Cliente clnt = clntDAO.buscarPorId(id);
                        VendaDAO venDAO = new VendaDAO();
                        
                        if (clnt != null) {

                            if (venDAO.listarPorCliente(clnt.getId()).size() == 0) {

                                if ( clntDAO.deletar(id) ) {

                                    ServletUtils.mensagem(
                                        "/ControllerClientes?acao=listar",
                                        "Cliente exclu??do!",
                                        Mensagem.Tipo.SUCESSO,
                                        request,
                                        response
                                    );

                                } else { throw new Exception("N??o foi poss??vel excluir o cliente informado!"); }

                            } else { throw new Exception("N??o foi poss??vel excluir o cliente informado pois h?? vendas cadastradas para ele!"); }

                        } else { throw new Exception("N??o foi poss??vel localizar o cliente informado!"); }

                        clntDAO.encerrarConexao();
                        venDAO.encerrarConexao();
                        
                    } else { throw new Exception("Apenas vendedores podem excluir clientes!"); }

                } else { throw new Exception("ID do cliente informado n??o ?? num??rico!"); }

            } else { throw new Exception("ID do cliente n??o foi informado!"); }

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "N??o foi poss??vel consultar o banco de dados!",
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

        ClienteDAO clntDAO = null;
        ArrayList<Cliente> lista = null;

        try {

            clntDAO = new ClienteDAO();
            lista = clntDAO.listar();
            clntDAO.encerrarConexao();

        } catch (SQLException excecao) {

            ServletUtils.mensagemErroFatal(
                "N??o foi poss??vel consultar o banco de dados!",
                excecao,
                request,
                response
            );

        }

        request.setAttribute("lista", lista);

        RequestDispatcher rd = request.getRequestDispatcher("/ControllerClientes.jsp");  
        rd.forward(request, response);

    }

}