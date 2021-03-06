<%@ page import="java.util.*, app.*" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
    <jsp:include page="include/head.jsp">
        <jsp:param name="titulo" value="Página Inicial"/>
    </jsp:include>
    <body>
        <div class="container g-0 m-0 p-sm-2 p-md-0 mw-100 min-vh-100">
            <div class="row m-0 p-0 min-vh-100">
                <div class="col-md bg-primary p-1 d-none d-md-flex flex-column justify-content-center align-items-center">
                </div>
                <div class="col-md p-1 d-flex flex-column justify-content-center align-items-center">
                    <div id="aviso"></div>
                    <div id="contentBody p-0 d-flex flex-column">
                        <div class="row"><h2 class="p-0">Aplicação</h2></div>
                        <div class="row"><p class="p-0">Página inicial</p></div>
                        <div class="row"><a href="AreaCliente" class="p-0"><button class="btn btn-outline-primary w-100">Área do Cliente</button></a></div>
                        <div class="row"><a href="Login?papel=0" class="p-0 pt-2"><button class="btn btn-outline-primary w-100">Área Privada (Administradores)</button></a></div>
                        <div class="row"><a href="Login?papel=1" class="p-0 pt-2"><button class="btn btn-outline-primary w-100">Área Privada (Vendedores)</button></a></div>
                        <div class="row"><a href="Login?papel=2" class="p-0 pt-2"><button class="btn btn-outline-primary w-100">Área Privada (Compradores)</button></a></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="include/scripts.html"/>
    </body>
    <script>

        <%
            Mensagem msg = null;

            if ( request.getParameter("papel") != null ) {

                if (request.getParameter("papel").equals("e")) {
                    
                    msg = new Mensagem("Funcionário não encontrado!", Mensagem.Tipo.ERRO);

                }

            } else {

                msg = (Mensagem) request.getAttribute("resMensagem");

            }

            if (msg != null) {

                %>exibirAviso("aviso", "<%=msg.getTextoTipo()%>", "<%=msg.getMensagem()%>");<%

            }
        %>

    </script>
</html>
