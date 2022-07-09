package controller;
import app.ServletUtils;
import app.Mensagem;
import model.Funcionario;
import dao.FuncionarioDAO;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.sql.SQLException;

@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        HttpSession sessao = request.getSession();
        sessao.invalidate();

        RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp");  
        rd.forward(request, response); 

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        // * ----------------------------------------------------------------- *
        // *                 Armazena dados enviados no formulário             *
        // * ----------------------------------------------------------------- *

        String prmCPF = request.getParameter("prmCPF");
        String prmSenha = request.getParameter("prmSenha");

        // * ----------------------------------------------------------------- *
        // *                    Realiza validações dos dados                   *
        // * ----------------------------------------------------------------- *

        // Valida quantidade de caracteres nos campos
        
        if ( prmCPF.length() != 14 ) {

            ServletUtils.mensagem(
                "/Login",
                "CPF não informado ou com formato inválido!",
                Mensagem.Tipo.ERRO,
                request,
                response
            );

        }

        if ( prmSenha.length() == 0 ) {

            ServletUtils.mensagem(
                "/Login",
                "Senha não informada!",
                Mensagem.Tipo.ERRO,
                request,
                response
            );

        }

        if ( prmSenha.length() > 10 ) {

            ServletUtils.mensagem(
                "/Login",
                "Senha informada é maior que o permitido!",
                Mensagem.Tipo.ERRO,
                request,
                response
            );

        }

        // * ----------------------------------------------------------------- *
        // *         Verifica a presença dos dados no banco de dados           *
        // * ----------------------------------------------------------------- *


        try {

            FuncionarioDAO funcDAO = new FuncionarioDAO();
            Funcionario func = funcDAO.buscarPorCredenciais(prmCPF, prmSenha);
            funcDAO.encerrarConexao();

            if (!(func == null)) {

                HttpSession sessao = request.getSession();

                sessao.setAttribute("logado", true);

                sessao.setAttribute("usuarioID",    func.getId()    );
                sessao.setAttribute("usuarioNome",  func.getNome()  );
                sessao.setAttribute("usuarioCPF",   func.getCpf()   );
                sessao.setAttribute("usuarioSenha", func.getSenha() );
                sessao.setAttribute("usuarioPapel", func.getPapel() );

                response.sendRedirect(request.getContextPath() + "/Area");

            } else {

                ServletUtils.mensagem(
                    "/Login",
                    "Não foi possível encontrar o usuário com as credenciais informadas!",
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

}