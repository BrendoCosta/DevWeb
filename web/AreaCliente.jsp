<%@ page import="java.util.ArrayList, app.Mensagem, model.Produto" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
    <jsp:include page="include/head.jsp">
        <jsp:param name="titulo" value="Área do Cliente"/>
    </jsp:include>
    <body>
        <div class="container g-0 m-0 mw-100">
            <nav class="navbar navbar-expand-lg navbar-dark bg-primary p-4">
                <div class="container-fluid">
                    <a class="navbar-brand" href="#"><strong>Área do Cliente</strong></a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNavDropdown">
                        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                            <li class="nav-item">
                                <a class="nav-link" aria-current="page" href="/<%=request.getContextPath().replace("/", "")%>/">Voltar</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <div class="row g-0 p-4">
                <div id="aviso"></div>
                <div class="pt-4 pb-4"><h2>Produtos Disponíveis</h2></div>
                <div class="row g-0 d-flex justify-content-md-start justify-content-center">
                    <div class="row pb-4">
                        <div class="col-md-auto text-md-start text-center g-0">
                            <input type="text" class="form-control" id="pesquisar" placeholder="Pesquisar...">
                        </div>
                    </div>
                </div>
                <div class="table-responsive">
                    <table class="table table-striped table-bordered text-center small" id="tabela">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">ID do Produto</th>
                                <th scope="col">Nome do Produto</th>
                                <th scope="col">Descrição do Produto</th>
                                <th scope="col">Preço do Produto</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                ArrayList<Produto> lista = (ArrayList<Produto>) request.getAttribute("lista");
                                
                                if (lista != null) {

                                    for (int i = 0; i < lista.size(); i++) {

                                        Produto aux = lista.get(i);

                                        %>
                                        <tr>
                                            <th scope="row"><%= i + 1 %></th>
                                            <td><%= aux.getId() %></td>
                                            <td><%= aux.getNomeProduto() %></td>
                                            <td><%= aux.getDescricao() %></td>
                                            <td><%= aux.getPrecoVenda() %></td>
                                        </tr>
                                        <%

                                    }

                                }
                                
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <jsp:include page="include/scripts.html"/>
        <script type="text/javascript">

            <%
                Mensagem msg = (Mensagem) request.getAttribute("resMensagem");
                if (msg != null) {

                    %>exibirAviso("aviso", "<%=msg.getTextoTipo()%>", "<%=msg.getMensagem()%>");<%

                }
            %>

            $(document).ready(function () {
                
                $("#pesquisar").on("input", function() {
                    
                    let texto = $(this).val().toLowerCase();
                    
                    $("#tabela tbody tr").filter(function() {

                        $(this).toggle($(this).text().toLowerCase().indexOf(texto) > -1);

                    });

                });

            });

        </script>
    </body>
</html>
