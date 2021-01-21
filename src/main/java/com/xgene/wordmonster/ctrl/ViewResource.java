package com.xgene.wordmonster.ctrl;

import com.xgene.wordmonster.model.Category;
import com.xgene.wordmonster.model.SingleChoice;
import com.xgene.wordmonster.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping(value = "view")
public class ViewResource {

    @RequestMapping(value = "index")
    public String index(Model model, ModelMap modelMap) {

        modelMap.addAttribute("exam", "exam");
        return "index";
    }

    @Autowired
    ExamService examService;

    @RequestMapping(value = "exam")
    public String exam(Model model, @RequestParam("count") int count, @RequestParam(value = "categories", defaultValue = "CAP_I") Category categories) {
        List<SingleChoice> exams = examService.getExam(count, List.of(categories));
        model.addAttribute("exams", exams);
        return "exam";
    }

}
