package com.vpu.mp.service.foundation.util;

import java.io.IOException;

import org.apache.commons.lang3.math.NumberUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 * Json反序列化 true/false 转换为 1/0
 * @author 黄壮壮
 *
 */
public class NumericBooleanDeserializer extends JsonDeserializer<Byte>{

	@Override
	public Byte deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		boolean res = p.getBooleanValue();
		return res ? NumberUtils.BYTE_ONE : NumberUtils.BYTE_ZERO;
	}

}
