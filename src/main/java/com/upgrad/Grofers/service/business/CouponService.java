package com.upgrad.Grofers.service.businness;

import com.upgrad.Grofers.service.dao.CouponDao;
import com.upgrad.Grofers.service.entity.CouponEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class CouponService {

    @Autowired
    private CouponDao couponDao;

    @Transactional
    public CouponEntity getCouponById(final Long couponId) {
        return couponDao.getCouponById(couponId);
    }

    @Transactional
    public CouponEntity getCouponByUuid(final String couponUuid) {
        return couponDao.getCouponByUuid(couponUuid);
    }

}
