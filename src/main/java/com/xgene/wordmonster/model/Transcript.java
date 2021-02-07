package com.xgene.wordmonster.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Data
@ToString
@NoArgsConstructor
@JacksonXmlRootElement(localName = "transcript")
public class Transcript implements Serializable {
    @JacksonXmlElementWrapper(useWrapping = false)
    List<Text> text;
}
