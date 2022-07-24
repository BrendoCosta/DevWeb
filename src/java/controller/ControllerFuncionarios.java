package controller;

import app.ServletUtils;
import app.Mensagem;
import model.Funcionario;
import dao.FuncionarioDAO;
import model.Venda;
import dao.VendaDAO;
import model.Compra;
import dao.CompraDAO;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.sql.SQLException;

@WebServlet(name = "ControllerFuncionarios", urlPatterns = {"/ControllerFuncionarios"})
public class ControllerFuncionarios extends HttpServlet {

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

        try {

            if (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.ADMIN) {

                if ( request.getParameter("prmAlteracao") != null ) {

                    alterarPost(request, response);

                } else {

                    incluirPost(request, response);

                }

            } else { throw new Exception("Somente administradores possuem acesso a esta função!"); }

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados!",
                e,
                request,
                response
            );

        } catch (Exception e) {

            ServletUtils.mensagem(
                "/ControllerFuncionarios?acao=listar",
                e.getMessage(),
                Mensagem.Tipo.ERRO,
                request,
                response
            );

        }

    }

    /* ---------------------------------------------------------------------- */

    private void incluirPost(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        try {

            Funcionario func = new Funcionario();
            FuncionarioDAO funcDAO = new FuncionarioDAO();

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmId") != null
                && !request.getParameter("prmId").isEmpty() ) {

                if ( request.getParameter("prmId").matches("\\d+")
                    && request.getParameter("prmId").length() <= 11 ) {

                    Funcionario funcAux = funcDAO.buscarPorId(Integer.parseInt(request.getParameter("prmId")));
                    
                    if (funcAux == null) {

                        func.setId(Integer.parseInt(request.getParameter("prmId")));

                    } else { throw new Exception("Já existe um funcionário com o ID informado!"); }

                } else { throw new Exception("ID informado é inválido!"); }

            }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmNome") != null
                && !request.getParameter("prmNome").isEmpty() ) {

                if ( request.getParameter("prmNome").length() <= 50 ) {

                    func.setNome(request.getParameter("prmNome"));

                } else { throw new Exception("Nome informado é inválido!"); }

            } else { throw new Exception("Nome não foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmCpf") != null
                && !request.getParameter("prmCpf").isEmpty() ) {

                if ( request.getParameter("prmCpf").length() == 14 ) {

                    func.setCpf(request.getParameter("prmCpf"));

                } else { throw new Exception("CPF informado é inválido!"); }

            } else { throw new Exception("CPF não foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmSenha") != null
                && !request.getParameter("prmSenha").isEmpty() ) {

                if ( request.getParameter("prmSenha").length() <= 10 ) {

                    func.setSenha(request.getParameter("prmSenha"));

                } else { throw new Exception("Senha informada é inválida!"); }

            } else { throw new Exception("Senha não foi informada!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmPapel") != null
                && !request.getParameter("prmPapel").isEmpty() ) {

                List<Integer> papeis = new ArrayList<>(Arrays.asList(0, 1, 2));

                if ( papeis.contains(Integer.parseInt(request.getParameter("prmPapel"))) ) {

                    func.setPapel(Funcionario.Papel.values()[Integer.parseInt(request.getParameter("prmPapel"))]);

                } else { throw new Exception("Papel informado é inválido!"); }

            } else { throw new Exception("Papel não foi informado!"); }

            // ---------------------------------------------------------------------
            // ---------------------------------------------------------------------

            boolean sucesso = funcDAO.inserir(func);
            funcDAO.encerrarConexao();

            if (sucesso) {

                Mensagem resMsg = new Mensagem("Funcionário cadastrado!", Mensagem.Tipo.SUCESSO);
                request.setAttribute("resMensagem", resMsg);
                this.doGet(request, response);

            } else { throw new Exception("Não foi possível cadastrar o funcionário!"); }

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

    private void alterarPost(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        try {

            Funcionario func = new Funcionario();
            FuncionarioDAO funcDAO = new FuncionarioDAO();

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmId") != null
                && !request.getParameter("prmId").isEmpty() ) {

                if ( request.getParameter("prmId").matches("\\d+")
                    && request.getParameter("prmId").length() <= 11 ) {

                    Funcionario funcAux = funcDAO.buscarPorId(Integer.parseInt(request.getParameter("prmId")));
                    
                    if (funcAux != null) {

                        func.setId(Integer.parseInt(request.getParameter("prmId")));

                    } else { throw new Exception("Não existe um funcionário com o ID informado!"); }

                } else { throw new Exception("ID informado é inválido!"); }

            } else { throw new Exception("ID não foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmNome") != null
                && !request.getParameter("prmNome").isEmpty() ) {

                if ( request.getParameter("prmNome").length() <= 50 ) {

                    func.setNome(request.getParameter("prmNome"));

                } else { throw new Exception("Nome informado é inválido!"); }

            } else { throw new Exception("Nome não foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmCpf") != null
                && !request.getParameter("prmCpf").isEmpty() ) {

                if ( request.getParameter("prmCpf").length() == 14 ) {

                    func.setCpf(request.getParameter("prmCpf"));

                } else { throw new Exception("CPF informado é inválido!"); }

            } else { throw new Exception("CPF não foi informado!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmSenha") != null
                && !request.getParameter("prmSenha").isEmpty() ) {

                if ( request.getParameter("prmSenha").length() <= 10 ) {

                    func.setSenha(request.getParameter("prmSenha"));

                } else { throw new Exception("Senha informada é inválida!"); }

            } else { throw new Exception("Senha não foi informada!"); }

            // ---------------------------------------------------------------------

            if ( request.getParameter("prmPapel") != null
                && !request.getParameter("prmPapel").isEmpty() ) {

                List<Integer> papeis = new ArrayList<>(Arrays.asList(0, 1, 2));

                if ( papeis.contains(Integer.parseInt(request.getParameter("prmPapel"))) ) {

                    func.setPapel(Funcionario.Papel.values()[Integer.parseInt(request.getParameter("prmPapel"))]);

                } else { throw new Exception("Papel informado é inválido!"); }

            } else { throw new Exception("Papel não foi informado!"); }

            // ---------------------------------------------------------------------
            // ---------------------------------------------------------------------

            boolean sucesso = funcDAO.alterar(func);
            funcDAO.encerrarConexao();

            if (sucesso) {

                Mensagem resMsg = new Mensagem("Funcionário alterado!", Mensagem.Tipo.SUCESSO);
                request.setAttribute("resMensagem", resMsg);
                this.doGet(request, response);

            } else { throw new Exception("Não foi possível alterar o funcionário!"); }

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

            if (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.ADMIN) {

                RequestDispatcher rd = request.getRequestDispatcher("/ControllerFuncionarios.jsp");  
                rd.forward(request, response);

            } else {

                throw new Exception("Somente administradores possuem acesso a esta função!");

            }

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados!",
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

    private void alterar(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        request.setAttribute("acao", "alterar");

        try {

            if (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.ADMIN) {

                if ( request.getParameter("id") != null
                && !request.getParameter("id").isEmpty() ) {

                    if ( request.getParameter("id").matches("\\d+") ) {

                        FuncionarioDAO funcDAO = new FuncionarioDAO();
                        Funcionario func = funcDAO.buscarPorId(Integer.parseInt(request.getParameter("id")));
                        funcDAO.encerrarConexao();

                        if (func != null) {

                            request.setAttribute("funcionario", func);

                            RequestDispatcher rd = request.getRequestDispatcher("/ControllerFuncionarios.jsp");  
                            rd.forward(request, response);
                            
                        } else { throw new Exception("Não foi possível localizar o funcionário informado!"); }

                        funcDAO.encerrarConexao();

                    } else { throw new Exception("ID do funcionário informado não é numérico!"); }

                } else { throw new Exception("ID do funcionário não foi informado!"); }

            } else { throw new Exception("Apenas administradores podem alterar funcionários!"); }

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados!",
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

        try {

            if (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.ADMIN) {

                FuncionarioDAO funcDAO = null;
                ArrayList<Funcionario> lista = null;

                funcDAO = new FuncionarioDAO();
                lista = funcDAO.listar();
                funcDAO.encerrarConexao();

                request.setAttribute("lista", lista);

                RequestDispatcher rd = request.getRequestDispatcher("/ControllerFuncionarios.jsp");  
                rd.forward(request, response);

            } else {

                throw new Exception("Somente administradores possuem acesso a esta função!");

            }

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados!",
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
    
    private void excluir(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        request.setAttribute("acao", "excluir");

        try {

            if (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.ADMIN) {

                if ( request.getParameter("id") != null ) {

                    if ( request.getParameter("id").matches("\\d+") ) {

                        int id = Integer.parseInt(request.getParameter("id"));

                        FuncionarioDAO funcDAO = new FuncionarioDAO();
                        Funcionario func = funcDAO.buscarPorId(id);

                        VendaDAO venDAO = new VendaDAO();
                        ArrayList<Venda> listaVendas = venDAO.listar();
                        
                        CompraDAO compDAO = new CompraDAO();
                        ArrayList<Compra> listaCompras = compDAO.listar();
                        
                        boolean existeVenda = false;
                        boolean existeCompra = false;

                        if (func != null) {

                            for (int i = 0; i < listaVendas.size(); i++) {

                                if (listaVendas.get(i).getIdFuncionario() == func.getId() ) {
                                    existeVenda = true;
                                    break;
                                }

                            }

                            for (int i = 0; i < listaCompras.size(); i++) {

                                if (listaCompras.get(i).getIdFuncionario() == func.getId() ) {
                                    existeCompra = true;
                                    break;
                                }

                            }

                            if (!existeVenda) {

                                if (!existeCompra) {

                                    if (func.getId() != (int) request.getSession().getAttribute("usuarioID")) {

                                        if ( funcDAO.deletar(id) ) {

                                            ServletUtils.mensagem(
                                                "/ControllerFuncionarios?acao=listar",
                                                "Funcionário excluído!",
                                                Mensagem.Tipo.SUCESSO,
                                                request,
                                                response
                                            );

                                        } else { throw new Exception("Não foi possível excluir o funcionário informado!"); }

                                    } else { throw new Exception("Não é possível excluir seu próprio usuário enquanto logado!"); }

                                } else { throw new Exception("Não foi possível excluir o funcionário informado pois há compras cadastradas para ele!"); }

                            } else { throw new Exception("Não foi possível excluir o funcionário informado pois há vendas cadastradas para ele!"); }

                        } else { throw new Exception("Não foi possível localizar o funcionário informado!"); }

                        funcDAO.encerrarConexao();
                        venDAO.encerrarConexao();
                        compDAO.encerrarConexao();

                    } else { throw new Exception("ID do funcionário informado não é numérico!"); }

                } else { throw new Exception("ID do funcionário não foi informado!"); }

            } else { throw new Exception("Apenas administradores podem excluir funcionários!"); }

        } catch (SQLException e) {

            ServletUtils.mensagemErroFatal(
                "Não foi possível consultar o banco de dados!",
                e,
                request,
                response
            );

        } catch (Exception e) {

            ServletUtils.mensagem(
                "/ControllerFuncionarios?acao=listar",
                e.getMessage(),
                Mensagem.Tipo.ERRO,
                request,
                response
            );

        }

    }

}