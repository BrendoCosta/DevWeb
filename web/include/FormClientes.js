$(document).ready(function () {

    $("#prmCpf").mask("000.000.000-00", { placeholder: "___.___.___-__" });
    $("#prmCep").mask("00000-000", { placeholder: "_____-___" });
    $("#prmTelefone").mask("(00) 0 0000-0000", { placeholder: "(__) 9 ____-____" });

    $("#formulario").submit(function (e) {

        $("#prmCep").unmask();

        if (!validaFormCadastro()) {

            e.preventDefault();

        }

    });

    function validaFormCadastro() {

        removerAviso("aviso");

        // Valida CPF

        if ( $("#prmCpf").val().length != 14 ) {

            exibirAviso("aviso", "ERRO", "CPF inválido!");
            return false;

        }

        // Valida nome

        if ( !($("#prmNome").val().length <= 50) ) {

            exibirAviso("aviso", "ERRO", "Nome excede o tamanho permitido!");
            return false;

        }

        if ( !($("#prmNome").val().trim().length)
            || ( $("#prmNome").val().length == 0) ) {

            exibirAviso("aviso", "ERRO", "Nome não informado!");
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

        if ( $("#prmCep").val().length != 8 ) {

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