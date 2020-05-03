package com.upgrad.Grofers.service.dao;

import com.upgrad.Grofers.service.entity.ItemEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class ItemDao {

    @PersistenceContext
    private EntityManager entityManager;

    public ItemEntity getItemById(final Integer itemId) {
        try {
            return entityManager.createNamedQuery("itemById", ItemEntity.class).setParameter("id", itemId)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public ItemEntity getItemByUuid(final String itemUuid) {
        try {
            return entityManager.createNamedQuery("itemByUuid", ItemEntity.class).setParameter("uuid", itemUuid)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }


}
