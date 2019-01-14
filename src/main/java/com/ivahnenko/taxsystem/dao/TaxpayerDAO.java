package com.ivahnenko.taxsystem.dao;

import java.util.Optional;

import com.ivahnenko.taxsystem.model.Taxpayer;

public interface TaxpayerDAO extends GenericDAO<Taxpayer> {

    /**
     * Getting taxpayer from database by id.
     */
    Optional<Taxpayer> getByEmployeeId(int id);

    /**
     * Updating taxpayer in database by id.
     */
    int update(Taxpayer taxpayer);

    /**
     * Checking if email exists in database.
     */
    boolean isEmailExists(String email);

    /**
     * Checking if registration number exists in database.
     */
    boolean isRegNumberExists(String regNumber);

}
