<%@ page import="java.util.ArrayList, model.Categoria" contentType="text/html;charset=UTF-8" %>
<div class="pt-4 pb-4">
    <h2>Cadastro de Categoria</h2>
    <br><p>Insira os dados da categoria a ser cadastrada</p>
    <form method="post" action="ControllerCategorias" id="formulario">
        <input type="hidden" id="prmId" name="prmId" value="">
        <div class="row p-1">
            <div class="col-md-1"><label>Nome da Categoria</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmNomeCategoria" name="prmNomeCategoria"></div>
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