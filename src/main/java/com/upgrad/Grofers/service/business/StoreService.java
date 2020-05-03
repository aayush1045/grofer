package com.upgrad.Grofers.service.businness;

import com.upgrad.Grofers.service.dao.CustomerDao;
import com.upgrad.Grofers.service.dao.StoreDao;
import com.upgrad.Grofers.service.entity.CustomerAuthEntity;
import com.upgrad.Grofers.service.entity.CustomerEntity;
import com.upgrad.Grofers.service.entity.StoreCategoryEntity;
import com.upgrad.Grofers.service.entity.StoreEntity;
import com.upgrad.Grofers.service.exception.AuthorizationFailedException;
import com.upgrad.Grofers.service.exception.InvalidRatingException;
import com.upgrad.Grofers.service.exception.StoreNotFoundException;
import com.upgrad.Grofers.service.exception.UpdateCustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

import java.util.List;

@Service
public class StoreBusinessService {

    @Autowired
    private StoreDao StoreDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private  CustomerService  customerService;

    // A Method is for getAllStores endpoint
    public List<StoreEntity> getAllStores() {
        return StoreDao.getAllStores();
    }

    // A Method which takes the StoreName as parameter for  getStoresByName endpoint
    public List<StoreEntity> getStoresByName(String StoreName) {
        return StoreDao.getStoresByName(StoreName);
    }

    // A Method which takes the categoryUUID as parameter for  getStoreByCategoryId endpoint
    public List<StoreCategoryEntity> getStoreByCategoryId(final Long categoryID) {
        return StoreDao.getStoreByCategoryId(categoryID);
    }

    // A Method which takes the StoreUUID as parameter for  getStoreByUUId endpoint
    public StoreEntity getStoreByUUId(String StoreUUID) {
        return StoreDao.getStoreByUUId(StoreUUID);
    }

    @Transactional
    public StoreEntity updateCustomerRating (final Double customerRating, final String Store_id, final String authorizationToken)
            throws AuthorizationFailedException, StoreNotFoundException, InvalidRatingException {

        final ZonedDateTime now = ZonedDateTime.now();

        // Validates the customer using the authorizationToken
         customerService.validateAccessToken(authorizationToken);

        // Throw exception if path variable(Store_id) is empty
        if(Store_id == null || Store_id.isEmpty() || Store_id.equalsIgnoreCase("\"\"")){
            throw new StoreNotFoundException("RNF-002", "Store id field should not be empty");
        }

        //get the Store Details using the StoreUuid
        StoreEntity StoreEntity =  StoreDao.getStoreByUUId(Store_id);

        if (StoreEntity == null) {
            throw new StoreNotFoundException("RNF-001", "No Store by this id");
        }

        // Throw exception if path variable(Store_id) is empty
        if(customerRating == null || customerRating.isNaN() || customerRating < 1 || customerRating > 5 ){
            throw new InvalidRatingException("IRE-001", "Store should be in the range of 1 to 5");
        }

        // Now calculate new customer rating  and set the updated rating and attach it to the StoreEntity
        BigDecimal oldRatingCalculation = (StoreEntity.getCustomerRating().multiply(new BigDecimal(StoreEntity.getNumCustomersRated())));
        BigDecimal calculatedRating = (oldRatingCalculation.add(new BigDecimal(customerRating))).divide(new BigDecimal(StoreEntity.getNumCustomersRated() + 1));
        StoreEntity.setCustomerRating(calculatedRating);
        StoreEntity.setNumCustomersRated(StoreEntity.getNumCustomersRated() + 1);

        //called StoreDao to merge the content and update in the database
        StoreDao.updateStore(StoreEntity);
        return StoreEntity;
    }

}
