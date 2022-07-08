<%@ page import="java.util.ArrayList, model.Cliente" contentType="text/html;charset=UTF-8" %>
<div class="pt-4 pb-4">
    <h2>Cadastro de Fornecedor</h2>
    <br><p>Insira os dados do fornecedor a ser cadastrado</p>
    <form method="post" action="ControllerFornecedores" id="formulario">
        <input type="hidden" id="prmId" name="prmId" value="">
        <div class="row p-1">
            <div class="col-md-1"><label>Razão Social</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmRazaoSocial" name="prmRazaoSocial"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>CNPJ</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmCnpj" name="prmCnpj"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>Endereço</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmEndereco" name="prmEndereco"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>Bairro</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmBairro" name="prmBairro"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>Cidade</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmCidade" name="prmCidade"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>UF</label></div>
            <div class="col-md-auto">
                <select id="prmUf" class="form-control" name="prmUf">
                    <option value="AC">Acre</option>
                    <option value="AL">Alagoas</option>
                    <option value="AP">Amapá</option>
                    <option value="AM">Amazonas</option>
                    <option value="BA">Bahia</option>
                    <option value="CE">Ceará</option>
                    <option value="DF">Distrito Federal</option>
                    <option value="ES">Espírito Santo</option>
                    <option value="GO">Goiás</option>
                    <option value="MA">Maranhão</option>
                    <option value="MT">Mato Grosso</option>
                    <option value="MS">Mato Grosso do Sul</option>
                    <option value="MG">Minas Gerais</option>
                    <option value="PA">Pará</option>
                    <option value="PB">Paraíba</option>
                    <option value="PR">Paraná</option>
                    <option value="PE">Pernambuco</option>
                    <option value="PI">Piauí</option>
                    <option value="RJ">Rio de Janeiro</option>
                    <option value="RN">Rio Grande do Norte</option>
                    <option value="RS">Rio Grande do Sul</option>
                    <option value="RO">Rondônia</option>
                    <option value="RR">Roraima</option>
                    <option value="SC">Santa Catarina</option>
                    <option value="SP">São Paulo</option>
                    <option value="SE">Sergipe</option>
                    <option value="TO">Tocantins</option>
                </select>
            </div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>CEP</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmCep" name="prmCep"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>Telefone</label></div>
            <div class="col-md-auto"><input type="text" class="form-control telefone" id="prmTelefone" name="prmTelefone"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>E-mail</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmEmail" name="prmEmail"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"></div>
            <div class="col-md-auto"><input type="submit" class="btn btn-outline-primary" id="btnEnviar" value="Enviar"></div>
        </div>
    </form>
    <script type="text/javascript">

        $(document).ready(function () {

            $("#prmCnpj").mask("00.000.000/0000-00", { placeholder: "__.___.___/____-__" });
            $("#prmCep").mask("00000-000", { placeholder: "_____-___" });
            $("#prmTelefone").mask("(00) 0 0000-0000", { placeholder: "(__) 9 ____-____" });

        });

        $("#formulario").submit(function (e) {

            if (!validaFormCadastro()) {

                event.preventDefault();

            }

        });

        function validaFormCadastro() {

            removerAviso("aviso");

            // Valida Razão Social

            if ( $("#prmRazaoSocial").val().length == 0 ) {

                exibirAviso("aviso", "ERRO", "Razão social não informada!");
                return false;

            }

            // Valida CNPJ

            if ( ($("#prmCnpj").val().length > 18) || ($("#prmCnpj").val().length == 0) ) {

                exibirAviso("aviso", "ERRO", "CNPJ não informado ou com formato inválido!");
                return false;

            }

            // Valida Endereço

            if ( $("#prmEndereco").val().length == 0 ) {

                exibirAviso("aviso", "ERRO", "Endereço não informado!");
                return false;

            }

            // Valida Bairro

            if ( $("#prmBairro").val().length == 0 ) {

                exibirAviso("aviso", "ERRO", "Bairro não informado!");
                return false;

            }

            // Valida Cidade

            if ( $("#prmCidade").val().length == 0 ) {

                exibirAviso("aviso", "ERRO", "Cidade não informada!");
                return false;

            }

            // Valida CEP

            if ( $("#prmCep").val().length != 9 ) {

                exibirAviso("aviso", "ERRO", "CEP não informado ou com formato inválido!");
                return false;

            }

            // Valida Telefone

            if ( $("#prmTelefone").val().length != 16 ) {

                exibirAviso("aviso", "ERRO", "Telefone não informado ou com formato inválido!");
                return false;

            }

            // Valida E-mail

            if ( ($("#prmEmail").val().length > 50) || ($("#prmEmail").val().length == 0) ) {

                exibirAviso("aviso", "ERRO", "E-mail não informado ou com formato inválido!");
                return false;

            }

            return true;

        }

    </script>
</div>