<%@ page import="java.util.ArrayList, model.Produto" contentType="text/html;charset=UTF-8" %>
<%
    Produto prod = (Produto) request.getAttribute("produto");

%>
<div class="pt-4 pb-4">
    <h2>Alterar Dados de Produto</h2>
    <br><p>Atualize os dados do produto abaixo</p>
    <form method="post" action="ControllerProdutos" id="formulario">
        <input type="hidden" id="prmId" name="prmId" value="<%=prod.getId()%>">
        <div class="row p-1">
            <div class="col-md-1"><label>Liberado para Venda?</label></div>
            <div class="col-md-auto">
                <select class="form-control" id="prmLiberadoVenda" name="prmLiberadoVenda">
                    <option value="S">Sim</option>
                    <option value="N">Não</option>
                </select>
                <script type="text/javascript">
                    $(document).ready(function() {

                        $("#prmLiberadoVenda option[value='<%=prod.getLiberadoVenda().name()%>']").attr("selected", true);

                    });
                </script>
            </div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"></div>
            <div class="col-md-auto"><input type="submit" class="btn btn-outline-primary" id="btnEnviar" value="Enviar"></div>
        </div>
    </form>
    <script type="text/javascript">

        $("#formulario").submit(function (e) {

            if (!validaFormCadastro()) {

                event.preventDefault();

            }

        });

        function validaFormCadastro() {

            removerAviso("aviso");

            // Valida Liberado para Venda

            if ( ($("#prmLiberadoVenda").val() != "S") && ($("#prmLiberadoVenda").val() != "N") ) {

                exibirAviso("aviso", "ERRO", `Opção desconhecida para o campo "Liberado para Venda"!`);
                return false;

            }

            return true;

        }

    </script>
</div>