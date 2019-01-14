package com.ivahnenko.taxsystem.controller.commands.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.controller.commands.Command;
import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.model.User.Type;
import com.ivahnenko.taxsystem.service.UserService;
import com.ivahnenko.taxsystem.service.impl.ServiceFactoryImpl;

/**
 * The class is an implementation of {@code Command} interface.
 * It executes method for changing user privileges.
 */
public class ChangeUserType implements Command {
    private static final String ENTITY_NAME = "user";
    private static final String TYPE = "type";

    private static final String REFRESHING_URL_DISPLAYED_LABEL = "redirect";

    private static final String EXCEPTION_IN_READING_DATA = "exception.dataReading";

    private static final String SUCCESSFUL_PAGE = "/WEB-INF/pages/home.jsp";
    private static final String EXCEPTION_PAGE = "/WEB-INF/pages/exceptionPage.jsp";

    private static final Logger LOGGER = Logger.getLogger(ChangeUserType.class);

    private UserService userService;

    public ChangeUserType() {
        this.userService = ServiceFactoryImpl.getInstance().getUserService();
    }

    public ChangeUserType(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ENTITY_NAME);
        LOGGER.info(String.format("User, id: %d requested ChangeUserType", user.getId()));

        try {
            int modifiedUserId = Integer.parseInt(request.getParameter(ENTITY_NAME));
            String type = request.getParameter(TYPE);

            User modifiedUser = new User.Builder()
                    .setId(modifiedUserId)
                    .setType(Type.valueOf(type))
                    .build();

            userService.changeType(modifiedUser);
            request.setAttribute(REFRESHING_URL_DISPLAYED_LABEL, REFRESHING_URL_DISPLAYED_LABEL);
            return SUCCESSFUL_PAGE;

        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.error(EXCEPTION_IN_READING_DATA, e);
            request.setAttribute("exception", EXCEPTION_IN_READING_DATA);
            return EXCEPTION_PAGE;
        }
    }
}
