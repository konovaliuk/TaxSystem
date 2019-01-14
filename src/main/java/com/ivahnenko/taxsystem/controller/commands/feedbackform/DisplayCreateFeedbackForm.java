package com.ivahnenko.taxsystem.controller.commands.feedbackform;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.controller.commands.Command;
import com.ivahnenko.taxsystem.model.Taxpayer;
import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.service.TaxpayerService;
import com.ivahnenko.taxsystem.service.impl.ServiceFactoryImpl;

/**
 * The class is an implementation of {@code Command} interface.
 * It executes method for displaying page for creating feedback form.
 */
public class DisplayCreateFeedbackForm implements Command {
    private static final String SUCCESSFUL_PAGE = "/WEB-INF/pages/feedbackform/createFeedbackForm.jsp";
    private static final String NEW_TAXPAYER_PAGE_IF_NOT_FOUND = "/WEB-INF/pages/taxpayer/createTaxpayer.jsp";

    private static final Logger LOGGER = Logger.getLogger(DisplayCreateFeedbackForm.class);

    private TaxpayerService taxpayerService;

    public DisplayCreateFeedbackForm() {
        this.taxpayerService = ServiceFactoryImpl.getInstance().getTaxpayerService();
    }

    public DisplayCreateFeedbackForm(TaxpayerService taxpayerService) {
        this.taxpayerService = taxpayerService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        LOGGER.info(String.format("User, id: %d requested DisplayCreateFeedbackForm", user.getId()));

        Optional<Taxpayer> optionalTaxpayer = taxpayerService.getByEmployeeId(user.getId());

        if (!optionalTaxpayer.isPresent()) {
            LOGGER.info("Taxpayer not found");
            return NEW_TAXPAYER_PAGE_IF_NOT_FOUND;

        }

        session.setAttribute("taxpayer", optionalTaxpayer.get());
        return SUCCESSFUL_PAGE;
    }
}

