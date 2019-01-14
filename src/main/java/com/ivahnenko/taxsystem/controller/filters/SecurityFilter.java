package com.ivahnenko.taxsystem.controller.filters;

import java.io.IOException;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.model.User;

/**
 * {@code SecurityFilter} class describes security filtering.
 * It validates requested resource and user rights to access it.
 * Depending on validation results, can forward to servlet or throw 404 error.
 */
public class SecurityFilter implements Filter {
    private static final String ERROR_RESOURCE_ACCESS_DENIED = "404.resourceAccessDenied";
    private static final String ERROR_RESOURCE_NOT_FOUND = "404.resourceNotFound";
    private static final String ERROR_LABEL = "error";

    private static final String PAGE_404 = "/WEB-INF/pages/404.jsp";
    private static final String LOGIN_PAGE = "/index.jsp";

    private static final Logger LOGGER = Logger.getLogger(SecurityFilter.class);

    private SecurityConfig securityConfig;

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        this.securityConfig = SecurityConfig.getInstance();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        User currentUser = (User) httpRequest.getSession().getAttribute("user");

        String commandRequested = httpRequest.getRequestURI().replaceAll(".*/", "");
        String page = PAGE_404;
        String userType;

        LOGGER.info(String.format("Validating rights to initialize command: %s", commandRequested));


        if (currentUser != null) {

            userType = currentUser.getType().toString();

        } else {

            userType = "GUEST";
        }

        Set<String> availableCommands = securityConfig.getCommandsByType(userType);

        if (securityConfig.isCommandExist(commandRequested)) {

            if (availableCommands.contains(commandRequested)) {

                chain.doFilter(httpRequest, httpResponse);

            } else if (userType.equals("GUEST")) {

                page = LOGIN_PAGE;
                LOGGER.info(String.format("Access denied to initialize command: %s, for user, type: %s. Forward to login page", commandRequested, userType));
                request.getRequestDispatcher(page).forward(request, response);

            } else {
                LOGGER.info(String.format("Access denied to initialize command: %s, for user, type: %s", commandRequested, userType));
                request.setAttribute(ERROR_LABEL, ERROR_RESOURCE_ACCESS_DENIED);
                request.getRequestDispatcher(page).forward(request, response);
            }

        } else {

            LOGGER.info(String.format("Requested command: %s not found", commandRequested));
            request.setAttribute(ERROR_LABEL, ERROR_RESOURCE_NOT_FOUND);
            request.getRequestDispatcher(page).forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
