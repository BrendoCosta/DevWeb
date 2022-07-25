<%@ page import="java.util.ArrayList, model.Venda, model.Produto" contentType="text/html;charset=UTF-8" %>
<div class="pt-4 pb-4">
    <h2>Cadastro de Venda</h2>
    <br><p>Insira os dados da venda a ser cadastrada</p>
    <form method="post" action="ControllerVendas" id="formulario">
        <div class="row p-1">
            <div class="col-md-1"><label>Produto</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmIdProduto" name="prmIdProduto"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>Valor da Venda</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmValorVenda" name="prmValorVenda"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>Quantidade</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmQuantidadeVenda" name="prmQuantidadeVenda"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>Data</label></div>
            <div class="col-md-auto"><input type="datetime-local" class="form-control" id="prmDataVenda" name="prmDataVenda"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>ID do Cliente</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmIdCliente" name="prmIdCliente"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"></div>
            <div class="col-md-auto"><input type="submit" class="btn btn-outline-primary" id="btnEnviar" value="Enviar"></div>
        </div>
    </form>
    <script type="text/javascript" src="include/FormVendas.js"></script>
</div>