package com.ck.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ck.backend.controller.websokect.SpringWebSokectController;
import com.ck.backend.mapper.MessageMapper;
import com.ck.backend.pojo.Message;
import jakarta.annotation.Resource;
import org.springframework.data.domain.PageRequest;
import com.ck.backend.util.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * (Message)表控制层
 *
 * @author hellSherry
 * @since 2024-01-10 13:14:19
 */
@RestController
@CrossOrigin("*")
@RequestMapping("message")
public class MessageController {
    /**
     * 服务对象
     */
    @Resource
    private MessageMapper messageMapper;
    @Resource
    SpringWebSokectController springWebSokectController;
    /**
     * 分页查询消息
     *
     * @param search 搜索关键字
     * @param page 当前页码
     * @param size 每页显示条数
     * @return 分页后的消息集合
     */
    @RequestMapping("page")
    public ResponseEntity<Page<Message>> page(String search, Integer page, Integer size) {
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(search)) {
            wrapper.like("title", search).or(
                    wt-> wt.like("details", search)
            );

        }
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Message> BookbagMapperPage = messageMapper.selectPage(new Page<Message>(page, size), wrapper);
        System.out.println("BookbagMapperPage.getRecords() = " + BookbagMapperPage.getRecords());
        return new ResponseEntity<>(BookbagMapperPage);
    }

    @RequestMapping("getAll")
    public ResponseEntity<List<Message>> getAll() {
        return new ResponseEntity<>(messageMapper.selectList(null));
    }
    @RequestMapping("delete")
    public ResponseEntity delete(@RequestBody List<Integer> ids) {
        System.out.println("ids = " + ids);
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.in("id", ids);
        return new ResponseEntity<>(messageMapper.delete(wrapper));
    }
    @RequestMapping("insert")
    public ResponseEntity insert(@RequestBody Message message) {
        message.setAddtime(new Date());
        springWebSokectController.sendAllMessage("message:"+message.getDetails());
        return new ResponseEntity<>(messageMapper.insert(message));
    }
    @RequestMapping("update")
    public ResponseEntity update(@RequestBody Message message) {
        System.out.println("message = " + message);
        return new ResponseEntity<>(messageMapper.updateById(message));
    }

}

