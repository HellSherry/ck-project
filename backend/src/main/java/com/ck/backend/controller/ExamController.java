package com.ck.backend.controller;

import com.ck.backend.domain.LoginUser;
import com.ck.backend.mapper.PaperMapper;
import com.ck.backend.mapper.QuestionMapper;
import com.ck.backend.pojo.Answer;
import com.ck.backend.pojo.Paper;
import com.ck.backend.pojo.Question;
import com.ck.backend.util.PageBean;
import com.ck.backend.util.ResponseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author HellSherrry
 * &#064;date 2024/1/3-18:49
 * @version 1.0
 * &#064;description
 */
@RestController
@RequestMapping("exam")
public class ExamController {

    @Resource
    PaperMapper paperMapper;
    @Resource
    QuestionMapper questionMapper;
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @RequestMapping("getUserExamsAreOngoing")
    public ResponseEntity getUserExamsAreOngoing(Integer paperid) {
         return ResponseEntity.ok(redisTemplate.keys("paper-"+paperid+"*Time"));
    }


        @RequestMapping("calculateTheScore")
    public ResponseEntity calculateTheScore(@AuthenticationPrincipal LoginUser customUser, Integer paperId, String answers) {
        ObjectMapper objectMapper = new ObjectMapper();
        String[] split = answers.split("-");
        Integer totalScore = paperMapper.getExamFractionById(paperId);
        if (totalScore == null) {
            totalScore = 100;
        }
        int rCount = 0;

        double score;
        try {
            Integer userId = customUser.getUser().getUserId();
            ArrayList<List> arrayList = objectMapper.readValue(redisTemplate.opsForValue().get("paper-" + paperId + ":" + userId).toString(), ArrayList.class);

            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).get(1).equals(split[i])) {
                    rCount++;
                } else {
                    arrayList.get(i).set(1, split[i]);
                }
            }
            score = ((float) rCount / (float) split.length) * totalScore;
            Answer answer = new Answer(userId, paperId, objectMapper.writeValueAsString(arrayList), score, (int) (System.currentTimeMillis() - Long.valueOf(redisTemplate.opsForValue().get("paper-" + paperId + ":" + userId + "-startTime").toString())));
            System.out.println("answer = " + answer);
            if (paperMapper.insertAnswer(answer) > 0) {
                redisTemplate.delete("paper-" + paperId + ":" + userId);
                redisTemplate.delete("paper-" + paperId + ":" + userId + "-startTime");
            }
            System.out.println(arrayList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(score);
    }




    @RequestMapping("getPaper")
    public ResponseEntity getPaper(@AuthenticationPrincipal LoginUser customUser, Integer paperId) {
        int userId = customUser.getUser().getUserId();
        Object o = redisTemplate.opsForValue().get("paper-" + paperId + ":" + userId);
        if (o == null) {

            Double score = paperMapper.getScore(paperId, userId);
            if (score != null) {
                return ResponseEntity.ok(score);
            }
            if (paperMapper.findOutIfItExists(paperId, userId) > 0) {
                if (redisTemplate.getExpire("papertime:" + paperId) == -2) {
                    return ResponseEntity.error("这场考试结束了哦");
                }
                ObjectMapper objectMapper = new ObjectMapper();
                //jackonstring转对象
                try {
                    Paper paper = objectMapper.readValue(redisTemplate.opsForValue().get("paper:" + paperId).toString(), Paper.class);
                    if (paper.getStarttime().getTime() > System.currentTimeMillis()) {
                        return ResponseEntity.error("考试还没开始");
                    }
                    //jackon string 转集合对象
                    List<Question> questions = objectMapper.readValue(paper.getQuestions(), new TypeReference<List<Question>>() {
                    });
//                    for (int i = 1; i < 6; i++) {
//                        System.out.println("第" + i + "次打乱：");
                    Collections.shuffle(questions);
                    Collections.shuffle(questions);
                    System.out.println(questions);
//                    }


                    List<String[]> map = new ArrayList<>();

                    ArrayList<String[]> returnMap = new ArrayList();
                    questions.forEach(item -> {
                        System.out.println(item);
                        map.add(new String[]{item.getId().toString(), item.getRanswer()});
                        returnMap.add(new String[]{item.getQuestion(), item.getAnswers(), String.valueOf((item.getRanswer().trim().length() + 1) / 2)});
                    });
                    System.out.println(map);
//                    System.out.println("objectMapper.writeValueAsString(map) = " + objectMapper.writeValueAsString(returnMap));

                    //将map转换成json
                    redisTemplate.opsForValue().set("paper-" + paperId + ":" + userId, objectMapper.writeValueAsString(map));
                    redisTemplate.opsForValue().set("paper-" + paperId + ":" + userId + "-startTime", System.currentTimeMillis());


                    return ResponseEntity.ok(returnMap);


//                    System.out.println("questions = " + questions);
//                    System.out.println("paper = " + paper);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

//                redisTemplate.opsForValue().set("paper-" + paperId + ":" + userId, "1");
            } else {
                return ResponseEntity.error("没有找到这场考试，稍后再试试");
            }
        } else {

            if (redisTemplate.getExpire("papertime:" + paperId) == -2) {
                return ResponseEntity.error("这场考试结束了哦");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            //jackonstring转对象

            List<String[]> resultMap;
            try {
                HashMap<String, String[]> map = new HashMap<>();
                resultMap = new ArrayList<>();
                Paper paper = objectMapper.readValue(redisTemplate.opsForValue().get("paper:" + paperId).toString(), Paper.class);
                if (paper.getStarttime().getTime() > System.currentTimeMillis()) {
                    return ResponseEntity.error("考试还没开始");
                }
                objectMapper.readValue(paper.getQuestions(), new TypeReference<List<Question>>() {
                }).forEach(e -> {
                    map.put(e.getId().toString(), new String[]{e.getQuestion(), e.getAnswers(), String.valueOf((e.getRanswer().trim().length() + 1) / 2)});
                });

                objectMapper.readValue(o.toString(), ArrayList.class).forEach((k) -> {
                    List k1 = (List) k;
                    resultMap.add(map.get(k1.getFirst()));
                });


            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            //jackon string 转集合对象

            return ResponseEntity.ok(resultMap);
        }

//        redisTemplate.opsForValue().set("paper:" + paper.getPaperid(), jackon.writeValueAsString(paper));
//        customUser.getUser();


//        int page = 0; // 第一页
//        int size = 10; // 每页条目数
//        Sort sort = Sort.by(Sort.Direction.DESC, "fieldName"); // 根据字段进行降序排序，替换 "fieldName" 为实际的字段名
//
//        Pageable pageable = PageRequest.of(page, size, sort);
//        System.out.println("paper = " +paper);
//        System.out.println("pageRequest = " + pageable);
//        return ResponseEntity.ok(this.paperService.queryByPage(paper, (PageRequest) pageable));
    }

    @RequestMapping("getExamById")
    public ResponseEntity getExamById(Integer paperId) {
        return ResponseEntity.ok(paperMapper.getExamById(paperId));
    }

    @RequestMapping("selectGrades")
    public ResponseEntity selectGrades(Integer classId,Integer paperId) {
        return ResponseEntity.ok(paperMapper.selectGrades(paperId,classId));
    }

    @RequestMapping("selectHistoryListByIdLimit")
    public ResponseEntity selectHistoryListByIdLimit(@AuthenticationPrincipal LoginUser customUser, Integer page) {
        return ResponseEntity.ok(paperMapper.selectHistoryListByIdLimit(customUser.getUser().getUserId(), page));
    }

    @RequestMapping("getHistoryDetail")
    public ResponseEntity getHistoryDetail(@AuthenticationPrincipal LoginUser customUser, Integer paperId) {

        Answer answer = paperMapper.selectGradeByPaperId(customUser.getUser().getUserId(), paperId);
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<ArrayList> arrayList;
        try {
            arrayList = objectMapper.readValue(answer.getAnswerContent(), ArrayList.class);
            arrayList.forEach(e -> {
                Question question = questionMapper.queryById(Integer.parseInt(e.get(0).toString()));

                e.set(0, question);
            });
            answer.setAnswerContent(objectMapper.writeValueAsString(arrayList));
         } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        return ResponseEntity.ok(answer);
    }

    @RequestMapping("getExamList")
    public ResponseEntity getExamList(@AuthenticationPrincipal LoginUser customUser) {
        return ResponseEntity.ok(paperMapper.selectListById(customUser.getUser().getUserId()));
    }

    @RequestMapping("getExamTTL")
    public ResponseEntity getExamTTL(Integer paperId) {
        return ResponseEntity.ok(redisTemplate.getExpire("papertime:" + paperId));
    }


}
