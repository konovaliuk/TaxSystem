package com.ivahnenko.taxsystem.controller.commands.taxpayer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.controller.commands.Command;
import com.ivahnenko.taxsystem.model.Taxpayer;
import com.ivahnenko.taxsystem.model.User;

/**
 * The class is an implementation of {@code Command} interface.
 * It executes method for displaying page for creating a new taxpayer.
 */
public class DisplayCreateTaxpayer implements Command {
    private static final String SUCCESSFUL_PAGE = "/WEB-INF/pages/taxpayer/createTaxpayer.jsp";
    private static final Logger LOGGER = Logger.getLogger(DisplayCreateTaxpayer.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        LOGGER.info(String.format("User, id: %d requested DisplayCreateTaxpayer", user.getId()));

        request.setAttribute("ownershipTypeList", Taxpayer.OwnershipType.values());
        return SUCCESSFUL_PAGE;
    }
}
