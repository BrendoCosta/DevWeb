<%@ page import="java.util.ArrayList, model.Produto" contentType="text/html;charset=UTF-8" %>
<div class="pt-4 pb-4">
    <h2>Cadastro de Compra</h2>
    <br><p>Insira os dados da compra a ser cadastrada</p>
    <form method="post" action="ControllerCompras" id="formulario">
        <input type="hidden" id="prmId" name="prmId">
        <div class="row p-1">
            <div class="col-md-1"><label>Produto</label></div>
            <div class="col-md-auto"><input type="text" class="form-control telefone" id="prmIdProduto" name="prmIdProduto"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>Valor da Compra</label></div>
            <div class="col-md-auto"><input type="text" class="form-control telefone" id="prmValorCompra" name="prmValorCompra"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>Quantidade</label></div>
            <div class="col-md-auto"><input type="text" class="form-control telefone" id="prmQuantidadeCompra" name="prmQuantidadeCompra"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>Data</label></div>
            <div class="col-md-auto"><input type="datetime-local" class="form-control" id="prmDataCompra" name="prmDataCompra"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"><label>ID do Fornecedor</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmIdFornecedor" name="prmIdFornecedor"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"></div>
            <div class="col-md-auto"><input type="submit" class="btn btn-outline-primary" id="btnEnviar" value="Enviar"></div>
        </div>
    </form>
    <script type="text/javascript" src="include/FormCompras.js"></script>
</div>