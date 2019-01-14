package com.ivahnenko.taxsystem.controller.commands.user;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.controller.commands.Command;
import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.service.UserService;
import com.ivahnenko.taxsystem.service.impl.ServiceFactoryImpl;

/**
 * The class is an implementation of {@code Command} interface.
 * It executes method for user login.
 */
public class Login implements Command {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final String EXCEPTION_USERNAME_PASSWORD = "exception.wrongUsernameOrPassword";
    private static final String EXCEPTION_IN_READING_DATA = "exception.dataReading";
    private static final String EXCEPTION_USER_SUSPENDED = "exception.userSuspended";

    private static final String REFRESHING_URL_DISPLAYED_LABEL = "redirect";

    private static final String HOME_PAGE = "/WEB-INF/pages/home.jsp";
    private static final String EXCEPTION_PAGE = "/WEB-INF/pages/exceptionPage.jsp";

    private static final Logger LOGGER = Logger.getLogger(Login.class);

    private UserService userService;

    public Login() {
        this.userService = ServiceFactoryImpl.getInstance().getUserService();
    }

    public Login(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String username = request.getParameter(USERNAME);
            String password = request.getParameter(PASSWORD);

            LOGGER.info(String.format("User, username: %s login", username));
            Optional<User> optionalUser = userService.login(username, password);

            if (!optionalUser.isPresent()) {
                LOGGER.info(EXCEPTION_USERNAME_PASSWORD);
                request.setAttribute("exception", EXCEPTION_USERNAME_PASSWORD);
                return HOME_PAGE;
            }

            User user = optionalUser.get();

            if (user.isDeleted()) {
                LOGGER.info(String.format("User, username: %s suspended. Login denied", username));
                request.setAttribute("exception", EXCEPTION_USER_SUSPENDED);
                return EXCEPTION_PAGE;
            }

            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            request.setAttribute(REFRESHING_URL_DISPLAYED_LABEL, REFRESHING_URL_DISPLAYED_LABEL);
            return HOME_PAGE;

        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.error(EXCEPTION_IN_READING_DATA, e);
            request.setAttribute("exception", EXCEPTION_IN_READING_DATA);
            return EXCEPTION_PAGE;
        }
    }
}
