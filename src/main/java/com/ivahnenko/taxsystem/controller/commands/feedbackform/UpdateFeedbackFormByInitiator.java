package com.ivahnenko.taxsystem.controller.commands.feedbackform;

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

import com.ivahnenko.taxsystem.controller.commands.Command;
import com.ivahnenko.taxsystem.controller.util.UtilCommands;
import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.model.form.FeedbackForm;
import com.ivahnenko.taxsystem.model.form.Form.Status;
import com.ivahnenko.taxsystem.service.FeedbackFormService;
import com.ivahnenko.taxsystem.service.impl.ServiceFactoryImpl;

/**
 * The class is an implementation of {@code Command} interface.
 * It executes method for updating feedback form details by initiator.
 */
public class UpdateFeedbackFormByInitiator implements Command {
    private static final String ENTITY_NAME = "feedbackForm";
    private static final String ALLOWED_STATUS_TO_MAKE_UPDATE = "RETURNED";
    private static final String DESCRIPTION = "description";

    private static final String EXCEPTION_IN_READING_DATA = "exception.dataReading";
    private static final String EXCEPTION_NO_FEEDBACK_FORM_FOR_UPDATE = "exception.forUpdateNotFound";

    private static final String EXCEPTION_LABEL = "Exception";
    private static final String USER_INPUT_LABEL = "Provided";
    private static final String REFRESHING_URL_DISPLAYED_LABEL = "redirect";

    private static final String SUCCESSFUL_PAGE = "/WEB-INF/pages/home.jsp";
    private static final String VALIDATION_EXCEPTION_PAGE = "/main/displayFeedbackFormDetailsByInitiator";
    private static final String EXCEPTION_PAGE = "/WEB-INF/pages/exceptionPage.jsp";

    private static final Logger LOGGER = Logger.getLogger(UpdateFeedbackFormByInitiator.class);

    private static Map<String, String> inputExceptionList = new HashMap<>();

    static {
        inputExceptionList.put(DESCRIPTION, "exception.text");
    }

    private FeedbackFormService feedbackFormService;

    public UpdateFeedbackFormByInitiator() {
        this.feedbackFormService = ServiceFactoryImpl.getInstance().getFeedbackFormService();
    }

    public UpdateFeedbackFormByInitiator(FeedbackFormService feedbackFormService) {
        this.feedbackFormService = feedbackFormService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        LOGGER.info(String.format("User, id: %d requested UpdateFeedbackFormByInitiator", user.getId()));

        try {
            int feedbackFormId = Integer.parseInt(request.getParameter(ENTITY_NAME));
            String description = request.getParameter(DESCRIPTION);

            Map<String, String> feedbackFormParams = new HashMap<>();
            feedbackFormParams.put(DESCRIPTION, description);

            List<String> validationExceptionList = UtilCommands.getValidationExceptionList(feedbackFormParams, ENTITY_NAME);

            if (!validationExceptionList.isEmpty()) {
                feedbackFormParams.keySet().stream()
                        .filter(prm -> !validationExceptionList.contains(prm))
                        .forEach(prm -> request.setAttribute(prm + USER_INPUT_LABEL, feedbackFormParams.get(prm)));

                validationExceptionList.forEach(excp -> request.setAttribute(excp + EXCEPTION_LABEL, inputExceptionList.get(excp)));
                LOGGER.info("Input validation exception");
                return VALIDATION_EXCEPTION_PAGE;
            }

            FeedbackForm feedbackForm = new FeedbackForm.Builder()
                    .setId(feedbackFormId)
                    .setInitiator(user)
                    .setDescription(description)
                    .setLastModifiedDate(LocalDate.now())
                    .setStatus(Status.IN_PROGRESS)
                    .build();

            boolean actionSuccess = feedbackFormService.updateByInitiatorAndStatus(feedbackForm, ALLOWED_STATUS_TO_MAKE_UPDATE);

            if (!actionSuccess) {
                LOGGER.info(EXCEPTION_NO_FEEDBACK_FORM_FOR_UPDATE);
                request.setAttribute("exception", EXCEPTION_NO_FEEDBACK_FORM_FOR_UPDATE);
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
