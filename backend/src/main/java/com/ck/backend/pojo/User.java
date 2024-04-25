package com.ck.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.In;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer userId;//` int(11) NOT NULL AUTO_INCREMENT,
    private String userEmail;//` varchar(255) DEFAULT NULL COMMENT '邮箱',
    private String username;//` varchar(255) NOT NULL,
    private String password;//` varchar(255) NOT NULL,
    private Integer grade;//` int(11) DEFAULT NULL,
    private String userImg;//` varchar(255) NOT NULL COMMENT '头像',
    private String defaultAddress;//` varchar(11) DEFAULT NULL COMMENT '默认地址',
    private Integer identity;//` int(11) DEFAULT NULL,
    private Integer classid;//` int(11) DEFAULT NULL,
    private String phone;//` int(11) DEFAULT NULL,
}
