package com.ivahnenko.taxsystem.service;

import java.util.List;
import java.util.Optional;

import com.ivahnenko.taxsystem.model.form.Form;

public interface FormService<Entity extends Form> {

    /**
     * Method for creation of a new form.
     */
    boolean save(Entity model);

    /**
     * Method for getting form by id.
     */
    Optional<Entity> getById(int id);

    /**
     * Method for getting form by id and initiator id.
     */
    Optional<Entity> getByIdAndInitiator(int entityId, int initiatorId);

    /**
     * Method for getting form by id and reviewer id.
     */
    Optional<Entity> getByIdAndReviewer(int entityId, int reviewerId);

    /**
     * Method for updating form by id, initiator id, status.
     */
    boolean updateByInitiatorAndStatus(Entity model, String status);

    /**
     * Method for updating form by id, reviewer id, status.
     */
    boolean updateByReviewerAndStatus(Entity model, String status);

    /**
     * Method for updating form's reviewer and status by id.
     */
    boolean updateReviewerAndStatus(Entity model);

    /**
     * Method for getting amount of existing forms.
     */
    int getAmount();

    /**
     * Method for getting all forms.
     */
    List<Entity> getAll();

    /**
     * Method for getting all forms by initiator id.
     */
    List<Entity> getAllByInitiatorId(int id, int pageRows, int offset);

    /**
     * Method for getting all forms by reviewer id.
     */
    List<Entity> getAllByReviewerId(int id, int pageRows, int offset);

    /**
     * Method for getting all forms by reviewer id and status.
     */
    List<Entity> getAllByReviewerAndStatus(int id, String status, int rows, int offset);

    /**
     * Method for getting all forms by status.
     */
    List<Entity> getAllByStatus(String status, int pageRows, int offset);

    /**
     * Method for checking if a form has reviewer.
     */
    boolean isReviewerAssigned(int id);
}
