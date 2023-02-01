package com.jirayutcc.webflux.webservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.jirayutcc.webflux.webservice.constant.Constant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

public class Utils {

    public static final Random random = new Random();

    public static final ObjectMapper staticObjectMapper = getObjectMapper();

    public static String getRandomUUID() {
        return UUID.randomUUID().toString();
    }

    public static String generateNowDateTimeWithFormat(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    public static ObjectMapper getObjectMapper() {
        return getObjectMapper(Constant.DateTimeFormat.DATE_TIME_FORMAT_WITH_SPACE);
    }

    public static ObjectMapper getObjectMapper(String dateTimeFormat) {
        var mapper = new ObjectMapper();
        var module = new JavaTimeModule();
        var localDateTimeDeserializer = new LocalDateTimeDeserializer(
                DateTimeFormatter.ofPattern(dateTimeFormat)
        );
        var localDateTimeSerializer = new LocalDateTimeSerializer(
                DateTimeFormatter.ofPattern(dateTimeFormat)
        );

        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
        module.addSerializer(LocalDateTime.class, localDateTimeSerializer);

        mapper.registerModule(module);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

        return mapper;
    }

    public static String writeValueAsString(Object obj) throws JsonProcessingException {
        return staticObjectMapper.writeValueAsString(obj);
    }
}
