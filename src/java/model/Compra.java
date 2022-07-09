package model;

public class Compra {

    private int id;
    private int quantidadeCompra;
    private String dataCompra;
    private int valorCompra;
    private int idFornecedor;
    private int idProduto;
    private int idFuncionario; 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidadeCompra() {
        return quantidadeCompra;
    }

    public void setQuantidadeCompra(int quantidadeCompra) {
        this.quantidadeCompra = quantidadeCompra;
    }

    public String getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(String dataCompra) {
        this.dataCompra = dataCompra;
    }

    public int getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(int valorCompra) {
        this.valorCompra = valorCompra;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }
    
}