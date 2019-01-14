package com.ivahnenko.taxsystem.controller.commands.feedbackform;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.controller.commands.Command;
import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.model.form.FeedbackForm;
import com.ivahnenko.taxsystem.service.FeedbackFormService;
import com.ivahnenko.taxsystem.service.impl.ServiceFactoryImpl;

/**
 * The class is an implementation of {@code Command} interface.
 * It executes method for displaying feedback form details for initiator.
 */
public class DisplayFeedbackFormDetailsByInitiator implements Command {
    private static final String ENTITY_NAME = "feedbackForm";

    private static final String EXCEPTION_FEEDBACK_FORM_NOT_FOUND = "exception.documentNotFound";
    private static final String EXCEPTION_IN_READING_DATA = "exception.dataReading";

    private static final String SUCCESSFUL_PAGE = "/WEB-INF/pages/feedbackform/feedbackFormDetails.jsp";
    private static final String EXCEPTION_PAGE = "/WEB-INF/pages/exceptionPage.jsp";


    private static final Logger LOGGER = Logger.getLogger(DisplayFeedbackFormDetailsByInitiator.class);

    private FeedbackFormService feedbackFormService;

    public DisplayFeedbackFormDetailsByInitiator() {
        this.feedbackFormService = ServiceFactoryImpl.getInstance().getFeedbackFormService();
    }

    public DisplayFeedbackFormDetailsByInitiator(FeedbackFormService feedbackFormService) {
        this.feedbackFormService = feedbackFormService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        LOGGER.info(String.format("User, id: %d requested DisplayFeedbackFormDetailsByInitiator", user.getId()));

        try {
            int feedbackFormId = Integer.parseInt(request.getParameter(ENTITY_NAME));
            Optional<FeedbackForm> optionalFeedbackForm = feedbackFormService.getByIdAndInitiator(feedbackFormId, user.getId());

            if (!optionalFeedbackForm.isPresent()) {
                LOGGER.info(String.format("User, id: %d requested feedbackForm, id: %d = not found", user.getId(), feedbackFormId));
                request.setAttribute("exception", EXCEPTION_FEEDBACK_FORM_NOT_FOUND);
                return EXCEPTION_PAGE;
            }

            request.setAttribute(ENTITY_NAME, optionalFeedbackForm.get());
            return SUCCESSFUL_PAGE;

        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.error(EXCEPTION_IN_READING_DATA, e);
            request.setAttribute("exception", EXCEPTION_IN_READING_DATA);
            return EXCEPTION_PAGE;
        }
    }
}
