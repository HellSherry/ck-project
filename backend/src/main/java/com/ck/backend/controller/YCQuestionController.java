package com.ck.backend.controller;

 
 
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.ck.backend.entity.*;
import com.ck.backend.entity.Questionbank;
import com.ck.backend.entity.QuestionbankTwo;
import com.ck.backend.mapper.QuestionBankMapper;
import com.ck.backend.util.ResponseEntity;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.List;
 import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description:
 * @author: YinChuan
 * @version:
 * @createDate: 2024/1/9 13:48
 */

@RestController
@CrossOrigin(origins = "*")
public class YCQuestionController {


    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private QuestionBankMapper questionBankMapper;

    @GetMapping("/getPresentData")
    public ResponseEntity getPresentData( Integer userId) {
        return  ResponseEntity.ok(SqlRunner.db().selectList("select questionId,correct from questionbank where  to_days(SubmissionTime)=to_days(now()) and userId="+ userId)) ;
    }


    @GetMapping("/login")
    public ResponseEntity contextLoads(@RequestParam(value = "username", required = false)String username, String password) {
        try {
            System.out.println("username: " + username);
            List<User> user = questionBankMapper.setByName(username);
            if (user == null) {
                return ResponseEntity.error("数据为空");
            }
            System.out.println(user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.error(e.getMessage());
        }
    }

    @GetMapping("/getReportByNumber")
    public ResponseEntity getReportByNumber(String number) {
        return  ResponseEntity.ok(questionBankMapper.getReportByNumber(number)) ;
    }
    @GetMapping("/Question")
    public ResponseEntity question() {
        try {
            List<Question> questions = questionBankMapper.selectQuestionList();
            if (questions == null) {
                return ResponseEntity.error("查询失败");
            }
            for (Question question : questions) {
                System.out.println(question.getCourseid());
            }
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            return ResponseEntity.error(e.getMessage());
        }
    }

    @GetMapping("/QuestionCourse")
    public ResponseEntity question2() {
        try {
            List<QuestionCourse> questions = questionBankMapper.selectQuestion();
            if (questions == null) {
                return ResponseEntity.error("查询失败");
            }
            System.out.println(questions);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            return ResponseEntity.error(e.getMessage());
        }
    }
    @GetMapping("/findByIdTwo")
    public ResponseEntity findByIdTwo(@RequestParam("groupId") int groupId) {
        try {
            QuestionbankTwo questionbankTwo = new QuestionbankTwo();
            ArrayList<QuestionbankTwo> listTwo = new ArrayList<>();
            List<Questionbank> questions = questionBankMapper.selectQuestionByIdTwo(groupId);
            System.out.println(groupId);
            if (questions == null) {
                return ResponseEntity.error("没有数据");
            }
            for (Questionbank question : questions) {
                questionbankTwo.setGName(question.getGname());
                questionbankTwo.setQuestion(question.getQuestion().replaceAll("<br>", ""));
                if (question.getRanswer().length() == 1) {
                    questionbankTwo.setLongestScore("单选题");
                } else {
                    questionbankTwo.setLongestScore("多选题");
                }
                questionbankTwo.setRanswer(question.getRanswer());
                String[] answers = question.getAnswers().replaceAll("[\\[\\]]", " ").split(",");
                questionbankTwo.setAnswers(Arrays.toString(answers));
                questionbankTwo.setAnswer1(answers[0]);
                questionbankTwo.setAnswer2(answers[1]);
                questionbankTwo.setAnswer3(answers[2]);
                questionbankTwo.setAnswer4(answers[3]);
                listTwo.add(questionbankTwo);
                questionbankTwo = new QuestionbankTwo(); // 清空对象，以便处理下一个问题
            }
            System.out.println(listTwo);
            return ResponseEntity.ok(listTwo);
        } catch (Exception e) {
            return ResponseEntity.error(e.getMessage());
        }
    }

    @GetMapping("/findById")
    public ResponseEntity findById(@RequestParam("groupId") int groupId, int userId) {
        System.out.println("groupId:" + groupId + "userId:" + userId);
        try {
            QuestionbankTwo questionbankTwo = new QuestionbankTwo();
            ArrayList<QuestionbankTwo> listTwo = new ArrayList<>();
            List<Questionbank> questions = questionBankMapper.selectQuestionById(groupId,userId);
            System.out.println(groupId);
            if (questions == null) {
                return ResponseEntity.error("没有数据");
            }
            for (Questionbank question : questions) {
                questionbankTwo.setGName(question.getGname());
                questionbankTwo.setQuestion(question.getQuestion().replaceAll("<br>", ""));
                if (question.getRanswer().length() == 1) {
                    questionbankTwo.setLongestScore("单选题");
                } else {
                    questionbankTwo.setLongestScore("多选题");
                }
                questionbankTwo.setRanswer(question.getRanswer());
                String[] answers = question.getAnswers().replaceAll("[\\[\\]]", " ").split(",");
                questionbankTwo.setAnswers(Arrays.toString(answers));
                questionbankTwo.setAnswer1(answers[0]);
                questionbankTwo.setAnswer2(answers[1]);
                questionbankTwo.setAnswer3(answers[2]);
                questionbankTwo.setAnswer4(answers[3]);
                listTwo.add(questionbankTwo);
                questionbankTwo = new QuestionbankTwo(); // 清空对象，以便处理下一个问题
            }
            return ResponseEntity.ok(listTwo);
        } catch (Exception e) {
            return ResponseEntity.error(e.getMessage());
        }
    }
    public int indexQuestionTwo(int id, List<String> selectedAnswer,int userId,String username) throws InterruptedException {
        List<Questionbank> questions = questionBankMapper.selectQuestionByIdTwo(id);
        Questionbank questionBankTwo = new Questionbank();
        System.out.println(questions);
        int number = 0;
        int count = 0;
        int size = selectedAnswer.size();
        for (Questionbank questionbank : questions) {
            questionBankTwo.setGroupId(id);
            questionBankTwo.setDifficulty(new Random().nextInt(5) + 1);
            Long increment = redisTemplate.opsForValue().increment("QuestionBank");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
            String format = String.format("%06d", increment);
            questionBankTwo.setId(sdf.format(new Date()) + format);

            questionBankTwo.setGname(SqlRunner.db().selectObj("SELECT `name` FROM `question_group` WHERE id = "+questionbank.getGroupId()).toString());
//            if (questionbank.getGroupId() == 1) {
//                questionBankTwo.setGname("使用DOM4J解析XML文档");
//
//            } else if (questionbank.getGroupId() == 2) {
//                questionBankTwo.setGname("sdaf");
//            } else if (questionbank.getGroupId() == 3) {
//                questionBankTwo.setGname("ssw");
//            } else if (questionbank.getGroupId() == 4) {
//                questionBankTwo.setGname("sdfsw");
//            } else if (questionbank.getGroupId() == 5) {
//                questionBankTwo.setGname("sdfssdfw");
//            }
            questionBankTwo.setQuestion(questionbank.getQuestion());
            questionBankTwo.setAnswers(questionbank.getAnswers());
            questionBankTwo.setRanswer(questionbank.getRanswer());
            questionBankTwo.setParse(questionbank.getParse());
            questionBankTwo.setResponse(questionbank.getResponse());
            Object value = redisTemplate.opsForValue().get("previousNumber_key");
            questionBankTwo.setCName(SqlRunner.db().selectObj("SELECT `name` FROM `question_course` WHERE id = "+questionbank.getCourseid()).toString());

//            if (questionbank.getCourseid() == 1) {
//                questionBankTwo.setCName("Java开发之框架基础技术");
//            } else {
//                questionBankTwo.setCName("sdafa");
//            }
//            if (value == null) {
//                questionBankTwo.setNumber("1");
//            } else {
//                questionBankTwo.setNumber(String.valueOf((int) value + 1));
//            }
            if (number < size) {
                String s = selectedAnswer.get(number);
                String ranswer = questionbank.getRanswer();
                if (s.equals(ranswer)) {
                    questionBankTwo.setCorrect("回答正确");
                    questionBankTwo.setResponse(s);
                    int add = 100 / questions.size();
                    count = count + add;
                } else {
                    questionBankTwo.setResponse(s);
                    questionBankTwo.setCorrect("回答错误");
                }
            } else {
                questionBankTwo.setCorrect("回答错误");
                questionBankTwo.setResponse("null");
            }
            SimpleDateFormat sdfTwo = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            questionBankTwo.setSubmissionTime(sdfTwo.format(new Date()));
            questionBankTwo.setQuestionId(questionbank.getId());
            ++number;
            if (number == questions.size()) {
                //使用redisTemplate操作数字类型的值
                redisTemplate.opsForValue().set("previousNumber_key", 1);
                if (value != null) {
                    redisTemplate.opsForValue().set("previousNumber_key", (int) value + 1);
                }
            }
            questionBankTwo.setUser(username);
            questionBankTwo.setUserId(userId);
            System.out.println(questionBankTwo);
            int i = questionBankMapper.insertEmp(questionBankTwo);
            Thread.sleep(100);
            System.out.println(i);
        }
        Object valueTwo = redisTemplate.opsForValue().get("previousNumber_key");
        return (int) valueTwo;
    }

    @PostMapping("/submit")
    public ResponseEntity submitAnswer(@RequestBody List<String> selectedAnswer, int id,int userId,String username) throws InterruptedException {
        System.out.println(id);
        System.out.println(selectedAnswer);
        System.out.println(userId);
        System.out.println(username);
        int i = indexQuestion(id, selectedAnswer,userId,username);
        // 在这里处理接收到的数组
        return  ResponseEntity.ok(i) ;
    }

    public int indexQuestion(int id, List<String> selectedAnswer,int userId,String username) throws InterruptedException {
        List<Questionbank> questions = questionBankMapper.selectQuestionById(id,userId);
        Questionbank questionBankTwo = new Questionbank();
        System.out.println(questions);
        int number = 0;
        int count = 0;
        if (questions.isEmpty()) {
            count = this.indexQuestionTwo(id, selectedAnswer, userId, username);
        }else{
            int size = selectedAnswer.size();
            for (Questionbank questionbank : questions) {
                questionBankTwo.setGroupId(id);
                questionBankTwo.setDifficulty(new Random().nextInt(5) + 1);
                Long increment = redisTemplate.opsForValue().increment("QuestionBank");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
                String format = String.format("%06d", increment);
                questionBankTwo.setId(sdf.format(new Date()) + format);
//                if (questionbank.getGroupId() == 1) {
//                    questionBankTwo.setGname("使用DOM4J解析XML文档");
//                } else if (questionbank.getGroupId() == 2) {
//                    questionBankTwo.setGname("sdaf");
//                } else if (questionbank.getGroupId() == 3) {
//                    questionBankTwo.setGname("ssw");
//                } else if (questionbank.getGroupId() == 4) {
//                    questionBankTwo.setGname("sdfsw");
//                } else if (questionbank.getGroupId() == 5) {
//                    questionBankTwo.setGname("sdfssdfw");
//                }
                questionBankTwo.setQuestion(questionbank.getQuestion());
                questionBankTwo.setAnswers(questionbank.getAnswers());
                questionBankTwo.setRanswer(questionbank.getRanswer());
                questionBankTwo.setParse(questionbank.getParse());
                questionBankTwo.setResponse(questionbank.getResponse());
                Object value = redisTemplate.opsForValue().get("previousNumber_key");
//                if (questionbank.getCourseid() == 1) {
//                    questionBankTwo.setCName("Java开发之框架基础技术");
//                } else {
//                    questionBankTwo.setCName("sdafa");
//                }
                questionBankTwo.setGname(SqlRunner.db().selectObj("SELECT `name` FROM `question_group` WHERE id = "+questionbank.getGroupId()).toString());

                questionBankTwo.setCName(SqlRunner.db().selectObj("SELECT `name` FROM `question_course` WHERE id = "+questionbank.getCourseid()).toString());

                if (value == null) {
                    questionBankTwo.setNumber("1");
                } else {
                    questionBankTwo.setNumber(String.valueOf((int) value + 1));
                }
                if (number < size) {
                    String s = selectedAnswer.get(number);
                    String ranswer = questionbank.getRanswer();
                    if (s.equals(ranswer)) {
                        questionBankTwo.setCorrect("回答正确");
                        questionBankTwo.setResponse(s);
                        int add = 100 / questions.size();
                        count = count + add;
                    } else {
                        questionBankTwo.setResponse(s);
                        questionBankTwo.setCorrect("回答错误");
                    }
                } else {
                    questionBankTwo.setCorrect("回答错误");
                    questionBankTwo.setResponse("null");
                }
                SimpleDateFormat sdfTwo = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                questionBankTwo.setSubmissionTime(sdfTwo.format(new Date()));
                questionBankTwo.setQuestionId(questionbank.getId());
                ++number;
                if (number == questions.size()) {
                    //使用redisTemplate操作数字类型的值
                    redisTemplate.opsForValue().set("previousNumber_key", 1);
                    if (value != null) {
                        redisTemplate.opsForValue().set("previousNumber_key", (int) value + 1);
                    }
                }
                questionBankTwo.setUser(username);
                questionBankTwo.setUserId(userId);
                System.out.println(questionBankTwo);
                int i = questionBankMapper.insertEmp(questionBankTwo);
                Thread.sleep(100);
                System.out.println(i);
            }
        }
        Object valueTwo = redisTemplate.opsForValue().get("previousNumber_key");
        return (int) valueTwo;
    }

    @GetMapping("/FillAnswersByNumber")
    public ResponseEntity fillAnswersByNumber(Integer userId) {
        try {
            List<Questionbank> questionBanks = questionBankMapper.FillAnswersByNumber(userId);
            if (questionBanks == null) {
                return ResponseEntity.error("没有数据");
            }
            questionBanks.forEach(questionBank -> {
                questionBank.setAnswers(
                        String.valueOf(questionBankMapper.getCount( questionBank.getNumber()))
                );
            });
            System.out.println(questionBanks);
            return ResponseEntity.ok(questionBanks);
        } catch (Exception e) {
            return ResponseEntity.error(e.getMessage());
        }
    }

    @GetMapping("/getQuestionBank")
    public ResponseEntity getQuestionBank(String user,String password){
        try {
            List<Questionbank> questionBank = questionBankMapper.getQuestionBank(user, Integer.parseInt(password));
            if (questionBank == null) {
                return ResponseEntity.error("没有数据");
            }
            return ResponseEntity.ok(questionBank);
        } catch (Exception e) {
            return ResponseEntity.error(e.getMessage());
        }
    }

    @GetMapping("/getQuestionBankByNumber")
    public ResponseEntity getQuestionBankByNumber(String number, String username,int userId) {
        try {
            System.out.println("number:"+number+"username:"+username+"userId:"+userId);
            List<Questionbank> questionBankByNumber = questionBankMapper.getQuestionBankByNumber(number,username,userId);
            System.out.println(questionBankByNumber);
            if (questionBankByNumber == null) {
                return ResponseEntity.error("没有数据");
            }
            QuestionbankTwo questionbankTwo = new QuestionbankTwo();
            ArrayList<QuestionbankTwo> listTwo = new ArrayList<>();
            for (Questionbank questionbank : questionBankByNumber) {
                questionbankTwo.setGName(questionbank.getGname());
                questionbankTwo.setParse(questionbank.getParse());
                questionbankTwo.setResponse(questionbank.getResponse());
                questionbankTwo.setQuestion(questionbank.getQuestion().replaceAll("<br>", ""));
                if (questionbank.getRanswer().length() == 1) {
                    questionbankTwo.setLongestScore("单选题");
                } else {
                    questionbankTwo.setLongestScore("多选题");
                }
                questionbankTwo.setRanswer(questionbank.getRanswer());
                String[] answers = questionbank.getAnswers().replaceAll("[\\[\\]]", " ").split(",");
                questionbankTwo.setAnswers(Arrays.toString(answers));
                questionbankTwo.setAnswer1(answers[0]);
                questionbankTwo.setAnswer2(answers[1]);
                questionbankTwo.setAnswer3(answers[2]);
                questionbankTwo.setAnswer4(answers[3]);
                listTwo.add(questionbankTwo);
                questionbankTwo = new QuestionbankTwo(); // 清空对象，以便处理下一个问题
            }
            System.out.println("listTwo = " + listTwo);
            return ResponseEntity.ok(listTwo);
        } catch (Exception e) {
            return ResponseEntity.error(e.getMessage());
        }
    }

    @GetMapping("/getFillAnswers")
    public ResponseEntity getFillAnswers(String number,String user,int password){
        try {
            List<Questionbank> questionbanks = questionBankMapper.getFillAnswers(number,user,password);
            System.out.println(questionbanks);
            if (questionbanks == null) {
                return ResponseEntity.error("没有数据");
            }
            return ResponseEntity.ok(questionbanks);
        } catch (Exception e) {
            return ResponseEntity.error(e.getMessage());
        }
    }


    @GetMapping("/getFillAnswersAll")
    public ResponseEntity getFillAnswersAll(String number,String user,int password){
        try {
            List<Questionbank> fillAnswersAll = questionBankMapper.getFillAnswersAll(number,user,password);
            System.out.println(number);
            if (fillAnswersAll == null) {
                return ResponseEntity.error("没有数据");
            }
            return ResponseEntity.ok(fillAnswersAll);
        } catch (Exception e) {
            return ResponseEntity.error(e.getMessage());
        }
    }

    @GetMapping("/getFillAll")
    public ResponseEntity getFillAll(String groupId,String username,int userId){
        try {
            System.out.println("groupId:"+groupId+"username:"+username+" userId:"+userId);
            List<Questionbank> fillAll = questionBankMapper.getFillAll(groupId,username,userId);
            if (fillAll == null) {
                return ResponseEntity.error("没有数据");
            }
            return ResponseEntity.ok(fillAll);
        } catch (Exception e) {
            return ResponseEntity.error(e.getMessage());
        }
    }
    @GetMapping("/getFillAllTwo")
    public ResponseEntity getFillAllTwo(int groupId,String user,int password){
        try {
            System.out.println("groupId:"+groupId+"username:"+user+" password:"+password);
            List<Questionbank> fillAll = questionBankMapper.getFillAllTwo(groupId,user,password);
            if (fillAll == null) {
                return ResponseEntity.error("没有数据");
            }
            System.out.println(fillAll);
            return ResponseEntity.ok(fillAll);
        } catch (Exception e) {
            return ResponseEntity.error(e.getMessage());
        }
    }
    @GetMapping("/getFill")
    public ResponseEntity getFill(String groupId,String username,int userId){
        try {
            System.out.println("groupId:"+groupId+"username:"+username+" password:"+userId);
            Questionbank questionbank = new Questionbank();
            List<Questionbank> fill = questionBankMapper.getFill(groupId,username,userId);
            if (fill.isEmpty()) {
                ArrayList<Questionbank> list = new ArrayList<>();
                questionbank.setCount("0");
                list.add(questionbank);
                System.out.println(list);
                return ResponseEntity.ok(list);
            }
            return ResponseEntity.ok(fill);
        } catch (Exception e) {
            return ResponseEntity.error(e.getMessage());
        }
    }

    @PostMapping("/QuestionBank")
    public ResponseEntity getFillAnswersAll(@RequestBody List<String> groupId,String user,Integer userId){
        try {
            List<Integer> list = new ArrayList<>();
             List<Integer> fillAnswers = getFillAnswersByUserId(groupId,user,userId);
            System.out.println(fillAnswers);
            int count =0;
            for (String id:groupId) {
                List<Questionbank> questionBanks = questionBankMapper.QuestionBank(Integer.parseInt(id));
                if (questionBanks == null){
                    return ResponseEntity.error("没有数据");
                }
                for (Questionbank question : questionBanks) {
                    count= Integer.parseInt(question.getCount());
                    list.add(count);
                }
            }
            double  number=0;
            List<Integer> listTwo = new ArrayList<>();
            for (int i = 0; i < list.size(); i++){
                // 创建DecimalFormat实例并设置格式
                DecimalFormat df = new DecimalFormat("#####.00");
                number =  ((double) 100 /list.get(i))*fillAnswers.get(i);
                listTwo.add((int) number);
            }
            System.out.println(listTwo);
            return ResponseEntity.ok(listTwo);
        } catch (NumberFormatException e) {
            return ResponseEntity.error(e.getMessage());
        }
    }

    public List<Integer> getFillAnswersByUserId(List<String> groupId, String user, Integer password){
        List<Integer> list = new ArrayList<>();
        int count =0;
        for (String id:groupId) {
            List<Questionbank> questionBanks = questionBankMapper.QuestionCourse(id,user,password);
            for (Questionbank question : questionBanks) {
                count= Integer.parseInt(question.getCount());
                list.add(count);
            }
        }
        System.out.println("list:"+list);
        return  list ;
    }

}