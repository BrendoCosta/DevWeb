<%@ page import="java.util.ArrayList, app.Mensagem, model.Cliente" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
    <jsp:include page="include/head.jsp">
        <jsp:param name="titulo" value="Área da Aplicação"/>
    </jsp:include>
    <body>
        <div class="container g-0 m-0 mw-100">
            <jsp:include page="include/Cabecalho.jsp"/>
            <div class="row g-0 p-4">
                <div id="aviso"></div>
                <%=request.getContextPath()%>
            </div>
        </div>
        <jsp:include page="include/scripts.html"/>
        <script src="assets/libraries/datatables/datatables.min.js"></script>
        <script src="assets/libraries/datatables/dataTables.responsive.min.js"></script>
        <script>

            <%
                Mensagem msg = (Mensagem) request.getAttribute("resMensagem");
                if (msg != null) {

                    %>exibirAviso("aviso", "<%=msg.getTextoTipo()%>", "<%=msg.getMensagem()%>");<%

                }
            %>

            $(document).ready(function () {

            });
        </script>
    </body>
</html>
