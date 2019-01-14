package com.ivahnenko.taxsystem.controller;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ivahnenko.taxsystem.controller.commands.Command;

/**
 * Class describes creation of {@code Command} object.
 */
@WebServlet("/FrontController")
public class FrontController extends HttpServlet {
    private static final String DEFAULT_PAGE = "/index.jsp";
    private static final String EXCEPTION_PAGE = "";

    private static final String EXCEPTION_MESSAGE = "exception.generalException";

    private static final Logger LOGGER = Logger.getLogger(FrontController.class);

    private static final long serialVersionUID = 1L;

    ControllerHelper controllerHelper;

    public void init() {
        this.controllerHelper = ControllerHelper.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(EXCEPTION_MESSAGE, e);
            request.getRequestDispatcher(EXCEPTION_PAGE).forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error(EXCEPTION_MESSAGE, e);
            request.setAttribute("exception", EXCEPTION_MESSAGE);
            request.getRequestDispatcher(EXCEPTION_PAGE).forward(request, response);
        }
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = controllerHelper.getCommand(request);
        String page;

        if (Objects.isNull(command)) {

            page = DEFAULT_PAGE;

        } else {

            Optional<String> optionalPage = Optional.of(command.execute(request, response));
            page = optionalPage.orElse(DEFAULT_PAGE);
        }

        LOGGER.info(String.format("FrontController forward to %s", page));
        request.getRequestDispatcher(page).forward(request, response);
    }
}
