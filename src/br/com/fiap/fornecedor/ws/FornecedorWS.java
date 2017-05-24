package br.com.fiap.fornecedor.ws;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceContext;

import br.com.fiap.fornecedor.dto.FreteDTO;
import br.com.fiap.fornecedor.dto.PedidoDTO;
import br.com.fiap.fornecedor.dto.ProdutoDTO;
import br.com.fiap.fornecedor.dto.RetornoTransportadoraDTO;
import br.com.fiap.fornecedor.exception.FornecedorException;
import br.com.fiap.fornecedor.service.AuthenticationService;

@WebService
public class FornecedorWS {

	private static List<ProdutoDTO> produtos = new ArrayList<>();
	private static List<PedidoDTO> pedidosRealizados = new ArrayList<>();
	private static final Properties properties = new Properties();
	private AuthenticationService service = new AuthenticationService();
	
	static {
		produtos.add(new ProdutoDTO(1, "Smart TV LED 40' Samsung 40J5500 Full HD com Conversor Digital 3 HDMI 2 USB Wi-Fi 120Hz", 1699.00));
		produtos.add(new ProdutoDTO(2, "iPhone 5S 16GB Cinza Espacial Tela Retina 4' C�mera de 8MP - Apple", 1299.99));
		produtos.add(new ProdutoDTO(3, "Smartphone Motorola Moto G4 Plus Dual Chip Android 6.0 Tela 5.5' 32GB C�mera 16MP - Preto", 1099.0));
		produtos.add(new ProdutoDTO(4, "T Shirt Rock In Rio M C Gola Redonda Mundo Masculina", 79.90));
		produtos.add(new ProdutoDTO(5, "Auto R�dio com MP3 Player e R�dio FM Naveg NVS 3068 com Entradas USB SD e Auxiliar", 49.99));
		produtos.add(new ProdutoDTO(6, "Mixer Philips Walita Linha Daily Collection Preto RI1602/9 c/ copo dosador e mini processador - 250W", 79.99));
		produtos.add(new ProdutoDTO(7, "Conjunto de Sobremesa Inox 12 Pe�as - Orb", 24.90));
		produtos.add(new ProdutoDTO(8, "Liquidificador Philco Ph800 2,4L 4 Velocidades Preto - 800W", 199.90));
		produtos.add(new ProdutoDTO(9, "Livro - Box Sherlock Holmes: As Aventuras de Sherlock Holmes (3 Volumes)", 16.90));
		produtos.add(new ProdutoDTO(10, "Livro - O Livro das Criaturas de Harry Potter", 61.99));
		produtos.add(new ProdutoDTO(11, "Box - Marvel: Guerra Civil e Guerras Secretas (Edi��o Slim) + P�ster", 19.90));
		produtos.add(new ProdutoDTO(12, "Game: Injustice 2 Ed. Limitada PS4", 249.90));
		produtos.add(new ProdutoDTO(13, "Game: Injustice 2 + Camiseta - PS4", 269.99));
		produtos.add(new ProdutoDTO(14, "Massageador Eletrico Digital Para Dores E Tensao Muscular Acupuntura Fisioterapia Lcd Com 4 Eletrod", 44.99));
		
		try (InputStream in = FornecedorWS.class.getClassLoader().getResourceAsStream("fornecedor.properties")) {
			properties.load(in);
		} catch (IOException e) {
			System.err.println("Erro ao carregar arquivo de propriedades");
			e.printStackTrace();
		}
	}
	
	@Resource
    private WebServiceContext webServiceContext;
	
	@WebMethod
	public List<ProdutoDTO> listarProdutos() throws FornecedorException {
		service.autheticate(webServiceContext);
		return produtos;
	}
	
	@WebMethod
	public boolean efetuarPedido(PedidoDTO pedido) throws FornecedorException {
		service.autheticate(webServiceContext);
		pedidosRealizados.add(pedido);
		
		
		
		// TODO Emitir nota fiscal no WS do grupo Governo
		
		// TODO Debitar valor total do grupo Financeira
		
		// Solicitar entrega no grupo Transportadora
		try {
			Response response = gerarFrete(pedido);
			if (response.getStatus() >= 400) {
				RetornoTransportadoraDTO retorno = response.readEntity(RetornoTransportadoraDTO.class);
				System.err.println("Erro ao gerar frete na trasportadora");
				System.err.println(retorno.getMensagem());
				return false;
			}
		} catch (Exception e) {
			System.err.println("Erro na requisi��o de gera��o de frete");
			e.printStackTrace();
			return false;
		}
		
		// Se todas as integrações forem bem sucedidas retornar true
		return true;
	}
	
	private static Response gerarFrete(PedidoDTO pedido) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(properties.getProperty("transportadora-url")).path(properties.getProperty("transportadora-path"));
		Builder builder = target.request(MediaType.APPLICATION_JSON);
		String auth = getAuth();
		builder.header("Authorization", auth);
		FreteDTO frete = criarFrete(pedido);
		return builder.post(Entity.entity(frete, MediaType.APPLICATION_JSON));
	}
	
	private static String getAuth() {
		byte[] auth = properties.getProperty("transportadora-auth").getBytes();
		String encoded = Base64.getEncoder().encodeToString(auth);
		return String.format("Basic %s", encoded);
	}
	
	private static FreteDTO criarFrete(PedidoDTO pedido) {
		FreteDTO frete = new FreteDTO();
		frete.setCpfCnpjDestinatario(pedido.getCpfCnpj());
		frete.setCpfCnpjRemetente(properties.getProperty("cnpj"));
		frete.setQuantidadeProdutos(pedido.getProdutos().size());
		frete.setValorTotalRemessa(pedido.getProdutos().stream().mapToDouble(p -> p.getValor()).sum());
		return frete;
	}

}
