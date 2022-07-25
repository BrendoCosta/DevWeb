<%@ page import="java.util.ArrayList, app.Mensagem, model.Produto" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
    <jsp:include page="include/head.jsp">
        <jsp:param name="titulo" value="Relatório de Estoque de Produtos"/>
    </jsp:include>
    <body>
        <div class="container g-0 m-0 mw-100">
            <jsp:include page="include/Cabecalho.jsp"/>
            <div class="row g-0 p-4">
                <div id="aviso"></div>
                <div class="pt-4 pb-4"><h2>Relatório de Estoque de Produtos</h2></div>
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
                                <th scope="col">ID</th>
                                <th scope="col">Nome</th>
                                <th scope="col">Descrição</th>
                                <th scope="col">Preço de Compra</th>
                                <th scope="col">Preço de Venda</th>
                                <th scope="col">Quantidade Disponível</th>
                                <th scope="col">Liberado para Venda?</th>
                                <th scope="col">ID da Categoria</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                ArrayList<Produto> lista = (ArrayList<Produto>) request.getAttribute("lista");
                                
                                if (lista != null) {

                                    for (int i = 0; i < lista.size(); i++) {

                                        Produto aux = lista.get(i);
                                        String estado = aux.getLiberadoVenda().name().equals("S") ? "Sim" : "Não" ;

                                        %>
                                        <tr>
                                            <th scope="row"><%= i + 1 %></th>
                                            <td><%= aux.getId() %></td>
                                            <td><%= aux.getNomeProduto() %></td>
                                            <td><%= aux.getDescricao() %></td>
                                            <td><%= aux.getPrecoCompra() %></td>
                                            <td><%= aux.getPrecoVenda() %></td>
                                            <td><%= aux.getQuantidadeDisponivel() %> unidade(s)</td>
                                            <td><%= estado %></td>
                                            <td><%= aux.getIdCategoria() %></td>
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
