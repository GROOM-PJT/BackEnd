package com.goorm.baromukja.baseUtil.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
public class BeforeSecurityFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.info("Security Filter Start");
		chain.doFilter(request, response);
	}
}
