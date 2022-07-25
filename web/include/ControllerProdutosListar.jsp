<%@ page import="java.util.ArrayList, model.Produto, model.Funcionario" contentType="text/html;charset=UTF-8" %>
<%
    boolean isComprador = (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.COMPRADOR);
%>
<div class="pt-4 pb-4"><h2>Controle de Produtos</h2></div>
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
                <th scope="col">Liberado para Venda?</th>
                <% if ( isComprador ) { %><th colspan="1">Ação</th><% } %>
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
                            <td><%= estado %></td>
                            <% if ( isComprador ) { %>
                                <td>
                                    <a href="?acao=alterar&id=<%= aux.getId() %>">
                                        <button class="btn btn-outline-primary" title="Alterar"><i class="bi bi-pencil-fill"></i></button>
                                    </a>
                                </td>
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

        });

    </script>
</div>