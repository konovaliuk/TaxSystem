package com.ivahnenko.taxsystem.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.dao.UtilDAO;
import com.ivahnenko.taxsystem.dao.exception.DAOException;
import com.ivahnenko.taxsystem.dao.TaxpayerDAO;
import com.ivahnenko.taxsystem.model.Taxpayer;

public class JdbcTaxpayerDAO implements TaxpayerDAO {

    private static final Logger LOGGER = Logger.getLogger(JdbcTaxpayerDAO.class);

    private final Connection connection;

    JdbcTaxpayerDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int save(Taxpayer taxpayer) {
        try (PreparedStatement statement = connection.prepareStatement(SQLTaxpayer.INSERT_NEW.QUERY)) {

            statement.setString(1, taxpayer.getName());
            statement.setString(2, taxpayer.getRegistrationNumber());
            statement.setString(3, taxpayer.getEmail());
            statement.setString(4, taxpayer.getPostcode());
            statement.setString(5, taxpayer.getOwnershipType().toString());
            statement.setInt(6, taxpayer.getEmployee().getId());

            return statement.executeUpdate();

        } catch (SQLException e) {
            String exception = String.format("Exception in saving taxpayer by user, id: %s", taxpayer.getEmployee().getId());
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
    }

    @Override
    public int update(Taxpayer taxpayer) {
        try (PreparedStatement statement = connection.prepareStatement(SQLTaxpayer.INSERT_NEW.QUERY)) {

            statement.setString(1, taxpayer.getName());
            statement.setString(2, taxpayer.getRegistrationNumber());
            statement.setString(3, taxpayer.getEmail());
            statement.setString(4, taxpayer.getPostcode());
            statement.setString(5, taxpayer.getOwnershipType().toString());
            statement.setInt(6, taxpayer.getEmployee().getId());
            statement.setInt(7, taxpayer.getId());

            return statement.executeUpdate();

        } catch (SQLException e) {
            String exception = String.format("Exception in updating taxpayer by id: %d", taxpayer.getId());
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
    }

    @Override
    public Optional<Taxpayer> get(int id) {
        Optional<Taxpayer> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQLTaxpayer.GET_BY_ID.QUERY)) {

            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                result = Optional.ofNullable(UtilDAO.createTaxpayer(rs));
            }

        } catch (SQLException e) {
            String exception = String.format("Exception in getting taxpayer by id: %d", id);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return result;
    }

