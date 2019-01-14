package com.ivahnenko.taxsystem.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.apache.log4j.Logger;

import com.ivahnenko.taxsystem.dao.exception.DAOException;
import com.ivahnenko.taxsystem.model.Taxpayer;
import com.ivahnenko.taxsystem.model.Taxpayer.OwnershipType;
import com.ivahnenko.taxsystem.model.User;
import com.ivahnenko.taxsystem.model.form.FeedbackForm;
import com.ivahnenko.taxsystem.model.form.Form.Status;
import com.ivahnenko.taxsystem.model.form.TaxReportForm;

public class UtilDAO {
    private static final Logger LOGGER = Logger.getLogger(UtilDAO.class);

    private UtilDAO() {
    }

    /**
     * Creates a new user using the data from the result set.
     */
    public static User createUser(ResultSet rs) {
        User user;
        try {
            user = new User.Builder()
                    .setId(rs.getInt("u_id"))
                    .setName(rs.getString("u_name"))
                    .setSurname(rs.getString("u_surname"))
                    .setPatronymic(rs.getString("u_patronymic"))
                    .setEmail(rs.getString("u_email"))
                    .setUsername(rs.getString("u_username"))
                    .setType(User.Type.valueOf(rs.getString("u_t_code")))
                    .setDeleted(rs.getInt("u_deletion_mark") == 1)
                    .build();

        } catch (SQLException e) {
            String message = "Exception during create user entity";
            LOGGER.error(message, e);
            throw new DAOException(message, e);
        }
        return user;
    }

    /**
     * Creates a new taxpayer using the data from the result set.
     */
    public static Taxpayer createTaxpayer(ResultSet rs) {
        Taxpayer taxpayer;
        try {


            User employee = new User.Builder()
                    .setId(rs.getInt("u_id"))
                    .setName(rs.getString("u_name"))
                    .setSurname(rs.getString("u_surname"))
                    .setPatronymic(rs.getString("u_patronymic"))
                    .setEmail(rs.getString("u_email"))
                    .build();

            taxpayer = new Taxpayer.Builder()
                    .setId(rs.getInt("t_id"))
                    .setName(rs.getString("t_name"))
                    .setEmail(rs.getString("t_email"))
                    .setRegistrationNumber(rs.getString("t_registration_number"))
                    .setPostcode(rs.getString("t_postcode"))
                    .setOwnershipType(OwnershipType.valueOf(rs.getString("ot_code")))
                    .setEmployee(employee)
                    .build();

        } catch (SQLException e) {
            String message = "Exception during create Taxpayer entity";
            LOGGER.error(message, e);
            throw new DAOException(message, e);
        }
        return taxpayer;
    }

    /**
     * Creates a new tax report using the data from the result set.
     */
    public static TaxReportForm createTaxReportForm(ResultSet rs) {
        TaxReportForm taxReportForm;
        try {

            Taxpayer taxpayer = new Taxpayer.Builder()
                    .setId(rs.getInt("t_id"))
                    .setName(rs.getString("t_name"))
                    .setRegistrationNumber(rs.getString("t_registration_number"))
                    .setEmail(rs.getString("t_email"))
                    .build();

            User initiator = new User.Builder()
                    .setId(rs.getInt("ui_id"))
                    .setName(rs.getString("ui_name"))
                    .setSurname(rs.getString("ui_surname"))
                    .setPatronymic(rs.getString("ui_patronymic"))
                    .setEmail(rs.getString("ui_email"))
                    .build();

            User reviewer = null;

            if (rs.getInt("ur_id") > 0) {
                reviewer = new User.Builder()
                        .setId(rs.getInt("ur_id"))
                        .setName(rs.getString("ur_name"))
                        .setSurname(rs.getString("ur_surname"))
                        .setPatronymic(rs.getString("ur_patronymic"))
                        .setEmail(rs.getString("ur_email"))
                        .build();
            }

            taxReportForm = new TaxReportForm.Builder()
                    .setId(rs.getInt("tf_id"))
                    .setQuarter(rs.getByte("tf_quarter"))
                    .setYear(rs.getShort("tf_year"))
                    .setTaxpayer(taxpayer)
                    .setInitiator(initiator)
                    .setReviewer(reviewer)
                    .setReviewerComment(rs.getString("tf_reviewer_comment"))
                    .setMainActivityIncome(rs.getDouble("tf_main_activity_income"))
                    .setInvestmentIncome(rs.getDouble("tf_investment_income"))
                    .setPropertyIncome(rs.getDouble("tf_property_income"))
                    .setMainActivityExpenses(rs.getDouble("tf_main_activity_expenses"))
                    .setInvestmentExpenses(rs.getDouble("tf_investment_expenses"))
                    .setPropertyExpenses(rs.getDouble("tf_property_expenses"))
                    .setCreationDate(LocalDate.parse(rs.getString("tf_creation_date")))
                    .setLastModifiedDate(LocalDate.parse(rs.getString("tf_last_modified_date")))
                    .setStatus(Status.valueOf(rs.getString("fs_code")))
                    .build();

        } catch (SQLException e) {
            String message = "Exception during create taxReportForm entity";
            LOGGER.error(message, e);
            throw new DAOException(message, e);
        }
        return taxReportForm;
    }

