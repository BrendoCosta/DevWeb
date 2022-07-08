package app;

import java.io.*;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;

public class ServletUtils {

    public static void mensagem(
        String servlet,
        String mensagem,
        Mensagem.Tipo tipo,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {

        Mensagem resMsg = new Mensagem(mensagem, tipo);
        request.setAttribute("resMensagem", resMsg);

        RequestDispatcher rd = request.getRequestDispatcher(servlet);  
        rd.forward(request, response);

    }

    /* ---------------------------------------------------------------------- */

    public static void mensagemErroFatal(
        String mensagem,
        SQLException e,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {

        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String textoExcecao = sw.toString();
        request.setAttribute("msgErro", mensagem);
        request.setAttribute("msgExcecao", textoExcecao);

        RequestDispatcher rd = request.getRequestDispatcher("/Erro");  
        rd.forward(request, response);

    }

    /* ---------------------------------------------------------------------- */

    public static boolean validaSessao(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession sessao = request.getSession();

        if ( ((Boolean) sessao.getAttribute("logado")) == null ) {

            response.sendRedirect(request.getContextPath() + "/Login");
            return false;

        } else {

            if ( !((Boolean) sessao.getAttribute("logado")) ) {

                ServletUtils.mensagem(
                    "/Login",
                    "Sessão expirada: realize login novamente para continuar!",
                    Mensagem.Tipo.ERRO,
                    request,
                    response
                );

                return false;

            }

        }

        return true;

    }

}