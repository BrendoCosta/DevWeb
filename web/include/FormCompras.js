$(document).ready(function () {

    if ($("#prmQuantidadeCompra").val().length == 0)  {
                
        $("#prmQuantidadeCompra").val(1);

    }
    
    $("#prmQuantidadeCompra").on("input", function () {
        
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

        // Valida Valor da Compra

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

});