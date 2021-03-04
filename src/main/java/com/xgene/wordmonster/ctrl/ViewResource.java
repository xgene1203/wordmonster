package com.xgene.wordmonster.ctrl;

import com.xgene.wordmonster.model.Category;
import com.xgene.wordmonster.model.SingleChoice;
import com.xgene.wordmonster.model.req.UserResultReq;
import com.xgene.wordmonster.model.res.UserResultRes;
import com.xgene.wordmonster.service.ExamService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

//    @RequestMapping(value = "exam")
//    public String exam(Model model, @RequestParam("count") int count, @RequestParam(value = "categories", defaultValue = "CAP_I") Category categories) {
//        List<SingleChoice> exams = examService.getExam(count, List.of(categories));
//        model.addAttribute("exams", exams);
//        UserResultReq req = new UserResultReq();
//        Map<Long, List<String>> map = new HashMap<>();
//        exams.forEach(exam->{
//            map.put(exam.getId(), exam.getChineseQuestions());
//        });
//        req.setMap(map);
//        model.addAttribute("userResultReq", req);
//        return "exam";
//    }

    @RequestMapping(value = "exam")
    public String exam(Model model, @RequestParam("count") int count, @RequestParam(value = "categories", defaultValue = "CAP_I") Category categories, @RequestParam(value = "userId", defaultValue = "") String userId) {
        List<SingleChoice> exams = examService.getExam(count, List.of(categories));
        model.addAttribute("exams", exams);
        UserResultReq req = new UserResultReq();
        req.setUserId(userId);
        model.addAttribute("userResultReq", req);
        return "exam";
    }

    @RequestMapping(value = "result", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public String exam(HttpServletRequest request, Model model, @ModelAttribute(value = "userResultReq") UserResultReq userResultReq, @RequestBody String apps) throws IOException {
        String[] params = URLDecoder.decode(apps, Charset.defaultCharset()).split("&");
        String[] keyValues;

        Map<Long, String> resultMap = new TreeMap<>();
        Map<Long, String> errorMap = new TreeMap<>();
        int correctNum = 0;
        for (String param : params) {
            keyValues = param.split("=");
            if (StringUtils.isNumeric(keyValues[0])) {
                String result = resultMap.get(Long.parseLong(keyValues[0]));
                if (StringUtils.isEmpty(result)) {
                    resultMap.put(Long.parseLong(keyValues[0]), keyValues[1]);
                } else if (result.equals(keyValues[1])) {
                    correctNum += 1;
                    resultMap.put(Long.parseLong(keyValues[0]), keyValues[1]);
                } else {
                    errorMap.put(Long.parseLong(keyValues[0]), result + "=>" + keyValues[1]);
                }
            }
        }


        UserResultRes res = examService.calculateScore(userResultReq, resultMap, errorMap);
        BigDecimal score = BigDecimal.valueOf(correctNum).divide(BigDecimal.valueOf(userResultReq.getCount())).multiply(BigDecimal.valueOf(100));
        res.setScore(score.intValue());
        model.addAttribute("userResultRes", res);
        return "result";
    }

}
