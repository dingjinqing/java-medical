package com.vpu.mp.service.foundation.util;

import static org.springframework.util.StringUtils.isEmpty;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

/**
 * @author 郑保乐
 */
@Component
public class VoTranslator {

    private HttpServletRequest request;

    private static final String HEADER_LANG = "V-Lang";
    private static final String DEFAULT_LANG = "zh_CN";

    public VoTranslator(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * 递归查找对象中需要翻译的字段并进行翻译
     *
     * @param object 出参对象
     */
    public void translateFields(Object object) {
        if (null == object) {
            return;
        }
        if (isRawType(object)) {
            return;
        }
        Class<?> clz = object.getClass();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                translateStringValue(field, object);
                translateListValue(field, object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 转换语言
     */
    public String translate(String prefix, String message, String defaultMessage) {
        return Util.translateMessage(getLanguage(), message, defaultMessage, prefix);
    }

    /**
     * 翻译 List 类型
     */
    @SuppressWarnings("unchecked")
    private void translateListValue(Field field, Object object) throws IllegalAccessException {
        I18N annotation = getI18nAnnotation(field);
        if (List.class.isAssignableFrom(field.getType())) {
            ParameterizedType type = (ParameterizedType) field.getGenericType();
            Class<?> realType = (Class<?>) type.getActualTypeArguments()[0];
            if (null != annotation && realType.equals(String.class)) {
                String fileName = annotation.propertiesFileName();
                List<String> list = (List<String>) field.get(object);
                if (null != list) {
                    List<String> translated = new LinkedList<>();
                    for (String s : list) {
                        String translate = translate(fileName, s, s);
                        translated.add(translate);
                    }
                    field.set(object, translated);
                }
            } else {
                List<?> o = (List<?>) field.get(object);
                if (null != o) {
                    o.forEach(this::translateFields);
                }
            }
        } else {
            Object o = field.get(object);
            translateFields(o);
        }
    }

    /**
     * 翻译 String 类型
     */
    private void translateStringValue(Field field, Object object) throws IllegalAccessException {
        I18N annotation = getI18nAnnotation(field);
        if (field.getType().equals(String.class) && null != annotation) {
            String value = (String) field.get(object);
            String fileName = annotation.propertiesFileName();
            String realValue = translate(fileName, value, value);
            field.set(object, realValue);
        }
    }

    /**
     * 取当前语言，默认中文
     */
    private String getLanguage() {
        String language = request.getHeader(HEADER_LANG);
        if (isEmpty(language)) {
            return DEFAULT_LANG;
        }
        return language;
    }

    /**
     * 判断对象是否原生类型（不可再递归翻译）
     */
    private boolean isRawType(Object object) {
        return object instanceof String || object instanceof Number;
    }

    /**
     * 获取国际化注解
     */
    private I18N getI18nAnnotation(Field field) {
        return field.getDeclaredAnnotation(I18N.class);
    }
}
