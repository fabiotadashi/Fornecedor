package br.com.fiap.fornecedor.service;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class AuthenticationService {

	private static Map<String, String> usersMap;
	
	public AuthenticationService() {
		usersMap =  new HashMap<String, String>();
		usersMap.put("gerente", "123456");
		usersMap.put("lojista", "123456");
	}



	public boolean autheticate(String authorizationHeader) {
		if(authorizationHeader == null) return false;
		
		final String encodedUserPassword = authorizationHeader.replaceFirst("Basic" + " ","");
		String userAndPassword = null;
		byte[] decodedBytes = Base64.getDecoder().decode(encodedUserPassword);
		try {
			userAndPassword = new String(decodedBytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		final StringTokenizer tokenizer = new StringTokenizer(userAndPassword, ":");
		if(tokenizer.countTokens() < 2 ) return false;
		
		final String user = tokenizer.nextToken();
		final String password = tokenizer.nextToken();
		
		if(usersMap.get(user) == null){
			return false;
		}else{
			return usersMap.get(user).equals(password);
		}
	}

}
