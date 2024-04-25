package com.ck.backend.controller;

import com.ck.backend.pojo.ClassInfo;
import com.ck.backend.pojo.User;
import com.ck.backend.service.ClassInfoService;
import com.ck.backend.util.PageBean;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.ck.backend.util.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * (ClassInfo)表控制层
 *
 * @author hellSherry
 * @since 2023-12-29 11:17:14
 */
@RestController
@RequestMapping("classInfo")
public class ClassInfoController {
    /**
     * 服务对象
     */
    @Resource
    private ClassInfoService classInfoService;

    @RequestMapping("getBySpeciaid")
    private  ResponseEntity<List<ClassInfo> > selectAllBySpeciaid(Integer id) {
        return ResponseEntity.ok(this.classInfoService.selectAllBySpeciaid(id));
    }

    @RequestMapping("/selectLimit")
    public ResponseEntity<PageBean<ClassInfo>>  selectLimit(Integer page, String search) {
        return ResponseEntity.ok(new PageBean<ClassInfo>(null,null,classInfoService.count(search),classInfoService.selectLimit(page,search))) ;
    }

    /**
     * 分页查询
     *
     * @param classInfo   筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<ClassInfo>> queryByPage(ClassInfo classInfo, PageRequest pageRequest) {
        return ResponseEntity.ok(this.classInfoService.queryByPage(classInfo, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<ClassInfo> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.classInfoService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param classInfo 实体
     * @return 新增结果
     */
    @RequestMapping("insert")
    public ResponseEntity<ClassInfo> add(@RequestBody ClassInfo classInfo) {
        return ResponseEntity.ok(this.classInfoService.insert(classInfo));
    }

    /**
     * 编辑数据
     *
     * @param classInfo 实体
     * @return 编辑结果
     */
    @RequestMapping("update")
    public ResponseEntity<ClassInfo> edit(@RequestBody ClassInfo classInfo) {
        return ResponseEntity.ok(this.classInfoService.update(classInfo));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.classInfoService.deleteById(id));
    }
    @RequestMapping("deletes")
    public ResponseEntity<Integer> deletes(String  ids) {
        System.out.println("ids = " + ids);
        return ResponseEntity.ok(this.classInfoService.deletes(ids));
    }

}

