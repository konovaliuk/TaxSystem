package com.ivahnenko.taxsystem.controller.commands.feedbackform;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.controller.commands.Command;
import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.model.form.FeedbackForm;
import com.ivahnenko.taxsystem.model.form.Form.Status;
import com.ivahnenko.taxsystem.service.FeedbackFormService;
import com.ivahnenko.taxsystem.service.impl.ServiceFactoryImpl;

public class ChangeFeedbackFormReviewer implements Command {
    private static final String ENTITY_NAME = "feedbackForm";
    private static final String REVIEWER = "user";

    private static final String EXCEPTION_IN_READING_DATA = "exception.dataReading";

    private static final String SUCCESSFUL_PAGE = "/WEB-INF/pages/home.jsp";

    private static final Logger LOGGER = Logger.getLogger(ChangeFeedbackFormReviewer.class);

    private FeedbackFormService feedbackFormService;

    public ChangeFeedbackFormReviewer() {
        this.feedbackFormService = ServiceFactoryImpl.getInstance().getFeedbackFormService();
    }

    public ChangeFeedbackFormReviewer(FeedbackFormService feedbackFormService) {
        this.feedbackFormService = feedbackFormService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        LOGGER.info(String.format("User, id: %d requested ChangeFeedbackFormReviewer", user.getId()));

        try {
            int feedbackFormId = Integer.parseInt(request.getParameter(ENTITY_NAME));
            int reviewerId = Integer.parseInt(request.getParameter(REVIEWER));

            User reviewer = new User.Builder()
                    .setId(reviewerId)
                    .build();

            FeedbackForm feedbackForm = new FeedbackForm.Builder()
                    .setId(feedbackFormId)
                    .setReviewer(reviewer)
                    .setLastModifiedDate(LocalDate.now())
                    .setStatus(Status.IN_PROGRESS)
                    .build();

            feedbackFormService.updateReviewerAndStatus(feedbackForm);
            return SUCCESSFUL_PAGE;

        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.error(EXCEPTION_IN_READING_DATA, e);
            request.setAttribute("exception", EXCEPTION_IN_READING_DATA);
            throw new RuntimeException(e);
        }
    }
}