    @Override
    public List<Taxpayer> getAll() {
        List<Taxpayer> resultList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLTaxpayer.GET_ALL.QUERY)) {

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                resultList.add(UtilDAO.createTaxpayer(rs));
            }

        } catch (SQLException e) {
            String exception = "Exception in getting all taxpayers";
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return resultList;
    }

    @Override
    public Optional<Taxpayer> getByEmployeeId(int id) {
        Optional<Taxpayer> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQLTaxpayer.GET_BY_EMPLOYEE_ID.QUERY)) {

            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                result = Optional.ofNullable(UtilDAO.createTaxpayer(rs));
            }

        } catch (SQLException e) {
            String exception = String.format("Exception in getting taxpayers by employee, id: %d", id);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return result;
    }

    @Override
    public int getAmount() {
        int amount = 0;
        try (PreparedStatement statement = connection.prepareStatement(SQLTaxpayer.GET_COUNT_ROWS.QUERY)) {

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                amount = rs.getInt(1);
            }

        } catch (SQLException e) {
            String exception = "Exception in getting taxpayer amount";
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return amount;
    }

    @Override
    public boolean isEmailExists(String email) {
        boolean isEmailExists;
        try (PreparedStatement statement = connection.prepareStatement(SQLTaxpayer.IS_EMAIL_EXISTS.QUERY)) {

            statement.setString(1, email);

            isEmailExists = statement.executeQuery().next();

        } catch (SQLException e) {
            String exception = String.format("Exception in checking if email: %s exists", email);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }

        return isEmailExists;
    }

    @Override
    public boolean isRegNumberExists(String registrationNumber) {
        boolean isEmailExists;
        try (PreparedStatement statement = connection.prepareStatement(SQLTaxpayer.IS_REGISTRATION_NUMBER_EXISTS.QUERY)) {

            statement.setString(1, registrationNumber);

            isEmailExists = statement.executeQuery().next();

        } catch (SQLException e) {
            String exception = String.format("Exception in checking if registrationNumber: %s exists", registrationNumber);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return isEmailExists;
    }

    private enum SQLTaxpayer {
        INSERT_NEW("INSERT INTO taxpayer ("
                + "name, "
                + "registration_number, "
                + "email, "
                + "postcode, "
                + "ownership_type_id, "
                + "employee_id) "
                + "VALUES (?,?,?,?,(SELECT id FROM ownership_type WHERE code =?), ?)"),
        GET_BY_ID("SELECT "
                + "t.id as t_id, "
                + "t.name as t_name, "
                + "t.registration_number as t_registration_number, "
                + "t.email as t_email,t.postcode as t_postcode, "
                + "t.employee_id as t_employee_id, "
                + "ot.code as ot_code, "
                + "u.id as u_id, "
                + "u.type_id as u_type_id, "
                + "u.name as u_name, "
                + "u.surname as u_surname, "
                + "u.patronymic as u_patronymic, "
                + "u.email as u_email, "
                + "u.username as u_username, "
                + "u.password as u_password, "
                + "u_t.code as u_t_code "
                + "FROM taxpayer as t "
                + "LEFT JOIN user as u ON u.id = t.employee_id "
                + "LEFT JOIN user_type as u_t ON u_t.id = u.type_id "
                + "LEFT JOIN ownership_type as ot ON t.ownership_type_id = ot.id "
                + "WHERE t.id =?"),
        GET_BY_EMPLOYEE_ID("SELECT "
                + "t.id as t_id, "
                + "t.name as t_name, "
                + "t.registration_number as t_registration_number, "
                + "t.email as t_email,t.postcode as t_postcode, "
                + "t.employee_id as t_employee_id, "
                + "ot.code as ot_code, "
                + "u.id as u_id, "
                + "u.type_id as u_type_id, "
                + "u.name as u_name, "
                + "u.surname as u_surname, "
                + "u.patronymic as u_patronymic, "
                + "u.email as u_email, "
                + "u.username as u_username, "
                + "u.password as u_password, "
                + "u_t.code as u_t_code "
                + "FROM taxpayer as t "
                + "LEFT JOIN user as u ON u.id = t.employee_id "
                + "LEFT JOIN user_type as u_t ON u_t.id = u.type_id "
                + "LEFT JOIN ownership_type as ot ON t.ownership_type_id = ot.id "
                + "WHERE t.employee_id =?"),
        GET_ALL("SELECT "
                + "t.id as t_id, "
                + "t.name as t_name, "
                + "t.registration_number as t_registration_number, "
                + "t.email as t_email, "
                + "t.postcode as t_postcode, "
                + "t.employee_id as t_employee_id, "
                + "ot.code as ot_code, "
                + "u.id as u_id, "
                + "u.type_id as u_type_id, "
                + "u.name as u_name, "
                + "u.surname as u_surname, "
                + "u.patronymic as u_patronymic, "
                + "u.email as u_email, "
                + "u.username as u_username, "
                + "u.password as u_password, "
                + "u_t.code as u_t_code "
                + "FROM taxpayer as t "
                + "LEFT JOIN user as u ON u.id = t.employee_id "
                + "LEFT JOIN user_type as u_t ON u_t.id = u.type_id "
                + "LEFT JOIN ownership_type as ot ON t.ownership_type_id = ot.id "),
        IS_EMAIL_EXISTS("SELECT id FROM taxpayer WHERE email =?"),
        IS_REGISTRATION_NUMBER_EXISTS("SELECT id FROM taxpayer WHERE registration_number =?"),
        GET_COUNT_ROWS("SELECT COUNT(*) FROM taxpayer");

        private final String QUERY;

        SQLTaxpayer(String QUERY) {
            this.QUERY = QUERY;
        }
    }

}
