package com.vpu.mp.service.shop.order.coupons;

import static com.vpu.mp.db.shop.tables.CustomerAvailCoupons.CUSTOMER_AVAIL_COUPONS;

import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.CustomerAvailCoupons;
/**
 * Table:customer_avail_coupons
 * 
 * @author 王帅
 *
 */
@Service
public class CustomerAvailCouponsService {

	public final CustomerAvailCoupons TABLE = CUSTOMER_AVAIL_COUPONS;

}
