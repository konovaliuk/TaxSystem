package com.ivahnenko.taxsystem.controller.commands.taxreportform;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.controller.commands.Command;
import com.ivahnenko.taxsystem.model.form.TaxReportForm;
import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.model.form.Form.Status;
import com.ivahnenko.taxsystem.service.TaxReportFormService;
import com.ivahnenko.taxsystem.service.impl.ServiceFactoryImpl;

/**
 * The class is an implementation of {@code Command} interface.
 * It executes method for changing tax report form reviewer.
 */
public class ChangeTaxReportFormReviewer implements Command {
    private static final String ENTITY_NAME = "taxReportForm";
    private static final String REVIEWER = "user";

    private static final String EXCEPTION_IN_READING_DATA = "exception.dataReading";

    private static final String REFRESHING_URL_DISPLAYED_LABEL = "redirect";

    private static final String SUCCESSFUL_PAGE = "/WEB-INF/pages/home.jsp";
    private static final String EXCEPTION_PAGE = "/WEB-INF/pages/exceptionPage.jsp";

    private static final Logger LOGGER = Logger.getLogger(ChangeTaxReportFormReviewer.class);

    private TaxReportFormService taxReportFormService;

    public ChangeTaxReportFormReviewer() {
        this.taxReportFormService = ServiceFactoryImpl.getInstance().getTaxReportFormService();
    }

    public ChangeTaxReportFormReviewer(TaxReportFormService taxReportFormService) {
        this.taxReportFormService = taxReportFormService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        LOGGER.info(String.format("User, id: %d requested ChangeTaxReportFormReviewer", user.getId()));

        try {
            int taxReportFormId = Integer.parseInt(request.getParameter(ENTITY_NAME));
            int reviewerId = Integer.parseInt(request.getParameter(REVIEWER));

            User reviewer = new User.Builder()
                    .setId(reviewerId)
                    .build();

            LocalDate lastModifiedDate = LocalDate.now();

            TaxReportForm taxReportForm = new TaxReportForm.Builder()
                    .setId(taxReportFormId)
                    .setReviewer(reviewer)
                    .setLastModifiedDate(lastModifiedDate)
                    .setStatus(Status.IN_PROGRESS)
                    .build();

            taxReportFormService.updateReviewerAndStatus(taxReportForm);
            request.setAttribute(REFRESHING_URL_DISPLAYED_LABEL, REFRESHING_URL_DISPLAYED_LABEL);
            return SUCCESSFUL_PAGE;

        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.error(EXCEPTION_IN_READING_DATA, e);
            request.setAttribute("exception", EXCEPTION_IN_READING_DATA);
            return EXCEPTION_PAGE;
        }
    }
}
