package controller;
import app.Mensagem;
import app.ServletUtils;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet(name = "Area", urlPatterns = {"/Area"})
public class Area extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        if (ServletUtils.validaSessao(request, response)) {

            RequestDispatcher rd = request.getRequestDispatcher("/Area.jsp");  
            rd.forward(request, response);

        }

    }

}