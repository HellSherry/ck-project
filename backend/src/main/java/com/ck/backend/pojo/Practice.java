package com.ck.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 02
 * &#064;date  2024/3/29-16:19
 * @version 1.0
 * &#064;description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Practice {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String starttime;
    private Integer duration;
    private String name;
    private Integer userid;
}
