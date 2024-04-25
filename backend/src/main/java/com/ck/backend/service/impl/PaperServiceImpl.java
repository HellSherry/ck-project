package com.ck.backend.service.impl;

import com.ck.backend.mapper.QuestionMapper;
import com.ck.backend.pojo.Paper;
import com.ck.backend.mapper.PaperMapper;
import com.ck.backend.pojo.Question;
import com.ck.backend.service.PaperService;
import com.ck.backend.util.PageBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gui.ava.html.parser.HtmlParser;
import gui.ava.html.parser.HtmlParserImpl;
import gui.ava.html.renderer.ImageRenderer;
import gui.ava.html.renderer.ImageRendererImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * (Paper)表服务实现类
 *
 * @author hellSherry
 * @since 2023-12-28 14:27:36
 */
@Service("paperService")
public class PaperServiceImpl implements PaperService {
    @Resource
    private PaperMapper paperMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @Override
    public  Long getPaperTime(String paperId) {
        return redisTemplate.getExpire("papertime:" + paperId);
    }


    /**
     * 通过ID查询单条数据
     *
     * @param paperid 主键
     * @return 实例对象
     */
    @Override
    public Paper queryById(Integer paperid) {
        return this.paperMapper.queryById(paperid);
    }

    /**
     * 分页查询
     *
     * @param paper       筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public PageBean<Paper> queryByPage(String search, Integer page, String starttime, String endtime) {


        int count = this.paperMapper.count(search, starttime, endtime);

        return new PageBean<>(null, null, count, this.paperMapper.selectAllByLimit(search, page, starttime, endtime));

//        return new PageImpl<>(this.paperMapper.queryAllByLimit(paper, pageRequest), pageRequest, total);
    }

    ObjectMapper jackon = new ObjectMapper();

    /**
     * 新增数据
     *
     * @param paper 实例对象
     * @return 实例对象
     */
    @Override
    public Paper insert(Paper paper) {

        Map hashMap;
        List<Question> questions = new ArrayList<>();
        List qids = new ArrayList<>();
        StringBuffer htmlTele = new StringBuffer();
        htmlTele.append("<!DOCTYPE html>\n" +
                "<html lang=\"en\" style=\"width: 520px;background-color: rgba(240,248,255,0)\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body style='width:520px;background-color:rgba(0,0,0,0)'>" + "<H1>").append(paper.getPapername()).append("</H1>")


        ;
        try {
            hashMap = jackon.readValue(paper.getQuestions(), HashMap.class);
            hashMap.forEach((k, v) -> {

                AtomicInteger num = new AtomicInteger(1);
                System.out.println("k = " + k);
                System.out.println("vk = " + v);
                questionMapper.queryByGroup(Integer.parseInt(k.toString()), Integer.parseInt(v.toString())).forEach(item -> {

                    htmlTele.append("<H3> 第").append(num.getAndIncrement()).append("题</H3>");
                    htmlTele.append("<p>").append(item.getQuestion()).append("</p>");
                    try {
                        List list = jackon.readValue(item.getAnswers(), List.class);
                        for (int i = 0; i < list.size(); i++) {
                            switch (i) {
                                case 0:
                                    htmlTele.append("A、");
                                    break;
                                case 1:
                                    htmlTele.append("B、");
                                    break;
                                case 2:
                                    htmlTele.append("C、");
                                    break;
                                case 3:
                                    htmlTele.append("D、");
                                    break;
                                case 4:
                                    htmlTele.append("E、");
                                    break;
                                case 5:
                                    htmlTele.append("F、");
                                    break;
                                case 6:
                                    htmlTele.append("G、");
                                    break;
                                case 7:
                                    htmlTele.append("H、");
                                    break;
                                default:
                                    htmlTele.append("O``");
                                    break;
                            }
                            htmlTele.append("<p style='border: 1px solid #505050;padding-left: 20px;margin-top: -20px'>").append(list.get(i)).append("</p>");
                        }


                    } catch (JsonProcessingException ex) {
                        throw new RuntimeException(ex);
                    }
                    qids.add(item.getId());
                    questions.add(item);
                    htmlTele.append("<hr/>");
                });

            });
//分组id-数量

            htmlTele.append("</body>\n" +
                    "\n" +
                    "</html>");

            HtmlParser htmlParser = new HtmlParserImpl();

            htmlParser.loadHtml(htmlTele.toString());
            // html 是我的html代码




            paper.setQuestions(jackon.writeValueAsString(qids));
            this.paperMapper.insert(paper);
            ImageRenderer imageRenderer = new ImageRendererImpl(htmlParser);
            imageRenderer.saveImage("D:\\javaWork\\IdeaProjects\\ck-project\\front-end\\src\\assets\\paperImg\\"+paper.getPaperid()+".png");

            jackon.readValue(paper.getClassid(), List.class).forEach(c -> {
                paperMapper.insertPaperClass(paper.getPaperid(), (Integer) c);
            });
            paper.setQuestions(jackon.writeValueAsString(questions));
            redisTemplate.opsForValue().set("paper:" + paper.getPaperid(), jackon.writeValueAsString(paper));
            redisTemplate.opsForValue().set("papertime:" + paper.getPaperid(), paper.getStarttime().getTime(), ((paper.getStarttime().getTime() - System.currentTimeMillis()) / 1000) + (60L * paper.getDuration()), TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(htmlTele.toString());
        return paper;
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }

    /**
     * 修改数据
     *
     * @param paper 实例对象
     * @return 实例对象
     */
    @Override
    public Paper update(Paper paper) {
        this.paperMapper.update(paper);
        return this.queryById(paper.getPaperid());
    }

    /**
     * 通过主键删除数据
     *
     * @param paperid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer paperid) {
        return this.paperMapper.deleteById(paperid) > 0;
    }
}
