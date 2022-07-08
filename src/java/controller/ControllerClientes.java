package controller;
import app.ServletUtils;
import app.Mensagem;
import model.Cliente;
import dao.ClienteDAO;
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

        // Verifica se o POST se refere a uma alteração

        if (request.getParameter("prmId") != null) {

            if (!((String)request.getParameter("prmId")).isEmpty()
                && Integer.parseInt(request.getParameter("prmId")) >= 0) {

                alteracao = true;
                clnt.setId(Integer.parseInt(request.getParameter("prmId")));

            }

        }
        
        clnt.setNome((String) request.getParameter("prmNome"));
        clnt.setCpf((String) request.getParameter("prmCPF"));
        clnt.setEndereco((String) request.getParameter("prmEndereco"));
        clnt.setBairro((String) request.getParameter("prmBairro"));
        clnt.setCidade((String) request.getParameter("prmCidade"));
        clnt.setUf((String) request.getParameter("prmUF"));
        clnt.setCep((String) request.getParameter("prmCEP"));
        clnt.setTelefone((String) request.getParameter("prmTelefone"));
        clnt.setEmail((String) request.getParameter("prmEmail"));

        try {

            if (alteracao) {

                clntDAO.alterar(clnt);

                Mensagem resMsg = new Mensagem("Cliente atualizado!", Mensagem.Tipo.SUCESSO);
                request.setAttribute("resMensagem", resMsg);
                this.doGet(request, response);

            } else {

                clntDAO.inserir(clnt);

                Mensagem resMsg = new Mensagem("Cliente inserido!", Mensagem.Tipo.SUCESSO);
                request.setAttribute("resMensagem", resMsg);
                this.doGet(request, response);

            }


        } catch(SQLException excecao) {

            ServletUtils.mensagem(
                "/ControllerClientes?acao=incluir",
                "Não foi possível inserir ou atualizar o cliente no banco de dados!",
                Mensagem.Tipo.ERRO,
                request,
                response
            );

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
        
        Integer id = null;
        
        if (!((String) request.getParameter("id") == null)) {

            id = Integer.parseInt( (String) request.getParameter("id") );

        }

        if (id != null) {

            ClienteDAO clntDAO = new ClienteDAO();
            Cliente clnt = null;

            try {

                clnt = clntDAO.buscarPorId(id);

                if (!(clnt == null)) {

                    request.setAttribute("cliente", clnt);

                } else {

                    ServletUtils.mensagem(
                        "/ControllerClientes?acao=listar",
                        "Não foi possível encontrar o cliente com o ID informado!",
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

        RequestDispatcher rd = request.getRequestDispatcher("/ControllerClientes.jsp");  
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

            ClienteDAO clntDAO = new ClienteDAO();

            try {

                if ( clntDAO.deletar(id) ) {

                    ServletUtils.mensagem(
                        "/ControllerClientes?acao=listar",
                        "Cliente excluído!",
                        Mensagem.Tipo.SUCESSO,
                        request,
                        response
                    );

                } else {

                    ServletUtils.mensagem(
                        "/ControllerClientes?acao=listar",
                        "Não foi possível excluir o cliente selecionado!",
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
                "ID do cliente não informado!",
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