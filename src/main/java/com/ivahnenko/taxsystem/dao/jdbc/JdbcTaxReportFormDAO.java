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
import com.ivahnenko.taxsystem.dao.TaxReportFormDAO;
import com.ivahnenko.taxsystem.model.form.TaxReportForm;

public class JdbcTaxReportFormDAO implements TaxReportFormDAO {

    private static final Logger LOGGER = Logger.getLogger(JdbcTaxReportFormDAO.class);

    private final Connection connection;

    JdbcTaxReportFormDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int save(TaxReportForm taxReportForm) {
        try (PreparedStatement statement = connection.prepareStatement(SQLTaxReportForm.INSERT_NEW.QUERY)) {

            statement.setByte(1, taxReportForm.getQuarter());
            statement.setShort(2, taxReportForm.getYear());
            statement.setInt(3, taxReportForm.getTaxpayer().getId());
            statement.setInt(4, taxReportForm.getInitiator().getId());
            statement.setString(5, taxReportForm.getReviewerComment());
            statement.setDouble(6, taxReportForm.getMainActivityIncome());
            statement.setDouble(7, taxReportForm.getInvestmentIncome());
            statement.setDouble(8, taxReportForm.getPropertyIncome());
            statement.setDouble(9, taxReportForm.getMainActivityExpenses());
            statement.setDouble(10, taxReportForm.getInvestmentExpenses());
            statement.setDouble(11, taxReportForm.getPropertyExpenses());
            statement.setDate(12, Date.valueOf(taxReportForm.getCreationDate()), Calendar.getInstance());
            statement.setDate(13, Date.valueOf(taxReportForm.getLastModifiedDate()), Calendar.getInstance());
            statement.setString(14, taxReportForm.getStatus().toString());

            return statement.executeUpdate();

        } catch (SQLException e) {
            String exception = String.format("Exception in saving taxReportForm, with initiator, id: %d", taxReportForm.getInitiator().getId());
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
    }

    @Override
    public Optional<TaxReportForm> get(int id) {
        Optional<TaxReportForm> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQLTaxReportForm.GET_BY_ID.QUERY)) {

            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                result = Optional.ofNullable(UtilDAO.createTaxReportForm(rs));
            }

        } catch (SQLException e) {
            String exception = String.format("Exception in getting taxReportForm by id: %d", id);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return result;
    }

    @Override
    public Optional<TaxReportForm> getByIdAndInitiator(int taxReportFormId, int initiatorId) {
        Optional<TaxReportForm> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQLTaxReportForm.GET_BY_ID_AND_INITIATOR.QUERY)) {

            statement.setInt(1, taxReportFormId);
            statement.setInt(2, initiatorId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                result = Optional.ofNullable(UtilDAO.createTaxReportForm(rs));
            }

        } catch (SQLException e) {
            String exception = String.format("Exception in getting taxReportForm by id: %d and initiator, id: %d", taxReportFormId, initiatorId);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return result;
    }

    @Override
    public Optional<TaxReportForm> getByIdAndReviewer(int taxReportFormId, int reviewerId) {
        Optional<TaxReportForm> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQLTaxReportForm.GET_BY_ID_AND_REVIEWER.QUERY)) {

            statement.setInt(1, taxReportFormId);
            statement.setInt(2, reviewerId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                result = Optional.ofNullable(UtilDAO.createTaxReportForm(rs));
            }

        } catch (SQLException e) {
            String exception = String.format("Exception in getting taxReportForm by id: %d and reviewer, id: %d", taxReportFormId, reviewerId);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }

        return result;
    }

    @Override
    public int getAmount() {
        int amount = 0;
        try (PreparedStatement statement = connection.prepareStatement(SQLTaxReportForm.GET_COUNT_ROWS.QUERY)) {

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                amount = rs.getInt(1);
            }

        } catch (SQLException e) {
            String exception = "Exception in getting amount of taxReportForms";
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return amount;
    }

    @Override
    public List<TaxReportForm> getAll() {
        List<TaxReportForm> resultList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLTaxReportForm.GET_ALL.QUERY)) {

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                resultList.add(UtilDAO.createTaxReportForm(rs));
            }

        } catch (SQLException e) {
            String exception = "Exception in getting all taxReportForms";
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return resultList;
    }

    @Override
    public int updateByReviewerAndStatus(TaxReportForm taxReportForm, String status) {
        try (PreparedStatement statement = connection.prepareStatement(SQLTaxReportForm.UPDATE_BY_REVIEWER_AND_STATUS.QUERY)) {

            statement.setString(1, taxReportForm.getReviewerComment());
            statement.setDate(2, Date.valueOf(taxReportForm.getLastModifiedDate()), Calendar.getInstance());
            statement.setString(3, taxReportForm.getStatus().toString());
            statement.setInt(4, taxReportForm.getId());
            statement.setInt(5, taxReportForm.getReviewer().getId());
            statement.setString(6, status);

            return statement.executeUpdate();

        } catch (SQLException e) {
            String exception = String.format("Exception in updating taxReportForm, id: %d, by reviewer, id: %d and status %s"
                    , taxReportForm.getId(), taxReportForm.getReviewer().getId(), status);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
    }

    @Override
    public int updateReviewerAndStatus(TaxReportForm taxReportForm) {
        try (PreparedStatement statement = connection.prepareStatement(SQLTaxReportForm.UPDATE_REVIEWER_AND_STATUS.QUERY)) {

            statement.setInt(1, taxReportForm.getReviewer().getId());
            statement.setString(2, taxReportForm.getStatus().toString());
            statement.setDate(3, Date.valueOf(taxReportForm.getLastModifiedDate()), Calendar.getInstance());
            statement.setInt(4, taxReportForm.getId());

            return statement.executeUpdate();

        } catch (SQLException e) {
            String exception = String.format("Exception in updating taxReportForm, id: %d. Setting reviewer, id = %d and status = %s"
                    , taxReportForm.getId(), taxReportForm.getReviewer().getId(), taxReportForm.getStatus());
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
    }

    @Override
    public List<TaxReportForm> getAllByInitiatorId(int id, int rows, int offset) {
        List<TaxReportForm> resultList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(String.format(SQLTaxReportForm.GET_ALL_BY_INITIATOR_ID.QUERY, offset))) {

            statement.setInt(1, id);
            statement.setInt(2, rows);
            statement.setInt(3, offset);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                resultList.add(UtilDAO.createTaxReportForm(rs));
            }

        } catch (SQLException e) {
            String exception = String.format("Exception in getting %d taxReportForm with offset %d, by initiator, id: %d"
                    , rows, offset, id);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return resultList;
    }

    @Override
    public List<TaxReportForm> getAllByReviewerId(int id, int rows, int offset) {
        List<TaxReportForm> resultList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLTaxReportForm.GET_ALL_BY_REVIEWER_ID.QUERY)) {

            statement.setInt(1, id);
            statement.setInt(2, rows);
            statement.setInt(3, offset);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                resultList.add(UtilDAO.createTaxReportForm(rs));
            }

        } catch (SQLException e) {
            String exception = String.format("Exception in getting %d taxReportForm with offset %d, by reviewer, id: %d"
                    , rows, offset, id);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }

        return resultList;
    }

    @Override
    public List<TaxReportForm> getAllByReviewerAndStatus(int id, String status, int rows, int offset) {
        List<TaxReportForm> resultList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLTaxReportForm.GET_ALL_BY_REVIEWER_AND_STATUS.QUERY)) {

            statement.setInt(1, id);
            statement.setString(2, status);
            statement.setInt(3, rows);
            statement.setInt(4, offset);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                resultList.add(UtilDAO.createTaxReportForm(rs));
            }

        } catch (SQLException e) {
            String exception = String.format("Exception in getting %d taxReportForm with offset %d, by reviewer, id: %d and status: %s"
                    , rows, offset, id, status);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return resultList;
    }

    @Override
    public List<TaxReportForm> getAllByStatus(String status, int rows, int offset) {
        List<TaxReportForm> resultList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLTaxReportForm.GET_ALL_BY_STATUS.QUERY)) {

            statement.setString(1, status);
            statement.setInt(2, rows);
            statement.setInt(3, offset);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                resultList.add(UtilDAO.createTaxReportForm(rs));
            }

        } catch (SQLException e) {
            String exception = String.format("Exception in getting %d taxReportForm with offset %d, by status: %s"
                    , rows, offset, status);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return resultList;
    }

    @Override
    public boolean isReviewerAssigned(int id) {
        boolean isReviewerAssigned;
        try (PreparedStatement statement = connection.prepareStatement(SQLTaxReportForm.IS_REVIEWER_ASSIGNED.QUERY)) {

            statement.setInt(1, id);

            isReviewerAssigned = statement.executeQuery().next();

        } catch (SQLException e) {
            String exception = String.format("Exception in checking if taxReportForm, id: %d has assigned reviewer", id);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return isReviewerAssigned;
    }

    @Override
    public int updateByInitiatorAndStatus(TaxReportForm taxReportForm, String status) {
        try (PreparedStatement statement = connection.prepareStatement(SQLTaxReportForm.UPDATE_BY_INITIATOR_AND_STATUS.QUERY)) {

            statement.setByte(1, taxReportForm.getQuarter());
            statement.setShort(2, taxReportForm.getYear());
            statement.setDouble(3, taxReportForm.getMainActivityIncome());
            statement.setDouble(4, taxReportForm.getInvestmentIncome());
            statement.setDouble(5, taxReportForm.getPropertyIncome());
            statement.setDouble(6, taxReportForm.getMainActivityExpenses());
            statement.setDouble(7, taxReportForm.getInvestmentExpenses());
            statement.setDouble(8, taxReportForm.getPropertyExpenses());
            statement.setDate(9, Date.valueOf(taxReportForm.getLastModifiedDate()), Calendar.getInstance());
            statement.setString(10, taxReportForm.getStatus().toString());
            statement.setInt(11, taxReportForm.getId());
            statement.setInt(12, taxReportForm.getInitiator().getId());
            statement.setString(13, status);

            return statement.executeUpdate();

        } catch (SQLException e) {
            String exception = String.format("Exception in updating taxReportForm by id: %d, initiator, id: %d, status: %s"
                    , taxReportForm.getId(), taxReportForm.getInitiator().getId(), status);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
    }

    private enum SQLTaxReportForm {

        INSERT_NEW("INSERT INTO tax_report_form ("
                + "quarter, "
                + "year, "
                + "taxpayer_id, "
                + "initiator_id, "
                + "reviewer_comment, "
                + "main_activity_income, "
                + "investment_income, "
                + "property_income, "
                + "main_activity_expenses, "
                + "investment_expenses, "
                + "property_expenses, "
                + "creation_date, "
                + "last_modified_date, "
                + "status_id) "
                + "VALUES "
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?, "
                + "(SELECT id FROM form_status WHERE code =?)) "),
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
                + "tf.id as tf_id, "
                + "tf.quarter as tf_quarter, "
                + "tf.year as tf_year, "
                + "tf.reviewer_comment as tf_reviewer_comment, "
                + "tf.main_activity_income as tf_main_activity_income, "
                + "tf.investment_income as tf_investment_income, "
                + "tf.property_income as tf_property_income, "
                + "tf.main_activity_expenses as tf_main_activity_expenses, "
                + "tf.investment_expenses as tf_investment_expenses, "
                + "tf.property_expenses as tf_property_expenses, "
                + "tf.creation_date as tf_creation_date, "
                + "tf.last_modified_date as tf_last_modified_date, "
                + "fs.code as fs_code  "
                + "FROM tax_report_form as tf "
                + "LEFT JOIN taxpayer as t ON tf.taxpayer_id = t.id "
                + "LEFT JOIN user as ui ON tf.initiator_id = ui.id "
                + "LEFT JOIN user as ur ON tf.reviewer_id = ur.id "
                + "LEFT JOIN form_status as fs ON tf.status_id = fs.id "
                + "WHERE tf.id =?"),
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
                + "tf.id as tf_id, "
                + "tf.quarter as tf_quarter, "
                + "tf.year as tf_year, "
                + "tf.reviewer_comment as tf_reviewer_comment, "
                + "tf.main_activity_income as tf_main_activity_income, "
                + "tf.investment_income as tf_investment_income, "
                + "tf.property_income as tf_property_income, "
                + "tf.main_activity_expenses as tf_main_activity_expenses, "
                + "tf.investment_expenses as tf_investment_expenses, "
                + "tf.property_expenses as tf_property_expenses, "
                + "tf.creation_date as tf_creation_date, "
                + "tf.last_modified_date as tf_last_modified_date, "
                + "fs.code as fs_code  "
                + "FROM tax_report_form as tf "
                + "LEFT JOIN taxpayer as t ON tf.taxpayer_id = t.id "
                + "LEFT JOIN user as ui ON tf.initiator_id = ui.id "
                + "LEFT JOIN user as ur ON tf.reviewer_id = ur.id "
                + "LEFT JOIN form_status as fs ON tf.status_id = fs.id "
                + "WHERE tf.id =? AND ui.id =? "),
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
                + "tf.id as tf_id, "
                + "tf.quarter as tf_quarter, "
                + "tf.year as tf_year, "
                + "tf.reviewer_comment as tf_reviewer_comment, "
                + "tf.main_activity_income as tf_main_activity_income, "
                + "tf.investment_income as tf_investment_income, "
                + "tf.property_income as tf_property_income, "
                + "tf.main_activity_expenses as tf_main_activity_expenses, "
                + "tf.investment_expenses as tf_investment_expenses, "
                + "tf.property_expenses as tf_property_expenses, "
                + "tf.creation_date as tf_creation_date, "
                + "tf.last_modified_date as tf_last_modified_date, "
                + "fs.code as fs_code  "
                + "FROM tax_report_form as tf "
                + "LEFT JOIN taxpayer as t ON tf.taxpayer_id = t.id "
                + "LEFT JOIN user as ui ON tf.initiator_id = ui.id "
                + "LEFT JOIN user as ur ON tf.reviewer_id = ur.id "
                + "LEFT JOIN form_status as fs ON tf.status_id = fs.id "
                + "WHERE tf.id =? AND ur.id =? "),
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
                + "tf.id as tf_id, "
                + "tf.quarter as tf_quarter, "
                + "tf.year as tf_year, "
                + "tf.reviewer_comment as tf_reviewer_comment, "
                + "tf.main_activity_income as tf_main_activity_income, "
                + "tf.investment_income as tf_investment_income, "
                + "tf.property_income as tf_property_income, "
                + "tf.main_activity_expenses as tf_main_activity_expenses, "
                + "tf.investment_expenses as tf_investment_expenses, "
                + "tf.property_expenses as tf_property_expenses, "
                + "tf.creation_date as tf_creation_date, "
                + "tf.last_modified_date as tf_last_modified_date, "
                + "fs.code as fs_code  "
                + "FROM tax_report_form as tf "
                + "LEFT JOIN taxpayer as t ON tf.taxpayer_id = t.id "
                + "LEFT JOIN user as ui ON tf.initiator_id = ui.id "
                + "LEFT JOIN user as ur ON tf.reviewer_id = ur.id "
                + "LEFT JOIN form_status as fs ON tf.status_id = fs.id "),
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
                + "tf.id as tf_id, "
                + "tf.quarter as tf_quarter, "
                + "tf.year as tf_year, "
                + "tf.reviewer_comment as tf_reviewer_comment, "
                + "tf.main_activity_income as tf_main_activity_income, "
                + "tf.investment_income as tf_investment_income, "
                + "tf.property_income as tf_property_income, "
                + "tf.main_activity_expenses as tf_main_activity_expenses, "
                + "tf.investment_expenses as tf_investment_expenses, "
                + "tf.property_expenses as tf_property_expenses, "
                + "tf.creation_date as tf_creation_date, "
                + "tf.last_modified_date as tf_last_modified_date, "
                + "fs.code as fs_code  "
                + "FROM tax_report_form as tf "
                + "LEFT JOIN taxpayer as t ON tf.taxpayer_id = t.id "
                + "LEFT JOIN user as ui ON tf.initiator_id = ui.id "
                + "LEFT JOIN user as ur ON tf.reviewer_id = ur.id "
                + "LEFT JOIN form_status as fs ON tf.status_id = fs.id "
                + "WHERE tf.initiator_id =? "
                + "ORDER BY tf.id ASC LIMIT ? OFFSET ?"),
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
                + "tf.id as tf_id, "
                + "tf.quarter as tf_quarter, "
                + "tf.year as tf_year, "
                + "tf.reviewer_comment as tf_reviewer_comment, "
                + "tf.main_activity_income as tf_main_activity_income, "
                + "tf.investment_income as tf_investment_income, "
                + "tf.property_income as tf_property_income, "
                + "tf.main_activity_expenses as tf_main_activity_expenses, "
                + "tf.investment_expenses as tf_investment_expenses, "
                + "tf.property_expenses as tf_property_expenses, "
                + "tf.creation_date as tf_creation_date, "
                + "tf.last_modified_date as tf_last_modified_date, "
                + "fs.code as fs_code  "
                + "FROM tax_report_form as tf "
                + "LEFT JOIN taxpayer as t ON tf.taxpayer_id = t.id "
                + "LEFT JOIN user as ui ON tf.initiator_id = ui.id "
                + "LEFT JOIN user as ur ON tf.reviewer_id = ur.id "
                + "LEFT JOIN form_status as fs ON tf.status_id = fs.id "
                + "WHERE tf.reviewer_id =? "
                + "ORDER BY tf.id ASC LIMIT ? OFFSET ?"),
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
                + "tf.id as tf_id, "
                + "tf.quarter as tf_quarter, "
                + "tf.year as tf_year, "
                + "tf.reviewer_comment as tf_reviewer_comment, "
                + "tf.main_activity_income as tf_main_activity_income, "
                + "tf.investment_income as tf_investment_income, "
                + "tf.property_income as tf_property_income, "
                + "tf.main_activity_expenses as tf_main_activity_expenses, "
                + "tf.investment_expenses as tf_investment_expenses, "
                + "tf.property_expenses as tf_property_expenses, "
                + "tf.creation_date as tf_creation_date, "
                + "tf.last_modified_date as tf_last_modified_date, "
                + "fs.code as fs_code  "
                + "FROM tax_report_form as tf "
                + "LEFT JOIN taxpayer as t ON tf.taxpayer_id = t.id "
                + "LEFT JOIN user as ui ON tf.initiator_id = ui.id "
                + "LEFT JOIN user as ur ON tf.reviewer_id = ur.id "
                + "LEFT JOIN form_status as fs ON tf.status_id = fs.id "
                + "WHERE status_id =(SELECT id FROM form_status WHERE code=?) "
                + "ORDER BY tf.id ASC LIMIT ? OFFSET ?"),
        GET_ALL_BY_REVIEWER_AND_STATUS("SELECT "
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
                + "tf.id as tf_id, "
                + "tf.quarter as tf_quarter, "
                + "tf.year as tf_year, "
                + "tf.reviewer_comment as tf_reviewer_comment, "
                + "tf.main_activity_income as tf_main_activity_income, "
                + "tf.investment_income as tf_investment_income, "
                + "tf.property_income as tf_property_income, "
                + "tf.main_activity_expenses as tf_main_activity_expenses, "
                + "tf.investment_expenses as tf_investment_expenses, "
                + "tf.property_expenses as tf_property_expenses, "
                + "tf.creation_date as tf_creation_date, "
                + "tf.last_modified_date as tf_last_modified_date, "
                + "fs.code as fs_code  "
                + "FROM tax_report_form as tf "
                + "LEFT JOIN taxpayer as t ON tf.taxpayer_id = t.id "
                + "LEFT JOIN user as ui ON tf.initiator_id = ui.id "
                + "LEFT JOIN user as ur ON tf.reviewer_id = ur.id "
                + "LEFT JOIN form_status as fs ON tf.status_id = fs.id "
                + "WHERE tf.reviewer_id =? AND tf.status_id =(SELECT id FROM form_status WHERE code=?) "
                + "ORDER BY tf.id ASC LIMIT ? OFFSET ?"),
        UPDATE_BY_INITIATOR_AND_STATUS("UPDATE tax_report_form SET "
                + "quarter =?, "
                + "year =?, "
                + "main_activity_income =?, "
                + "investment_income =?, "
                + "property_income =?, "
                + "main_activity_expenses =?, "
                + "investment_expenses =?, "
                + "property_expenses = ?, "
                + "last_modified_date =?, "
                + "status_id =(SELECT id FROM form_status WHERE code =?) "
                + "WHERE id =? AND initiator_id =? AND status_id =(SELECT id FROM form_status WHERE code =?)"),
        UPDATE_BY_REVIEWER_AND_STATUS("UPDATE tax_report_form SET "
                + "reviewer_comment = ?, "
                + "last_modified_date =?, "
                + "status_id = (SELECT id FROM form_status WHERE code =?) "
                + "WHERE  id =? and reviewer_id =? and status_id =(SELECT id FROM form_status WHERE code =?)"),
        UPDATE_STATUS_ID_BY_ID("UPDATE tax_report_form SET "
                + "status_id = (SELECT id FROM form_status WHERE code =?), "
                + "last_modified_date =? "
                + "WHERE id =? AND reviewer_id =? AND status_id =(SELECT id FROM form_status WHERE code =?)"),
        UPDATE_REVIEWER_AND_STATUS("UPDATE tax_report_form SET "
                + "reviewer_id =?, "
                + "status_id = (SELECT id FROM form_status WHERE code =?), "
                + "last_modified_date =? "
                + "WHERE id =?"),
        GET_COUNT_ROWS("SELECT COUNT(*) FROM tax_report_form"),
        IS_REVIEWER_ASSIGNED("SELECT id FROM user WHERE id = (SELECT reviewer_id FROM tax_report_form WHERE id =?)");

        private final String QUERY;

        SQLTaxReportForm(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}

