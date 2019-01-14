package com.ivahnenko.taxsystem.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.ivahnenko.taxsystem.dao.connection.ConnectionFactory;
import com.ivahnenko.taxsystem.dao.connection.DAOConnection;
import com.ivahnenko.taxsystem.dao.DAOFactory;
import com.ivahnenko.taxsystem.dao.TaxReportFormDAO;
import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.model.form.TaxReportForm;
import com.ivahnenko.taxsystem.service.impl.TaxReportFormServiceImpl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Test class for {@code TaxReportFormService}
 */
public class TaxReportFormServiceTest {
    private static final int POSITIVE_INT_MOCK = 1;
    @Mock
    private DAOFactory daoFactory;
    @Mock
    private ConnectionFactory connectionFactory;
    @Mock
    private DAOConnection connection;
    @Mock
    private TaxReportFormDAO taxReportFormDAO;
    @InjectMocks
    private TaxReportFormServiceImpl taxreturnFormService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public final void saveShouldReturnTrue() {
        TaxReportForm taxReportForm = new TaxReportForm();
        User user = new User();
        user.setId(POSITIVE_INT_MOCK);
        taxReportForm.setInitiator(user);

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(daoFactory.getTaxReportFormDAO(connection)).thenReturn(taxReportFormDAO);
        when(taxReportFormDAO.save(taxReportForm)).thenReturn(POSITIVE_INT_MOCK);

        boolean actionPerformedSuccesfulyActual = taxreturnFormService.save(taxReportForm);

        verify(connectionFactory).getConnection();
        verify(daoFactory).getTaxReportFormDAO(connection);
        verify(taxReportFormDAO).save(taxReportForm);

        assertTrue(actionPerformedSuccesfulyActual);
    }

    @Test
    public final void updateByInitiatorAndStatusShouldReturnTrue() {
        TaxReportForm taxReportForm = new TaxReportForm();
        User user = new User();
        user.setId(POSITIVE_INT_MOCK);
        taxReportForm.setInitiator(user);
        String status = "anyStatus";

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(daoFactory.getTaxReportFormDAO(connection)).thenReturn(taxReportFormDAO);
        when(taxReportFormDAO.updateByInitiatorAndStatus(taxReportForm, status)).thenReturn(POSITIVE_INT_MOCK);

        boolean actionPerformedSuccesfulyActual = taxreturnFormService.updateByInitiatorAndStatus(taxReportForm, status);

        verify(connectionFactory).getConnection();
        verify(daoFactory).getTaxReportFormDAO(connection);
        verify(taxReportFormDAO).updateByInitiatorAndStatus(taxReportForm, status);

        assertTrue(actionPerformedSuccesfulyActual);
    }

    @Test
    public final void isReviewerAssignedShouldReturnTrue() {
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(daoFactory.getTaxReportFormDAO(connection)).thenReturn(taxReportFormDAO);
        when(taxReportFormDAO.isReviewerAssigned(POSITIVE_INT_MOCK)).thenReturn(true);

        boolean reviewerAssignedResultActual = taxreturnFormService.isReviewerAssigned(POSITIVE_INT_MOCK);
        verify(connectionFactory).getConnection();
        verify(daoFactory).getTaxReportFormDAO(connection);
        verify(taxReportFormDAO).isReviewerAssigned(POSITIVE_INT_MOCK);

        assertTrue(reviewerAssignedResultActual);
    }

    @Test
    public void getAmountShouldReturnCorrectAmount() {
        int amountExpected = 50;
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(daoFactory.getTaxReportFormDAO(connection)).thenReturn(taxReportFormDAO);
        when(taxReportFormDAO.getAmount()).thenReturn(amountExpected);

        int amountActual = taxreturnFormService.getAmount();

        verify(connectionFactory).getConnection();
        verify(daoFactory).getTaxReportFormDAO(connection);
        verify(taxReportFormDAO).getAmount();
        assertEquals(50, amountActual);
    }

    @Test
    public final void getAllShouldReturnCorrectTaxreturnFormList() {
        List<TaxReportForm> taxreturnFormListExpected = new ArrayList<>();

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(daoFactory.getTaxReportFormDAO(connection)).thenReturn(taxReportFormDAO);
        when(taxReportFormDAO.getAll()).thenReturn(taxreturnFormListExpected);

        List<TaxReportForm> taxreturnFormListActual = taxreturnFormService.getAll();

        verify(connectionFactory).getConnection();
        verify(daoFactory).getTaxReportFormDAO(connection);
        verify(taxReportFormDAO).getAll();
        assertEquals(taxreturnFormListExpected, taxreturnFormListActual);
    }

    @Test
    public void getByIdShouldReturnOptionalTaxReportForm() {
        TaxReportForm taxReportForm = new TaxReportForm();
        Optional<TaxReportForm> optionaltaxReportFormExcpected = Optional.of(taxReportForm);

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(daoFactory.getTaxReportFormDAO(connection)).thenReturn(taxReportFormDAO);
        when(taxReportFormDAO.get(1)).thenReturn(optionaltaxReportFormExcpected);

        Optional<TaxReportForm> optionaltaxReportFormActual = taxreturnFormService.getById(1);
        verify(connectionFactory).getConnection();
        verify(daoFactory).getTaxReportFormDAO(connection);
        verify(taxReportFormDAO).get(1);
        assertEquals(optionaltaxReportFormExcpected, optionaltaxReportFormActual);
    }
}
