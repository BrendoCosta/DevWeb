<%@ page import="java.util.ArrayList, model.Funcionario" contentType="text/html;charset=UTF-8" %>
<%
    Funcionario func = (Funcionario) request.getAttribute("funcionario");

%>
<div class="pt-4 pb-4">
    <h2>Alterar Dados de Funcionário</h2>
    <br><p>Atualize os dados do funcionário abaixo</p>
    <form method="post" action="ControllerFuncionarios" id="formulario">
        <input type="hidden" id="prmAlteracao" name="prmAlteracao" value="1">
        <div class="row p-1">
            <div class="col-md-1"><label>ID</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmId" name="prmId" readonly value="<%=func.getId()%>"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>Nome</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmNome" name="prmNome" value="<%=func.getNome()%>"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>CPF</label></div>
            <div class="col-md-auto"><input type="text" class="form-control cpf" id="prmCpf" name="prmCpf" value="<%=func.getCpf()%>"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>Senha</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmSenha" name="prmSenha" value="<%=func.getSenha()%>"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>Papel</label></div>
            <div class="col-md-auto">
                <select class="form-control" id="prmPapel" name="prmPapel">
                    <option value="0">Administrador</option>
                    <option value="1">Vendedor</option>
                    <option value="2">Comprador</option>
                </select>
                <script type="text/javascript">
                    $(document).ready(function() {

                        $("#prmPapel option[value='<%=func.getPapel().ordinal()%>']").attr("selected", true);

                    });
                </script>
            </div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"></div>
            <div class="col-md-auto"><input type="submit" class="btn btn-outline-primary" id="btnEnviar" value="Enviar"></div>
        </div>
    </form>
    <script type="text/javascript" src="include/FormFuncionarios.js"></script>
</div>