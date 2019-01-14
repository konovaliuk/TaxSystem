package com.ivahnenko.taxsystem.controller.commands.feedbackform;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ivahnenko.taxsystem.controller.util.UtilCommands;
import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.model.form.FeedbackForm;
import com.ivahnenko.taxsystem.service.FeedbackFormService;


/**
 * Test class for {@code UpdateFeedbackFormByInitiator}
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(UtilCommands.class)
public class UpdateFeedbackFormByInitiatorTest {
    private static final String FEEDBACK_FORM_ID_STRING = "2";
    private static final int USER_ID = 2;
    private static final String ENTITY_NAME = "feedbackForm";
    private static final String DESCRIPTION = "description";
    private static final String NULL_VALUE = null;

    private static final String SUCCESSFUL_PAGE_EXCPECTED = "/WEB-INF/pages/home.jsp";
    private static final String EXCEPTION_PAGE_EXCPECTED = "/WEB-INF/pages/exceptionPage.jsp";
    private static final String VALIDATION_EXCEPTION_PAGE_EXCPECTED = "/main/displayFeedbackFormDetailsByInitiator";

    UpdateFeedbackFormByInitiator updateFeedbackFormByInitiator;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @Mock
    FeedbackFormService feedbackFormService;

    @Before
    public void setUp() throws Exception {
        updateFeedbackFormByInitiator = new UpdateFeedbackFormByInitiator(feedbackFormService);
    }

    @Test
    public void testExecuteShouldReturnSuccessfulPageWhenInputParamValid() throws ServletException, IOException {
        User user = new User.Builder().setId(USER_ID).build();
        List<String> emptyList = new ArrayList<>();
        PowerMockito.mockStatic(UtilCommands.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter(ENTITY_NAME)).thenReturn(FEEDBACK_FORM_ID_STRING);
        when(request.getParameter(DESCRIPTION)).thenReturn(DESCRIPTION);
        when(UtilCommands.getValidationExceptionList(any(), any())).thenReturn(emptyList);
        when(feedbackFormService.updateByInitiatorAndStatus(any(FeedbackForm.class), any(String.class))).thenReturn(true);

        String pageActual = updateFeedbackFormByInitiator.execute(request, response);

        PowerMockito.verifyStatic(UtilCommands.class);
        UtilCommands.getValidationExceptionList(any(), any());
        verify(feedbackFormService).updateByInitiatorAndStatus(any(FeedbackForm.class), any(String.class));

        assertEquals(SUCCESSFUL_PAGE_EXCPECTED, pageActual);
    }


    @Test
    public void executeShouldReturnExceptionPageWhenInputNull() throws ServletException, IOException {
        User user = new User.Builder().setId(USER_ID).build();
        List<String> emptyList = new ArrayList<>();
        PowerMockito.mockStatic(UtilCommands.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter(ENTITY_NAME)).thenReturn(FEEDBACK_FORM_ID_STRING);
        when(request.getParameter(DESCRIPTION)).thenReturn(NULL_VALUE);
        when(UtilCommands.getValidationExceptionList(any(), any())).thenReturn(emptyList);

        String pageActual = updateFeedbackFormByInitiator.execute(request, response);

        PowerMockito.verifyStatic(UtilCommands.class);
        UtilCommands.getValidationExceptionList(any(), any());

        assertEquals(EXCEPTION_PAGE_EXCPECTED, pageActual);
    }

    @Test
    public void executeShouldReturnValidationExceptionPageWhenInputNotValid() throws ServletException, IOException {
        User user = new User.Builder().setId(USER_ID).build();
        List<String> notEmptyList = new ArrayList<>();
        notEmptyList.add("anyValueToBeNotEmpty");
        PowerMockito.mockStatic(UtilCommands.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter(ENTITY_NAME)).thenReturn(FEEDBACK_FORM_ID_STRING);
        when(request.getParameter(DESCRIPTION)).thenReturn(DESCRIPTION);
        when(UtilCommands.getValidationExceptionList(any(), any())).thenReturn(notEmptyList);

        String pageActual = updateFeedbackFormByInitiator.execute(request, response);

        PowerMockito.verifyStatic(UtilCommands.class);
        UtilCommands.getValidationExceptionList(any(), any());

        assertEquals(VALIDATION_EXCEPTION_PAGE_EXCPECTED, pageActual);
    }

    @Test
    public void executeShouldReturnExceptionPageWhenIncorrectNumberValue() throws ServletException, IOException {
        User user = new User.Builder().setId(USER_ID).build();
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter(ENTITY_NAME)).thenReturn(anyString());
        when(request.getParameter(DESCRIPTION)).thenReturn(DESCRIPTION);

        String pageActual = updateFeedbackFormByInitiator.execute(request, response);

        assertEquals(EXCEPTION_PAGE_EXCPECTED, pageActual);
    }
}
