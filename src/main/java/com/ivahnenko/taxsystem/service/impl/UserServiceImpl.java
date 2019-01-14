package com.ivahnenko.taxsystem.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.dao.UtilDAO;
import com.ivahnenko.taxsystem.dao.connection.ConnectionFactory;
import com.ivahnenko.taxsystem.dao.connection.DAOConnection;
import com.ivahnenko.taxsystem.dao.DAOFactory;
import com.ivahnenko.taxsystem.dao.UserDAO;
import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.service.UserService;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    private DAOFactory daoFactory;
    private ConnectionFactory connectionFactory;

    UserServiceImpl() {
        this.daoFactory = DAOFactory.getInstance();
        this.connectionFactory = ConnectionFactory.getInstance();
    }

    UserServiceImpl(DAOFactory daoFactory, ConnectionFactory connectionFactory) {
        this.daoFactory = daoFactory;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public boolean save(User user) {
        boolean actionSuccess = false;
        try (DAOConnection connection = connectionFactory.getConnection()) {

            UserDAO userDAO = daoFactory.getUserDAO(connection);

            LOGGER.info(String.format("Saving user with username: %s", user.getUsername()));

            int rowCount = userDAO.save(user);

            if (rowCount > 0) {
                actionSuccess = true;
            }
        }
        return actionSuccess;
    }

    @Override
    public Optional<User> getById(int id) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            UserDAO userDAO = daoFactory.getUserDAO(connection);

            LOGGER.info(String.format("Getting user by id: %d", id));

            return userDAO.get(id);
        }
    }

    @Override
    public Optional<User> getByUsername(String username) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            UserDAO userDAO = daoFactory.getUserDAO(connection);

            LOGGER.info(String.format("Getting user by username: %s", username));

            return userDAO.getByUsername(username);
        }
    }

    @Override
    public boolean changeType(User user) {
        boolean actionSuccess = false;
        try (DAOConnection connection = connectionFactory.getConnection()) {

            UserDAO userDAO = daoFactory.getUserDAO(connection);

            LOGGER.info(String.format("Updating user by id: %s. Setting userType: %s", user.getId(), user.getType()));

            int rowCount = userDAO.changeType(user);

            if (rowCount > 0) {
                actionSuccess = true;
            }
        }
        return actionSuccess;
    }

    @Override
    public List<User> getAll() {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            UserDAO userDAO = daoFactory.getUserDAO(connection);

            LOGGER.info("Getting all users");

            return userDAO.getAll();
        }
    }

    @Override
    public List<User> getAllByType(String type) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            UserDAO userDAO = daoFactory.getUserDAO(connection);

            LOGGER.info(String.format("Getting all users by type: %s", type));

            return userDAO.getAllByType(type);
        }
    }

    @Override
    public int getAmount() {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            UserDAO userDAO = daoFactory.getUserDAO(connection);

            LOGGER.info("Getting amount of users");

            return userDAO.getAmount();
        }
    }

    @Override
    public boolean isEmailExists(String email) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            UserDAO userDAO = daoFactory.getUserDAO(connection);

            LOGGER.info(String.format("Checking if email: %s exists", email));

            return userDAO.isEmailExists(email);
        }
    }

    @Override
    public boolean isUsernameExists(String username) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            UserDAO userDAO = daoFactory.getUserDAO(connection);

            LOGGER.info(String.format("Checking if username: %s exists", username));

            return userDAO.isUsernameExists(username);
        }
    }

    @Override
    public Optional<User> login(String username, String password) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            UserDAO userDAO = daoFactory.getUserDAO(connection);

            LOGGER.info(String.format("Login user username: %s", username));

            Optional<User> user = userDAO.getByUsername(username);

            if (!user.isPresent()) {

                LOGGER.info(String.format("User with username: %s not found", username));

                return Optional.empty();
            }

            Optional<String> currentPassword = userDAO.getPassword(username);

            if (currentPassword.isPresent() && UtilDAO.validatePassword(password, currentPassword.get())) {

                LOGGER.info(String.format("User, username: %s, password confirmed", username));

                return user;
            }

            LOGGER.info(String.format("User, username: %s, password not confirmed", username));
            return Optional.empty();
        }
    }

    @Override
    public boolean isUserHasTaxpayer(User user) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            UserDAO userDAO = daoFactory.getUserDAO(connection);

            LOGGER.info(String.format("Checking if user, id: %d assigned to taxpayer", user.getId()));

            return userDAO.isUserHasTaxpayer(user);
        }
    }

    @Override
    public boolean changeDeletionStatus(User user) {
        boolean actionSuccess = false;
        try (DAOConnection connection = connectionFactory.getConnection()) {

            UserDAO userDAO = daoFactory.getUserDAO(connection);

            LOGGER.info(String.format("Updating user by id: %d. Setting deleted: %s", user.getId(), user.isDeleted()));

            int rowCount = userDAO.changeDeleted(user);

            if (rowCount > 0) {
                actionSuccess = true;
            }
        }
        return actionSuccess;
    }
}
