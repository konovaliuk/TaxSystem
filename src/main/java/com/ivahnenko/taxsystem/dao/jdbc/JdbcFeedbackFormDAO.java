package com.ivahnenko.taxsystem.dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.dao.UtilDAO;
import com.ivahnenko.taxsystem.dao.exception.DAOException;
import com.ivahnenko.taxsystem.dao.FeedbackFormDAO;
import com.ivahnenko.taxsystem.model.form.FeedbackForm;

public class JdbcFeedbackFormDAO implements FeedbackFormDAO {

    private static final Logger LOGGER = Logger.getLogger(JdbcFeedbackFormDAO.class);

    private final Connection connection;

    JdbcFeedbackFormDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int save(FeedbackForm feedbackForm) {
        try (PreparedStatement statement = connection.prepareStatement(SQLFeedbackForm.INSERT_NEW.QUERY)) {

            statement.setInt(1, feedbackForm.getTaxpayer().getId());
            statement.setInt(2, feedbackForm.getInitiator().getId());
            statement.setString(3, feedbackForm.getDescription());
            statement.setDate(4, Date.valueOf(feedbackForm.getCreationDate()), Calendar.getInstance());
            statement.setDate(5, Date.valueOf(feedbackForm.getLastModifiedDate()), Calendar.getInstance());
            statement.setString(6, feedbackForm.getStatus().toString());

            return statement.executeUpdate();

        } catch (SQLException e) {
            String exception = String.format("Exception in saving feedbackForm, with initiator, id: %d", feedbackForm.getInitiator().getId());
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
    }

    @Override
    public Optional<FeedbackForm> get(int id) {
        Optional<FeedbackForm> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQLFeedbackForm.GET_BY_ID.QUERY)) {

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                result = Optional.ofNullable(UtilDAO.createFeedbackForm(rs));
            }

        } catch (SQLException e) {
            String exception = String.format("Exception in getting feedbackForm by id: %d", id);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return result;
    }

    @Override
    public Optional<FeedbackForm> getByIdAndInitiator(int feedbackFormId, int initiatorId) {
        Optional<FeedbackForm> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQLFeedbackForm.GET_BY_ID_AND_INITIATOR.QUERY)) {

            statement.setInt(1, feedbackFormId);
            statement.setInt(2, initiatorId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                result = Optional.ofNullable(UtilDAO.createFeedbackForm(rs));
            }

        } catch (SQLException e) {
            String exception = String.format("Exception in getting feedbackForm by id: %d and initiator, id: %d", feedbackFormId, initiatorId);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return result;
    }

    @Override
    public Optional<FeedbackForm> getByIdAndReviewer(int feedbackFormId, int reviewerId) {
        Optional<FeedbackForm> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQLFeedbackForm.GET_BY_ID_AND_REVIEWER.QUERY)) {

            statement.setInt(1, feedbackFormId);
            statement.setInt(2, reviewerId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                result = Optional.ofNullable(UtilDAO.createFeedbackForm(rs));
            }

        } catch (SQLException e) {
            String exception = String.format("Exception in getting feedbackForm by id: %d and reviewer, id: %d", feedbackFormId, reviewerId);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return result;
    }

    @Override
    public int updateByReviewerAndStatus(FeedbackForm feedbackForm, String status) {
        try (PreparedStatement statement = connection.prepareStatement(SQLFeedbackForm.UPDATE_BY_REVIEWER_AND_STATUS.QUERY)) {

            statement.setString(1, feedbackForm.getReviewerComment());
            statement.setString(2, feedbackForm.getStatus().toString());
            statement.setDate(3, Date.valueOf(feedbackForm.getLastModifiedDate()), Calendar.getInstance());
            statement.setInt(4, feedbackForm.getId());
            statement.setInt(5, feedbackForm.getReviewer().getId());
            statement.setString(6, status);

            return statement.executeUpdate();

        } catch (SQLException e) {
            String exception = String.format("Exception in updating feedbackForm, id: %d, by reviewer, id: %d and status %s"
                    , feedbackForm.getId(), feedbackForm.getReviewer().getId(), status);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
    }

    @Override
    public int getAmount() {
        int amount = 0;
        try (PreparedStatement statement = connection.prepareStatement(SQLFeedbackForm.GET_COUNT_ROWS.QUERY)) {

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                amount = rs.getInt(1);
            }

        } catch (SQLException e) {
            String exception = "Exception in getting amount of feedbackForms";
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return amount;
    }

    @Override
    public List<FeedbackForm> getAll() {
        List<FeedbackForm> resultList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLFeedbackForm.GET_ALL.QUERY)) {

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                resultList.add(UtilDAO.createFeedbackForm(rs));
            }

        } catch (SQLException e) {
            String exception = "Exception in getting all feedbackForms";
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return resultList;
    }

    @Override
    public List<FeedbackForm> getAllByInitiatorId(int id, int rows, int offset) {
        List<FeedbackForm> resultList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLFeedbackForm.GET_ALL_BY_INITIATOR_ID.QUERY)) {

            statement.setInt(1, id);
            statement.setInt(2, rows);
            statement.setInt(3, offset);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                resultList.add(UtilDAO.createFeedbackForm(rs));
            }

        } catch (SQLException e) {
            String exception = String.format("Exception in getting %d feedbackForm with offset %d, by initiator, id: %d"
                    , rows, offset, id);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return resultList;
    }

    @Override
    public List<FeedbackForm> getAllByReviewerId(int id, int rows, int offset) {
        List<FeedbackForm> resultList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLFeedbackForm.GET_ALL_BY_REVIEWER_ID.QUERY)) {

            statement.setInt(1, id);
            statement.setInt(2, rows);
            statement.setInt(3, offset);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                resultList.add(UtilDAO.createFeedbackForm(rs));
            }

        } catch (SQLException e) {
            String exception = String.format("Exception in getting %d feedbackForm with offset %d, by reviewer, id: %d"
                    , rows, offset, id);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return resultList;
    }

    @Override
    public List<FeedbackForm> getAllByStatus(String status, int rows, int offset) {
        List<FeedbackForm> resultList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLFeedbackForm.GET_ALL_BY_STATUS.QUERY)) {
            statement.setString(1, status);
            statement.setInt(2, rows);
            statement.setInt(3, offset);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                resultList.add(UtilDAO.createFeedbackForm(rs));
            }

        } catch (SQLException e) {
            String exception = String.format("Exception in getting %d feedbackForm with offset %d, by status: %s"
                    , rows, offset, status);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return resultList;
    }

    @Override
    public boolean isReviewerAssigned(int id) {
        boolean isReviewerAssigned;
        try (PreparedStatement statement = connection.prepareStatement(SQLFeedbackForm.IS_REVIEWER_ASSIGNED.QUERY)) {

            statement.setInt(1, id);

            isReviewerAssigned = statement.executeQuery().next();

        } catch (SQLException e) {
            String exception = String.format("Exception in checking if feedbackForm, id: %d has assigned reviewer", id);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return isReviewerAssigned;
    }


    @Override
    public int updateByInitiatorAndStatus(FeedbackForm feedbackForm, String status) {
        try (PreparedStatement statement = connection.prepareStatement(SQLFeedbackForm.UPDATE_BY_INITIATOR_AND_STATUS.QUERY)) {

            statement.setString(1, feedbackForm.getDescription());
            statement.setString(2, feedbackForm.getStatus().toString());
            statement.setDate(3, Date.valueOf(feedbackForm.getLastModifiedDate()), Calendar.getInstance());
            statement.setInt(4, feedbackForm.getId());
            statement.setInt(5, feedbackForm.getInitiator().getId());
            statement.setString(6, status);

            return statement.executeUpdate();

        } catch (SQLException e) {
            String exception = String.format("Exception in updating feedbackForm by id: %d, initiator, id: %d, status: %s"
                    , feedbackForm.getId(), feedbackForm.getInitiator().getId(), status);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }

    }

    @Override
    public int updateReviewerAndStatus(FeedbackForm feedbackForm) {
        try (PreparedStatement statement = connection.prepareStatement(SQLFeedbackForm.UPDATE_REVIEWER_AND_STATUS.QUERY)) {

            statement.setInt(1, feedbackForm.getReviewer().getId());
            statement.setString(2, feedbackForm.getStatus().toString());
            statement.setDate(3, Date.valueOf(feedbackForm.getLastModifiedDate()), Calendar.getInstance());
            statement.setInt(4, feedbackForm.getId());

            return statement.executeUpdate();

        } catch (SQLException e) {
            String exception = String.format("Exception in updating feedbackForm, id: %d. Setting reviewer, id = %d and status = %s"
                    , feedbackForm.getId(), feedbackForm.getReviewer().getId(), feedbackForm.getStatus());
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
    }

    @Override
    public List<FeedbackForm> getAllByReviewerAndStatus(int id, String status, int rows, int offset) {
        List<FeedbackForm> resultList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLFeedbackForm.GET_ALL_BY__REVIEWER_AND_STATUS.QUERY)) {

            statement.setInt(1, id);
            statement.setInt(2, rows);
            statement.setInt(3, offset);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                resultList.add(UtilDAO.createFeedbackForm(rs));
            }

        } catch (SQLException e) {
            String exception = String.format("Exception in getting %d feedbackForm with offset %d, by reviewer, id: %d and status: %s"
                    , rows, offset, id, status);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return resultList;
    }

    private enum SQLFeedbackForm {

        INSERT_NEW("INSERT INTO feedback_form ("
                + "taxpayer_id, "
                + "initiator_id, "
                + "description, "
                + "creation_date, "
                + "last_modified_date, "
                + "status_id) "
                + "VALUES "
                + "(?,?,?,?,?, "
                + "(SELECT id FROM form_status WHERE code =?))"),
        GET_BY_ID("SELECT "
                + "t.id as t_id, "
                + "t.name as t_name, "
                + "t.email as t_email, "
                + "t.registration_number as t_registration_number, "
                + "ui.id as ui_id, "
                + "ui.name as ui_name, "
                + "ui.surname as ui_surname, "
                + "ui.patronymic as ui_patronymic, "
                + "ui.email as ui_email, "
                + "ur.id as ur_id, "
                + "ur.name as ur_name, "
                + "ur.surname as ur_surname, "
                + "ur.patronymic as ur_patronymic, "
                + "ur.email as ur_email, "
                + "ff.id as ff_id, "
                + "ff.description as ff_description, "
                + "ff.reviewer_comment as ff_reviewer_comment, "
                + "ff.creation_date as ff_creation_date, "
                + "ff.last_modified_date as ff_last_modified_date, "
                + "fs.code as fs_code "
                + "FROM feedback_form as ff "
                + "LEFT JOIN taxpayer as t ON ff.taxpayer_id = t.id "
                + "LEFT JOIN user as ui ON ff.initiator_id = ui.id "
                + "LEFT JOIN user as ur ON ff.reviewer_id = ur.id "
                + "LEFT JOIN form_status as fs ON ff.status_id = fs.id "
                + "WHERE ff.id =?"),
        GET_BY_ID_AND_INITIATOR("SELECT "
                + "t.id as t_id, "
                + "t.name as t_name, "
                + "t.email as t_email, "
                + "t.registration_number as t_registration_number, "
                + "ui.id as ui_id, "
                + "ui.name as ui_name, "
                + "ui.surname as ui_surname, "
                + "ui.patronymic as ui_patronymic, "
                + "ui.email as ui_email, "
                + "ur.id as ur_id, "
                + "ur.name as ur_name, "
                + "ur.surname as ur_surname, "
                + "ur.patronymic as ur_patronymic, "
                + "ur.email as ur_email, "
                + "ff.id as ff_id, "
                + "ff.description as ff_description, "
                + "ff.reviewer_comment as ff_reviewer_comment, "
                + "ff.creation_date as ff_creation_date, "
                + "ff.last_modified_date as ff_last_modified_date, "
                + "fs.code as fs_code "
                + "FROM feedback_form as ff "
                + "LEFT JOIN taxpayer as t ON ff.taxpayer_id = t.id "
                + "LEFT JOIN user as ui ON ff.initiator_id = ui.id "
                + "LEFT JOIN user as ur ON ff.reviewer_id = ur.id "
                + "LEFT JOIN form_status as fs ON ff.status_id = fs.id "
                + "WHERE ff.id =? AND ui.id=?"),
        GET_BY_ID_AND_REVIEWER("SELECT "
                + "t.id as t_id, "
                + "t.name as t_name, "
                + "t.email as t_email, "
                + "t.registration_number as t_registration_number, "
                + "ui.id as ui_id, "
                + "ui.name as ui_name, "
                + "ui.surname as ui_surname, "
                + "ui.patronymic as ui_patronymic, "
                + "ui.email as ui_email, "
                + "ur.id as ur_id, "
                + "ur.name as ur_name, "
                + "ur.surname as ur_surname, "
                + "ur.patronymic as ur_patronymic, "
                + "ur.email as ur_email, "
                + "ff.id as ff_id, "
                + "ff.description as ff_description, "
                + "ff.reviewer_comment as ff_reviewer_comment, "
                + "ff.creation_date as ff_creation_date, "
                + "ff.last_modified_date as ff_last_modified_date, "
                + "fs.code as fs_code "
                + "FROM feedback_form as ff "
                + "LEFT JOIN taxpayer as t ON ff.taxpayer_id = t.id "
                + "LEFT JOIN user as ui ON ff.initiator_id = ui.id "
                + "LEFT JOIN user as ur ON ff.reviewer_id = ur.id "
                + "LEFT JOIN form_status as fs ON ff.status_id = fs.id "
                + "WHERE ff.id =? AND ur.id=?"),
        GET_ALL("SELECT "
                + "t.id as t_id, "
                + "t.name as t_name, "
                + "t.email as t_email, "
                + "t.registration_number as t_registration_number, "
                + "ui.id as ui_id, "
                + "ui.name as ui_name, "
                + "ui.surname as ui_surname, "
                + "ui.patronymic as ui_patronymic, "
                + "ui.email as ui_email, "
                + "ur.id as ur_id, "
                + "ur.name as ur_name, "
                + "ur.surname as ur_surname, "
                + "ur.patronymic as ur_patronymic, "
                + "ur.email as ur_email, "
                + "ff.id as ff_id, "
                + "ff.description as ff_description, "
                + "ff.reviewer_comment as ff_reviewer_comment, "
                + "ff.creation_date as ff_creation_date, "
                + "ff.last_modified_date as ff_last_modified_date, "
                + "fs.code as fs_code "
                + "FROM feedback_form as ff "
                + "LEFT JOIN taxpayer as t ON ff.taxpayer_id = t.id "
                + "LEFT JOIN user as ui ON ff.initiator_id = ui.id "
                + "LEFT JOIN user as ur ON ff.reviewer_id = ur.id "
                + "LEFT JOIN form_status as fs ON ff.status_id = fs.id "),
        GET_ALL_BY_INITIATOR_ID("SELECT "
                + "t.id as t_id, "
                + "t.name as t_name, "
                + "t.email as t_email, "
                + "t.registration_number as t_registration_number, "
                + "ui.id as ui_id, "
                + "ui.name as ui_name, "
                + "ui.surname as ui_surname, "
                + "ui.patronymic as ui_patronymic, "
                + "ui.email as ui_email, "
                + "ur.id as ur_id, "
                + "ur.name as ur_name, "
                + "ur.surname as ur_surname, "
                + "ur.patronymic as ur_patronymic, "
                + "ur.email as ur_email, "
                + "ff.id as ff_id, "
                + "ff.description as ff_description, "
                + "ff.reviewer_comment as ff_reviewer_comment, "
                + "ff.creation_date as ff_creation_date, "
                + "ff.last_modified_date as ff_last_modified_date, "
                + "fs.code as fs_code "
                + "FROM feedback_form as ff "
                + "LEFT JOIN taxpayer as t ON ff.taxpayer_id = t.id "
                + "LEFT JOIN user as ui ON ff.initiator_id = ui.id "
                + "LEFT JOIN user as ur ON ff.reviewer_id = ur.id "
                + "LEFT JOIN form_status as fs ON ff.status_id = fs.id "
                + "WHERE ff.initiator_id =? "
                + "ORDER BY ff.id ASC LIMIT  ?  OFFSET ?"),
        GET_ALL_BY_REVIEWER_ID("SELECT "
                + "t.id as t_id, "
                + "t.name as t_name, "
                + "t.email as t_email, "
                + "t.registration_number as t_registration_number, "
                + "ui.id as ui_id, "
                + "ui.name as ui_name, "
                + "ui.surname as ui_surname, "
                + "ui.patronymic as ui_patronymic, "
                + "ui.email as ui_email, "
                + "ur.id as ur_id, "
                + "ur.name as ur_name, "
                + "ur.surname as ur_surname, "
                + "ur.patronymic as ur_patronymic, "
                + "ur.email as ur_email, "
                + "ff.id as ff_id, "
                + "ff.description as ff_description, "
                + "ff.reviewer_comment as ff_reviewer_comment, "
                + "ff.creation_date as ff_creation_date, "
                + "ff.last_modified_date as ff_last_modified_date, "
                + "fs.code as fs_code "
                + "FROM feedback_form as ff "
                + "LEFT JOIN taxpayer as t ON ff.taxpayer_id = t.id "
                + "LEFT JOIN user as ui ON ff.initiator_id = ui.id "
                + "LEFT JOIN user as ur ON ff.reviewer_id = ur.id "
                + "LEFT JOIN form_status as fs ON ff.status_id = fs.id "
                + "WHERE ff.reviewer_id =? "
                + "ORDER BY ff.id ASC LIMIT ? OFFSET ?"),
        GET_ALL_BY__REVIEWER_AND_STATUS("SELECT "
                + "t.id as t_id, "
                + "t.name as t_name, "
                + "t.email as t_email, "
                + "t.registration_number as t_registration_number, "
                + "ui.id as ui_id, "
                + "ui.name as ui_name, "
                + "ui.surname as ui_surname, "
                + "ui.patronymic as ui_patronymic, "
                + "ui.email as ui_email, "
                + "ur.id as ur_id, "
                + "ur.name as ur_name, "
                + "ur.surname as ur_surname, "
                + "ur.patronymic as ur_patronymic, "
                + "ur.email as ur_email, "
                + "ff.id as ff_id, "
                + "ff.description as ff_description, "
                + "ff.reviewer_comment as ff_reviewer_comment, "
                + "ff.creation_date as ff_creation_date, "
                + "ff.last_modified_date as ff_last_modified_date, "
                + "fs.code as fs_code "
                + "FROM feedback_form as ff "
                + "LEFT JOIN taxpayer as t ON ff.taxpayer_id = t.id "
                + "LEFT JOIN user as ui ON ff.initiator_id = ui.id "
                + "LEFT JOIN user as ur ON ff.reviewer_id = ur.id "
                + "LEFT JOIN form_status as fs ON ff.status_id = fs.id "
                + "WHERE ff.reviewer_id =? AND fs.code=? "
                + "ORDER BY ff.id ASC LIMIT ? OFFSET ?"),
        GET_ALL_BY_STATUS("SELECT "
                + "t.id as t_id, "
                + "t.name as t_name, "
                + "t.email as t_email, "
                + "t.registration_number as t_registration_number, "
                + "ui.id as ui_id, "
                + "ui.name as ui_name, "
                + "ui.surname as ui_surname, "
                + "ui.patronymic as ui_patronymic, "
                + "ui.email as ui_email, "
                + "ur.id as ur_id, "
                + "ur.name as ur_name, "
                + "ur.surname as ur_surname, "
                + "ur.patronymic as ur_patronymic, "
                + "ur.email as ur_email, "
                + "ff.id as ff_id, "
                + "ff.description as ff_description, "
                + "ff.reviewer_comment as ff_reviewer_comment, "
                + "ff.creation_date as ff_creation_date, "
                + "ff.last_modified_date as ff_last_modified_date, "
                + "fs.code as fs_code "
                + "FROM feedback_form as ff "
                + "LEFT JOIN taxpayer as t ON ff.taxpayer_id = t.id "
                + "LEFT JOIN user as ui ON ff.initiator_id = ui.id "
                + "LEFT JOIN user as ur ON ff.reviewer_id = ur.id "
                + "LEFT JOIN form_status as fs ON ff.status_id = fs.id "
                + "WHERE ff.status_id = (SELECT id FROM form_status WHERE code =?) "
                + "ORDER BY ff.id ASC LIMIT ? OFFSET ?"),
        UPDATE_BY_INITIATOR_AND_STATUS("UPDATE feedback_form SET "
                + "description = ?, "
                + "status_id =(SELECT id FROM form_status WHERE code =?), "
                + "last_modified_date =? "
                + "WHERE id =? AND initiator_id =? AND status_id =(SELECT id FROM form_status WHERE code =?)"),
        UPDATE_BY_REVIEWER_AND_STATUS("UPDATE feedback_form SET "
                + "reviewer_comment =?, "
                + "status_id =(SELECT id FROM form_status WHERE code =?), "
                + "last_modified_date =? "
                + "WHERE id =? AND reviewer_id =? AND status_id =(SELECT id FROM form_status WHERE code =?)"),
        UPDATE_REVIEWER_AND_STATUS("UPDATE feedback_form SET "
                + "reviewer_id =?, "
                + "status_id =(SELECT id FROM form_status WHERE code =?), "
                + "last_modified_date =? "
                + "WHERE id =?"),
        UPDATE_STATUS_ID_BY_ID("UPDATE feedback_form SET status_id = (SELECT id FROM form_status WHERE code =?"),
        DELETE_BY_ID("DELETE FROM feedback_form WHERE id =?"),
        GET_COUNT_ROWS("SELECT COUNT(*) FROM feedback_form"),
        IS_REVIEWER_ASSIGNED("SELECT reviewer_id FROM feedback_form WHERE id =?");

        private final String QUERY;

        SQLFeedbackForm(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}

