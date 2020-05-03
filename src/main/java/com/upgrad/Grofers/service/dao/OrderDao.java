package com.upgrad.Grofers.service.dao;

import com.upgrad.Grofers.service.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    public CouponEntity getCouponByName(String couponName) {
        try {
            return entityManager.createNamedQuery("couponByName", CouponEntity.class).setParameter("couponName", couponName)
                    .getSingleResult();
        } catch(NoResultException nre) {
            return null;
        }
    }

    public List<OrdersEntity> getCustomerOrders(CustomerEntity customerEntity) {
        try {
            return entityManager.createNamedQuery("ordersByCustomer", OrdersEntity.class).setParameter("customer", customerEntity)
                    .getResultList();
        } catch(NoResultException nre) {
            return null;
        }
    }

    public OrdersEntity saveOrder(OrdersEntity ordersEntity) {
        entityManager.persist(ordersEntity);
        return ordersEntity;
    }

    public List<OrdersEntity> getOrdersByStore(final StoreEntity StoreEntity) {
        try {
            return entityManager.createNamedQuery("ordersByStore", OrdersEntity.class)
                    .setParameter("Store", StoreEntity).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
