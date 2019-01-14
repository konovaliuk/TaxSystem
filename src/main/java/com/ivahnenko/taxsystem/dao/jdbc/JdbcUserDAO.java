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
import com.ivahnenko.taxsystem.dao.UserDAO;
import com.ivahnenko.taxsystem.model.User;


public class JdbcUserDAO implements UserDAO {
    private static final Logger LOGGER = Logger.getLogger(JdbcUserDAO.class);

    private final Connection connection;

    JdbcUserDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int save(User user) {
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.INSERT_NEW.QUERY)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPatronymic());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getUsername());
            statement.setString(6, UtilDAO.getEncryptedPassword(user.getPassword()));
            statement.setString(7, user.getType().toString());

            return statement.executeUpdate();

        } catch (SQLException e) {
            String exception = String.format("Exception in saving user with username: %s", user.getUsername());
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
    }

    public Optional<User> get(int id) {
        Optional<User> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.GET_BY_ID.QUERY)) {

            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                result = Optional.ofNullable(UtilDAO.createUser(rs));
            }

        } catch (SQLException e) {
            String exception = String.format("Exception in getting user by id: %d", id);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return result;
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.GET_ALL.QUERY)) {

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                userList.add(UtilDAO.createUser(rs));
            }

        } catch (SQLException e) {
            String exception = "Exception in getting all users";
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return userList;
    }

    @Override
    public int getAmount() {
        int amount = 0;
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.GET_COUNT_ROWS.QUERY)) {

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                amount = rs.getInt(1);
            }

        } catch (SQLException e) {
            String exception = "Exception in getting user amount";
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return amount;
    }

    @Override
    public Optional<User> getByUsername(String username) {
        Optional<User> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.GET_BY_USERNAME.QUERY)) {

            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                result = Optional.ofNullable(UtilDAO.createUser(rs));
            }

        } catch (SQLException e) {
            String exception = String.format("Exception in getting user by username: %s", username);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return result;
    }

    @Override
    public List<User> getAllByType(String type) {
        List<User> userList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.GET_ALL_BY_TYPE.QUERY)) {

            statement.setString(1, type);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                userList.add(UtilDAO.createUser(rs));
            }

        } catch (SQLException e) {
            String exception = String.format("Exception in getting all users by type: %s", type);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return userList;
    }

    @Override
    public int changeType(User user) {
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.UPDATE_USER_TYPE.QUERY)) {

            statement.setString(1, user.getType().toString());
            statement.setInt(2, user.getId());

            return statement.executeUpdate();

        } catch (SQLException e) {
            String exception = String.format("Exception in updating user, id: %d. Setting userType: %s", user.getId(), user.getType());
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
    }

    @Override
    public boolean isEmailExists(String email) {
        boolean isEmailExists;
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.IS_EMAIL_EXISTS.QUERY)) {

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
    public boolean isUsernameExists(String username) {
        boolean isLoginExists;
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.IS_USERNAME_EXISTS.QUERY)) {

            statement.setString(1, username);

            isLoginExists = statement.executeQuery().next();

        } catch (SQLException e) {
            String exception = String.format("Exception in checking if username: %s exists", username);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return isLoginExists;
    }

    @Override
    public boolean isUserHasTaxpayer(User user) {
        boolean isUserHasTaxpayer;
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.IS_USER_HAS_TAXPAYER.QUERY)) {

            statement.setInt(1, user.getId());

            isUserHasTaxpayer = statement.executeQuery().next();

        } catch (SQLException e) {
            String exception = String.format("Exception in checking if user, id: %d has taxpayer", user.getId());
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return isUserHasTaxpayer;
    }

    @Override
    public Optional<String> getPassword(String username) {
        Optional<String> optionalPassword = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.GET_USER_PASSWORD.QUERY)) {

            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                optionalPassword = Optional.of(rs.getString("password"));
            }

        } catch (SQLException e) {
            String exception = String.format("Exception in getting user's, username: %s password ", username);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
        return optionalPassword;
    }

    @Override
    public int changeDeleted(User user) {
        try (PreparedStatement statement = connection.prepareStatement(SQLUser.UPDATE_DELETED.QUERY)) {

            statement.setInt(1, user.isDeleted() ? 1 : 0);
            statement.setInt(2, user.getId());

            return statement.executeUpdate();

        } catch (SQLException e) {
            String exception = String.format("Exception in updating user, id: %d. Setting deleted: %s", user.getId(), user.isDeleted() ? 1 : 0);
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
    }

    enum SQLUser {

        INSERT_NEW("INSERT INTO user (name,surname,patronymic,email,username,password,type_id)"
                + "VALUES"
                + "(?,?,?,?,?,?,(SELECT id FROM user_type WHERE code = ?))"),
        GET_BY_ID("SELECT "
                + "u.id as u_id, "
                + "u.name as u_name, "
                + "u.surname as u_surname, "
                + "u.patronymic as u_patronymic, "
                + "u.email as u_email, "
                + "u.username as u_username, "
                + "u.deletion_mark as u_deletion_mark, "
                + "u_t.code as u_t_code "
                + "FROM user as u"
                + "LEFT JOIN user_type as u_t ON u.type_id = u_t.id "
                + "WHERE u.id =?"),
        GET_BY_USERNAME("SELECT "
                + "u.id as u_id, "
                + "u.name as u_name, "
                + "u.surname as u_surname, "
                + "u.patronymic as u_patronymic, "
                + "u.email as u_email, "
                + "u.username as u_username, "
                + "u.deletion_mark as u_deletion_mark, "
                + "u_t.code as u_t_code "
                + "FROM user as u "
                + "LEFT JOIN user_type as u_t ON u.type_id = u_t.id "
                + "WHERE u.username =?"),
        GET_ALL("SELECT "
                + "u.id as u_id, "
                + "u.name as u_name, "
                + "u.surname as u_surname, "
                + "u.patronymic as u_patronymic, "
                + "u.email as u_email, "
                + "u.username as u_username, "
                + "u.deletion_mark as u_deletion_mark, "
                + "u_t.code as u_t_code "
                + "FROM user as u "
                + "LEFT JOIN user_type as u_t ON u.type_id = u_t.id"),
        GET_ALL_BY_TYPE("SELECT "
                + "u.id as u_id, "
                + "u.name as u_name, "
                + "u.surname as u_surname, "
                + "u.patronymic as u_patronymic, "
                + "u.email as u_email, "
                + "u.username as u_username, "
                + "u.deletion_mark as u_deletion_mark, "
                + "u_t.code as u_t_code "
                + "FROM user as u "
                + "LEFT JOIN user_type as u_t ON u.type_id = u_t.id "
                + "WHERE type_id = (SELECT id FROM user_type WHERE code =?)"),
        GET_USER_PASSWORD("SELECT password FROM user WHERE username =?"),
        GET_COUNT_ROWS("SELECT COUNT(*) FROM user"),
        UPDATE_USER_TYPE("UPDATE user SET type_id = (SELECT id FROM user_type WHERE code =?) "
                + "WHERE id =?"),
        UPDATE_DELETED("UPDATE user SET deletion_mark =? "
                + "WHERE id =?"),
        IS_EMAIL_EXISTS("SELECT id FROM user WHERE email =?"),
        IS_USERNAME_EXISTS("SELECT id FROM user WHERE username =?"),
        IS_USER_HAS_TAXPAYER("SELECT id FROM taxpayer where employee_id=?");

        private final String QUERY;

        SQLUser(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
