package com.vpu.mp.auth;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import com.vpu.mp.service.pojo.shop.auth.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.config.AuthConfig;
import com.vpu.mp.db.main.tables.records.ShopAccountRecord;
import com.vpu.mp.db.main.tables.records.ShopChildAccountRecord;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.db.main.tables.records.UserLoginRecordRecord;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.saas.SaasApplication;

/**
 * @author chenjie
 * @date 2020年08月20日
 */
@Component
public class StoreAuth {

    @Autowired
    protected AuthConfig authConfig;

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected SaasApplication saas ;

    @Autowired
    protected JedisManager jedis;

    protected Logger log = LoggerFactory.getLogger(StoreAuth.class);

    protected static final String TOKEN = "V-Token";
    protected static final String AUTH_SECRET = "auth.secret";
    protected static final String AUTH_TIMEOUT = "auth.timeout";
    protected static final String TOKEN_PREFIX = "STO@";

    /**
     * 登出
     */
    public boolean logout() {
        StoreTokenAuthInfo info = user();
        if(info == null) {
            return false;
        }
        this.deleteTokenInfo(info);
        return true;
    }

    protected String getToken() {
        return request != null ? request.getHeader(TOKEN) : null;
    }

    /**
     * 是否有效store登录TOKEN
     *
     * @param  token
     * @return
     */
    public boolean isValidToken(String token) {
        return StringUtils.isNotEmpty(token) && StringUtils.startsWith(token, TOKEN_PREFIX);
    }

    /**
     * 登录账户
     *
     * @param  param
     * @return
     */
    public StoreTokenAuthInfo login(StoreLoginParam param) {
        ShopAccountRecord account = saas.shop.account.getAccountInfo(param.username);
        if (account == null) {
            return null;
        }
        param.setSysId(account.getSysId());
        StoreAuthInfoVo storeAuthInfo = saas.shop.storeManageService.storeAccountService.getStoreAccountFlag(param);
        if (!StoreAuthConstant.STORE_AUTH_OK.equals(storeAuthInfo.getIsOk())) {
            return null;
        }
        ShopChildAccountRecord subAccount = null;
//        if (StoreAuthConstant.STORE_CLERK.equals(param.storeAccountType) ) {
//            subAccount = saas.shop.subAccount.verify(account.getSysId(), param.subUsername, param.password);
//            if (subAccount == null) {
//                return null;
//            }
//        } else {
//            if (!account.getPassword().equals(Util.md5(param.password))) {
//                return null;
//            }
//        }

        StoreTokenAuthInfo info = new StoreTokenAuthInfo();
        info.setSysId(account.getSysId());
        info.setUserName(account.getUserName());
        info.setSubAccountId(subAccount != null ? subAccount.getAccountId() : 0);
        info.setSubUserName(subAccount != null ? subAccount.getAccountName() : "");
        info.setSubLogin(subAccount != null);
        info.setLoginShopId(0);
        info.setAccountName(subAccount != null ? subAccount.getAccountName() : account.getAccountName());

        // 如果当前登录用户与正在登录的用户相同，则使用当前登录用户的Token
        StoreTokenAuthInfo user = user();
        if(user!=null && user.getSysId().equals(info.getSysId()) && user.getSubAccountId().equals(info.getSubAccountId())) {
            info.setToken(user.getToken());
        }

        this.saveTokenInfo(info);
        return info;
    }

    /**
     * 保存登录信息
     *
     * @param info
     */
    public void saveTokenInfo(StoreTokenAuthInfo info) {
        if (StringUtils.isBlank(info.getToken())) {
            String loginToken = TOKEN_PREFIX
                + Util.md5(String.format("admin_login_%d_%d_%s_%d", info.getSysId(), info.getSubAccountId(),
                Util.randomId(), Calendar.getInstance().getTimeInMillis()));
            info.setToken(loginToken);
        }
        jedis.set(info.token, Util.toJson(info), authConfig.getTimeout());
    }

    /**
     * 删除登录信息
     *
     * @param info
     */
    public void deleteTokenInfo(StoreTokenAuthInfo info) {
        jedis.delete(info.token);
    }

    /**
     * 得到当前登录用户
     *
     * @return
     */
    public StoreTokenAuthInfo user() {
        String token = getToken();
        if (this.isValidToken(token)) {
            String json = jedis.get(token);
            if (!StringUtils.isBlank(json)) {
                return Util.parseJson(json, StoreTokenAuthInfo.class);
            }
        }
        return null;
    }

    /**
     * 重置token的存活时间
     *
     */
    public void reTokenTtl() {
        StoreTokenAuthInfo info = user();
        if (null != info) {
            this.saveTokenInfo(info);
        }
    }

    /**
     * 登录时间表更新
     * @param info
     * @param shop
     * @return
     * @return
     */
    public int insert(StoreTokenAuthInfo info, ShopRecord shop) {
        UserLoginRecordRecord record = new UserLoginRecordRecord();
        record.setUserName(info.getUserName());
        record.setUserId(info.getSysId());
        if (info.isSubLogin()) {
            record.setUserName(info.getSubUserName());
            record.setUserId(info.getSubAccountId());

        }
        record.setSysId(info.getSysId());
        record.setShopName(shop.getShopName());
        record.setShopId(shop.getShopId());
        record.setUserIp(Util.getCleintIp(request));
        record.setAccountType((byte)0);
        return saas.shop.insertUserLoginRecord(record);
    }

    /**
     * 更新accountName
     * @param accountName
     */
    public void updateAccountName(String accountName) {
        StoreTokenAuthInfo user = user();
        user.setAccountName(accountName);
        jedis.set(getToken(), Util.toJson(user));
    }
}
