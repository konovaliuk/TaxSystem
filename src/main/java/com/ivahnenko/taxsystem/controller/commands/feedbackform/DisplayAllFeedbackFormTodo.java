package com.ivahnenko.taxsystem.controller.commands.feedbackform;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.controller.commands.Command;
import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.service.FeedbackFormService;
import com.ivahnenko.taxsystem.service.impl.ServiceFactoryImpl;

/**
 * The class is an implementation of {@code Command} interface.
 * It executes method for displaying all feedback forms with {@code TO DO} status.
 */
public class DisplayAllFeedbackFormTodo implements Command {
    private static final int PAGE_ROWS = 7;
    private static final String SUB_COMMAND = "AllFeedbackFormTodo";
    private static final String BUTTON_NEXT = "next";
    private static final String BUTTON_PREVIOUS = "previous";

    private static final String EXCEPTION_IN_READING_DATA = "exception.dataReading";

    private static final String SUCCESSFUL_PAGE = "/WEB-INF/pages/feedbackform/allFeedbackForm.jsp";
    private static final String EXCEPTION_PAGE = "/WEB-INF/pages/exceptionPage.jsp";

    private static final Logger LOGGER = Logger.getLogger(DisplayAllFeedbackFormTodo.class);

    private FeedbackFormService feedbackFormService;

    public DisplayAllFeedbackFormTodo() {
        this.feedbackFormService = ServiceFactoryImpl.getInstance().getFeedbackFormService();
    }

    public DisplayAllFeedbackFormTodo(FeedbackFormService feedbackFormService) {
        this.feedbackFormService = feedbackFormService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        LOGGER.info(String.format("User, id: %d requested DisplayAllFeedbackFormTodo", user.getId()));

        try {
            int offset = getOffset(request);

            request.setAttribute("offset", offset);
            request.setAttribute("command", SUB_COMMAND);
            request.setAttribute("feedbackFormList", feedbackFormService.getAllByStatus("TODO", PAGE_ROWS, offset));
            return SUCCESSFUL_PAGE;

        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.error(EXCEPTION_IN_READING_DATA, e);
            request.setAttribute("exception", EXCEPTION_IN_READING_DATA);
            return EXCEPTION_PAGE;
        }
    }

    private int getOffset(HttpServletRequest request) throws NumberFormatException, NullPointerException {
        int offset = 0;
        String submit;

        if ((submit = request.getParameter("submit")) != null) {
            offset = Integer.parseInt(request.getParameter("offset"));
            int rowsAmount = feedbackFormService.getAmount();

            if (submit.equals(BUTTON_PREVIOUS)) {
                offset -= PAGE_ROWS;

                if (offset < 0) {
                    offset = 0;
                }
            }

            if (offset + PAGE_ROWS < rowsAmount && submit.equals(BUTTON_NEXT)) {
                offset += PAGE_ROWS;
            }
        }
        return offset;
    }
}
