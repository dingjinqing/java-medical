package com.vpu.mp.service.shop.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Service;

import static com.vpu.mp.db.shop.tables.ShopCfg.SHOP_CFG;

/**
 * @author 王兵兵
 *
 *         2019年6月26日
 *
 */
@Service
@Slf4j
public class BaseShopConfigService extends ShopBaseService {


	/**
	 * 获取配置key对应value
     *
	 * @param  key
	 * @return
	 */
	protected String get(String key) {
		return db().select().from(SHOP_CFG).where(SHOP_CFG.K.eq(key)).fetchAny(SHOP_CFG.V);
	}

	/**
     * 判断key是否存在
	 * true:存在
	 * false:不存在
	 * @param key
	 * @return
	 */
	protected Boolean isHaveKey(String key) {
		Record record = db().select().from(SHOP_CFG).where(SHOP_CFG.K.eq(key)).fetchAny();
		if(record==null) {
			return false;
		}
		return true;
	}

	/**
	 * 设置配置key对应value
     *
	 * @param  key
	 * @param  value
	 * @return
	 */
	protected int set(String key, String value) {
		if (!isHaveKey(key)) {
			return db().insertInto(SHOP_CFG, SHOP_CFG.K, SHOP_CFG.V).values(key, value).execute();
		} else {
			return db().update(SHOP_CFG).set(SHOP_CFG.V, value).where(SHOP_CFG.K.eq(key)).execute();
		}
	}

	/**
	 * 设置配置key对应value
     *
	 * @param  key
	 * @param  value
	 * @param  db
	 * @return
	 */
	protected int set(DSLContext db, String key, String value) {
		if (!isHaveKey(key)) {
			return db.insertInto(SHOP_CFG, SHOP_CFG.K, SHOP_CFG.V).values(key, value).execute();
		} else {
			return db.update(SHOP_CFG).set(SHOP_CFG.V, value).where(SHOP_CFG.K.eq(key)).execute();
		}
	}

	/**
	 * 设置其他类型数据配置
     *
	 * @param  <T>
	 * @param  key
	 * @param  value
	 * @param  toClass
	 * @return
	 */
	protected <T> int set(String key, T value, Class<? extends T> toClass) {
        return this.set(key, value.toString());
    }

	/**
	 * 设置其他类型数据配置
     *
	 * @param  db
	 * @param  <T>
	 * @param  key
	 * @param  value
	 * @param  toClass
	 * @return
	 */
	protected <T> int set(DSLContext db, String key, T value, Class<? extends T> toClass) {
		return this.set(db, key, value.toString());
	}

	/**
	 * 设置json对象数据配置
     *
	 * @param  key
	 * @param  value
	 * @return
	 */
	protected int setJsonObject(String key, Object value) {
		return this.set(key, Util.toJson(value));
	}

	/**
	 * 设置json对象数据配置
     *
	 * @param  key
	 * @param  value
	 * @param  db
	 * @return
	 */
	protected int setJsonObject(DSLContext db, String key, Object value) {
		return this.set(db, key, Util.toJson(value));
	}

	/**
	 * 获取配置key对应value,未取到时，则返回默认值
     *
	 * @param  key
	 * @param  defaultValue
	 * @return
	 */
	protected String get(String key, String defaultValue) {
		String val = get(key);
		return val == null ? defaultValue : val;
	}

	/**
	 * 按T类型取配置key对应value
     *
	 * @param  <T>
	 * @param  key
	 * @param  toClass
	 * @param  defaultValue
	 * @return
	 */
	protected <T> T get(String key, Class<? extends T> toClass, T defaultValue) {
		return Util.convert(get(key), toClass, defaultValue);
	}

    /**
     * Gets 2 object.按T类型取配置key对应value
     * 支持直接将json字符串转换为复杂对象
     *
     * @param <T>          the type parameter
     * @param key          the key
     * @param reference    the reference
     * @param defaultValue the default value
     * @return the 2 object
     */
    protected <T> T getJsonObject(String key, TypeReference<T> reference, T defaultValue) {
        String s = db().select(SHOP_CFG.V).from(SHOP_CFG).where(SHOP_CFG.K.eq(key)).fetchOneInto(String.class);
        if (StringUtils.isBlank(s)) {
            return defaultValue;
        }
        T t = Util.json2Object(s, reference, false);
        return t != null ? t : defaultValue;
    }

	/**
	 * 按T类型取配置key对应json对象的value
	 *
	 * @param  <T>
	 * @param  key
	 * @param  toClass
	 * @return
	 */
	protected <T> T getJsonObject(String key, Class<? extends T> toClass) {
		String value = get(key);
		if (null != value) {
			return Util.parseJson(value, toClass);
		} else {
			return null;
		}
	}

	/**
	 * 按T类型取配置key对应json对象的value
	 *
	 * @param  <T>
	 * @param  key
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected <T> T getJsonObject(String key, TypeReference valueTypeRef) {
		return Util.parseJson(get(key), valueTypeRef);
	}

	/**
	 * 按T类型取配置key对应json对象的value,如果未取到，则返回默认值
	 *
	 * @param  <T>
	 * @param  key
	 * @param  toClass
	 * @param  defaultValue
	 * @return
	 */
	protected <T> T getJsonObject(String key, Class<? extends T> toClass, T defaultValue) {
		T result = getJsonObject(key, toClass);
		if (result == null) {
			return defaultValue;
		}
		return result;
	}
}
