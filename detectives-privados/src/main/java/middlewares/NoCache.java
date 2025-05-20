package middlewares;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/authenticated/*")
public class NoCache implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.addHeader("Pragma", "no-cache");
		resp.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		resp.addDateHeader("Expires", 0);
		
		
		chain.doFilter(request, resp);
		
	}

}
