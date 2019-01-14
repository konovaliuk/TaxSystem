package com.ivahnenko.taxsystem.controller.commands.user;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.mockito.Mock;

import com.ivahnenko.taxsystem.controller.util.UtilCommands;
import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.service.UserService;

/**
 * Test class for {@code CreateUserTest}
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(UtilCommands.class)
public class CreateUserTest {
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String PATRONYMIC = "patronymic";
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String CONFIRMED_PASSWORD = "password";
    private static final String CONFIRMED_PASSWORD2 = "confirmedPassword";
    private static final String SUCCESSFUL_PAGE_EXCPECTED = "/main/login";
    private static final String VALIDATION_EXCEPTION_PAGE_EXCPECTED = "/WEB-INF/pages/user/createUser.jsp";
    private static final String EXCEPTION_PAGE_EXCPECTED = "/WEB-INF/pages/exceptionPage.jsp";

    private static final String EMAIL_VALUE = "email@.com";
    private static final String NAME_INVALID_VALUE = "5555";

    CreateUser createUser;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    UserService userService;

    @Before
    public void setUp() throws Exception {
        createUser = new CreateUser(userService);
    }

    @Test
    public void executeShouldReturnSuccessfulPage() throws ServletException, IOException {
        List<String> emptyList = new ArrayList<>();
        PowerMockito.mockStatic(UtilCommands.class);

        when(request.getParameter(NAME)).thenReturn(NAME);
        when(request.getParameter(SURNAME)).thenReturn(SURNAME);
        when(request.getParameter(PATRONYMIC)).thenReturn(PATRONYMIC);
        when(request.getParameter(EMAIL)).thenReturn(EMAIL_VALUE);
        when(request.getParameter(USERNAME)).thenReturn(USERNAME);
        when(request.getParameter(PASSWORD)).thenReturn(PASSWORD);
        when(request.getParameter(CONFIRMED_PASSWORD2)).thenReturn(CONFIRMED_PASSWORD);
        when(UtilCommands.getValidationExceptionList(any(), any())).thenReturn(emptyList);
        when(userService.isUsernameExists(USERNAME)).thenReturn(false);
        when(userService.isEmailExists(EMAIL_VALUE)).thenReturn(false);

        String pageActual = createUser.execute(request, response);

        PowerMockito.verifyStatic(UtilCommands.class);
        UtilCommands.getValidationExceptionList(any(), any());

        verify(userService).isUsernameExists(USERNAME);
        verify(userService).isEmailExists(EMAIL_VALUE);
        verify(userService).save(any(User.class));

        assertEquals(SUCCESSFUL_PAGE_EXCPECTED, pageActual);
    }

    @Test
    public void executeShouldReturnValidationPage() throws ServletException, IOException {
        List<String> notEmptyList = new ArrayList<>();
        notEmptyList.add("anyValueToBeNotEmpty");
        PowerMockito.mockStatic(UtilCommands.class);

        when(request.getParameter(NAME)).thenReturn(NAME_INVALID_VALUE);
        when(request.getParameter(SURNAME)).thenReturn(SURNAME);
        when(request.getParameter(PATRONYMIC)).thenReturn(PATRONYMIC);
        when(request.getParameter(EMAIL)).thenReturn(EMAIL_VALUE);
        when(request.getParameter(USERNAME)).thenReturn(USERNAME);
        when(request.getParameter(PASSWORD)).thenReturn(PASSWORD);
        when(request.getParameter(CONFIRMED_PASSWORD2)).thenReturn(CONFIRMED_PASSWORD);
        when(UtilCommands.getValidationExceptionList(any(), any())).thenReturn(notEmptyList);
        when(userService.isUsernameExists(USERNAME)).thenReturn(false);
        when(userService.isEmailExists(EMAIL)).thenReturn(false);

        String pageActual = createUser.execute(request, response);

        PowerMockito.verifyStatic(UtilCommands.class);
        UtilCommands.getValidationExceptionList(any(), any());

        assertEquals(VALIDATION_EXCEPTION_PAGE_EXCPECTED, pageActual);
    }

    @Test
    public void executeShouldReturnExceptionPage() throws ServletException, IOException {
        PowerMockito.mockStatic(UtilCommands.class);

        when(request.getParameter(NAME)).thenReturn(null);
        when(request.getParameter(SURNAME)).thenReturn(SURNAME);
        when(request.getParameter(PATRONYMIC)).thenReturn(PATRONYMIC);
        when(request.getParameter(EMAIL)).thenReturn(EMAIL_VALUE);
        when(request.getParameter(USERNAME)).thenReturn(USERNAME);
        when(request.getParameter(PASSWORD)).thenReturn(PASSWORD);
        when(request.getParameter(CONFIRMED_PASSWORD2)).thenReturn(CONFIRMED_PASSWORD);
        when(UtilCommands.getValidationExceptionList(any(), any())).thenReturn(null);

        String pageActual = createUser.execute(request, response);

        PowerMockito.verifyStatic(UtilCommands.class);
        UtilCommands.getValidationExceptionList(any(), any());
        assertEquals(EXCEPTION_PAGE_EXCPECTED, pageActual);
    }
}
