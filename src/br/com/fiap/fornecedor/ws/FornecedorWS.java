package br.com.fiap.fornecedor.ws;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import br.com.fiap.fornecedor.dto.PedidoDTO;
import br.com.fiap.fornecedor.dto.ProdutoDTO;
import br.com.fiap.fornecedor.exception.FornecedorException;
import br.com.fiap.fornecedor.service.AuthenticationService;

@WebService
public class FornecedorWS {

	private static List<ProdutoDTO> produtos;
	private static List<PedidoDTO> pedidosRealizados;
	private AuthenticationService service;
	
	@Resource
    private WebServiceContext webServiceContext;
	
	public FornecedorWS() {
		produtos = new ArrayList<>();
		pedidosRealizados = new ArrayList<>();
		service = new AuthenticationService();
		
		// TODO inicializar lista com produtos -> ver com o grupo Loja
		produtos.add(new ProdutoDTO(1, "Produto 1", 20));
		produtos.add(new ProdutoDTO(2, "Produto 2", 14.4));
		produtos.add(new ProdutoDTO(3, "Produto 3", 34.9));
	}
	
	@WebMethod
	public List<ProdutoDTO> listarProdutos() throws FornecedorException {
		service.autheticate(webServiceContext);
		return produtos;
	}
	
	@WebMethod
	public boolean efetuarPedido(PedidoDTO pedidoDTO) throws FornecedorException {
		service.autheticate(webServiceContext);
		pedidosRealizados.add(pedidoDTO);
		
		// TODO Emitir nota fiscal no WS do grupo Governo
		
		// TODO Debitar valor total do grupo Financeira
		
		// TODO Solicitar entrega no grupo Transportador
		
		// Se todas as integrações forem bem sucedidas retornar true
		return true;
	}

}
