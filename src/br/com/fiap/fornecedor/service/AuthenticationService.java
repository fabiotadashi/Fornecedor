package br.com.fiap.fornecedor.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import br.com.fiap.fornecedor.exception.FornecedorException;

public class AuthenticationService {

	private static Map<String, String> usersMap;
	private static final String ACESSO_NAO_AUTORIZADO = "Acesso nao autorizado";
	
	public AuthenticationService() {
		usersMap =  new HashMap<String, String>();
		usersMap.put("gerente", "123456");
		usersMap.put("lojista", "123456");
	}
	
	public void autheticate(WebServiceContext webServiceContext) throws FornecedorException {
		MessageContext messageContext = webServiceContext.getMessageContext();

		Map<?,?> requestHeaders = (Map<?,?>) messageContext.get(MessageContext.HTTP_REQUEST_HEADERS);
		List<?> usernameList = (List<?>) requestHeaders.get("username");
		List<?> passwordList = (List<?>) requestHeaders.get("password");
		String username = "";
		String password = "";

		if (usernameList != null) {
			username = usernameList.get(0).toString();
		}

		if (passwordList != null) {
			password = passwordList.get(0).toString();
		}

		if(usersMap.get(username) == null){
			throw new FornecedorException(ACESSO_NAO_AUTORIZADO);
		}else{
			if(!usersMap.get(username).equals(password)){
				throw new FornecedorException(ACESSO_NAO_AUTORIZADO);
			}
		}
	}

}
