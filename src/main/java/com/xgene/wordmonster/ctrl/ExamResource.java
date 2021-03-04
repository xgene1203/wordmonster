package com.xgene.wordmonster.ctrl;

import com.xgene.wordmonster.model.Category;
import com.xgene.wordmonster.model.SingleChoice;
import com.xgene.wordmonster.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/exam")
@RestController
public class ExamResource {
    @Autowired
    ExamService examService;

    @GetMapping
    public List<SingleChoice> getExam(@RequestParam(value = "count") int count, @RequestParam(value = "categories", defaultValue = "{CAP_I,CAP_II,SAT_I,SAT_II}") List<Category> categories) {
        List<SingleChoice> exam = examService.getExam(count, categories);
        return exam;
    }

//    @RequestMapping
//    public String field(Model model){
//        List<SingleChoice> exam = examService.getExam(count, categories);
//        user.setName("Xiaohong");
//        user.setSex(0);
//        model.addAttribute("user", user);
//        //Gender setting
//        Map<String, Object> sexes = new HashMap<String, Object>();
//        sexes.put("male", 1);
//        sexes.put("female", 0);
//        model.addAttribute("sexes", sexes);
//        return "field";
//    }
}
