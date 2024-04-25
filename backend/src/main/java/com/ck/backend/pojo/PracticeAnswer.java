package com.ck.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 02
 * &#064;date  2024/3/29-16:21
 * @version 1.0
 * &#064;description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PracticeAnswer {

    private Integer practiceid;
    private Integer questionid;
    private String answer;
}
