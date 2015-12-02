package de.mupitu.vokki.presentation.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.mupitu.vokki.presentation.UserSession;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(final ServletRequest request,
			final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse res = (HttpServletResponse) response;

		final UserSession userSession = (UserSession) req.getSession()
				.getAttribute("userSession");

		if (userSession == null || !userSession.isLoggedIn()) {
			res.sendRedirect(req.getContextPath() + "/login.jsf");
		} else {
			chain.doFilter(request, response);
		}

	}

        @Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
