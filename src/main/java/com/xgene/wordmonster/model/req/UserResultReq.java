package com.xgene.wordmonster.model.req;

import com.xgene.wordmonster.model.Category;
import lombok.Data;

import java.util.Map;

@Data
public class UserResultReq {
    private String userId;
    private Category category;
    private long count;
    private Map<Long, String> map;
}
