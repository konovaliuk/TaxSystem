package com.ivahnenko.taxsystem.controller.commands.user;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.controller.commands.Command;
import com.ivahnenko.taxsystem.controller.util.UtilCommands;
import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.service.UserService;
import com.ivahnenko.taxsystem.service.impl.ServiceFactoryImpl;

/**
 * The class is an implementation of {@code Command} interface.
 * It executes creation of a new user.
 */
public class CreateUser implements Command {
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String PATRONYMIC = "patronymic";
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String CONFIRMED_PASSWORD = "confirmedPassword";
    private static final String ENTITY_NAME = "user";

    private static final String EXCEPTION_IN_READING_DATA = "exception.dataReading";
    private static final String EXISTS_USERNAME = "exception.user.usernameExists";
    private static final String EXISTS_EMAIL = "exception.user.emailExists";
    private static final String EXCEPTION_NOT_CONFIRMED_PASSWORD = "exception.user.confirmedPassword";

    private static final String EXCEPTION_LABEL = "Exception";
    private static final String USER_INPUT_LABEL = "Provided";

    private static final String SUCCESSFUL_PAGE = "/main/login";
    private static final String VALIDATION_EXCEPTION_PAGE = "/WEB-INF/pages/user/createUser.jsp";
    private static final String EXCEPTION_PAGE = "/WEB-INF/pages/exceptionPage.jsp";

    private static final Logger LOGGER = Logger.getLogger(CreateUser.class);

    private static Map<String, String> inputExceptionList = new HashMap<>();

    static {
        inputExceptionList.put(NAME, "exception.user.name");
        inputExceptionList.put(SURNAME, "exception.user.surname");
        inputExceptionList.put(PATRONYMIC, "exception.user.patronymic");
        inputExceptionList.put(EMAIL, "exception.user.email");
        inputExceptionList.put(USERNAME, "exception.user.username");
        inputExceptionList.put(PASSWORD, "exception.user.password");
    }

    private UserService userService;

    public CreateUser() {
        this.userService = ServiceFactoryImpl.getInstance().getUserService();
    }

    public CreateUser(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String name = request.getParameter(NAME);
            String surname = request.getParameter(SURNAME);
            String patronymic = request.getParameter(PATRONYMIC);
            String email = request.getParameter(EMAIL);
            String username = request.getParameter(USERNAME);
            String password = request.getParameter(PASSWORD);
            String confirmedPassword = request.getParameter(CONFIRMED_PASSWORD);

            LOGGER.info(String.format("GUEST requested CreateUser with username: %s", username));

            Map<String, String> userParams = new HashMap<>();
            userParams.put(NAME, name);
            userParams.put(SURNAME, surname);
            userParams.put(EMAIL, email);
            userParams.put(USERNAME, username);
            userParams.put(PASSWORD, password);
            
            if (!patronymic.equals("")) {
                userParams.put(PATRONYMIC, patronymic);
            }

            List<String> validationExceptionList = UtilCommands.getValidationExceptionList(userParams, ENTITY_NAME);
            boolean validationError = false;

            if (!validationExceptionList.isEmpty()) {
                LOGGER.info("Input validation exception");
                validationError = true;

            } else if (userService.isUsernameExists(username)) {
                LOGGER.info(EXISTS_USERNAME);
                request.setAttribute(USERNAME + EXCEPTION_LABEL, EXISTS_USERNAME);
                validationError = true;

            } else if (userService.isEmailExists(email)) {
                LOGGER.info(EXISTS_EMAIL);
                request.setAttribute(EMAIL + EXCEPTION_LABEL, EXISTS_EMAIL);
                validationError = true;

            } else if (!password.equals(confirmedPassword)) {
                LOGGER.info(EXCEPTION_NOT_CONFIRMED_PASSWORD);
                request.setAttribute(CONFIRMED_PASSWORD + EXCEPTION_LABEL, EXCEPTION_NOT_CONFIRMED_PASSWORD);
                validationError = true;
            }

            if (validationError) {

                userParams.keySet().stream()
                        .filter(prm -> !validationExceptionList.contains(prm))
                        .forEach(prm -> request.setAttribute(prm + USER_INPUT_LABEL, userParams.get(prm)));

                validationExceptionList.forEach(excp -> request.setAttribute(excp + EXCEPTION_LABEL, inputExceptionList.get(excp)));
                return VALIDATION_EXCEPTION_PAGE;
            }

            User user = new User.Builder()
                    .setName(name)
                    .setSurname(surname)
                    .setPatronymic(patronymic)
                    .setEmail(email)
                    .setUsername(username)
                    .setPassword(password)
                    .setType(User.Type.CLIENT)
                    .build();

            userService.save(user);
            return SUCCESSFUL_PAGE;

        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.error(EXCEPTION_IN_READING_DATA, e);
            request.setAttribute("exception", EXCEPTION_IN_READING_DATA);
            return EXCEPTION_PAGE;
        }
    }

}
