package com.ivahnenko.taxsystem.controller.commands.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.controller.commands.Command;
import com.ivahnenko.taxsystem.model.User;

/**
 * The class is an implementation of {@code Command} interface.
 * It executes method for user logout.
 */
public class Logout implements Command {
    private static final String REFRESHING_URL_DISPLAYED_LABEL = "redirect";

    private static final String SUCCESSFUL_PAGE = "/WEB-INF/pages/home.jsp";

    private static final Logger LOGGER = Logger.getLogger(Logout.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
            session.invalidate();
            LOGGER.info(String.format("User, id: %d logout", user.getId()));
            request.setAttribute(REFRESHING_URL_DISPLAYED_LABEL, REFRESHING_URL_DISPLAYED_LABEL);
        }
        return SUCCESSFUL_PAGE;
    }
}
