package br.com.fiap.fornecedor.dto;

import java.io.Serializable;
import java.util.List;

public class PedidoDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String cpfCnpj;
	private List<ProdutoDTO> produtos;

	public String getCpfCnpj() {
		return cpfCnpj;
	}
	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}
	public List<ProdutoDTO> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<ProdutoDTO> produtos) {
		this.produtos = produtos;
	}
	
}
