package com.ck.backend.controller;

import com.ck.backend.pojo.ClassInfo;
import com.ck.backend.pojo.Specia;
import com.ck.backend.service.SpeciaService;
import com.ck.backend.util.PageBean;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.ck.backend.util.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * (Specia)表控制层
 *
 * @author hellSherry
 * @since 2023-12-29 11:26:14
 */
@RestController
@RequestMapping("specia")
public class SpeciaController {
    /**
     * 服务对象
     */
    @Resource
    private SpeciaService speciaService;


    @RequestMapping("deletes")
    public ResponseEntity<Integer> deletes(String ids) {
        return ResponseEntity.ok(this.speciaService.deletes(ids));
    }

    @RequestMapping("/selectLimit")
    public ResponseEntity<PageBean<ClassInfo>> selectLimit(Integer page, String search) {
        return ResponseEntity.ok(new PageBean<Specia>(null, null, speciaService.count(search), speciaService.selectLimit(page, search)));
    }

    @RequestMapping("getAll")
    public ResponseEntity<List<Specia>> getAll() {
        return ResponseEntity.ok(this.speciaService.getAll());
    }

    @RequestMapping("getAllFull")
    public ResponseEntity<List<Specia>> getAllFull() {
        return ResponseEntity.ok(this.speciaService.getAllFull());
    }

    /**
     * 分页查询
     *
     * @param specia      筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<Specia>> queryByPage(Specia specia, PageRequest pageRequest) {
        return ResponseEntity.ok(this.speciaService.queryByPage(specia, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Specia> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.speciaService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param specia 实体
     * @return 新增结果
     */
    @RequestMapping("insert")

    public ResponseEntity<Integer> add(@RequestBody     Specia specia) {
        return ResponseEntity.ok(this.speciaService.insert(specia));
    }

    /**
     * 编辑数据
     *
     * @param specia 实体
     * @return 编辑结果
     */
    @RequestMapping("update")

    public ResponseEntity<Integer> edit(@RequestBody Specia specia) {
        return ResponseEntity.ok(this.speciaService.update(specia));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.speciaService.deleteById(id));
    }

}

