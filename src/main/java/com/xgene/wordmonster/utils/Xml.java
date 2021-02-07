package com.xgene.wordmonster.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.xgene.wordmonster.model.Transcript;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Slf4j
public class Xml {
    private static ObjectMapper xmlMapper = new XmlMapper();

    public static Transcript toTranscript(String xml) {
        try {
            Transcript transcript = xmlMapper.readValue(xml, Transcript.class);
            log.info("xml:{}, transcript:{}\n", xml, transcript);
            return transcript;
        } catch (JsonProcessingException e) {
            log.warn("parse xml error:{}, xml:{}", ExceptionUtils.getStackTrace(e), xml);
        }
        return null;
    }

    public static Transcript toTranscript2(String xml) {
        try {
            JsonNode node = xmlMapper.readTree(xml.getBytes());
            ObjectMapper jsonMapper = new ObjectMapper();
            String json = jsonMapper.writeValueAsString(node);
            log.info("xml:{}, transcript:{}\n", xml, json);
            return new Transcript();
        } catch (Exception e) {
            log.warn("parse xml error:{}, xml:{}", ExceptionUtils.getStackTrace(e), xml);
        }
        return null;
    }
}
