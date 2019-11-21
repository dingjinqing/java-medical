package com.vpu.mp.service.shop.member;

import com.vpu.mp.config.TxMapLBSConfig;
import com.vpu.mp.db.shop.tables.records.UserAddressRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.HttpsUtils;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.address.AddressCode;
import com.vpu.mp.service.pojo.shop.member.address.AddressInfo;
import com.vpu.mp.service.pojo.shop.member.address.AddressLocation;
import com.vpu.mp.service.pojo.shop.member.address.UserAddressVo;
import com.vpu.mp.service.pojo.shop.member.address.WxAddress;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vpu.mp.db.shop.Tables.USER_ADDRESS;

/**
 * @author 黄壮壮
 * @Date: 2019年8月16日
 * @Description: 用户地址服务
 */
@Service
@Slf4j
public class AddressService extends ShopBaseService {
    private static final String QQ_MAP_GEOCODER_URL = "https://apis.map.qq.com/ws/geocoder/v1";

    @Autowired
    private TxMapLBSConfig txMapLBSConfig;


    /**
     * 获取id用户的详细地址信息
     */
    public List<String> getUserAddressById(Integer userId) {
        logger().info("获取用户" + userId + "的详细地址信息");
        List<String> addressList = db().select(USER_ADDRESS.COMPLETE_ADDRESS)
                .from(USER_ADDRESS)
                .where(USER_ADDRESS.USER_ID.eq(userId))
                .fetch()
                .into(String.class);

        addressList.forEach(logger()::info);
        return addressList;
    }

    /**
     * 王帅
     * 获取地址
     *
     * @param addressId 地址id
     * @param userId    用户id
     * @return 地址
     */
    public UserAddressVo get(Integer addressId, Integer userId) {
        if (addressId == null || userId == null) {
            return null;
        }
        UserAddressVo address = db().select().from(USER_ADDRESS).where(USER_ADDRESS.ADDRESS_ID.eq(addressId).and(USER_ADDRESS.USER_ID.eq(userId))).fetchAnyInto(UserAddressVo.class);
        return address;
    }

    /**
     * 选择地址cd
     *
     * @return
     */
    public UserAddressRecord chooseAddress(Integer userId, WxAddress wxAddress) {
        UserAddressRecord userAddress = getUserAddressInfo(userId, wxAddress);
        if (userAddress == null) {
            log.info("添加用户地址[userid:" + userId + "]" + Util.toJson(wxAddress));
            userAddress = addWxAddress(userId, wxAddress);
        }
        return userAddress;
    }

    /**
     * 获取用户地址信息
     *
     * @param userId
     * @param wxAddress
     * @return
     */
    public UserAddressRecord getUserAddressInfo(Integer userId, WxAddress wxAddress) {
        UserAddressRecord addressRecord = db().selectFrom(USER_ADDRESS)
                .where(USER_ADDRESS.USER_ID.eq(userId))
                .and(USER_ADDRESS.CONSIGNEE.eq(wxAddress.getUserName()))
                .and(USER_ADDRESS.MOBILE.eq(wxAddress.getTelNumber()))
                .and(USER_ADDRESS.PROVINCE_NAME.like(likeValue(wxAddress.getProvinceName())))
                .and(USER_ADDRESS.CITY_NAME.like(likeValue(wxAddress.getCityName())))
                .and(USER_ADDRESS.DISTRICT_NAME.like(likeValue(wxAddress.getCountyName())))
                .and(USER_ADDRESS.ADDRESS.like(likeValue(wxAddress.getDetailInfo())))
                .fetchOne();
        if (addressRecord != null && (addressRecord.getLat() == null || addressRecord.getLng() == null)) {
            AddressLocation addressLocation = getGeocoderAddressLocation(wxAddress.getCompleteAddress());
            if (AddressLocation.STATUS_OK.equals(addressLocation.getStatus())){
                addressRecord.setLat(addressLocation.getResult().getLocation().getLat());
                addressRecord.setLng(addressLocation.getResult().getLocation().getLng());
                addressRecord.update();
            }
        }
        return addressRecord;
    }

