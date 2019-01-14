package com.ivahnenko.taxsystem.controller.commands.feedbackform;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.controller.commands.Command;
import com.ivahnenko.taxsystem.controller.util.UtilCommands;
import com.ivahnenko.taxsystem.model.Taxpayer;
import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.model.form.FeedbackForm;
import com.ivahnenko.taxsystem.model.form.Form.Status;
import com.ivahnenko.taxsystem.service.FeedbackFormService;
import com.ivahnenko.taxsystem.service.impl.ServiceFactoryImpl;

/**
 * The class is an implementation of {@code Command} interface.
 * It executes method for creating feedback form.
 */
public class CreateFeedbackForm implements Command {
    private static final String ENTITY_NAME = "feedbackForm";
    private static final String USER = "user";
    private static final String TAXPAYER = "taxpayer";
    private static final String DESCRIPTION = "description";

    private static final String EXCEPTION_TAXPAYER_NOT_FOUND = "exception.taxpayer.notFound";
    private static final String EXCEPTION_IN_READING_DATA = "exception.dataReading";

    private static final String EXCEPTION_LABEL = "Exception";
    private static final String USER_INPUT_LABEL = "Provided";
    private static final String REFRESHING_URL_DISPLAYED_LABEL = "redirect";

    private static final String SUCCESSFUL_PAGE = "/WEB-INF/pages/home.jsp";
    private static final String VALIDATION_EXCEPTION_PAGE = "/WEB-INF/pages/feedbackform/createFeedbackForm.jsp";
    private static final String EXCEPTION_PAGE = "/WEB-INF/pages/exceptionPage.jsp";

    private static final Logger LOGGER = Logger.getLogger(CreateFeedbackForm.class);

    private static Map<String, String> inputExceptionList = new HashMap<>();

    static {
        inputExceptionList.put(DESCRIPTION, "exception.text");
    }

    private FeedbackFormService feedbackFormService;

    public CreateFeedbackForm() {
        this.feedbackFormService = ServiceFactoryImpl.getInstance().getFeedbackFormService();
    }

    public CreateFeedbackForm(FeedbackFormService feedbackFormService) {
        this.feedbackFormService = feedbackFormService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        LOGGER.info(String.format("User, id: %d requested CreateFeedbackForm", user.getId()));

        try {
            String description = request.getParameter(DESCRIPTION);
            Status status = Status.TODO;

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

            Taxpayer taxpayer = (Taxpayer) session.getAttribute(TAXPAYER);

            if (Objects.isNull(taxpayer)) {
                LOGGER.info(EXCEPTION_TAXPAYER_NOT_FOUND);
                request.setAttribute("exception", EXCEPTION_TAXPAYER_NOT_FOUND);
                return EXCEPTION_PAGE;
            }

            FeedbackForm feedbackForm = new FeedbackForm.Builder()
                    .setTaxpayer(taxpayer)
                    .setInitiator(user)
                    .setDescription(description)
                    .setCreationDate(LocalDate.now())
                    .setLastModifiedDate(LocalDate.now())
                    .setStatus(status)
                    .build();

            feedbackFormService.save(feedbackForm);
            request.setAttribute(REFRESHING_URL_DISPLAYED_LABEL, REFRESHING_URL_DISPLAYED_LABEL);
            return SUCCESSFUL_PAGE;

        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.error(EXCEPTION_IN_READING_DATA, e);
            request.setAttribute("exception", EXCEPTION_IN_READING_DATA);
            return EXCEPTION_PAGE;
        }
    }
}
