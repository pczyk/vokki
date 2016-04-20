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
import javax.inject.Inject;

public class LoginFilter implements Filter {

    private static final String LOGIN_TARGET = "/login.xhtml";
    private static final String URI_PREFIX = "/vokki/";

    @Inject
    private UserSession userSession;
    
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(final ServletRequest request,
            final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        if (userSession == null) {
            redirectToLoginPage(req, res);
        } else if (!userSession.isLoggedIn()) {
            userSession.setRequestedPage(req.getRequestURI().replace(URI_PREFIX, ""));
            redirectToLoginPage(req, res);
        } else {
            chain.doFilter(request, response);
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    private void redirectToLoginPage(final HttpServletRequest request,
            final HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + LOGIN_TARGET);
    }
}
