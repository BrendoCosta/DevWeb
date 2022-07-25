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

        try {

            if ( request.getParameter("papel") != null
                && !request.getParameter("papel").isEmpty() ) {

                if ( request.getParameter("papel").matches("\\d+") ) {

                    request.setAttribute("papel", request.getParameter("papel"));

                    RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp");  
                    rd.forward(request, response);

                } else { throw new Exception("Papel inválido!"); }

            } else { throw new Exception("Papel não informado!"); }

        } catch (Exception e) {

            ServletUtils.mensagem(
                "/Index",
                e.getMessage(),
                Mensagem.Tipo.ERRO,
                request,
                response
            );

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        try {

            // * ----------------------------------------------------------------- *
            // *                 Armazena dados enviados no formulário             *
            // * ----------------------------------------------------------------- *

            String prmCPF = request.getParameter("prmCPF");
            String prmSenha = request.getParameter("prmSenha");
            String prmPapel = request.getParameter("prmPapel");

            // * ----------------------------------------------------------------- *
            // *                    Realiza validações dos dados                   *
            // * ----------------------------------------------------------------- *

            // Valida quantidade de caracteres nos campos
            
            if ( prmCPF.length() != 14 ) {

                throw new Exception("CPF não informado ou com formato inválido!");

            }

            if ( prmSenha.length() == 0 ) {

                throw new Exception("Senha não informada!");

            }

            if ( prmSenha.length() > 10 ) {

                throw new Exception("Senha informada é maior que o permitido!");

            }

            if ( prmPapel.length() != 1 ) {

                throw new Exception("Papel inválido!");

            }

            // * ----------------------------------------------------------------- *
            // *         Verifica a presença dos dados no banco de dados           *
            // * ----------------------------------------------------------------- *

            FuncionarioDAO funcDAO = new FuncionarioDAO();
            Funcionario func = funcDAO.buscarPorCredenciais(prmCPF, prmSenha, prmPapel);
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

            } else { throw new Exception("Não foi possível encontrar o usuário com as credenciais informadas!"); }

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
            
            response.sendRedirect(request.getContextPath() + "/Index?papel=e");

        }

    }

}