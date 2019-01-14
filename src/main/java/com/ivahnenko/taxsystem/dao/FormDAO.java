package com.ivahnenko.taxsystem.dao;

import java.util.List;
import java.util.Optional;

import com.ivahnenko.taxsystem.model.form.Form;


public interface FormDAO<Entity extends Form> extends GenericDAO<Entity> {

    /**
     * Getting form from database by id and initiator.
     */
    Optional<Entity> getByIdAndInitiator(int entityId, int initiatorId);

    /**
     * Getting form from database by id and reviewer.
     */
    Optional<Entity> getByIdAndReviewer(int entityId, int reviewerId);

    /**
     * Updating reviewer and status of a form in database by id.
     */
    int updateReviewerAndStatus(Entity entity);

    /**
     * Updating form in database by id, initiator and status.
     */
    int updateByInitiatorAndStatus(Entity entity, String status);

    /**
     * Updating form in database by id, reviewer and status.
     */
    int updateByReviewerAndStatus(Entity entity, String status);

    /**
     * Getting all forms from database by initiator id, with rows amount and offset.
     */
    List<Entity> getAllByInitiatorId(int id, int pageRows, int offset);

    /**
     * Getting all forms from database by reviewer id, with rows amount and offset.
     */
    List<Entity> getAllByReviewerId(int id, int pageRows, int offset);

    /**
     * Getting all forms from database by reviewer id and status, with rows amount and offset.
     */
    List<Entity> getAllByReviewerAndStatus(int id, String status, int rows, int offset);

    /**
     * Getting all forms from database by status, with rows amount and offset.
     */
    List<Entity> getAllByStatus(String status, int pageRows, int offset);

    /**
     * Checking if a form has a reviewer by id.
     */
    boolean isReviewerAssigned(int id);
}
