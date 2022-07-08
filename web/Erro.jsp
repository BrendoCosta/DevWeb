<%@ page import="java.util.*, app.*" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
    <jsp:include page="include/head.jsp">
        <jsp:param name="titulo" value="Página de Login"/>
    </jsp:include>
    <body>
        <div class="container m-0 p-sm-2 p-md-0 mw-100 min-vh-100">
            <div class="row m-0 p-0 min-vh-100">
                <div class="col-md p-sm-2 p-md-5">
                    <div id="main">
                        <div class="row"><h2 class="p-0">Erro na aplicação</h2></div>
                        <div class="row"><p class="p-0"><%= (String) request.getAttribute("msgErro") %></p></div>
                        <div class="row">
                            <textarea class="p-0 form-control" style="white-space: pre; font-family: monospace; font-size: 0.8rem;" readonly rows="24" wrap="off"><%= (String) request.getAttribute("msgExcecao") %></textarea>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="assets/libraries/jquery-3.6.0.min.js"></script>
        <script src="assets/libraries/jquery.mask.min.js"></script>
        <script src="assets/libraries/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="assets/scripts/utils.js"></script>
    </body>
</html>
