$.fn.iniciarCarregar = function(alvoOcultar) {
                    
    $(this).attr("class", "spinner-border text-primary");
    $(this).attr("role", "status");
    $(this).attr("alvo", alvoOcultar);

    $(alvoOcultar).toggleClass("d-none");
    
};

$.fn.pararCarregar = function() {
    
    $($(this).attr("alvo")).toggleClass("d-none");

    $(this).removeAttr("class");
    $(this).removeAttr("role");
    $(this).removeAttr("alvo");
    
};

function exibirAviso(elementoID, tipoAviso, mensagem) {
                    
    elementoID = "#" + elementoID;
    let tipoAvisoClasse;
    let avisoMensagem;

    switch (tipoAviso.toUpperCase()) {
        case "SUCESSO":
            tipoAvisoClasse = "success";
            avisoMensagem = "Sucesso";
            break;
        case "AVISO":
            tipoAvisoClasse = "warning";
            avisoMensagem = "Alerta";
            break;
        case "ERRO":
            tipoAvisoClasse = "danger";
            avisoMensagem = "Erro";
            break;
        case "INFO":
            tipoAvisoClasse = "info";
            avisoMensagem = "Informação";
            break; 
        default:
            tipoAvisoClasse = "info";
            avisoMensagem = "Informação";
    }
    
    $(elementoID).attr("class", "alert alert-" + tipoAvisoClasse + " text-center");
    $(elementoID).attr("role", "alert");
    $(elementoID).html("<strong>" + avisoMensagem + "!</strong> " + mensagem);

}

function removerAviso(elementoID) {

    elementoID = "#" + elementoID;
    
    $(elementoID).attr("class", "d-none");
    $(elementoID).removeAttr("role");
    
}