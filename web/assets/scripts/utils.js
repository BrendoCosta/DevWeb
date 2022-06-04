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
    
    switch (tipoAviso) {
        case "sucesso":
            tipoAvisoClasse = "success";
            break;
        case "aviso":
            tipoAvisoClasse = "warning";
            break;
        case "erro":
            tipoAvisoClasse = "danger";
            break;
        case "info":
            tipoAvisoClasse = "info";
            break; 
        default:
            tipoAvisoClasse = "info";
    }
    
    $(elementoID).attr("class", "alert alert-" + tipoAvisoClasse + " text-center");
    $(elementoID).attr("role", "alert");
    $(elementoID).html("<strong>" + tipoAviso.toUpperCase() + ":</strong> " + mensagem);

}

function removerAviso(elementoID) {

    elementoID = "#" + elementoID;
    
    $(elementoID).attr("class", "d-none");
    $(elementoID).removeAttr("role");
    
}