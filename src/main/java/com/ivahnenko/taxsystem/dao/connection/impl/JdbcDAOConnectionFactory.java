package com.ivahnenko.taxsystem.dao.connection.impl;

import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.dao.connection.ConnectionFactory;
import com.ivahnenko.taxsystem.dao.connection.DAOConnection;
import com.ivahnenko.taxsystem.dao.exception.DAOConfigurationException;
import com.ivahnenko.taxsystem.dao.exception.DAOException;

public class JdbcDAOConnectionFactory extends ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger(JdbcDAOConnectionFactory.class);

    private static DataSource dataSource;

    public JdbcDAOConnectionFactory() {
        try {
            InitialContext context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/taxsystem");
        } catch (NamingException e) {
            String exception = "Exception in reading datasource properties ";
            LOGGER.error(exception, e);
            throw new DAOConfigurationException(exception, e);
        }
    }

    @Override
    public DAOConnection getConnection() {
        try {
            return new JdbcDAOConnection(dataSource.getConnection());
        } catch (SQLException e) {
            String exception = "Exception in getting datasource connection ";
            LOGGER.error(exception, e);
            throw new DAOException(exception, e);
        }
    }
}
