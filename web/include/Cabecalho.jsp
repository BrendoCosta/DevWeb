<%@ page import="model.Funcionario" contentType="text/html;charset=UTF-8" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary p-4">
    <div class="container-fluid">
        <a class="navbar-brand" href="#"><strong>Área da Aplicação</strong></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="/<%=request.getContextPath().replace("/", "")%>/Area">Início</a>
                </li>
                <%
                    Funcionario.Papel papel = (Funcionario.Papel) request.getSession().getAttribute("usuarioPapel");
                    switch (papel) {

                        case VENDEDOR:
                            %>
                            <li class="nav-item">
                                <a class="nav-link" aria-current="page" href="ControllerClientes">Clientes</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" aria-current="page" href="ControllerVendas">Vendas</a>
                            </li>
                            <%
                            break;
                        
                        case COMPRADOR:
                            %>
                            <li class="nav-item">
                                <a class="nav-link" aria-current="page" href="ControllerFornecedores">Fornecedores</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" aria-current="page" href="ControllerCategorias">Categorias</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" aria-current="page" href="ControllerProdutos">Produtos</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" aria-current="page" href="ControllerCompras">Compras</a>
                            </li>
                            <%
                            break;

                        case ADMIN:
                            %>
                            <li class="nav-item">
                                <a class="nav-link" aria-current="page" href="ControllerFuncionarios">Funcionários</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" aria-current="page" href="ControllerRelatorioEstoque">Relatório de Estoque de Produtos</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" aria-current="page" href="ControllerRelatorioVendas">Relatório de Vendas de Produtos</a>
                            </li>
                            <%
                            break;

                    }

                %>
            </ul>
            <div class="row navbar-text d-flex flex-row align-items-center">
                <div class="col-md-auto">
                    <span>Logado como <strong>
                    <%=(String)request.getSession().getAttribute("usuarioNome")%> | 
                    <%
                        
                        switch (papel) {

                            case VENDEDOR:
                                %>Vendedor<%
                                break;
                            
                            case COMPRADOR:
                                %>Comprador<%
                                break;

                            case ADMIN:
                                %>Administrador<%
                                break;

                        }

                    %>
                    </strong></span>
                </div>
                <div class="col-md-auto">
                    <button id="btn-logout" class="btn btn-danger" title="Logout"><i class="bi bi-x-lg"></i></button>
                    <script type="text/javascript">
                        $("#btn-logout").on("click", function() {
                            if (confirm("Deseja sair?")) {
                                window.location.href = "/<%=request.getContextPath().replace("/", "")%>/";
                            }
                        });
                    </script>
                </div>
            </div>
        </div>
    </div>
</nav>