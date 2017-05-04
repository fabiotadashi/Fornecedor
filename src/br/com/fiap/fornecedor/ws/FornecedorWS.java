package br.com.fiap.fornecedor.ws;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import br.com.fiap.fornecedor.dto.PedidoDTO;
import br.com.fiap.fornecedor.dto.ProdutoDTO;

@WebService
public class FornecedorWS {

	private static List<ProdutoDTO> produtos;
	private static List<PedidoDTO> pedidosRealizados;
	
	public FornecedorWS() {
		produtos = new ArrayList<>();
		// TODO inicializar lista com produtos -> ver com o grupo Loja
	}
	
	@WebMethod
	public List<ProdutoDTO> listarProdutos() {
		return produtos;
	}
	
	@WebMethod
	public boolean efetuarPedido(PedidoDTO pedidoDTO) {
		pedidosRealizados.add(pedidoDTO);
		
		// TODO Emitir nota fiscal no WS do grupo Governo
		
		// TODO Debitar valor total do grupo Financeira
		
		// TODO Solicitar entrega no grupo Transportador
		
		// Se todas as integrações forem bem sucedidas retornar true
		return true;
	}

}
