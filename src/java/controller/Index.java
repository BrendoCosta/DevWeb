package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.sql.SQLException;

@WebServlet(name = "Index", urlPatterns = {"", "/Index"})
public class Index extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        HttpSession sessao = request.getSession();
        sessao.invalidate();

        RequestDispatcher rd = request.getRequestDispatcher("/Index.jsp");  
        rd.forward(request, response);

    }

}