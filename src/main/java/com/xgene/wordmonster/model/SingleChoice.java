package com.xgene.wordmonster.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SingleChoice {
    private String english;
    private String chinese;
    private List<String> chineseQuestions;
}
