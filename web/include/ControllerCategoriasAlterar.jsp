<%@ page import="java.util.ArrayList, model.Categoria" contentType="text/html;charset=UTF-8" %>
<%
    Categoria cat = (Categoria) request.getAttribute("categoria");

%>
<div class="pt-4 pb-4">
    <h2>Alterar Dados de Categoria</h2>
    <br><p>Atualize os dados da categoria abaixo</p>
    <form method="post" action="ControllerCategorias" id="formulario">
        <input type="hidden" id="prmId" name="prmId" value="<%=cat.getId()%>">
        <div class="row p-1">
            <div class="col-md-1"><label>Nome da Categoria</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmNomeCategoria" name="prmNomeCategoria" value="<%=cat.getNomeCategoria()%>"></div>
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

            // Valida Nome da Categoria

            if ( $("#prmNomeCategoria").val().length == 0 ) {

                exibirAviso("aviso", "ERRO", "Nome da categoria não informado!");
                return false;

            }

            if ( $("#prmNomeCategoria").val().length > 50 ) {

                exibirAviso("aviso", "ERRO", "Nome da categoria excede o tamanho permitido!");
                return false;

            }

            return true;

        }

    </script>
</div>