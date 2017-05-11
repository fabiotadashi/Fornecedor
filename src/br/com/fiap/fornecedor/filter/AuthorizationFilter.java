package br.com.fiap.fornecedor.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.fiap.fornecedor.service.AuthenticationService;

public class AuthorizationFilter implements Filter {

	private static final String ACESSO_NAO_AUTORIZADO = "Acesso nao autorizado";
	private static final String AUTHORIZATION = "Authorization";

	public AuthorizationFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if(request instanceof HttpServletRequest){
			HttpServletRequest req = (HttpServletRequest) request;
			
			String authorizationHeader = req.getHeader(AUTHORIZATION);
			
			AuthenticationService service = new AuthenticationService();
			
			if(service.autheticate(authorizationHeader)){
				chain.doFilter(request, response);
			}else{
				if(response instanceof HttpServletResponse){
					HttpServletResponse res = (HttpServletResponse) response;
					res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					PrintWriter writer = res.getWriter();
					writer.write(ACESSO_NAO_AUTORIZADO);
					writer.flush();
					writer.close();
				}
			}
		}
	}

	@Override
	public void destroy() {

	}
}