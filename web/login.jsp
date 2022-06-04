<%@ page import="java.util.*" contentType="text/html; charset = UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
    <jsp:include page="include/head.jsp">
        <jsp:param name="titulo" value="Página de Login"/>
    </jsp:include>
    <body>
        <div class="container m-0 p-sm-2 p-md-0 mw-100 min-vh-100">
            <div class="row m-0 p-0 min-vh-100">
                <div class="col-md bg-primary p-1 d-none d-md-flex flex-column justify-content-center align-items-center">
                </div>
                <div class="col-md p-1 d-flex flex-row justify-content-center align-items-center">
                    <div id="formBody">
                        <div id="carregando"></div>
                        <form id="formLogin" method="post" action="#">
                            <div class="row"><h2 class="p-0">Login</h2></div>
                            <div class="row"><p class="p-0">Insira as credenciais de acesso</p></div>
                            <div class="row">
                                <div id="aviso"></div>
                            </div>
                            <div class="row pb-2">
                                <div class="col-md-2 ps-0"><label>CPF</label></div>
                                <div class="col-md-10 ps-0"><input type="text" id="prmCPF" name="prmCPF" data-mask="000.000.000-00"></div>
                            </div>
                            <div class="row pb-2">
                                <div class="col-md-2 ps-0"><label>Senha</label></div>
                                <div class="col-md-10 ps-0"><input type="password" id="prmSenha" name="prmSenha"></div>
                            </div>
                            <div class="row">
                                <div class="col-md-2 ps-0"></div>
                                <div class="col-md-10 ps-0"><button type="submit" class="btn btn-primary" id="btnEnviar" name="btnEnviar">Entrar</button></div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script src="assets/libraries/jquery-3.6.0.min.js"></script>
        <script src="assets/libraries/jquery.mask.min.js"></script>
        <script src="assets/libraries/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="assets/scripts/utils.js"></script>
        <script>
            $(document).ready(function() {

                $("#formLogin").submit((event) => {
                    
                    $("#carregando").iniciarCarregar("#formLogin");
                    
                    if (!validaFormLogin()) {

                        event.preventDefault();
                        $("#carregando").pararCarregar();

                    }
                    
                });

                function validaFormLogin() {
                    
                    removerAviso("aviso");

                    // Valida CPF

                    prmCPF_val = $("#prmCPF").cleanVal();

                    if (prmCPF_val.length != 11) {

                        exibirAviso("aviso", "erro", "CPF contém menos de 11 caracteres!");
                        return false;
                        
                    }

                    // Valida senha

                    prmSenha_val = $("#prmSenha").val();

                    if (prmSenha_val.length === 0) {
                        
                        exibirAviso("aviso", "erro", "Senha não informada!");
                        return false;

                    }
                    
                    return true;

                }

            });
        </script>
    </body>
</html>
