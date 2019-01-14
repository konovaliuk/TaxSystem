package com.ivahnenko.taxsystem.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.dao.connection.ConnectionFactory;
import com.ivahnenko.taxsystem.dao.connection.DAOConnection;
import com.ivahnenko.taxsystem.dao.DAOFactory;
import com.ivahnenko.taxsystem.dao.TaxReportFormDAO;
import com.ivahnenko.taxsystem.model.form.TaxReportForm;
import com.ivahnenko.taxsystem.service.TaxReportFormService;

public class TaxReportFormServiceImpl implements TaxReportFormService {
    private static final Logger LOGGER = Logger.getLogger(TaxReportFormServiceImpl.class);

    private DAOFactory daoFactory;
    private ConnectionFactory connectionFactory;

    TaxReportFormServiceImpl() {
        this.daoFactory = DAOFactory.getInstance();
        this.connectionFactory = ConnectionFactory.getInstance();
    }

    TaxReportFormServiceImpl(DAOFactory daoFactory, ConnectionFactory connectionFactory) {
        this.daoFactory = daoFactory;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public boolean save(TaxReportForm taxReportForm) {
        boolean actionSuccess = false;
        try (DAOConnection connection = connectionFactory.getConnection()) {

            TaxReportFormDAO taxReportFormDAO = daoFactory.getTaxReportFormDAO(connection);

            LOGGER.info(String.format("Saving taxReportForm by initiator, id: %d", taxReportForm.getInitiator().getId()));

            int rowCount = taxReportFormDAO.save(taxReportForm);

            if (rowCount > 0) {
                actionSuccess = true;
            }
        }
        return actionSuccess;
    }

    @Override
    public Optional<TaxReportForm> getById(int id) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            TaxReportFormDAO taxReportFormDAO = daoFactory.getTaxReportFormDAO(connection);

            LOGGER.info(String.format("Getting taxReportForm by id: %d", id));

            return taxReportFormDAO.get(id);
        }
    }

    @Override
    public boolean updateByReviewerAndStatus(TaxReportForm taxReportForm, String status) {
        boolean actionSuccess = false;
        try (DAOConnection connection = connectionFactory.getConnection()) {

            TaxReportFormDAO taxReportFormDAO = daoFactory.getTaxReportFormDAO(connection);

            LOGGER.info(String.format("Updating taxReportForm, id: %d by reviewer, id: %d and status %s",
                    taxReportForm.getId(), taxReportForm.getReviewer().getId(), status));

            int rowCount = taxReportFormDAO.updateByReviewerAndStatus(taxReportForm, status);

            if (rowCount > 0) {
                actionSuccess = true;
            }
        }
        return actionSuccess;
    }

    @Override
    public boolean updateReviewerAndStatus(TaxReportForm taxReportForm) {
        boolean actionSuccess = false;
        try (DAOConnection connection = connectionFactory.getConnection()) {

            TaxReportFormDAO taxReportFormDAO = daoFactory.getTaxReportFormDAO(connection);

            LOGGER.info(String.format("Updating taxReportForm, id: %d. Setting reviewer, id = %s and status = %s",
                    taxReportForm.getId(), taxReportForm.getReviewer().getId(), taxReportForm.getStatus()));

            int rowCount = taxReportFormDAO.updateReviewerAndStatus(taxReportForm);

            if (rowCount > 0) {
                actionSuccess = true;
            }
        }
        return actionSuccess;
    }

    @Override
    public int getAmount() {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            TaxReportFormDAO taxReportFormDAO = daoFactory.getTaxReportFormDAO(connection);

            LOGGER.info("Getting amount of taxReportForm");

            return taxReportFormDAO.getAmount();
        }
    }

    @Override
    public List<TaxReportForm> getAll() {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            TaxReportFormDAO taxReportFormDAO = daoFactory.getTaxReportFormDAO(connection);

            LOGGER.info("Getting all taxReportForm");

            return taxReportFormDAO.getAll();
        }
    }

    @Override
    public List<TaxReportForm> getAllByInitiatorId(int id, int pageRows, int offset) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            TaxReportFormDAO taxReportFormDAO = daoFactory.getTaxReportFormDAO(connection);

            LOGGER.info(String.format("Getting %d taxReportForm with offset %d, by initiator, id: %d",
                    pageRows, offset, id));

            return taxReportFormDAO.getAllByInitiatorId(id, pageRows, offset);
        }
    }

    @Override
    public List<TaxReportForm> getAllByReviewerId(int id, int pageRows, int offset) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            TaxReportFormDAO taxReportFormDAO = daoFactory.getTaxReportFormDAO(connection);

            LOGGER.info(String.format("Getting %d taxReportForm with offset %d, by reviewer, id: %d",
                    pageRows, offset, id));

            return taxReportFormDAO.getAllByReviewerId(id, pageRows, offset);
        }
    }

    @Override
    public List<TaxReportForm> getAllByStatus(String status, int pageRows, int offset) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            TaxReportFormDAO taxReportFormDAO = daoFactory.getTaxReportFormDAO(connection);

            LOGGER.info(String.format("Getting %d taxReportForm with offset %d, by status: %s",
                    pageRows, offset, status));

            return taxReportFormDAO.getAllByStatus(status, pageRows, offset);
        }
    }

    @Override
    public List<TaxReportForm> getAllByReviewerAndStatus(int id, String status, int pageRows, int offset) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            TaxReportFormDAO taxReportFormDAO = daoFactory.getTaxReportFormDAO(connection);

            LOGGER.info(String.format("Getting %d taxReportForm with offset %d, by reviewer, id: %d and status: %s",
                    pageRows, offset, id, status));

            return taxReportFormDAO.getAllByReviewerAndStatus(id, status, pageRows, offset);
        }
    }

    @Override
    public boolean isReviewerAssigned(int id) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            TaxReportFormDAO taxReportFormDAO = daoFactory.getTaxReportFormDAO(connection);

            LOGGER.info(String.format("Checking if taxReportForm, id: %d has assigned reviewer", id));

            return taxReportFormDAO.isReviewerAssigned(id);
        }
    }

    @Override
    public Optional<TaxReportForm> getByIdAndInitiator(int taxReportFormId, int initiatorId) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            TaxReportFormDAO taxReportFormDAO = daoFactory.getTaxReportFormDAO(connection);

            LOGGER.info(String.format("Getting taxReportForm by id: %d and initiator, id: %d", taxReportFormId, initiatorId));

            return taxReportFormDAO.getByIdAndInitiator(taxReportFormId, initiatorId);
        }
    }

    @Override
    public Optional<TaxReportForm> getByIdAndReviewer(int taxReportFormId, int reviewerId) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            TaxReportFormDAO taxReportFormDAO = daoFactory.getTaxReportFormDAO(connection);

            LOGGER.info(String.format("Getting taxReportForm by id: %d and reviewer, id: %d", taxReportFormId, reviewerId));

            return taxReportFormDAO.getByIdAndReviewer(taxReportFormId, reviewerId);
        }
    }

    @Override
    public boolean updateByInitiatorAndStatus(TaxReportForm taxReportForm, String status) {
        boolean actionSuccess = false;
        try (DAOConnection connection = connectionFactory.getConnection()) {

            TaxReportFormDAO taxReportFormDAO = daoFactory.getTaxReportFormDAO(connection);

            LOGGER.info(String.format("Updating taxReportForm by id: %d, initiator, id: %d, status: %s",
                    taxReportForm.getId(), taxReportForm.getInitiator().getId(), status));

            int rowCount = taxReportFormDAO.updateByInitiatorAndStatus(taxReportForm, status);

            if (rowCount > 0) {
                actionSuccess = true;
            }
        }
        return actionSuccess;
    }
}
