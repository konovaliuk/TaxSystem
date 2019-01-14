package com.ivahnenko.taxsystem.controller.commands.taxreportform;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.model.form.TaxReportForm;
import com.ivahnenko.taxsystem.controller.commands.Command;
import com.ivahnenko.taxsystem.controller.util.UtilCommands;
import com.ivahnenko.taxsystem.model.form.Form.Status;
import com.ivahnenko.taxsystem.service.TaxReportFormService;
import com.ivahnenko.taxsystem.service.impl.ServiceFactoryImpl;

/**
 * The class is an implementation of {@code Command} interface.
 * It executes method for updating tax report form details by reviewer.
 */
public class UpdateTaxReportFormByReviewer implements Command {
    private static final String ENTITY_NAME = "taxReportForm";
    private static final String ALLOWED_STATUS_TO_MAKE_UPDATE = "IN_PROGRESS";
    private static final String REVIEWER_COMMENT = "reviewerComment";
    private static final String STATUS_SUBMITTED = "status";

    private static final String EXCEPTION_IN_READING_DATA = "exception.dataReading";
    private static final String EXCEPTION_NO_TAX_REPORT_FORM_FOR_UPDATE = "exception.forUpdateNotFound";
    private static final String EXCEPTION_LABEL = "Exception";
    private static final String USER_INPUT_LABEL = "Provided";
    private static final String REFRESHING_URL_DISPLAYED_LABEL = "redirect";

    private static final String SUCCESSFUL_PAGE = "/WEB-INF/pages/home.jsp";
    private static final String VALIDATION_EXCEPTION_PAGE = "/main/displayTaxReportFormDetailsByReviewer";
    private static final String EXCEPTION_PAGE = "/WEB-INF/pages/exceptionPage.jsp";

    private static final Logger LOGGER = Logger.getLogger(UpdateTaxReportFormByReviewer.class);

    private static Map<String, String> inputExceptionList = new HashMap<>();

    static {
        inputExceptionList.put(REVIEWER_COMMENT, "exception.text");
    }

    private TaxReportFormService taxReportFormService;

    public UpdateTaxReportFormByReviewer() {
        this.taxReportFormService = ServiceFactoryImpl.getInstance().getTaxReportFormService();
    }

    public UpdateTaxReportFormByReviewer(TaxReportFormService taxReportFormService) {
        this.taxReportFormService = taxReportFormService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        LOGGER.info(String.format("User, id: %d requested UpdateTaxReportFormByReviewer", user.getId()));

        try {
            int taxReportFormId = Integer.parseInt(request.getParameter(ENTITY_NAME));
            String reviewerComment = request.getParameter(REVIEWER_COMMENT);
            String status = request.getParameter(STATUS_SUBMITTED);

            Map<String, String> taxReportFormParams = new HashMap<>();
            taxReportFormParams.put(REVIEWER_COMMENT, reviewerComment);

            List<String> validationExceptionList = UtilCommands.getValidationExceptionList(taxReportFormParams, ENTITY_NAME);

            if (!validationExceptionList.isEmpty()) {
                taxReportFormParams.keySet().stream()
                        .filter(prm -> !validationExceptionList.contains(prm))
                        .forEach(prm -> request.setAttribute(prm + USER_INPUT_LABEL, taxReportFormParams.get(prm)));

                validationExceptionList.forEach(excp -> request.setAttribute(excp + EXCEPTION_LABEL, inputExceptionList.get(excp)));

                request.setAttribute(ENTITY_NAME, taxReportFormId);
                LOGGER.info("Input validation exception");
                return VALIDATION_EXCEPTION_PAGE;
            }

            TaxReportForm taxReportForm = new TaxReportForm.Builder()
                    .setId(taxReportFormId)
                    .setReviewer(user)
                    .setReviewerComment(reviewerComment)
                    .setLastModifiedDate(LocalDate.now())
                    .setStatus(Status.valueOf(status))
                    .build();

            boolean actionSuccess = taxReportFormService.updateByReviewerAndStatus(taxReportForm, ALLOWED_STATUS_TO_MAKE_UPDATE);

            if (!actionSuccess) {
                LOGGER.info(String.format("TaxReportForm for update with id: %d, user,id: %d, status: %s = not found"
                        , taxReportFormId, user.getId(), ALLOWED_STATUS_TO_MAKE_UPDATE));
                request.setAttribute("exception", EXCEPTION_NO_TAX_REPORT_FORM_FOR_UPDATE);
                return EXCEPTION_PAGE;
            }

            request.setAttribute(REFRESHING_URL_DISPLAYED_LABEL, REFRESHING_URL_DISPLAYED_LABEL);
            return SUCCESSFUL_PAGE;

        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.error(EXCEPTION_IN_READING_DATA, e);
            request.setAttribute("exception", EXCEPTION_IN_READING_DATA);
            return EXCEPTION_PAGE;
        }
    }
}
