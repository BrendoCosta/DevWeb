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

            if ( request.getParameter("prmId") != null ) {

                if ( request.getParameter("prmId").matches("\\d+")
                    && request.getParameter("prmId").length() <= 11 ) {

                    clnt = clntDAO.buscarPorId(Integer.parseInt(request.getParameter("prmId")));
                    
                    if (clnt != null) {

                        if (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.VENDEDOR) {

                            alteracao = true;
                            clnt.setId(Integer.parseInt(request.getParameter("prmId")));

                        } else { throw new Exception("Apenas vendedores podem alterar clientes!"); }

                    } else { throw new Exception("Não foi possível localizar o cliente com o ID informado!"); }

                }

            }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmNome") != null
                && !request.getParameter("prmNome").isEmpty() ) {

                if ( request.getParameter("prmNome").length() <= 50 ) {

                    clnt.setNome(request.getParameter("prmNome"));

                } else { throw new Exception("Nome é inválido!"); }

            } else { throw new Exception("Nome não foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmCpf") != null
                && !request.getParameter("prmCpf").isEmpty() ) {

                if ( request.getParameter("prmCpf").length() == 14 ) {

                    clnt.setCpf(request.getParameter("prmCpf"));

                } else { throw new Exception("CPF é inválido!"); }

            } else { throw new Exception("CPF não foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmEndereco") != null
                && !request.getParameter("prmEndereco").isEmpty() ) {

                if ( request.getParameter("prmEndereco").length() <= 50 ) {

                    clnt.setEndereco(request.getParameter("prmEndereco"));

                } else { throw new Exception("Endereço é inválido!"); }

            } else { throw new Exception("Endereço não foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmBairro") != null
                && !request.getParameter("prmBairro").isEmpty() ) {

                if ( request.getParameter("prmBairro").length() <= 50 ) {

                    clnt.setBairro(request.getParameter("prmBairro"));

                } else { throw new Exception("Bairro é inválido!"); }

            } else { throw new Exception("Bairro não foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmCidade") != null
                && !request.getParameter("prmCidade").isEmpty() ) {

                if ( request.getParameter("prmCidade").length() <= 50 ) {

                    clnt.setCidade(request.getParameter("prmCidade"));

                } else { throw new Exception("Cidade é inválida!"); }

            } else { throw new Exception("Cidade não foi informada!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmUf") != null
                && !request.getParameter("prmUf").isEmpty() ) {

                if ( request.getParameter("prmUf").length() == 2 ) {

                    clnt.setUf(request.getParameter("prmUf"));

                } else { throw new Exception("UF é inválida!"); }

            } else { throw new Exception("UF não foi informada!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmCep") != null
                && !request.getParameter("prmCep").isEmpty() ) {

                if ( request.getParameter("prmCep").length() == 8 ) {

                    clnt.setCep(request.getParameter("prmCep"));

                } else { throw new Exception("CEP é inválido!"); }

            } else { throw new Exception("CEP não foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmTelefone") != null
                && !request.getParameter("prmTelefone").isEmpty() ) {

                if ( request.getParameter("prmTelefone").length() <= 20 ) {

                    clnt.setTelefone(request.getParameter("prmTelefone"));

                } else { throw new Exception("Telefone é inválido!"); }

            } else { throw new Exception("Telefone não foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmEmail") != null
                && !request.getParameter("prmEmail").isEmpty() ) {

                if ( request.getParameter("prmEmail").length() <= 50 ) {

                    clnt.setEmail(request.getParameter("prmEmail"));

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

                if ( clntDAO.alterar(clnt) ) {

                    Mensagem resMsg = new Mensagem("Cliente alterado!", Mensagem.Tipo.SUCESSO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                } else { throw new Exception("Não foi possível alterar os dados do cliente!"); }

            } else {

                if ( clntDAO.inserir(clnt) ) {

                    Mensagem resMsg = new Mensagem("Cliente cadastrado!", Mensagem.Tipo.SUCESSO);
                    request.setAttribute("resMensagem", resMsg);
                    this.doGet(request, response);

                } else { throw new Exception("Não foi possível cadastrar o cliente!"); }

            }

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
                        
                    } else { throw new Exception("Não foi possível localizar o cliente informado!"); }

                } else { throw new Exception("ID do cliente informado não é numérico!"); }

            } else { throw new Exception("ID do cliente não foi informado!"); }

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados!",
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

            if ( request.getParameter("id") != null ) {

                if ( request.getParameter("id").matches("\\d+") ) {

                    int id = Integer.parseInt(request.getParameter("id"));
                    ClienteDAO clntDAO = new ClienteDAO();
                    Cliente clnt = clntDAO.buscarPorId(id);
                    VendaDAO venDAO = new VendaDAO();
                    
                    if (clnt != null) {

                        if (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.VENDEDOR) {

                            if (venDAO.listarPorCliente(clnt.getId()).size() == 0) {

                                if ( clntDAO.deletar(id) ) {

                                    ServletUtils.mensagem(
                                        "/ControllerClientes?acao=listar",
                                        "Cliente excluído!",
                                        Mensagem.Tipo.SUCESSO,
                                        request,
                                        response
                                    );

                                } else { throw new Exception("Não foi possível excluir o cliente informado!"); }

                            } else { throw new Exception("Não foi possível excluir o cliente informado pois há vendas cadastradas para ele!"); }
                            
                        } else { throw new Exception("Apenas vendedores podem excluir clientes!"); }

                    } else { throw new Exception("Não foi possível localizar o cliente informado!"); }

                    clntDAO.encerrarConexao();
                    venDAO.encerrarConexao();

                } else { throw new Exception("ID do cliente informado não é numérico!"); }

            } else { throw new Exception("ID do cliente não foi informado!"); }

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados!",
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
                "Não foi possível consultar o banco de dados!",
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