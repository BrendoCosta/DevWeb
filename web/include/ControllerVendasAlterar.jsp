<%@ page import="java.util.ArrayList, model.Venda, model.Produto, model.Cliente" contentType="text/html;charset=UTF-8" %>
<%
    Venda ven    = (Venda) request.getAttribute("venda");
    Produto prod = (Produto) request.getAttribute("produto");
    Cliente clnt = (Cliente) request.getAttribute("cliente");

%>
<div class="pt-4 pb-4">
    <h2>Cadastro de Venda</h2>
    <br><p>Insira os dados da venda a ser cadastrada</p>
    <form method="post" action="ControllerVendas" id="formulario">
        <input type="hidden" id="prmId" name="prmId" value="<%= ven.getId() %>">
        <div class="row p-1">
            <div class="col-md-1"><label>Produto</label></div>
            <div class="col-md-auto">
                <select class="form-control" id="prmIdProduto" name="prmIdProduto">
                <%
                    ArrayList<Produto> lista = (ArrayList<Produto>) request.getAttribute("produtosDisponiveis");

                    if (lista != null) {

                        for (int i = 0; i < lista.size(); i++) {

                            Produto aux = lista.get(i);
                            String liberado = aux.getLiberadoVenda() == Produto.Liberado.S ? "" : "disabled";

                            %>
                                <option value="<%= aux.getId() %>" <%= liberado %> ><%= aux.getNomeProduto() %></option>
                            <%
                        }

                    }
                    
                %>
                </select>
                <script type="text/javascript">
                    $(document).ready(function() {

                        $("#prmIdProduto option[value='<%=ven.getIdProduto()%>']").attr("selected", true);

                    });
                </script>
            </div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>Quantidade</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmQuantidadeVenda" name="prmQuantidadeVenda" value="<%=ven.getQuantidadeVenda()%>"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>Data</label></div>
            <div class="col-md-auto"><input type="datetime-local" class="form-control" id="prmDataVenda" name="prmDataVenda" value="<%=ven.getDataVenda()%>T00:00"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>CPF do Cliente</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmCpfCliente" name="prmCpfCliente" value="<%=clnt.getCpf()%>"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"></div>
            <div class="col-md-auto"><input type="submit" class="btn btn-outline-primary" id="btnEnviar" value="Enviar"></div>
        </div>
    </form>
    <script type="text/javascript">

        $(document).ready(function () {

            $("#prmCpfCliente").mask("000.000.000-00");

            $("#prmQuantidadeVenda").on("input", function () {
                if ( parseInt( $(this).val() ) < 1 ) { 
                    $(this).val(1); 
                }
            });

            $("#formulario").submit(function (e) {

                if (!validaFormCadastro()) {

                    event.preventDefault();

                }

            });

            function validaFormCadastro() {

                removerAviso("aviso");

                // Valida CPF

                if ( $("#prmCpfCliente").val().length != 14 ) {

                    exibirAviso("aviso", "ERRO", "CPF não informado ou com formato inválido!");
                    return false;

                }

                // Valida quantidade

                if ( !(parseInt($("#prmQuantidadeVenda").val()) >= 1) ) {

                    exibirAviso("aviso", "ERRO", "Quantidade informada inválida!");
                    return false;

                }

                // Valida data

                if ( $("#prmDataVenda").val() == "" ) {

                    exibirAviso("aviso", "ERRO", "Data da venda não informada!");
                    return false;

                }

                return true;

            }

        });

    </script>
</div>