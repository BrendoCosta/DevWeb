$(document).ready(function () {

    $("#prmQuantidadeVenda").on("input", function () {
        if ( parseInt( $(this).val() ) < 1 ) { 
            $(this).val(1); 
        }
    });

    $("#formulario").submit(function (e) {

        if (!validaFormCadastro()) {

            e.preventDefault();

        }

    });

    function validaFormCadastro() {

        removerAviso("aviso");

        // Valida ID do cliente

        if ( !(parseInt($("#prmIdCliente").val()) >= 1) ) {

            exibirAviso("aviso", "ERRO", "ID do cliente não informado!");
            return false;

        }

        // Valida Valor da venda

        if ( !(parseInt($("#prmValorVenda").val()) >= 1) ) {

            exibirAviso("aviso", "ERRO", "Valor da venda informado inválido!");
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