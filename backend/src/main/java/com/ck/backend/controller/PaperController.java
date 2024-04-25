package com.ck.backend.controller;

import com.ck.backend.domain.LoginUser;
import com.ck.backend.pojo.Paper;
import com.ck.backend.service.PaperService;
import com.ck.backend.util.PageBean;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.ck.backend.util.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * (Paper)表控制层
 *
 * @author hellSherry
 * @since 2023-12-28 13:33:36
 */
@RestController
@CrossOrigin("*")
@RequestMapping("paper")
public class PaperController {
    /**
     * 服务对象
     */
    @Resource
    private PaperService paperService;

    /**
     * 分页查询
     *
     * @param paper       筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @RequestMapping("get")
    public ResponseEntity<PageBean<Paper>> queryByPage(String search, Integer page, String starttime, String endtime) {
        System.out.println("starttime = " + starttime);
        System.out.println("starttime = " + endtime);

        return  ResponseEntity.ok(paperService.queryByPage( search,  page,  starttime,  endtime));


//        int page = 0; // 第一页
//        int size = 10; // 每页条目数
//        Sort sort = Sort.by(Sort.Direction.DESC, "fieldName"); // 根据字段进行降序排序，替换 "fieldName" 为实际的字段名
//
//        Pageable pageable = PageRequest.of(page, size, sort);
//        System.out.println("paper = " +paper);
//        System.out.println("pageRequest = " + pageable);
//        return ResponseEntity.ok(this.paperService.queryByPage(paper, (PageRequest) pageable));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Paper> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.paperService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param paper 实体
     * @return 新增结果
     */
    @PostMapping("add")
    public ResponseEntity<Paper> add(@AuthenticationPrincipal LoginUser customUser , @RequestBody Paper paper) {
        System.out.println("paper = " + paper);
        System.out.println("customUser.getUser() = " + customUser.getUser());
        paper.setCreatorusername(customUser.getUser().getUsername());
        return ResponseEntity.ok(this.paperService.insert(paper));
    }

    /**
     * 编辑数据
     *
     * @param paper 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Paper> edit(Paper paper) {
        return ResponseEntity.ok(this.paperService.update(paper));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.paperService.deleteById(id));
    }

}

