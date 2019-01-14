package com.ivahnenko.taxsystem.controller.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    /**
     * The method executes action depending on the {@code Command} object.
     *
     * @param request  http servlet request.
     * @param response http servlet response.
     * @return the string with path to the corresponding page.
     */
    String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;
}