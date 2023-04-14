package com.digitalqd.production.utils;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonConvert {

    private ObjectMapper objectMapper;

    public JsonConvert() {
        objectMapper = new ObjectMapper();
        // 允许无双引号
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 忽略不匹配字段
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    // 去除引号
    private String removeQuotation(String str) {
        str = str.replace("\"", "");
        str = str.replace("'", "");
        return str;
    }
    
    // 参数不要传入" T ", 必须使用 " TypeReference<T> valueTypeRef "
    public <T> T jsonToType(String json_str, TypeReference<T> valueTypeRef) {
        // json_str = removeQuotation(json_str);
        try {
            return objectMapper.readValue(json_str, valueTypeRef);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
