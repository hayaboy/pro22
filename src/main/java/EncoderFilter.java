


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;  //chain of a filtered request 
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = "/*")
public class EncoderFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding( "utf-8" );
		chain.doFilter(request, response);
		
		System.out.println("한글 인코딩 필터 적용됨");
	}

}
