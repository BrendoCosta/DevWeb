package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet(name = "Erro", urlPatterns = {"/Erro"})
public class Erro extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

        RequestDispatcher rd = request.getRequestDispatcher("/Erro.jsp");  
        rd.forward(request, response); 

    }

}