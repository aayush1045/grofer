package com.upgrad.Grofers.service.dao;

import com.upgrad.Grofers.service.entity.CustomerAddressEntity;
import com.upgrad.Grofers.service.entity.CustomerAuthEntity;
import com.upgrad.Grofers.service.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class CustomerDao {

    @PersistenceContext
    private EntityManager entityManager;

    // Creates customer by persisting the record in the database
    public CustomerEntity createCustomer(CustomerEntity customerEntity) {
        entityManager.persist(customerEntity);
        return customerEntity;
    }

    // Gets the customer details from the database based on id
    public CustomerEntity getCustomerById(final Integer customerId) {
        try {
            return entityManager.createNamedQuery("customerById", CustomerEntity.class).setParameter("id", customerId)
                    .getSingleResult();
        } catch(NoResultException nre) {
            return null;
        }
    }

    // Gets the customer details from the database based on uuid
    public CustomerEntity getCustomerByUuid(final String uuid) {
        try {
            return entityManager.createNamedQuery("customerByUuid", CustomerEntity.class).setParameter("uuid", uuid)
                    .getSingleResult();
        } catch(NoResultException nre) {
            return null;
        }
    }

    // Gets the customer details from the database based on contact number
    public CustomerEntity getCustomerByContactNumber(final String customerContactNumber) {
        try {
            return entityManager.createNamedQuery("customerByContactNumber", CustomerEntity.class).setParameter("contactNumber", customerContactNumber)
                    .getSingleResult();
        } catch(NoResultException nre) {
            return null;
        }
    }

    // Creates auth token by persisting the record in the database
    public CustomerAuthEntity createAuthToken(final CustomerAuthEntity CustomerAuthEntity) {
        entityManager.persist(CustomerAuthEntity);
        return CustomerAuthEntity;
    }

    // Updates the customer details to the database
    public void updateCustomer(final CustomerEntity updatedCustomerEntity) {
        entityManager.merge(updatedCustomerEntity);
    }

    //
    public void updateCustomerAuth(final CustomerAuthEntity CustomerAuthEntity) {
        entityManager.merge(CustomerAuthEntity);
    }

    public CustomerEntity deleteCustomer(final CustomerEntity customerEntity){
        entityManager.remove(customerEntity);
        return customerEntity;
    }

    public CustomerAuthEntity getCustomerAuth(final String accessToken) {
        try {
            return entityManager.createNamedQuery("CustomerAuthByAccessToken", CustomerAuthEntity.class)
                    .setParameter("access", access).getSingleResult();
        } catch(NoResultException nre) {
            return null;
        }
    }

    public CustomerAuthEntity getCustomerAuthById(final Long customerId) {
        try {
            return entityManager.createNamedQuery("CustomerAuthById", CustomerAuthEntity.class)
                    .setParameter("customer", customerId).getSingleResult();
        } catch(NoResultException nre) {
            return null;
        }
    }


}
