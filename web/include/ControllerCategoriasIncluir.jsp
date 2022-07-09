<%@ page import="java.util.ArrayList, model.Categoria" contentType="text/html;charset=UTF-8" %>
<div class="pt-4 pb-4">
    <h2>Cadastro de Categoria</h2>
    <br><p>Insira os dados da categoria a ser cadastrada</p>
    <form method="post" action="ControllerCategorias" id="formulario">
        <input type="hidden" id="prmId" name="prmId" value="">
        <div class="row p-1">
            <div class="col-md-1"><label>Nome da Categoria</label></div>
            <div class="col-md-auto"><input type="text" class="form-control" id="prmNomeCategoria" name="prmNomeCategoria"></div>
        </div>
        <div class="row p-1">
            <div class="col-md-1"></div>
            <div class="col-md-auto"><input type="submit" class="btn btn-outline-primary" id="btnEnviar" value="Enviar"></div>
        </div>
    </form>
    <script type="text/javascript" src="include/FormCategorias.js"></script>
</div>