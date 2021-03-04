package com.xgene.wordmonster.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SingleChoice {
    private long id;
    private String english;
    private String chinese;
    private List<String> chineseQuestions;
}
