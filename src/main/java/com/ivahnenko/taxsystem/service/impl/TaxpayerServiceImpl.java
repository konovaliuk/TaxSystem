package com.ivahnenko.taxsystem.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.dao.connection.ConnectionFactory;
import com.ivahnenko.taxsystem.dao.connection.DAOConnection;
import com.ivahnenko.taxsystem.dao.DAOFactory;
import com.ivahnenko.taxsystem.dao.TaxpayerDAO;
import com.ivahnenko.taxsystem.model.Taxpayer;
import com.ivahnenko.taxsystem.service.TaxpayerService;

public class TaxpayerServiceImpl implements TaxpayerService {
    private static final Logger LOGGER = Logger.getLogger(TaxpayerServiceImpl.class);

    private DAOFactory daoFactory;
    private ConnectionFactory connectionFactory;

    TaxpayerServiceImpl() {
        this.daoFactory = DAOFactory.getInstance();
        this.connectionFactory = ConnectionFactory.getInstance();
    }

    TaxpayerServiceImpl(DAOFactory daoFactory, ConnectionFactory connectionFactory) {
        this.daoFactory = daoFactory;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public boolean save(Taxpayer taxpayer) {
        boolean actionSuccess = false;
        try (DAOConnection connection = connectionFactory.getConnection()) {

            TaxpayerDAO taxpayerDAO = daoFactory.getTaxpayerDAO(connection);

            LOGGER.info(String.format("Saving taxpayer by employee, id: %d", taxpayer.getEmployee().getId()));

            int rowCount = taxpayerDAO.save(taxpayer);

            if (rowCount > 0) {
                actionSuccess = true;
            }
        }
        return actionSuccess;
    }

    @Override
    public Optional<Taxpayer> getById(int id) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            TaxpayerDAO taxpayerDAO = daoFactory.getTaxpayerDAO(connection);

            LOGGER.info(String.format("Getting taxpayer by id: %d", id));

            return taxpayerDAO.get(id);
        }
    }

    @Override
    public int getAmount() {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            TaxpayerDAO taxpayerDAO = daoFactory.getTaxpayerDAO(connection);

            LOGGER.info("Getting amount of taxpayers");

            return taxpayerDAO.getAmount();
        }
    }

    @Override
    public List<Taxpayer> getAll() {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            TaxpayerDAO taxpayerDAO = daoFactory.getTaxpayerDAO(connection);

            LOGGER.info("Getting all taxpayer");

            return taxpayerDAO.getAll();
        }
    }

    @Override
    public Optional<Taxpayer> getByEmployeeId(int id) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            TaxpayerDAO taxpayerDAO = daoFactory.getTaxpayerDAO(connection);

            LOGGER.info(String.format("Getting taxpayer by employee, id: %d", id));

            return taxpayerDAO.getByEmployeeId(id);
        }
    }

    @Override
    public boolean isEmailExists(String email) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            TaxpayerDAO taxpayerDAO = daoFactory.getTaxpayerDAO(connection);

            LOGGER.info(String.format("Checking if email: %s exists", email));

            return taxpayerDAO.isEmailExists(email);
        }
    }

    @Override
    public boolean isRegNumberExists(String registrationNumber) {
        try (DAOConnection connection = connectionFactory.getConnection()) {

            TaxpayerDAO taxpayerDAO = daoFactory.getTaxpayerDAO(connection);

            LOGGER.info(String.format("Checking if registrationNumber: %s exists", registrationNumber));

            return taxpayerDAO.isRegNumberExists(registrationNumber);
        }
    }
}
