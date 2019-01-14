package com.ivahnenko.taxsystem.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.dao.connection.ConnectionFactory;
import com.ivahnenko.taxsystem.dao.connection.DAOConnection;
import com.ivahnenko.taxsystem.dao.DAOFactory;
import com.ivahnenko.taxsystem.dao.FeedbackFormDAO;
import com.ivahnenko.taxsystem.model.form.FeedbackForm;
import com.ivahnenko.taxsystem.service.FeedbackFormService;

public class FeedbackFormServiceImpl implements FeedbackFormService {
    private static final Logger LOGGER = Logger.getLogger(FeedbackFormServiceImpl.class);

    private DAOFactory daoFactory;
    private ConnectionFactory connectionFactory;

    FeedbackFormServiceImpl() {
        this.daoFactory = DAOFactory.getInstance();
        this.connectionFactory = ConnectionFactory.getInstance();
    }

    FeedbackFormServiceImpl(DAOFactory daoFactory, ConnectionFactory connectionFactory) {
        this.daoFactory = daoFactory;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public boolean save(FeedbackForm feedbackForm) {
        boolean actionSuccess = false;
        try (DAOConnection connection = connectionFactory.getConnection()) {

            FeedbackFormDAO feedbackFormDAO = daoFactory.getFeedbackFormDAO(connection);

            LOGGER.info(String.format("Saving feedbackForm by initiator, id: %d", feedbackForm.getInitiator().getId()));

            int rowCount = feedbackFormDAO.save(feedbackForm);

            if (rowCount > 0) {
                actionSuccess = true;
            }
        }
        return actionSuccess;
    }

    @Override
    public Optional<FeedbackForm> getById(int id) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            FeedbackFormDAO feedbackFormDAO = daoFactory.getFeedbackFormDAO(connection);

            LOGGER.info(String.format("Getting feedbackForm by id: %d", id));

            return feedbackFormDAO.get(id);
        }
    }


    @Override
    public boolean updateByInitiatorAndStatus(FeedbackForm feedbackForm, String status) {
        boolean actionSuccess = false;
        try (DAOConnection connection = connectionFactory.getConnection()) {

            FeedbackFormDAO feedbackFormDAO = daoFactory.getFeedbackFormDAO(connection);

            LOGGER.info(String.format("Updating feedbackForm, id: %d by initiator, id: %d and status %s",
                    feedbackForm.getId(), feedbackForm.getInitiator().getId(), status));

            int rowCount = feedbackFormDAO.updateByInitiatorAndStatus(feedbackForm, status);

            if (rowCount > 0) {
                actionSuccess = true;
            }
        }
        return actionSuccess;
    }

    @Override
    public boolean updateByReviewerAndStatus(FeedbackForm feedbackForm, String status) {
        boolean actionSuccess = false;
        try (DAOConnection connection = connectionFactory.getConnection()) {

            FeedbackFormDAO feedbackFormDAO = daoFactory.getFeedbackFormDAO(connection);

            LOGGER.info(String.format("Updating feedbackForm, id: %d by reviewer, id: %d and status %s",
                    feedbackForm.getId(), feedbackForm.getReviewer().getId(), status));

            int rowCount = feedbackFormDAO.updateByReviewerAndStatus(feedbackForm, status);

            if (rowCount > 0) {
                actionSuccess = true;
            }
        }
        return actionSuccess;
    }

    @Override
    public int getAmount() {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            FeedbackFormDAO feedbackFormDAO = daoFactory.getFeedbackFormDAO(connection);

            LOGGER.info("Getting amount of feedbackForms");

            return feedbackFormDAO.getAmount();
        }
    }

    @Override
    public List<FeedbackForm> getAll() {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            FeedbackFormDAO feedbackFormDAO = daoFactory.getFeedbackFormDAO(connection);

            LOGGER.info("Getting all feedbackForm");

            return feedbackFormDAO.getAll();
        }
    }

    @Override
    public List<FeedbackForm> getAllByInitiatorId(int id, int pageRows, int offset) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            FeedbackFormDAO feedbackFormDAO = daoFactory.getFeedbackFormDAO(connection);

            LOGGER.info(String.format("Getting %d feedbackForm with offset %d, by initiator, id: %d"
                    , pageRows, offset, id));

            return feedbackFormDAO.getAllByInitiatorId(id, pageRows, offset);
        }
    }

    @Override
    public List<FeedbackForm> getAllByReviewerId(int id, int pageRows, int offset) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            FeedbackFormDAO feedbackFormDAO = daoFactory.getFeedbackFormDAO(connection);

            LOGGER.info(String.format("Getting %d feedbackForm with offset %d, by reviewer, id: %d"
                    , pageRows, offset, id));

            return feedbackFormDAO.getAllByReviewerId(id, pageRows, offset);
        }
    }

    @Override
    public List<FeedbackForm> getAllByStatus(String status, int pageRows, int offset) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            FeedbackFormDAO feedbackFormDAO = daoFactory.getFeedbackFormDAO(connection);

            LOGGER.info(String.format("Getting %d feedbackForm with offset %d, by status: %s"
                    , pageRows, offset, status));

            return feedbackFormDAO.getAllByStatus(status, pageRows, offset);
        }
    }

    @Override
    public boolean updateReviewerAndStatus(FeedbackForm feedbackForm) {
        boolean actionSuccess = false;
        try (DAOConnection connection = connectionFactory.getConnection()) {

            FeedbackFormDAO feedbackFormDAO = daoFactory.getFeedbackFormDAO(connection);

            LOGGER.info(String.format("Updating feedbackForm, id: %d. Setting reviewer, id = %s and status = %s"
                    , feedbackForm.getId(), feedbackForm.getReviewer().getId(), feedbackForm.getStatus()));

            int rowCount = feedbackFormDAO.updateReviewerAndStatus(feedbackForm);

            if (rowCount > 0) {
                actionSuccess = true;
            }
        }
        return actionSuccess;
    }

    @Override
    public List<FeedbackForm> getAllByReviewerAndStatus(int id, String status, int pageRows, int offset) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            FeedbackFormDAO feedbackFormDAO = daoFactory.getFeedbackFormDAO(connection);


            LOGGER.info(String.format("Getting %d feedbackForm with offset %d, by reviewer, id: %d and status: %s"
                    , pageRows, offset, id, status));

            return feedbackFormDAO.getAllByReviewerAndStatus(id, status, pageRows, offset);
        }
    }

    @Override
    public boolean isReviewerAssigned(int id) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            FeedbackFormDAO feedbackFormDAO = daoFactory.getFeedbackFormDAO(connection);

            LOGGER.info(String.format("Checking if feedbackForm, id: %d has assigned reviewer", id));

            return feedbackFormDAO.isReviewerAssigned(id);
        }
    }

    @Override
    public Optional<FeedbackForm> getByIdAndInitiator(int feedbackFormId, int initiatorId) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            FeedbackFormDAO feedbackFormDAO = daoFactory.getFeedbackFormDAO(connection);

            LOGGER.info("Getting feedbackForm, id: " + feedbackFormId + " and initiator, id: " + initiatorId);

            return feedbackFormDAO.getByIdAndInitiator(feedbackFormId, initiatorId);
        }
    }

    @Override
    public Optional<FeedbackForm> getByIdAndReviewer(int feedbackFormId, int reviewerId) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            FeedbackFormDAO feedbackFormDAO = daoFactory.getFeedbackFormDAO(connection);

            LOGGER.info("Getting feedbackForm, id: " + feedbackFormId + " and reviewer, id: " + reviewerId);

            return feedbackFormDAO.getByIdAndReviewer(feedbackFormId, reviewerId);
        }
    }
}
