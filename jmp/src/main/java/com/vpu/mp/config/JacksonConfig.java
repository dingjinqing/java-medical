package com.vpu.mp.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Configuration
public class JacksonConfig {

    @Value("${spring.jackson.date-format}")
	private String dateFormat;

    @Bean
    public SimpleModule customerTimestampModule(){
        Logger logger= LoggerFactory.getLogger(this.getClass());

        logger.debug("customer-timestamp-module begin init");
        SimpleModule simpleModule=new SimpleModule("customer-timestamp-module");
        simpleModule.addDeserializer(Timestamp.class,new CustomerTimestampDeserilizer());
        simpleModule.addSerializer(Timestamp.class,new CustomerTimestampSerilizer());
        logger.debug("customer-timestamp-module end init");
        return simpleModule;
    }

     class CustomerTimestampDeserilizer extends JsonDeserializer<Timestamp> {

        @Override
        public Timestamp deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String text=p.getText();
            if(text!=null&&text.length()!=0){
                return Timestamp.valueOf(text);
            }
            return null;
        }
    }

    class CustomerTimestampSerilizer extends JsonSerializer<Timestamp>{
        @Override
        public void serialize(Timestamp value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value==null)
                gen.writeNull();
            else {
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat(dateFormat);
                String text=simpleDateFormat.format(value);
                gen.writeString(text);
            }
        }
    }
}
