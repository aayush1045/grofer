package com.upgrad.Grofers.service.dao;

import com.upgrad.Grofers.service.entity.StoreCategoryEntity;
import com.upgrad.Grofers.service.entity.StoreEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class StoreDao  {

    @PersistenceContext
    private EntityManager entityManager;

    public List<StoreEntity> getAllStores() {
        try {
            return entityManager.createNamedQuery("allStores", StoreEntity.class).getResultList();
        } catch(NoResultException nre) {
            return null;
        }
    }

    public List<StoreEntity> getStoresByName(String StoreName) {
        try {
             return entityManager.createNamedQuery("findByName", StoreEntity.class).setParameter("StoreName","%" + StoreName.toLowerCase() + "%" ).getResultList();
        } catch(NoResultException nre) {
            return null;
        }
    }

    public List<StoreCategoryEntity> getStoreByCategoryId(final Long categoryID) {
        try {
            return entityManager.createNamedQuery("StoresByCategoryId", StoreCategoryEntity.class).setParameter("id",categoryID).getResultList();
        } catch(NoResultException nre) {
            return null;
        }
    }

    public StoreEntity getStoreByUUId(String StoreUUID) {
        try {
            return entityManager.createNamedQuery("findStoreByUUId", StoreEntity.class).setParameter("StoreUUID",StoreUUID.toLowerCase()).getSingleResult();
        } catch(NoResultException nre) {
            return null;
        }
    }

    public void updateStore(final StoreEntity updatedStoreEntity) {
        entityManager.merge(updatedStoreEntity);
    }
  
}
