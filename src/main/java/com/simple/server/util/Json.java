package com.simple.server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class Json {
    public static ObjectMapper myObjectMapper = defaultObjectMapper() ;

    public  static ObjectMapper defaultObjectMapper(){
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return  om;
    }

    public static JsonNode parse(String jsonSrc) throws IOException {
        return myObjectMapper.readTree(jsonSrc);
    }

    public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException {
        return myObjectMapper.treeToValue(node,clazz);
    }

    public static JsonNode toJson(Object o) {
        return myObjectMapper.valueToTree(o);
    }

    public static String stringify(JsonNode jNode) throws JsonProcessingException{
        return  generateJson(jNode,false);
    }

    public static String stringifyPretty(JsonNode jNode) throws JsonProcessingException{
        return  generateJson(jNode,true);
    }

    private static String generateJson(Object o, boolean pretty) throws JsonProcessingException {
        ObjectWriter myObjectWriter = myObjectMapper.writer();

        if(pretty) {
            myObjectWriter = myObjectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }

        return myObjectWriter.writeValueAsString(o);
    }
}
