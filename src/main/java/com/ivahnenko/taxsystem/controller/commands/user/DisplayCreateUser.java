package com.ivahnenko.taxsystem.controller.commands.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.controller.commands.Command;

/**
 * The class is an implementation of {@code Command} interface.
 * It executes method for displaying page for creating a new user.
 */
public class DisplayCreateUser implements Command {
    private static final String SUCCESSFUL_PAGE = "/WEB-INF/pages/user/createUser.jsp";

    private static final Logger LOGGER = Logger.getLogger(DisplayCreateUser.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.info("Guest requested DisplayCreateUser");
        return SUCCESSFUL_PAGE;
    }
}
