package com.ivahnenko.taxsystem.controller.commands.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.controller.commands.Command;

/**
 * The class is an implementation of {@code Command} interface.
 * It executes method for displaying user login pages.
 */
public class DisplayLogin implements Command {
    private static final String SUCCESSFUL_PAGE = "/WEB-INF/pages/user/login.jsp";

    private static final Logger LOGGER = Logger.getLogger(DisplayLogin.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.info("Guest requested display login");
        return SUCCESSFUL_PAGE;
    }
}
