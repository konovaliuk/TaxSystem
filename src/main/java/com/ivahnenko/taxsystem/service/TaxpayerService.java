package com.ivahnenko.taxsystem.service;

import java.util.List;
import java.util.Optional;

import com.ivahnenko.taxsystem.model.Taxpayer;

public interface TaxpayerService {

    /**
     * Method for saving of a new taxpayer.
     */
    boolean save(Taxpayer taxpayer);

    /**
     * Method for getting taxpayer by id.
     */
    Optional<Taxpayer> getById(int id);

    /**
     * Method for getting amount of existing taxpayers.
     */
    int getAmount();

    /**
     * Method for getting all taxpayers.
     */
    List<Taxpayer> getAll();

    /**
     * Method for getting taxpayer by employee id.
     */
    Optional<Taxpayer> getByEmployeeId(int id);

    /**
     * Method for checking if email already exists in database.
     */
    boolean isEmailExists(String email);

    /**
     * Method for checking if resitration number already exists in database.
     */
    boolean isRegNumberExists(String registrationNumber);
}
