package com.ck.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.ck.backend.mapper.SoftMapper;
import com.ck.backend.pojo.Soft;
import com.ck.backend.util.QiNiuUtil;
import com.ck.backend.util.ResponseEntity;
import jakarta.annotation.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * (soft)表控制层
 *
 * @author hellSherry
 * @since 2024-01-10 13:14:19
 */
@RestController
@CrossOrigin("*")
@RequestMapping("public/soft")
public class SoftController {
    @PostMapping("upload")
    public ResponseEntity upload(MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.error("文件不能为空！");
        }
//        soft.setName(file.getOriginalFilename());
//        softMapper.insert(soft);
        return ResponseEntity.ok(QiNiuUtil.upload(file) ? file.getOriginalFilename() : "上传失败！");

    }

    @RequestMapping("/getSizeByUrl")
    public ResponseEntity<Long> getFileSize(String fileUrl) throws IOException {
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String contentLength = connection.getHeaderField("Content-Length");
                if (contentLength != null) {
                    return new ResponseEntity<Long>(Long.parseLong(contentLength));
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<Long>(-1L);
        }


        return new ResponseEntity<Long>(-1L);
    }

    @RequestMapping("/addDownload")
    public ResponseEntity addDownload(Integer id){
        return ResponseEntity.ok(SqlRunner.db().update("UPDATE `soft` SET `quantity` = `quantity`+1 WHERE `id` = " + id));
    }

    /**
     * 服务对象
     */
    @Resource
    private SoftMapper softMapper;

    /**
     * 分页查询消息
     *
     * @param search 搜索关键字
     * @param page   当前页码
     * @param size   每页显示条数
     * @return 分页后的消息集合
     */
    @RequestMapping("page")
    public ResponseEntity<Page<Soft>> page(String search, Integer page, Integer size, Boolean isHot) {
        size = 10;

        QueryWrapper<Soft> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(search)) {
            wrapper.like("name", search).or(
                    wt -> wt.like("particulars", search)
            );
        }
        if (isHot != null) wrapper.orderByDesc(isHot ? "quantity" : "addtime");
        return new ResponseEntity<>(softMapper.selectPage(new Page<Soft>(page, size), wrapper));
    }

    @RequestMapping("getAll")
    public ResponseEntity<List<Soft>> getAll() {
        return new ResponseEntity<>(softMapper.selectList(null));
    }

    @RequestMapping("getDetail")
    public ResponseEntity<Soft> getDetail(Integer id) {
        return new ResponseEntity<>(softMapper.selectById(id));
    }

    @RequestMapping("delete")
    public ResponseEntity delete(@RequestBody List<Integer> ids) {
        System.out.println("ids = " + ids);
        QueryWrapper<Soft> wrapper = new QueryWrapper<>();
        wrapper.in("id", ids);
        return new ResponseEntity<>(softMapper.delete(wrapper));
    }

    @RequestMapping("insert")
    public ResponseEntity insert(@RequestBody Soft soft) {
        soft.setAddtime(new Date());
        soft.setQuantity(0);
        return new ResponseEntity<>(softMapper.insert(soft));
    }

    @RequestMapping("update")
    public ResponseEntity update(@RequestBody Soft soft) {
        System.out.println("soft = " + soft);
        return new ResponseEntity<>(softMapper.updateById(soft));
    }

}

