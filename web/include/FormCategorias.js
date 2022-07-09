$(document).ready(function () {

    $("#formulario").submit(function (e) {

        if (!validaFormCadastro()) {

            e.preventDefault();

        }

    });

    function validaFormCadastro() {

        removerAviso("aviso");

        // Valida Nome da Categoria

        if ( !($("#prmNomeCategoria").val().length <= 50) ) {

            exibirAviso("aviso", "ERRO", "Nome da categoria excede o tamanho permitido!");
            return false;

        }

        if ( !($("#prmNomeCategoria").val().trim().length)
            || ( $("#prmNomeCategoria").val().length == 0) ) {

            exibirAviso("aviso", "ERRO", "Nome da categoria não informado!");
            return false;

        }

        return true;

    }

});