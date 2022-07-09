<%@ page import="java.util.ArrayList, model.Produto" contentType="text/html;charset=UTF-8" %>
<div class="pt-4 pb-4">
    <h2>Cadastro de Compra</h2>
    <br><p>Insira os dados da compra a ser cadastrada</p>
    <form method="post" action="ControllerCompras" id="formulario">
        <input type="hidden" id="prmId" name="prmId" value="">
        <div class="row p-1">
            <div class="col-md-1"><label>Produto</label></div>
            <div class="col-md-auto">
                <select class="form-control" id="prmIdProduto" name="prmIdProduto">
                <%
                    ArrayList<Produto> lista = (ArrayList<Produto>) request.getAttribute("produtos");

                    if (lista != null) {

                        for (int i = 0; i < lista.size(); i++) {

                            Produto aux = lista.get(i);
                            %>
                                <option value="<%= aux.getId() %>"><%= aux.getNomeProduto() %></option>
                            <%
                        }

                    }
                    
                %>
                </select>
            </div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>Preço</label></div>
            <div class="col-md-auto"><input type="text" class="form-control telefone" id="prmValorCompra" name="prmValorCompra"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>Quantidade</label></div>
            <div class="col-md-auto"><input type="text" class="form-control telefone" id="prmQuantidadeCompra" name="prmQuantidadeCompra"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>Data</label></div>
            <div class="col-md-auto"><input type="datetime-local" class="form-control" id="prmDataCompra" name="prmDataCompra"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>ID do Fornecedor</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmIdFornecedor" name="prmIdFornecedor"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"></div>
            <div class="col-md-auto"><input type="submit" class="btn btn-outline-primary" id="btnEnviar" value="Enviar"></div>
        </div>
    </form>
    <script type="text/javascript">

        $(document).ready(function () {

            $("#prmQuantidadeCompra").val(1);
            $("#prmQuantidadeCompra").on("input", function () {
                if ( parseInt( $(this).val() ) < 1 ) { 
                    $(this).val(1); 
                }
            });

            $("#prmCnpjFornecedor").mask("00.000.000/0000-00", { placeholder: "__.___.___/____-__" });

        });

        $("#formulario").submit(function (e) {

            if (!validaFormCadastro()) {

                event.preventDefault();

            }

        });

        function validaFormCadastro() {

            removerAviso("aviso");

            // Valida Valor

            if ( !(parseInt($("#prmValorCompra").val()) >= 1) ) {

                exibirAviso("aviso", "ERRO", "Valor informado inválido!");
                return false;

            }

            // Valida Quantidade

            if ( !(parseInt($("#prmQuantidadeCompra").val()) >= 1) ) {

                exibirAviso("aviso", "ERRO", "Quantidade informada inválida!");
                return false;

            }

            // Valida Data

            if ( $("#prmDataCompra").val() == "" ) {

                exibirAviso("aviso", "ERRO", "Data da compra não informada!");
                return false;

            }

            // Valida ID do Fornecedor

            if ( ($("#prmIdFornecedor").val() < 0) || ($("#prmIdFornecedor").val().length == 0) ) {

                exibirAviso("aviso", "ERRO", "ID do fornecedor não informado ou com formato inválido!");
                return false;

            }

            return true;

        }

    </script>
</div>