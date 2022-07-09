<%@ page import="java.util.ArrayList, model.Categoria, model.Funcionario" contentType="text/html;charset=UTF-8" %>
<%
    boolean isComprador = (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.COMPRADOR);
%>
<div class="pt-4 pb-4"><h2>Controle de Categorias</h2></div>
<div class="row g-0 d-flex justify-content-md-start justify-content-center">
    <% if ( isComprador ) { %>
        <div class="row pb-4">
            <div class="col-md-auto text-md-start text-center g-0">
                <a href="?acao=incluir">
                    <button class="btn btn-outline-primary"><i class="bi bi-plus-lg"></i> Cadastrar Categoria</button>
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
                <th scope="col">ID da Categoria</th>
                <th scope="col">Nome da Categoria</th>
                <% if ( isComprador ) { %><th colspan="2">Ação</th><% } %>
            </tr>
        </thead>
        <tbody>
            <%
                ArrayList<Categoria> lista = (ArrayList<Categoria>) request.getAttribute("lista");
                
                if (lista != null) {

                    for (int i = 0; i < lista.size(); i++) {

                        Categoria aux = lista.get(i);

                        %>
                        <tr>
                            <th scope="row"><%= i + 1 %></th>
                            <td><%= aux.getId() %></td>
                            <td><%= aux.getNomeCategoria() %></td>
                            <% if ( isComprador ) { %>
                                <td>
                                    <a href="?acao=alterar&id=<%= aux.getId() %>">
                                        <button class="btn btn-outline-primary" title="Alterar"><i class="bi bi-pencil-fill"></i></button>
                                    </a>
                                </td>
                            <% } %>
                            <% if ( isComprador ) { %>
                                <td>
                                    <a class="link-deletar" href="?acao=excluir&id=<%= aux.getId() %>">
                                        <button class="btn btn-outline-danger" title="Deletar"><i class="bi bi-x-lg"></i></button>
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

            $(".link-deletar").click(function(e) {

                if (!confirm("Confirmar exclusão?")) {

                    e.preventDefault();

                }

            });

        });

    </script>
</div>