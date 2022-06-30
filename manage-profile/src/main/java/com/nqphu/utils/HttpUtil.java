
package com.nqphu.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phu
 */
public class HttpUtil {

    private String value;

    public HttpUtil(String value) {
        this.value = value;
    }
    
    public <T> T toModel(Class<T> tClass) {
        try {
            return new ObjectMapper().readValue(this.value, tClass);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(HttpUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    

    public static HttpUtil of(BufferedReader reader) {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HttpUtil(sb.toString());
    }

    public String getValue() {
        return value;
    }
    
    
}
