<%@ page import="java.util.ArrayList, model.Funcionario" contentType="text/html;charset=UTF-8" %>
<%
    boolean isAdmin = (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.ADMIN);
%>
<div class="pt-4 pb-4"><h2>Controle de Funcionários</h2></div>
<div class="row g-0 d-flex justify-content-md-start justify-content-center">
    <% if ( isAdmin ) { %>
        <div class="row pb-4">
            <div class="col-md-auto text-md-start text-center g-0">
                <a href="?acao=incluir">
                    <button class="btn btn-outline-primary"><i class="bi bi-plus-lg"></i> Cadastrar Funcionário</button>
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
                <th scope="col">ID</th>
                <th scope="col">Nome</th>
                <th scope="col">CPF</th>
                <th scope="col">Senha</th>
                <th scope="col">Papel</th>
                <% if ( isAdmin ) { %><th colspan="2">Ação</th><% } %>
            </tr>
        </thead>
        <tbody>
            <%
                ArrayList<Funcionario> lista = (ArrayList<Funcionario>) request.getAttribute("lista");
                
                if (lista != null) {

                    for (int i = 0; i < lista.size(); i++) {

                        Funcionario aux = lista.get(i);

                        %>
                        <tr>
                            <th scope="row"><%= i + 1 %></th>
                            <td><%= aux.getId() %></td>
                            <td><%= aux.getNome() %></td>
                            <td class="cpf"><%= aux.getCpf() %></td>
                            <td><%= aux.getSenha() %></td>
                            <td>
                                <%
                                    switch (aux.getPapel()) {

                                        case ADMIN:
                                            %>Administrador<%
                                            break;
                                        case VENDEDOR:
                                            %>Vendedor<%
                                            break;
                                        case COMPRADOR:
                                            %>Comprador<%
                                            break;

                                    }
                                %>
                            </td>
                            <% if ( isAdmin && aux.getId() != (int) request.getSession().getAttribute("usuarioID") ) { %>
                                <td>
                                    <a href="?acao=alterar&id=<%= aux.getId() %>">
                                        <button class="btn btn-outline-primary" title="Alterar"><i class="bi bi-pencil-fill"></i></button>
                                    </a>
                                </td>
                            <% } else { %><td></td><% } %>
                            <% if ( isAdmin && aux.getId() != (int) request.getSession().getAttribute("usuarioID") ) { %>
                                <td>
                                    <a class="link-deletar" href="?acao=excluir&id=<%= aux.getId() %>">
                                        <button class="btn btn-outline-danger" title="Deletar"><i class="bi bi-x-lg"></i></button>
                                    </a>
                                </td>
                            <% } else { %><td></td><% } %>
                        </tr>
                        <%

                    }

                }
                
            %>
        </tbody>
    </table>
    <script type="text/javascript">

        $(document).ready(function () {

            $(".cpf").mask("000.000.000-00");
            
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