package com.xgene.wordmonster.service;

import com.xgene.wordmonster.model.Category;
import com.xgene.wordmonster.model.SingleChoice;
import com.xgene.wordmonster.model.entity.WordEntity;
import com.xgene.wordmonster.model.req.UserResultReq;
import com.xgene.wordmonster.model.res.UserResultRes;
import com.xgene.wordmonster.repo.WordRepo;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExamService {
    @Autowired
    WordRepo wordRepo;

    public List<SingleChoice> getExam(int count, List<Category> categories) {
        List<SingleChoice> exam = new ArrayList<>();
        List<WordEntity> wordEntities = new ArrayList<>();
        categories.forEach(category->{
            wordEntities.addAll(wordRepo.findByCategory(category));
        });
//        List<WordEntity> wordEntities = wordRepo.findByCategoryIn(categories);
        Set<Integer> usedSet=new HashSet<>();
        while (exam.size() < count) {
            int index = RandomUtils.nextInt(0, wordEntities.size());
            if (usedSet.contains(index)) {
                continue;
            }
            WordEntity word = wordEntities.get(index);
            List<String> questions = new ArrayList<>();
            int one = RandomUtils.nextInt(0, wordEntities.size());
            int two = RandomUtils.nextInt(0, wordEntities.size());
            int three = RandomUtils.nextInt(0, wordEntities.size());
            questions.add(wordEntities.get(one).getChinese());
            questions.add(wordEntities.get(two).getChinese());
            questions.add(wordEntities.get(three).getChinese());
            questions.add(word.getChinese());
            Collections.shuffle(questions);
            SingleChoice singleChoice = new SingleChoice(word.getId(), word.getEnglish(), word.getChinese(), questions);
            exam.add(singleChoice);
            usedSet.add(index);
        }
        return exam;
    }

    public UserResultRes calculateScore(UserResultReq userResultReq, Map<Long, String> resultMap) {
        return new UserResultRes();
    }

    public UserResultRes calculateScore(UserResultReq userResultReq, Map<Long, String> resultMap, Map<Long, String> errorMap) {
        Map<String, String> wrongMap = new HashMap<>();
        for (Map.Entry<Long, String> idMessage : errorMap.entrySet()) {
            Optional<WordEntity> byId = wordRepo.findById(idMessage.getKey());
            byId.ifPresent(wordEntity -> wrongMap.put(wordEntity.getEnglish(), idMessage.getValue()));
        }
        UserResultRes res = new UserResultRes();
        res.setCategory(userResultReq.getCategory());
        res.setUserId(userResultReq.getUserId());
        res.setSeconds(0);
        res.setWrongMap(wrongMap);
        return res;
    }
}
