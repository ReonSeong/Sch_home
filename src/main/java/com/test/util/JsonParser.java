/**
 * File Name : JsonParser.java
 *
 * Updated Date     Version     User        Change log
 * 2026-05-01           0.1     ReonQ       Published
 *
 * Now Version : 0.1
 *
 * Description:
 * Return object to json string
 */

package com.test.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.controller.DashboardController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonParser {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("*****JsonParser Error*****", e.getMessage());
            return "[]"; // if error return empty
        }
    }
}
