package com.ck.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * 假条管理表(Leaverequest)实体类
 *
 * @author makejava
 * @since 2024-02-27 14:02:59
 */
@Data
@Component
public class Leaverequest implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 学生编号
     */
    private String name;
    /**
     * 开始时间
     */
    private Date startdate;
    /**
     * 结束时间
     */
    private Date enddate;
    /**
     * 请假理由
     */
    private String eeason;
    /**
     * 状态
     */
    private String status;
    /**
     * 批假人
     */
    private String batchdummy;


}

