$(document).ready(function () {

    $("#prmCnpj").mask("00.000.000/0000-00", { placeholder: "__.___.___/____-__" });
    $("#prmCep").mask("00000-000", { placeholder: "_____-___" });
    $("#prmTelefone").mask("(00) 0 0000-0000", { placeholder: "(__) 9 ____-____" });

    $("#formulario").submit(function (e) {

        if (!validaFormCadastro()) {

            e.preventDefault();

        }

    });

    function validaFormCadastro() {

        removerAviso("aviso");

        // Valida CNPJ

        if ( $("#prmCnpj").val().length > 18 ) {

            exibirAviso("aviso", "ERRO", "CNPJ inválido!");
            return false;

        }

        // Valida Razão Social

        if ( !($("#prmRazaoSocial").val().length <= 50) ) {

            exibirAviso("aviso", "ERRO", "Razão Social excede o tamanho permitido!");
            return false;

        }

        if ( !($("#prmRazaoSocial").val().trim().length)
            || ( $("#prmRazaoSocial").val().length == 0) ) {

            exibirAviso("aviso", "ERRO", "Razão Social não informado!");
            return false;

        }

        // Valida Endereço

        if ( !($("#prmEndereco").val().length <= 50) ) {

            exibirAviso("aviso", "ERRO", "Endereço excede o tamanho permitido!");
            return false;

        }

        if ( !($("#prmEndereco").val().trim().length)
            || ( $("#prmEndereco").val().length == 0) ) {

            exibirAviso("aviso", "ERRO", "Endereço não informado!");
            return false;

        }

        // Valida Bairro

        if ( !($("#prmBairro").val().length <= 50) ) {

            exibirAviso("aviso", "ERRO", "Bairro excede o tamanho permitido!");
            return false;

        }

        if ( !($("#prmBairro").val().trim().length)
            || ( $("#prmBairro").val().length == 0) ) {

            exibirAviso("aviso", "ERRO", "Bairro não informado!");
            return false;

        }

        // Valida Cidade

        if ( !($("#prmCidade").val().length <= 50) ) {

            exibirAviso("aviso", "ERRO", "Cidade excede o tamanho permitido!");
            return false;

        }

        if ( !($("#prmCidade").val().trim().length)
            || ( $("#prmCidade").val().length == 0) ) {

            exibirAviso("aviso", "ERRO", "Cidade não informado!");
            return false;

        }

        // Valida CEP

        if ( $("#prmCep").val().length != 9 ) {

            exibirAviso("aviso", "ERRO", "CEP inválido!");
            return false;

        }

        // Valida Telefone

        if ( !($("#prmTelefone").val().length <= 20) ) {

            exibirAviso("aviso", "ERRO", "Telefone excede o tamanho permitido!");
            return false;

        }

        if ( !($("#prmTelefone").val().trim().length)
            || ( $("#prmTelefone").val().length == 0) ) {

            exibirAviso("aviso", "ERRO", "Telefone não informado!");
            return false;

        }

        // Valida E-mail

        if ( !($("#prmEmail").val().length <= 50) ) {

            exibirAviso("aviso", "ERRO", "E-mail excede o tamanho permitido!");
            return false;

        }

        if ( !($("#prmEmail").val().trim().length)
            || ( $("#prmEmail").val().length == 0) ) {

            exibirAviso("aviso", "ERRO", "E-mail não informado!");
            return false;

        }

        return true;

    }

});