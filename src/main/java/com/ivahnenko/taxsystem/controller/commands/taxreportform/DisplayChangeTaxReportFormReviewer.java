package com.ivahnenko.taxsystem.controller.commands.taxreportform;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.controller.commands.Command;
import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.model.User.Type;
import com.ivahnenko.taxsystem.service.TaxReportFormService;
import com.ivahnenko.taxsystem.service.UserService;
import com.ivahnenko.taxsystem.service.impl.ServiceFactoryImpl;

/**
 * The class is an implementation of {@code Command} interface.
 * It executes method for displaying page for changing tax report form reviewer.
 */
public class DisplayChangeTaxReportFormReviewer implements Command {
    private static final String SUCCESSFUL_PAGE = "/WEB-INF/pages/taxreportform/changeTaxReportFormReviewer.jsp";

    private static final Logger LOGGER = Logger.getLogger(DisplayChangeTaxReportFormReviewer.class);

    private TaxReportFormService taxReportFormService;
    private UserService userService;

    public DisplayChangeTaxReportFormReviewer() {
        this.taxReportFormService = ServiceFactoryImpl.getInstance().getTaxReportFormService();
        this.userService = ServiceFactoryImpl.getInstance().getUserService();
    }

    public DisplayChangeTaxReportFormReviewer(TaxReportFormService taxReportFormService, UserService userService) {
        this.taxReportFormService = taxReportFormService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        LOGGER.info(String.format("User, id: %d requested DisplayChangeTaxReportFormReviewer", user.getId()));

        request.setAttribute("userList", userService.getAllByType(Type.INSPECTOR.toString()));
        request.setAttribute("taxReportFormList", taxReportFormService.getAll());
        return SUCCESSFUL_PAGE;
    }
}
