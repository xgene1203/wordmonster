package com.xgene.wordmonster.model.res;

import com.xgene.wordmonster.model.Category;
import lombok.Data;

import java.util.Map;

@Data
public class UserResultRes {
    private String userId;
    private Category category;
    private int score;
    private int seconds;
    private Map<String, String> wrongMap;
}
