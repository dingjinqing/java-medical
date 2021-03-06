package com.vpu.sql.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * @author
 */
public class JsonUtil {

    static final ObjectMapper MAPPER = new ObjectMapper();



    public static Map<String,String> toMap(String jsonStr){
        TypeReference<HashMap<String,String>> typeRef
                = new TypeReference<HashMap<String,String>>() {};
        Map<String,String>  result =Maps.newHashMap();
        try {
            result =  MAPPER.readValue(jsonStr, typeRef);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static <T>T toEntity(String jsonStr,Class<T> clz){
        try {
             return MAPPER.readValue(jsonStr, clz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T>T toEntityAndIgnoreExtraFields(String jsonStr,Class<T> clz){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper.readValue(jsonStr, clz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
