package br.com.fiap.fornecedor.ws;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import br.com.fiap.fornecedor.client.financeira.Cobranca;
import br.com.fiap.fornecedor.client.financeira.CobrarCliente;
import br.com.fiap.fornecedor.client.financeira.CobrarClienteService;
import br.com.fiap.fornecedor.client.governo.Exception_Exception;
import br.com.fiap.fornecedor.client.governo.Governo;
import br.com.fiap.fornecedor.client.governo.GovernoService;
import br.com.fiap.fornecedor.client.governo.NotaFiscal;
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
		
		// Emitir nota fiscal no WS do grupo Governo
		NotaFiscal notaFiscal = null;
		try {
			notaFiscal = emitirNotaFiscal(pedido);
		} catch (Exception_Exception e) {
			System.err.println("Erro na requisição de emissão de NF");
			e.printStackTrace();
			return false;
		}
		
		double total = notaFiscal.getValorTotal() + notaFiscal.getValorTotalImpostos();
		
		// Debitar valor total do grupo Financeira
		try {
			boolean cobrou = cobrar(pedido, total);
			if (!cobrou) {
				System.err.println("Erro ao cobrar");
				return false;
			}
		} catch (Exception e) {
			System.err.println("Erro na requisição da cobrança");
			e.printStackTrace();
			return false;
		}
		
		// Solicitar entrega no grupo Transportadora
		try {
			Response response = gerarFrete(pedido, total);
			if (response.getStatus() >= 400) {
				RetornoTransportadoraDTO retorno = response.readEntity(RetornoTransportadoraDTO.class);
				System.err.println("Erro ao gerar frete na trasportadora");
				System.err.println(retorno.getMensagem());
				return false;
			}
		} catch (Exception e) {
			System.err.println("Erro na requisição de geração de frete");
			e.printStackTrace();
			return false;
		}
		
		// Se todas as integraÃ§Ãµes forem bem sucedidas retornar true
		return true;
	}
	
	private static NotaFiscal emitirNotaFiscal(PedidoDTO pedido) throws Exception_Exception {
		Governo port = new GovernoService().getGovernoPort();
		Map<String, Object> context = ((BindingProvider) port).getRequestContext();
		Map<String, List<String>> headers = new HashMap<>();
		headers.put("documento", Collections.singletonList(properties.getProperty("cnpj")));
		headers.put("senha", Collections.singletonList(properties.getProperty("governo-senha")));
		context.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
		double valor = pedido.getProdutos().stream().mapToDouble(p -> p.getValor()).sum();
		return port.emitirNotaFiscal(pedido.getCpfCnpj(), valor);
	}
	
	private static boolean cobrar(PedidoDTO pedido, double total) {
		CobrarCliente port = new CobrarClienteService().getCobrarClientePort();
		Map<String, Object> context = ((BindingProvider) port).getRequestContext();
		context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, properties.getProperty("financeira-url"));
		Map<String, List<String>> headers = new HashMap<>();
		headers.put("cargo", Collections.singletonList(properties.getProperty("financeira-cargo")));
		context.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
		Cobranca cobranca = new Cobranca();
		cobranca.setCpf(Long.parseLong(pedido.getCpfCnpj()));
		cobranca.setValor(total);
		return port.cobrar(cobranca);
	}
	
	private static Response gerarFrete(PedidoDTO pedido, double total) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(properties.getProperty("transportadora-url")).path(properties.getProperty("transportadora-path"));
		Builder builder = target.request(MediaType.APPLICATION_JSON);
		String auth = getAuth();
		builder.header("Authorization", auth);
		FreteDTO frete = criarFrete(pedido, total);
		return builder.post(Entity.entity(frete, MediaType.APPLICATION_JSON));
	}
	
	private static String getAuth() {
		byte[] auth = properties.getProperty("transportadora-auth").getBytes();
		String encoded = Base64.getEncoder().encodeToString(auth);
		return String.format("Basic %s", encoded);
	}
	
	private static FreteDTO criarFrete(PedidoDTO pedido, double total) {
		FreteDTO frete = new FreteDTO();
		frete.setCpfCnpjDestinatario(pedido.getCpfCnpj());
		frete.setCpfCnpjRemetente(properties.getProperty("cnpj"));
		frete.setQuantidadeProdutos(pedido.getProdutos().size());
		frete.setValorTotalRemessa(total);
		return frete;
	}

}