    /**
     * Creates a new feedback form using the data from the result set.
     */
    public static FeedbackForm createFeedbackForm(ResultSet rs) {
        FeedbackForm feedbackForm;
        try {

            Taxpayer taxpayer = new Taxpayer.Builder()
                    .setId(rs.getInt("t_id"))
                    .setName(rs.getString("t_name"))
                    .setRegistrationNumber(rs.getString("t_registration_number"))
                    .setEmail(rs.getString("t_email"))
                    .build();

            User initiator = new User.Builder()
                    .setId(rs.getInt("ui_id"))
                    .setName(rs.getString("ui_name"))
                    .setSurname(rs.getString("ui_surname"))
                    .setPatronymic(rs.getString("ui_patronymic"))
                    .setEmail(rs.getString("ui_email"))
                    .build();

            User reviewer = null;
            if (rs.getInt("ur_id") > 0) {
                reviewer = new User.Builder()
                        .setId(rs.getInt("ur_id"))
                        .setName(rs.getString("ur_name"))
                        .setSurname(rs.getString("ur_surname"))
                        .setPatronymic(rs.getString("ur_patronymic"))
                        .setEmail(rs.getString("ur_email"))
                        .build();
            }

            feedbackForm = new FeedbackForm.Builder()
                    .setId(rs.getInt("ff_id"))
                    .setTaxpayer(taxpayer)
                    .setInitiator(initiator)
                    .setReviewer(reviewer)
                    .setDescription(rs.getString("ff_description"))
                    .setReviewerComment(rs.getString("ff_reviewer_comment"))
                    .setCreationDate(LocalDate.parse(rs.getString("ff_creation_date")))
                    .setLastModifiedDate(LocalDate.parse(rs.getString("ff_last_modified_date")))
                    .setStatus(Status.valueOf(rs.getString("fs_code")))
                    .build();

        } catch (SQLException e) {
            String message = "Exception during create feedbackForm entity";
            LOGGER.error(message, e);
            throw new DAOException(message, e);
        }
        return feedbackForm;
    }

    /**
     * Get the password hash using the SHA-256 encoding algorithm .
     */
    public static String getEncryptedPassword(String password) {
        StringBuilder hexStringPasswordHash = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] passwordHashBytes = md.digest(password.getBytes());

            for (byte passwordHashByte : passwordHashBytes) {
                String hex = Integer.toHexString(0xff & passwordHashByte);

                if (hex.length() == 1) {
                    hexStringPasswordHash.append('0');
                }
                hexStringPasswordHash.append(hex);
            }

        } catch (NoSuchAlgorithmException e) {
            String message = "Exception during encrypting password";
            LOGGER.error(message, e);
            throw new RuntimeException(message, e);
        }
        return hexStringPasswordHash.toString();
    }

    /**
     * Validating match of provided and stored passwords .
     */
    public static boolean validatePassword(String password, String storedPassword) {
        return getEncryptedPassword(password).equals(storedPassword);
    }
}
