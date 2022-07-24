$(document).ready(function () {

    $("#prmCpf").mask("000.000.000-00", { placeholder: "___.___.___-__" });

    $("#formulario").submit(function (e) {

        if (!validaFormCadastro()) {

            e.preventDefault();

        }

    });

    function validaFormCadastro() {

        removerAviso("aviso");

        // Valida ID

        if ( !($("#prmId").val().length <= 11) ) {

            exibirAviso("aviso", "ERRO", "ID inválido!");
            return false;

        }

        // Valida Nome

        if ( !($("#prmNome").val().length <= 50) ) {

            exibirAviso("aviso", "ERRO", "Nome excede o tamanho permitido!");
            return false;

        }

        if ( !($("#prmNome").val().trim().length)
            || ( $("#prmNome").val().length == 0) ) {

            exibirAviso("aviso", "ERRO", "Nome não informado!");
            return false;

        }

        // Valida CPF

        if ( $("#prmCpf").val().length != 14 ) {

            exibirAviso("aviso", "ERRO", "CPF inválido!");
            return false;

        }

        // Valida Senha

        if ( !($("#prmSenha").val().length <= 10) ) {

            exibirAviso("aviso", "ERRO", "Senha excede o tamanho permitido!");
            return false;

        }

        if ( !($("#prmSenha").val().trim().length)
            || ( $("#prmSenha").val().length == 0) ) {

            exibirAviso("aviso", "ERRO", "Senha não informada!");
            return false;

        }

        // Valida Papel

        if ( !([0, 1, 2].includes(parseInt($("#prmPapel").val()))) ) {

            exibirAviso("aviso", "ERRO", "Papel desconhecido!");
            return false;

        }

        return true;

    }

});