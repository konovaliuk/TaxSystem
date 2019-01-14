package com.ivahnenko.taxsystem.dao;

import java.util.List;
import java.util.Optional;


public interface GenericDAO<Entity> {

    /**
     * Saving entity in database by id.
     */
    int save(Entity model);

    /**
     * Getting entity from database by id.
     */
    Optional<Entity> get(int id);

    /**
     * Getting all entities for database.
     */
    List<Entity> getAll();

    /**
     * Getting amount of specified entities in database.
     */
    int getAmount();
}
