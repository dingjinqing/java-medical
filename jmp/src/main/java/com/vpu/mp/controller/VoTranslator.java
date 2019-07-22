package com.vpu.mp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 郑保乐
 */
@Component
public class VoTranslator {

    private HttpServletRequest request;

    @Autowired
    public VoTranslator(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * 递归查找对象中需要翻译的字段并进行翻译
     *
     * @param object 出参对象
     */
    @SuppressWarnings("unchecked")
    void translateFields(Object object) {
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
                        List<String> list;
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
