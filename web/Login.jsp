<%@ page import="java.util.*, app.*" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
    <jsp:include page="include/head.jsp">
        <jsp:param name="titulo" value="Página de Login"/>
    </jsp:include>
    <body>
        <div class="container g-0 m-0 p-sm-2 p-md-0 mw-100 min-vh-100">
            <div class="row m-0 p-0 min-vh-100">
                <div class="col-md bg-primary p-1 d-none d-md-flex flex-column justify-content-center align-items-center">
                </div>
                <div class="col-md p-1 d-flex flex-column justify-content-center align-items-center">
                    <div id="aviso"></div>
                    <div id="formBody">
                        <div id="carregando"></div>
                        <form id="formLogin" method="post" action="Login">
                            <div class="row"><h2 class="p-0">Login</h2></div>
                            <div class="row"><p class="p-0">Insira suas credenciais de acesso</p></div>
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

            <%
                Mensagem msg = (Mensagem) request.getAttribute("resMensagem");
                if (msg != null) {

                    %>exibirAviso("aviso", "<%=msg.getTextoTipo()%>", "<%=msg.getMensagem()%>");<%

                }
            %>

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

                    if ( $("#prmCPF").val().length != 14 ) {

                        exibirAviso("aviso", "ERRO", "CPF não informado ou com formato inválido!");
                        return false;

                    }

                    // Valida senha

                    if ( $("#prmSenha").val().length == 0 ) {

                        exibirAviso("aviso", "ERRO", "Senha não informada!");
                        return false;
                        
                    }

                    if ( $("#prmSenha").val().length > 10 ) {

                        exibirAviso("aviso", "ERRO", "Senha informada é maior que o permitido!");
                        return false;
                        
                    }
                    
                    return true;

                }

            });
        </script>
    </body>
</html>
