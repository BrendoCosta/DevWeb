<%@ page import="java.util.ArrayList, model.Fornecedor" contentType="text/html;charset=UTF-8" %>
<%
    Fornecedor forn = (Fornecedor) request.getAttribute("fornecedor");

%>
<div class="pt-4 pb-4">
    <h2>Alterar Dados de Fornecedor</h2>
    <br><p>Atualize os dados do fornecedor abaixo</p>
    <form method="post" action="ControllerFornecedores" id="formulario">
        <input type="hidden" id="prmId" name="prmId" value="<%=forn.getId()%>">
        <div class="row p-1">
            <div class="col-md-1"><label>Razão Social</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmRazaoSocial" name="prmRazaoSocial" value="<%=forn.getRazaoSocial()%>"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>CNPJ</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmCnpj" name="prmCnpj" value="<%=forn.getCnpj()%>"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>Endereço</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmEndereco" name="prmEndereco" value="<%=forn.getEndereco()%>"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>Bairro</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmBairro" name="prmBairro" value="<%=forn.getBairro()%>"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>Cidade</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmCidade" name="prmCidade" value="<%=forn.getCidade()%>"></div>
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
                <script type="text/javascript">
                    $(document).ready(function() {

                        $("#prmUf option[value='<%=forn.getUf()%>']").attr("selected", true);

                    });
                </script>
            </div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>CEP</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmCep" name="prmCep" value="<%=forn.getCep()%>"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>Telefone</label></div>
            <div class="col-md-auto"><input type="text" class="form-control telefone" id="prmTelefone" name="prmTelefone" value="<%=forn.getTelefone()%>"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>E-mail</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmEmail" name="prmEmail" value="<%=forn.getEmail()%>"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"></div>
            <div class="col-md-auto"><input type="submit" class="btn btn-outline-primary" id="btnEnviar" value="Enviar"></div>
        </div>
    </form>
    <script type="text/javascript" src="include/FormFornecedores.js"></script>
</div>