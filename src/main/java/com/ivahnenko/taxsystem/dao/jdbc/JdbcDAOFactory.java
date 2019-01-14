package com.ivahnenko.taxsystem.dao.jdbc;

import java.sql.Connection;

import com.ivahnenko.taxsystem.dao.connection.DAOConnection;
import com.ivahnenko.taxsystem.dao.connection.impl.JdbcDAOConnection;
import com.ivahnenko.taxsystem.dao.DAOFactory;
import com.ivahnenko.taxsystem.dao.FeedbackFormDAO;
import com.ivahnenko.taxsystem.dao.OwnershipTypeDAO;
import com.ivahnenko.taxsystem.dao.TaxReportFormDAO;
import com.ivahnenko.taxsystem.dao.TaxpayerDAO;
import com.ivahnenko.taxsystem.dao.UserDAO;


public class JdbcDAOFactory extends DAOFactory {

    @Override
    public UserDAO getUserDAO(DAOConnection connection) {
        JdbcDAOConnection jdbcConnection = (JdbcDAOConnection) connection;
        Connection basicConnection = jdbcConnection.getConnection();
        return new JdbcUserDAO(basicConnection);
    }

    @Override
    public TaxpayerDAO getTaxpayerDAO(DAOConnection connection) {
        JdbcDAOConnection jdbcConnection = (JdbcDAOConnection) connection;
        Connection basicConnection = jdbcConnection.getConnection();
        return new JdbcTaxpayerDAO(basicConnection);
    }

    @Override
    public TaxReportFormDAO getTaxReportFormDAO(DAOConnection connection) {
        JdbcDAOConnection jdbcConnection = (JdbcDAOConnection) connection;
        Connection basicConnection = jdbcConnection.getConnection();
        return new JdbcTaxReportFormDAO(basicConnection);
    }

    @Override
    public FeedbackFormDAO getFeedbackFormDAO(DAOConnection connection) {
        JdbcDAOConnection jdbcConnection = (JdbcDAOConnection) connection;
        Connection basicConnection = jdbcConnection.getConnection();
        return new JdbcFeedbackFormDAO(basicConnection);
    }
}
