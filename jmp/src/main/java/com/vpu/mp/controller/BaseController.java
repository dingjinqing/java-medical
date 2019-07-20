package com.vpu.mp.controller;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.vpu.mp.service.pojo.shop.summary.visit.VisitPageParam;
import org.jooq.meta.firebird.FirebirdDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.Util;
import com.vpu.mp.service.saas.SaasApplication;

/**
 * 
 * @author 新国
 *
 */

public class BaseController {
	@Autowired
	protected SaasApplication saas ;

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected Environment env;
	

	/**
	 * Json输出
	 * @param resultCode
	 * @param content
	 * @return
	 */
	public JsonResult result(JsonResultCode resultCode, Object content,Object ...args) {
		String language = getLang();
		return (new JsonResult()).result(language, resultCode, content,args);
	}

	public JsonResult success() {
		return result(JsonResultCode.CODE_SUCCESS, null);
	}

	public JsonResult success(Object content) {
		return result(JsonResultCode.CODE_SUCCESS, content);
	}

	public JsonResult fail(JsonResultCode resultCode,Object ...args) {
		return result(resultCode, null,args);
	}


	public JsonResult fail() {
		return result(JsonResultCode.CODE_FAIL, null);
	}

	protected boolean isPost() {
		return "POST".equals(request.getMethod());
	}

	protected String post(String key) {
		return isPost() ? request.getParameter(key) : null;
	}

	protected String input(String key) {
		return request.getParameter(key);
	}

	protected Map<String, String[]> input() {
		return request.getParameterMap();
	}

	protected String getLang() {
		return request.getHeader("V-Lang");
	}

	/**
	 * 响应错误信息
	 * @param apiMessageKey 错误信息key，对应JsonResultCode的message
	 * @return
	 */
	public JsonResult fail(String apiMessageKey) {
		for (JsonResultCode code : JsonResultCode.values()) {
			if (code.getMessage().equals(apiMessageKey)) {
				return result(code, null);
			}
		}
		throw new RuntimeException(apiMessageKey + " not defined in JsonResultCode.");
	}

	/**
	 * 输入参数Map
	 * @param delim
	 * @return
	 */
	protected Map<String, String> inputMap(String delim) {
		Map<String, String> result = new HashMap<String, String>(0);
		Map<String, String[]> maps = this.input();
		for (String key : maps.keySet()) {
			String value = StringUtils.arrayToDelimitedString(maps.get(key), delim);
			result.put(key, value);
		}
		return result;
	}

	/**
	 * 输入参数Map
	 * @return
	 */
	protected Map<String, String> inputMap() {
		return inputMap(",");
	}

	/**
	 * 主站路径URL
	 * @param path
	 * @return
	 */
	public String mainUrl(String path) {
		return Util.imageUrl(path, request.getScheme());
	}

	/**
	 * 图片路径URL
	 * @param path
	 * @return
	 */
	public String imageUrl(String path) {
		return Util.imageUrl(path, request.getScheme());
	}


	/**
	 * 返回带有国际化的 json 响应
	 *
	 * 使用方法：在入参或出参对象及其中嵌套对象中需要翻译的字段中添加 {@link I18N} 注解并指定对应的
	 * properties 文件名，确保这些字段的值均为 properties 中的某个 key，调用此方法后会通过
	 * {@link BaseController#translateFields(Object)} 方法自动对所有这些字段进行翻译
	 *
	 * 目前支持的属性类型：{@code Class<String>, Class<? extends Object> Class<List<? extends Object>>, Class<List<String>>}
	 * 分别对应 json 中的 字符串（string）、对象（object）以及字符串数组（array）
	 *
	 * 使用示例：{@link com.vpu.mp.controller.admin.AdminSummaryController#getVisitPage(VisitPageParam)}
	 *
	 * @param content 出参对象
	 * @author 郑保乐
	 */
	protected JsonResult i18nSuccess(Object content) {
		translateFields(content);
		return result(JsonResultCode.CODE_SUCCESS, content);
	}

	/**
	 * 递归查找对象中需要翻译的字段并进行翻译
	 *
	 * @param object 出参对象
	 */
	@SuppressWarnings("unchecked")
	private void translateFields(Object object) {
		if (isRawType(object)) {
			return;
		}
		String lang = request.getHeader("V-Lang");
		if (StringUtils.isEmpty(lang)) {
			lang = "zh_CN";
		}
		Class<?> clz = object.getClass();
		Field[] fields = clz.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				I18N annotation = getI18nAnnotation(field);
				if (field.getType().equals(String.class) && null != annotation) {
					String value = (String) field.get(object);
					String fileName = annotation.propertiesFileName();
					String realValue = translate(fileName, lang, value, value);
					field.set(object, realValue);
				}
				if (field.getType().isAssignableFrom(List.class)) {
					ParameterizedType type = (ParameterizedType) field.getGenericType();
					Class<?> realType = (Class<?>) type.getActualTypeArguments()[0];
					if (null != annotation && realType.equals(String.class)) {
						String fileName = annotation.propertiesFileName();
						List<String> list = null;
						list = (List<String>) field.get(object);
						String finalLang = lang;
						List<String> translated = Objects.requireNonNull(list).parallelStream()
								.map(i -> translate(fileName, finalLang, i, i))
								.collect(Collectors.toList());
						field.set(object, translated);
					} else {
						List<?> o = (List<?>) field.get(object);
						o.forEach(this::translateFields);
					}
				} else {
					Object o = field.get(object);
					translateFields(o);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 判断对象是否原生类型（不可再递归翻译）
	 */
	private boolean isRawType(Object object) {
		return object instanceof String || object instanceof Integer || object instanceof Double;
	}

	/**
	 * 获取国际化注解
	 */
	private I18N getI18nAnnotation(Field field) {
		return field.getDeclaredAnnotation(I18N.class);
	}

	/**
	 * 转换语言
	 */
	private String translate(String prefix, String language, String message, String defaultMessage) {
		language = org.apache.commons.lang3.StringUtils.isBlank(language) ? "zh_CN" : language;
		ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
		source.setDefaultEncoding("UTF-8");
		source.setBasename("static/i18n/" + prefix);
		MessageSourceAccessor accessor = new MessageSourceAccessor(source);
		String[] languages = language.split("_");
		Locale locale = new Locale(languages[0], languages[1]);
		return accessor.getMessage(message, defaultMessage, locale);
	}
}
