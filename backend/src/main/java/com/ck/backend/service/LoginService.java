package com.ck.backend.service;


import com.ck.backend.pojo.User;
import com.ck.backend.util.ResponseEntity;

/**
 * @author HellSherrry
 * &#064;date 2023/12/17-13:30
 * @version 1.0
 * &#064;description
 */

public interface LoginService {


    ResponseEntity login(User userInfo);
}
