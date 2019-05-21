package com.vpu.mp.support;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.jooq.tools.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 
 * @author 新国
 *
 */
public class LineToHumpHandler extends HandlerMethodArgumentResolverComposite {
	private static MappingJackson2HttpMessageConverter converter;

	static {
		converter = new MappingJackson2HttpMessageConverter();
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(LineConvertHump.class);
	}

	private String underLineToCamel(String str) {
		String regex = "_(\\w)";
		Matcher matcher = Pattern.compile(regex).matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	@SuppressWarnings("deprecation")
	@Override
	public Object resolveArgument(
			MethodParameter methodParameter,
			ModelAndViewContainer modelAndViewContainer,
			NativeWebRequest nativeWebRequest,
			WebDataBinderFactory webDataBinderFactory)
			throws Exception {

		HttpServletRequest servletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
		ServletServerHttpRequest inputMessage = new ServletServerHttpRequest(servletRequest);
		Object result = null;
		try {
			Type genericParameterType = methodParameter.getGenericParameterType();
			String contentType = servletRequest.getContentType();
			if (MediaType.APPLICATION_JSON_VALUE.equals(contentType)) {
				result = converter.read(Class.forName(genericParameterType.getTypeName()), inputMessage);
			} else {
				Object obj = BeanUtils.instantiateClass(methodParameter.getParameterType());
				BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
				Map<String, String[]> parameterMap = nativeWebRequest.getParameterMap();
				for (Map.Entry<String, String[]> map : parameterMap.entrySet()) {
					String paramName = map.getKey();
					String[] paramValue = map.getValue();
					Field[] declaredFields = obj.getClass().getDeclaredFields(); 
					for (Field declaredField : declaredFields) {
						// 如果pojo里有带下划线则直接设置
						if (declaredField.getName().contains("_") && paramName.equals(declaredField.getName())) {
							wrapper.setPropertyValue(paramName, paramValue);
							break;
						}
						String underLineParamName = underLineToCamel(paramName);
						if (declaredField.getName().equals(underLineParamName)) {
							if (declaredField.getType().getName().equals("java.sql.Timestamp")) {
								if(paramValue.length == 0 || StringUtils.isEmpty(paramValue[0])){
									wrapper.setPropertyValue(underLineParamName,null);
								}else {
									wrapper.setPropertyValue(underLineParamName, Timestamp.valueOf(paramValue[0]));	
								}
							} else {
								wrapper.setPropertyValue(underLineParamName, paramValue);
							}
							break;
						}
					}
				}
				result = obj;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
