<%@ page import="java.util.ArrayList, model.Cliente, model.Funcionario" contentType="text/html;charset=UTF-8" %>
<%
    boolean isVendedor = (request.getSession().getAttribute("usuarioPapel") == Funcionario.Papel.VENDEDOR);
%>
<div class="pt-4 pb-4"><h2>Controle de Clientes</h2></div>
<div class="row g-0 d-flex justify-content-md-start justify-content-center">
    <% if ( isVendedor ) { %>
        <div class="row pb-4">
            <div class="col-md-auto text-md-start text-center g-0">
                <a href="?acao=incluir">
                    <button class="btn btn-outline-primary"><i class="bi bi-plus-lg"></i> Cadastrar Cliente</button>
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
                <th scope="col">Nome</th>
                <th scope="col">CPF</th>
                <th scope="col">Endereço</th>
                <th scope="col">Bairro</th>
                <th scope="col">Cidade</th>
                <th scope="col">UF</th>
                <th scope="col">CEP</th>
                <th scope="col">Telefone</th>
                <th scope="col">E-mail</th>
                <% if ( isVendedor ) { %><th colspan="2">Ação</th><% } %>
            </tr>
        </thead>
        <tbody>
            <%
                ArrayList<Cliente> lista = (ArrayList<Cliente>) request.getAttribute("lista");
                
                if (lista != null) {

                    for (int i = 0; i < lista.size(); i++) {

                        Cliente aux = lista.get(i);

                        %>
                        <tr>
                            <th scope="row"><%= i + 1 %></th>
                            <td><%= aux.getNome() %></td>
                            <td class="cpf"><%= aux.getCpf() %></td>
                            <td><%= aux.getEndereco() %></td>
                            <td><%= aux.getBairro() %></td>
                            <td><%= aux.getCidade() %></td>
                            <td><%= aux.getUf() %></td>
                            <td class="cep"><%= aux.getCep() %></td>
                            <td class="telefone"><%= aux.getTelefone() %></td>
                            <td><%= aux.getEmail() %></td>
                            <% if ( isVendedor ) { %>
                                <td>
                                    <a href="?acao=alterar&id=<%= aux.getId() %>">
                                        <button class="btn btn-outline-primary" title="Alterar"><i class="bi bi-pencil-fill"></i></button>
                                    </a>
                                </td>
                            <% } %>
                            <% if ( isVendedor ) { %>
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

            $(".cpf").mask("000.000.000-00");
            $(".cep").mask("00000-000");
            $(".telefone").mask("(00) 0000-0000");
            
            $("#pesquisar").on("input", function() {
                
                let texto = $(this).val().toLowerCase();
                
                $("#tabela tbody tr").filter(function() {

                    $(this).toggle($(this).text().toLowerCase().indexOf(texto) > -1);

                });

            });

        });

    </script>
</div>