    /**
     * 根据微信地图定位获取库中的区县code
     *
     * @param addressInfo 微信放回的地址信息
     * @return DistrictId 可以为null
     */
    public Integer getUserAddressDistrictId(AddressInfo addressInfo) {
        if (addressInfo == null) {
            return null;
        } else if (!AddressInfo.STATUS_OK.equals(addressInfo.getStatus())) {
            return null;
        }
        AddressInfo.Result.AddressComponent address = addressInfo.getResult().getAddressComponent();
        if (StringUtils.isEmpty(address.getCity())||StringUtils.isEmpty(address.getDistrict())
                ||StringUtils.isEmpty(address.getProvince())||StringUtils.isEmpty(address.getNation())){
            return null;
        }
        AddressCode addressCode = checkAndUpdateAddress(address.getProvince(), address.getCity(), address.getDistrict());
        if (addressCode==null){
            return null;
        }
        return addressCode.getDistrictId();
    }

    /**
     * 添加地址信息
     *
     * @param wxAddress 微信的地址系信息
     * @return
     */
    public UserAddressRecord addWxAddress(Integer userId, WxAddress wxAddress) {
        AddressCode addressCode = checkAndUpdateAddress(wxAddress.getProvinceName(),wxAddress.getCityName(),wxAddress.getCountyName());
        UserAddressRecord address = db().newRecord(USER_ADDRESS);
        address.setProvinceCode(addressCode.getPostalId());
        address.setProvinceName(wxAddress.getProvinceName());
        address.setCityCode(addressCode.getCityId());
        address.setCityName(wxAddress.getCityName());
        address.setDistrictCode(addressCode.getDistrictId());
        address.setDistrictName(wxAddress.getCountyName());
        address.setConsignee(wxAddress.getUserName());
        address.setAddressName(wxAddress.getNationalCode());
        address.setAddress(wxAddress.getDetailInfo());
        address.setCompleteAddress(wxAddress.getCompleteAddress());
        address.setZipcode(wxAddress.getPostalCode());
        address.setMobile(wxAddress.getTelNumber());
        address.setUserId(userId);
        address.insert();
        return address;
    }
    /**
     * 检查并跟新获取库中地址
     * @param provinceName 省
     * @param cityName 市
     * @param districtNmae 区县
     * @return  AddressCode 省市区的本地code 可能null
     */
    public AddressCode checkAndUpdateAddress(String provinceName,String cityName,String districtNmae){
        Integer provinceId = saas.region.province.getProvinceIdByName(provinceName);
        AddressCode addressCode =new AddressCode();
        if (provinceId == null) {
            provinceId = saas.region.province.getProvinceIdByName(provinceName.substring(0, 2));
            if (provinceId == null) {
                log.error("微信地址[province:" +provinceName + "] 在库中未找到!");
                return null;
            } else {
                log.info("根据微信地址,跟新[provinceId:" + provinceId + ",province:" + provinceName + "]");
                saas.region.province.updateProvinceName(provinceId, provinceName);
            }
        }
        Integer cityId = saas.region.city.getCityIdByNameAndProvinceId(provinceId, cityName);
        if (cityId == null) {
            log.info("新增库中没有的微信地址城市[cityName:" + cityName + "]");
            cityId = saas.region.city.addNewCity(provinceId, cityName);
        }
        Integer districtId = saas.region.district.getDistrictIdByNameAndCityId(cityId, districtNmae);
        if (districtId == null) {
            log.info("新增库中没有的微信地址区县[district:" + districtNmae + "]");
            districtId = saas.region.district.addNewDistrict(cityId, districtNmae);
        }
        addressCode.setPostalId(provinceId);
        addressCode.setCityId(cityId);
        addressCode.setDistrictId(districtId);
        return addressCode;
    }
    /**
     * 地址解析获取定位
     *
     * @return
     */
    public AddressLocation getGeocoderAddressLocation(String address) {
        Map<String, Object> param = new HashMap<>(2);
        param.put("address", address);
        param.put("key", txMapLBSConfig.getKey());
        return Util.json2Object(HttpsUtils.get(QQ_MAP_GEOCODER_URL, param, true), AddressLocation.class, true);
    }

    /**
     * 根据 经纬度获取地址
     *
     * @param lat 经度
     * @param lng 纬度
     * @return
     */
    public AddressInfo getGeocoderAddressInfo(String lat, String lng) {
        Map<String, Object> param = new HashMap<>(2);
        param.put("location", lat + "," + lng);
        param.put("key", txMapLBSConfig.getKey());
        return Util.json2Object(HttpsUtils.get(QQ_MAP_GEOCODER_URL, param, true), AddressInfo.class, true);
    }

}
