package com.ivahnenko.taxsystem.controller.commands.taxreportform;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.controller.commands.Command;
import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.model.form.TaxReportForm;
import com.ivahnenko.taxsystem.service.TaxReportFormService;
import com.ivahnenko.taxsystem.service.impl.ServiceFactoryImpl;

/**
 * The class is an implementation of {@code Command} interface.
 * It executes method for displaying tax report form details for initiator.
 */
public class DisplayTaxReportFormDetailsByInitiator implements Command {
    private static final String ENTITY_NAME = "taxReportForm";

    private static final String EXCEPTION_TAX_REPORT_FORM_NOT_FOUND = "exception.documentNotFound";
    private static final String EXCEPTION_IN_READING_DATA = "exception.dataReading";

    private static final String SUCCESSFUL_PAGE = "/WEB-INF/pages/taxreportform/taxReportFormDetails.jsp";
    private static final String EXCEPTION_PAGE = "/WEB-INF/pages/exceptionPage.jsp";

    private static final Logger LOGGER = Logger.getLogger(DisplayTaxReportFormDetailsByInitiator.class);

    private TaxReportFormService taxReportFormService;

    public DisplayTaxReportFormDetailsByInitiator() {
        this.taxReportFormService = ServiceFactoryImpl.getInstance().getTaxReportFormService();
    }

    public DisplayTaxReportFormDetailsByInitiator(TaxReportFormService taxReportFormService) {
        this.taxReportFormService = taxReportFormService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        LOGGER.info(String.format("User, id: %d requested DisplayTaxReportFormDetailsByInitiator", user.getId()));

        try {
            int taxReportFormId = Integer.parseInt(request.getParameter(ENTITY_NAME));
            Optional<TaxReportForm> optionalTaxReportForm = taxReportFormService.getByIdAndInitiator(taxReportFormId, user.getId());

            if (!optionalTaxReportForm.isPresent()) {
                LOGGER.info(String.format("User, id: %d requested taxReportForm details, id: %d by initiator, id: %1$d = not found", user.getId(), taxReportFormId));
                request.setAttribute("exception", EXCEPTION_TAX_REPORT_FORM_NOT_FOUND);
                return EXCEPTION_PAGE;
            }

            request.setAttribute(ENTITY_NAME, optionalTaxReportForm.get());
            return SUCCESSFUL_PAGE;

        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.error(EXCEPTION_IN_READING_DATA, e);
            request.setAttribute("exception", EXCEPTION_IN_READING_DATA);
            return EXCEPTION_PAGE;
        }
    }
}

