<%@ page import="java.util.ArrayList, model.Venda, model.Funcionario" contentType="text/html;charset=UTF-8" %>
<%
    boolean isVendedor = (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.VENDEDOR);
%>
<div class="pt-4 pb-4"><h2>Controle de Vendas</h2></div>
<div class="row g-0 d-flex justify-content-md-start justify-content-center">
    <% if ( isVendedor ) { %>
        <div class="row pb-4">
            <div class="col-md-auto text-md-start text-center g-0">
                <a href="?acao=incluir">
                    <button class="btn btn-outline-primary"><i class="bi bi-plus-lg"></i> Cadastrar Venda</button>
                </a>
            </div>
        </div>
    <% } %>
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
                <th scope="col">ID da Venda</th>
                <th scope="col">ID do Produto</th>
                <th scope="col">ID do Cliente</th>
                <th scope="col">ID do Vendedor</th>
                <th scope="col">Quantidade Vendida</th>
                <th scope="col">Valor Total</th>
                <th scope="col">Data</th>
                <% if ( isVendedor ) { %> <th colspan="2">Ação</th> <% } %>
            </tr>
        </thead>
        <tbody>
            <%
                ArrayList<Venda> lista = (ArrayList<Venda>) request.getAttribute("lista");
                
                if (lista != null) {

                    for (int i = 0; i < lista.size(); i++) {

                        Venda aux = lista.get(i);

                        %>
                        <tr>
                            <th scope="row"><%= i + 1 %></th>
                            <td><%= aux.getId() %></td>
                            <td><%= aux.getIdProduto() %></td>
                            <td><%= aux.getIdCliente() %></td>
                            <td><%= aux.getIdFuncionario() %></td>
                            <td><%= aux.getQuantidadeVenda() %> unidade(s)</td>
                            <td><%= aux.getValorVenda() %> R$</td>
                            <td class="dataVenda"><%= aux.getDataVenda() %></td>
                            <% if ( isVendedor && ( aux.getIdFuncionario() == (int) request.getSession().getAttribute("usuarioID") ) ) { %>
                                <td>
                                    <a href="?acao=alterar&id=<%= aux.getId() %>">
                                        <button class="btn btn-outline-primary" title="Alterar"><i class="bi bi-pencil-fill"></i></button>
                                    </a>
                                </td>
                                <td>
                                    <a class="link-deletar" href="?acao=excluir&id=<%= aux.getId() %>">
                                        <button class="btn btn-outline-danger" title="Deletar"><i class="bi bi-x-lg"></i></button>
                                    </a>
                                </td>
                            <% } else if ( isVendedor && !( aux.getIdFuncionario() == (int) request.getSession().getAttribute("usuarioID") ) ) { %>
                                <td></td>
                                <td></td>
                            <% } %>
                        </tr>
                        <%

                    }

                }
                
            %>
        </tbody>
    </table>
    <script type="text/javascript">

        $(document).ready(function () {
            
            $("#pesquisar").on("input", function() {
                
                let texto = $(this).val().toLowerCase();
                
                $("#tabela tbody tr").filter(function() {

                    $(this).toggle($(this).text().toLowerCase().indexOf(texto) > -1);

                });

            });

            $(".link-deletar").click(function(e) {

                if (!confirm("Confirmar exclusão?")) {

                    e.preventDefault();

                }

            });

        });

    </script>
</div>