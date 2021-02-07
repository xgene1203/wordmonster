package com.xgene.wordmonster.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Text implements Serializable {
    @JacksonXmlProperty(localName = "start", isAttribute = true)
    private double start;
    @JacksonXmlProperty(localName = "dur", isAttribute = true)
    private double dur;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private double end;
    @JacksonXmlText(value = true)
    private String text;
}
