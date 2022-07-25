<%@ page import="java.util.ArrayList, app.Mensagem, model.Venda, model.TotalVendas" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
    <jsp:include page="include/head.jsp">
        <jsp:param name="titulo" value="Relatório de Vendas de Produtos"/>
    </jsp:include>
    <body>
        <div class="container g-0 m-0 mw-100">
            <jsp:include page="include/Cabecalho.jsp"/>
            <div class="row g-0 p-4">
                <div id="aviso"></div>
                <div class="pt-4 pb-4"><h2>Relatório de Vendas de Produtos</h2></div>
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
                                <th scope="col">Data da Venda</th>
                                <th scope="col">Valor da Venda</th>
                                <th scope="col">ID do Produto</th>
                                <th scope="col">Quantidade Vendida</th>
                                <th scope="col">ID do Cliente</th>
                                <th scope="col">ID do Funcionário</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                ArrayList<Venda> listaVendas = (ArrayList<Venda>) request.getAttribute("listaVendas");
                                
                                if (listaVendas != null) {

                                    for (int i = 0; i < listaVendas.size(); i++) {

                                        Venda aux = listaVendas.get(i);

                                        %>
                                        <tr>
                                            <th scope="row"><%= i + 1 %></th>
                                            <td><%= aux.getId() %></td>
                                            <td><%= aux.getDataVenda() %></td>
                                            <td><%= aux.getValorVenda() %></td>
                                            <td><%= aux.getIdProduto() %></td>
                                            <td><%= aux.getQuantidadeVenda() %> unidade(s)</td>
                                            <td><%= aux.getIdCliente() %></td>
                                            <td><%= aux.getIdFuncionario() %></td>
                                        </tr>
                                        <%

                                    }

                                }
                                
                            %>
                        </tbody>
                    </table>
                </div>
                <div class="pt-4 pb-4"><h4>Vendas por Data</h4></div>
                <div class="table-responsive">
                    <table class="table table-striped table-bordered text-center small" id="tabela">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Data</th>
                                <th scope="col">Total de Vendas</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                ArrayList<TotalVendas> listaTotalVendas = (ArrayList<TotalVendas>) request.getAttribute("listaTotalVendas");
                                
                                if (listaTotalVendas != null) {

                                    for (int i = 0; i < listaTotalVendas.size(); i++) {

                                        TotalVendas aux = listaTotalVendas.get(i);

                                        %>
                                        <tr>
                                            <th scope="row"><%= i + 1 %></th>
                                            <td><%= aux.getDataVenda() %></td>
                                            <td><%= aux.getQuantidadeDataVenda() %></td>
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
