package app;

public class Mensagem {

    public enum Tipo {
        SUCESSO,
        AVISO,
        ERRO,
        INFO
    }

    Tipo tipo = Tipo.INFO;
    String mensagem;

    public Mensagem(String mensagem, Tipo tipo) {

        this.tipo = tipo;
        this.mensagem = mensagem;

    }

    public Mensagem(String mensagem) {

        this.mensagem = mensagem;

    }

    //

    public String getTextoTipo() {

        return (String) this.tipo.name();

    }

    public String getMensagem() {

        return this.mensagem;

    }

}