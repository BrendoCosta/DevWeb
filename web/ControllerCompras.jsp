<%@ page import="java.util.ArrayList, app.Mensagem" contentType="text/html;charset=UTF-8" %>
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
                <%

                    switch ((String) request.getAttribute("acao")) {

                        case "incluir":

                            %><jsp:include page="include/ControllerComprasIncluir.jsp"/><%
                            break;

                        case "alterar":

                            %><jsp:include page="include/ControllerComprasAlterar.jsp"/><%
                            break;

                        case "listar":

                            %><jsp:include page="include/ControllerComprasListar.jsp"/><%
                            break;

                        default:

                            %><jsp:include page="include/ControllerComprasListar.jsp"/><%
                            break;

                    }
                    
                %>
            </div>
        </div>
        <jsp:include page="include/scripts.html"/>
        <script>

            <%
                Mensagem msg = (Mensagem) request.getAttribute("resMensagem");
                if (msg != null) {

                    %>exibirAviso("aviso", "<%=msg.getTextoTipo()%>", "<%=msg.getMensagem()%>");<%

                }
            %>

            $(document).ready(function () {

                $(".link-deletar").click(function(e) {

                    if (!confirm("Confirmar exclusão?")) {

                        e.preventDefault();

                    }

                })

            });
        </script>
    </body>
</html>
