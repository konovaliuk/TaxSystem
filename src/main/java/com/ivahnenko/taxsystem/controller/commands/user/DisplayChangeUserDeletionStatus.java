package com.ivahnenko.taxsystem.controller.commands.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.controller.commands.Command;
import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.service.UserService;
import com.ivahnenko.taxsystem.service.impl.ServiceFactoryImpl;
import com.ivahnenko.taxsystem.service.impl.UserServiceImpl;

/**
 * The class is an implementation of {@code Command} interface.
 * It executes method for displaying page for activation / deactivation of a user.
 */
public class DisplayChangeUserDeletionStatus implements Command {
    private static final String SUCCESSFUL_PAGE = "/WEB-INF/pages/user/changeUserDeletionState.jsp";

    private static final Logger LOGGER = Logger.getLogger(DisplayChangeUserDeletionStatus.class);

    private UserService userService;

    public DisplayChangeUserDeletionStatus() {
        this.userService = ServiceFactoryImpl.getInstance().getUserService();
    }

    public DisplayChangeUserDeletionStatus(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        LOGGER.info(String.format("User, id: %d requested DisplayChangeUserDeletionState", user.getId()));

        request.setAttribute("userList", userService.getAll());
        return SUCCESSFUL_PAGE;
    }
}