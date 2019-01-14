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
 * It executes activation / deactivation of a user.
 */
public class ChangeUserDeletionStatus implements Command {
    private static final String ENTITY_NAME = "user";
    private static final String ENTITY_DELETE_STATUS_CHOSEN = "deleted";

    private static final String REFRESHING_URL_DISPLAYED_LABEL = "redirect";

    private static final String EXCEPTION_IN_READING_DATA = "exception.dataReading";

    private static final String SUCCESSFUL_PAGE = "/WEB-INF/pages/home.jsp";
    private static final String EXCEPTION_PAGE = "/WEB-INF/pages/exceptionPage.jsp";

    private static final Logger LOGGER = Logger.getLogger(ChangeUserDeletionStatus.class);

    private UserService userService;

    public ChangeUserDeletionStatus() {
        this.userService = ServiceFactoryImpl.getInstance().getUserService();
    }

    public ChangeUserDeletionStatus(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ENTITY_NAME);
        LOGGER.info(String.format("User, id: %d requested ChangeUserDeletionState", user.getId()));

        try {
            int userForUpdateId = Integer.parseInt(request.getParameter(ENTITY_NAME));
            boolean deleted = Boolean.parseBoolean(request.getParameter(ENTITY_DELETE_STATUS_CHOSEN));

            User userForUpdate = new User.Builder()
                    .setId(userForUpdateId)
                    .setDeleted(deleted)
                    .build();

            userService.changeDeletionStatus(userForUpdate);
            request.setAttribute(REFRESHING_URL_DISPLAYED_LABEL, REFRESHING_URL_DISPLAYED_LABEL);
            return SUCCESSFUL_PAGE;

        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.error(EXCEPTION_IN_READING_DATA, e);
            request.setAttribute("exception", EXCEPTION_IN_READING_DATA);
            return EXCEPTION_PAGE;
        }
    }
}
