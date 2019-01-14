package com.ivahnenko.taxsystem.controller.commands.taxreportform;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.ivahnenko.taxsystem.model.form.TaxReportForm;
import com.ivahnenko.taxsystem.service.TaxReportFormService;

/**
 * Test class for {@code UpdateTaxReportFormByInitiatorTest}
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(UtilCommands.class)
public class UpdateTaxReportFormByInitiatorTest {
    private static final String ENTITY_NAME = "taxReportForm";
    private static final String TAX_REPORT_FORM_ID_STRING = "2";
    private static final int USER_ID = 2;

    private static final String QUARTER = "quarter";
    private static final String YEAR = "year";
    private static final String MAIN_ACTIVITY_INCOME = "mainActivityIncome";
    private static final String INVESTMENT_INCOME = "investmentIncome";
    private static final String PROPERTY_INCOME = "propertyIncome";
    private static final String MAIN_ACTIVITY_EXPENSES = "mainActivityExpenses";
    private static final String INVESTMENT_EXPENSES = "investmentExpenses";
    private static final String PROPERTY_EXPENSES = "propertyExpenses";

    private static final String QUARTER_VALUE = "1";
    private static final String YEAR_VALUE = "2018";
    private static final String MONEY_VALUE = "5555";
    private static final String MONEY_INVALID_VALUE = "notNumber";

    private static final String SUCCESSFUL_PAGE_EXCPECTED = "/WEB-INF/pages/home.jsp";
    private static final String VALIDATION_EXCEPTION_PAGE_EXCPECTED = "/main/displayTaxReportFormDetailsByInitiator";
    private static final String EXCEPTION_PAGE_EXCPECTED = "/WEB-INF/pages/exceptionPage.jsp";

    UpdateTaxReportFormByInitiator updateTaxReportFormByInitiator;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @Mock
    TaxReportFormService taxReportFormService;

    @Before
    public void setUp() throws Exception {
        updateTaxReportFormByInitiator = new UpdateTaxReportFormByInitiator(taxReportFormService);
    }

    @Test
    public void executeShouldReturnSuccessfulPage() throws ServletException, IOException {
        User user = new User.Builder().setId(USER_ID).build();
        List<String> emptyList = new ArrayList<>();
        PowerMockito.mockStatic(UtilCommands.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter(ENTITY_NAME)).thenReturn(TAX_REPORT_FORM_ID_STRING);
        when(request.getParameter(QUARTER)).thenReturn(QUARTER_VALUE);
        when(request.getParameter(YEAR)).thenReturn(YEAR_VALUE);
        when(request.getParameter(MAIN_ACTIVITY_INCOME)).thenReturn(MONEY_VALUE);
        when(request.getParameter(INVESTMENT_INCOME)).thenReturn(MONEY_VALUE);
        when(request.getParameter(PROPERTY_INCOME)).thenReturn(MONEY_VALUE);
        when(request.getParameter(MAIN_ACTIVITY_EXPENSES)).thenReturn(MONEY_VALUE);
        when(request.getParameter(INVESTMENT_EXPENSES)).thenReturn(MONEY_VALUE);
        when(request.getParameter(PROPERTY_EXPENSES)).thenReturn(MONEY_VALUE);
        when(request.getParameter(ENTITY_NAME)).thenReturn(MONEY_VALUE);
        when(UtilCommands.getValidationExceptionList(any(), any())).thenReturn(emptyList);
        when(taxReportFormService.updateByInitiatorAndStatus(any(TaxReportForm.class), any(String.class))).thenReturn(true);

        String pageActual = updateTaxReportFormByInitiator.execute(request, response);

        PowerMockito.verifyStatic(UtilCommands.class);
        UtilCommands.getValidationExceptionList(any(), any());
        verify(taxReportFormService).updateByInitiatorAndStatus(any(TaxReportForm.class), any(String.class));

        assertEquals(SUCCESSFUL_PAGE_EXCPECTED, pageActual);
    }

    @Test
    public void executeShouldReturnValidationExceptionPageWhenInputNotValid() throws ServletException, IOException {
        User user = new User.Builder().setId(USER_ID).build();
        List<String> notEmptyList = new ArrayList<>();
        notEmptyList.add("anyValueToBeNotEmpty");
        PowerMockito.mockStatic(UtilCommands.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter(ENTITY_NAME)).thenReturn(TAX_REPORT_FORM_ID_STRING);
        when(request.getParameter(QUARTER)).thenReturn(QUARTER_VALUE);
        when(request.getParameter(YEAR)).thenReturn(YEAR_VALUE);
        when(request.getParameter(MAIN_ACTIVITY_INCOME)).thenReturn(MONEY_INVALID_VALUE);
        when(request.getParameter(INVESTMENT_INCOME)).thenReturn(MONEY_VALUE);
        when(request.getParameter(PROPERTY_INCOME)).thenReturn(MONEY_VALUE);
        when(request.getParameter(MAIN_ACTIVITY_EXPENSES)).thenReturn(MONEY_VALUE);
        when(request.getParameter(INVESTMENT_EXPENSES)).thenReturn(MONEY_VALUE);
        when(request.getParameter(PROPERTY_EXPENSES)).thenReturn(MONEY_VALUE);
        when(request.getParameter(ENTITY_NAME)).thenReturn(MONEY_VALUE);
        when(UtilCommands.getValidationExceptionList(any(), any())).thenReturn(notEmptyList);
        when(taxReportFormService.updateByInitiatorAndStatus(any(TaxReportForm.class), any(String.class))).thenReturn(true);

        String pageActual = updateTaxReportFormByInitiator.execute(request, response);

        PowerMockito.verifyStatic(UtilCommands.class);
        UtilCommands.getValidationExceptionList(any(), any());


        assertEquals(VALIDATION_EXCEPTION_PAGE_EXCPECTED, pageActual);
    }

    @Test
    public void executeShouldReturnExcpetionPageWhenServiceReturnFalse() throws ServletException, IOException {
        User user = new User.Builder().setId(USER_ID).build();
        List<String> emptyList = new ArrayList<>();
        PowerMockito.mockStatic(UtilCommands.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter(ENTITY_NAME)).thenReturn(TAX_REPORT_FORM_ID_STRING);
        when(request.getParameter(QUARTER)).thenReturn(QUARTER_VALUE);
        when(request.getParameter(YEAR)).thenReturn(YEAR_VALUE);
        when(request.getParameter(MAIN_ACTIVITY_INCOME)).thenReturn(MONEY_VALUE);
        when(request.getParameter(INVESTMENT_INCOME)).thenReturn(MONEY_VALUE);
        when(request.getParameter(PROPERTY_INCOME)).thenReturn(MONEY_VALUE);
        when(request.getParameter(MAIN_ACTIVITY_EXPENSES)).thenReturn(MONEY_VALUE);
        when(request.getParameter(INVESTMENT_EXPENSES)).thenReturn(MONEY_VALUE);
        when(request.getParameter(PROPERTY_EXPENSES)).thenReturn(MONEY_VALUE);
        when(request.getParameter(ENTITY_NAME)).thenReturn(MONEY_VALUE);

        when(UtilCommands.getValidationExceptionList(any(), any())).thenReturn(emptyList);
        when(taxReportFormService.updateByInitiatorAndStatus(any(TaxReportForm.class), any(String.class))).thenReturn(false);

        String pageActual = updateTaxReportFormByInitiator.execute(request, response);

        PowerMockito.verifyStatic(UtilCommands.class);
        UtilCommands.getValidationExceptionList(any(), any());
        verify(taxReportFormService).updateByInitiatorAndStatus(any(TaxReportForm.class), any(String.class));

        assertEquals(EXCEPTION_PAGE_EXCPECTED, pageActual);
    }

    @Test
    public void executeShouldReturnExcpetionPageWhenInputNull() throws ServletException, IOException {
        User user = new User.Builder().setId(USER_ID).build();
        List<String> emptyList = new ArrayList<>();
        PowerMockito.mockStatic(UtilCommands.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter(anyString())).thenReturn(null);
        when(UtilCommands.getValidationExceptionList(any(), any())).thenReturn(emptyList);

        String pageActual = updateTaxReportFormByInitiator.execute(request, response);

        PowerMockito.verifyStatic(UtilCommands.class);
        UtilCommands.getValidationExceptionList(any(), any());

        assertEquals(EXCEPTION_PAGE_EXCPECTED, pageActual);
    }
}
