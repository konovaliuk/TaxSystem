package com.ivahnenko.taxsystem.dao.connection.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.dao.connection.DAOConnection;
import com.ivahnenko.taxsystem.dao.exception.DAOException;


public class JdbcDAOConnection implements DAOConnection {
    private static final Logger LOGGER = Logger.getLogger(JdbcDAOConnection.class);
    private final Connection connection;
    private boolean inTransaction = false;

    public JdbcDAOConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void begin() {
        try {
            connection.setAutoCommit(false);
            inTransaction = true;
        } catch (SQLException e) {
            String exception = "Exception in beginning transaction";
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
    }

    @Override
    public void rollback() {
        try {
            connection.rollback();
            inTransaction = false;
        } catch (SQLException e) {
            String exception = "Exception in rollback of transaction";
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
    }

    @Override
    public void commit() {
        try {
            connection.commit();
            inTransaction = false;
        } catch (SQLException e) {
            String exception = "Exception in committing transaction";
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
    }

    @Override
    public void close() {
        if (inTransaction) {
            rollback();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            String exception = "Exception in closing connection";
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
    }


    @Override
    public Connection getConnection() {
        return connection;
    }

}
