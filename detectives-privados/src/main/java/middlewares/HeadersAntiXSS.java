package middlewares;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class HeadersAntiXSS implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		
//		default-src, script-src, img-src, style-src, font-src
		resp.setHeader("Content-Security-Policy", "default-src 'self'; script-src 'self' https://cdn.jsdelivr.net;");
		
		chain.doFilter(request, resp);
		
	}

}
