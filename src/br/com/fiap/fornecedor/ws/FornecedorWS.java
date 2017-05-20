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

	private static List<ProdutoDTO> produtos = new ArrayList<>();
	private static List<PedidoDTO> pedidosRealizados = new ArrayList<>();
	private AuthenticationService service = new AuthenticationService();
	
	static {
		produtos.add(new ProdutoDTO(1, "Smart TV LED 40' Samsung 40J5500 Full HD com Conversor Digital 3 HDMI 2 USB Wi-Fi 120Hz", 1699.00));
		produtos.add(new ProdutoDTO(2, "iPhone 5S 16GB Cinza Espacial Tela Retina 4' Câmera de 8MP - Apple", 1299.99));
		produtos.add(new ProdutoDTO(3, "Smartphone Motorola Moto G4 Plus Dual Chip Android 6.0 Tela 5.5' 32GB Câmera 16MP - Preto", 1099.0));
		produtos.add(new ProdutoDTO(4, "T Shirt Rock In Rio M C Gola Redonda Mundo Masculina", 79.90));
		produtos.add(new ProdutoDTO(5, "Auto Rádio com MP3 Player e Rádio FM Naveg NVS 3068 com Entradas USB SD e Auxiliar", 49.99));
		produtos.add(new ProdutoDTO(6, "Mixer Philips Walita Linha Daily Collection Preto RI1602/9 c/ copo dosador e mini processador - 250W", 79.99));
		produtos.add(new ProdutoDTO(7, "Conjunto de Sobremesa Inox 12 Peças - Orb", 24.90));
		produtos.add(new ProdutoDTO(8, "Liquidificador Philco Ph800 2,4L 4 Velocidades Preto - 800W", 199.90));
		produtos.add(new ProdutoDTO(9, "Livro - Box Sherlock Holmes: As Aventuras de Sherlock Holmes (3 Volumes)", 16.90));
		produtos.add(new ProdutoDTO(10, "Livro - O Livro das Criaturas de Harry Potter", 61.99));
		produtos.add(new ProdutoDTO(11, "Box - Marvel: Guerra Civil e Guerras Secretas (Edição Slim) + Pôster", 19.90));
		produtos.add(new ProdutoDTO(12, "Game: Injustice 2 Ed. Limitada PS4", 249.90));
		produtos.add(new ProdutoDTO(13, "Game: Injustice 2 + Camiseta - PS4", 269.99));
		produtos.add(new ProdutoDTO(14, "Massageador Eletrico Digital Para Dores E Tensao Muscular Acupuntura Fisioterapia Lcd Com 4 Eletrod", 44.99));
	}
	
	@Resource
    private WebServiceContext webServiceContext;
	
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
		
		// Se todas as integraÃ§Ãµes forem bem sucedidas retornar true
		return true;
	}

}
