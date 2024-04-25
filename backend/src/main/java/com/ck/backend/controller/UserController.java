
package com.ck.backend.controller;


import com.ck.backend.controller.websokect.SpringWebSokectController;
import com.ck.backend.domain.LoginUser;
import com.ck.backend.mapper.UserMapper;
import com.ck.backend.pojo.ClassInfo;
import com.ck.backend.pojo.User;
import com.ck.backend.service.LoginService;
import com.ck.backend.service.RegistService;
import com.ck.backend.util.FileUtil;
import com.ck.backend.util.JwtUtil;
import com.ck.backend.util.PageBean;
import com.ck.backend.util.ResponseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

//请求处理类
@RestController
@CrossOrigin(origins = "*")
public class UserController {

    ////    @Qualifier("phoneDaoImp")//指定注入的名字
    @Autowired//运行时，Ioc容器自动注入
//    @Resource(name="phoneDaoImp")//jdk提供的
    private LoginService loginService;
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RegistService registService;
    @Autowired
    public PasswordEncoder passwordEncoder;
    @Resource
    private SpringWebSokectController webSocket;

    @RequestMapping("/public/user/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/user/login")
    public ResponseEntity login(@RequestBody User user) {
        System.out.println("userInfo = " + user);
        ResponseEntity login = loginService.login(user);
        return login;
    }

    @RequestMapping("/public/logout")
    public ResponseEntity logout(String userId) {
        redisTemplate.expire("login:" + userId, 1, TimeUnit.SECONDS);
        return null;
    }

    @RequestMapping("/user/logout")
    public ResponseEntity logoutMyLogin(@AuthenticationPrincipal LoginUser customUser) {
        redisTemplate.expire("login:" + customUser.getUser().getUserId(), 1, TimeUnit.SECONDS);
        return null;
    }

    @RequestMapping("/user/regist")
    public ResponseEntity regist(@RequestBody User user) {
        System.out.println("userInfo = " + user);
        ResponseEntity regist = registService.regist(user);
        return regist;
    }

    @RequestMapping("/user/insert")
    public ResponseEntity add(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(this.userMapper.insert(user));
    }

    @RequestMapping("/user/update")
    public ResponseEntity edit(@RequestBody User user) {
        return ResponseEntity.ok(this.userMapper.update(user));
    }


    @RequestMapping("/public/user/all")
    public List<User> all() {
        return userMapper.selectAll();
    }

    /**
     * 更新前端用户图片
     */
    //处理上传的文件
    @RequestMapping("/user/selectLimit")
    public ResponseEntity<PageBean<User>> selectLimit(Integer page, Integer identity, String search) {

        return ResponseEntity.ok(new PageBean<User>(null, null, userMapper.count(identity, search), userMapper.selectLimit(page, identity, search)));
    }
    @RequestMapping("/user/getById")
    public ResponseEntity<User> getById(Integer id ) {
        User user = userMapper.selectById(id);
        user.setPassword(null);
        return ResponseEntity.ok(user  );
    }

    @RequestMapping("/user/deletes")
    public ResponseEntity<Integer> deletes(String ids) {
        return ResponseEntity.ok(this.userMapper.deletes(ids));
    }

//    @PostMapping("/user/upload")
//    public String upload(MultipartFile image){
//        System.out.println(image.getName());
////        try {
////            image.transferTo(new File("D:\\javaWork\\IdeaProjects\\ck-project\\front-end\\src\\assets\\userAvatar\\"));
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
//        return  "上传成功";
//    }

    @RequestMapping("/user/updatePassword")
    public ResponseEntity<Integer> updatePassword(String old, String newP, @AuthenticationPrincipal LoginUser customUser) {

        if (passwordEncoder.matches(old, userMapper.getPasswordById(customUser.getUser().getUserId()))) {
            customUser.getUser().setPassword(passwordEncoder.encode(newP));
            return ResponseEntity.ok(this.userMapper.update(customUser.getUser()));
        } else {
            return ResponseEntity.ok("err");
        }

    }

    @RequestMapping("/user/updateName")
    public ResponseEntity updateName(String name, @AuthenticationPrincipal LoginUser customUser) {
        customUser.getUser().setUsername(name);
        this.userMapper.update(customUser.getUser());
        String jwt = null;
        ObjectMapper mapper = new ObjectMapper();
        String userJson;
        try {
            userJson = mapper.writeValueAsString(customUser.getUser());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        jwt = JwtUtil.generateToken(userJson);
        redisTemplate.opsForValue().set("login:" + customUser.getUser().getUserId(), jwt, 7, TimeUnit.DAYS);
        return ResponseEntity.ok(jwt);
    }
    @RequestMapping("/user/getNewToken")
    public ResponseEntity getNewToken(@AuthenticationPrincipal LoginUser customUser) {
        User user = this.userMapper.selectById(customUser.getUser().getUserId());
        String jwt = null;
        ObjectMapper mapper = new ObjectMapper();
        String userJson;
        try {
            userJson = mapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        jwt = JwtUtil.generateToken(userJson);
        redisTemplate.opsForValue().set("login:" + customUser.getUser().getUserId(), jwt, 7, TimeUnit.DAYS);
        return ResponseEntity.ok(jwt);
    }


    @PostMapping("/user/updateAvatar")
    public ResponseEntity<String> uploadImg(
            // 定义一个 MultipartFile 类型的参数 file
           @AuthenticationPrincipal LoginUser customUser,String imgPath) {
        Integer id = customUser.getUser().getUserId();
         // 如果图片为空，显示error

//            Head head = new Head();
            // 获取图片的名字
//            String fileName = file.getOriginalFilename();
            // 图片存放的文件夹地址
//            String filePath = "D:\\javaWork\\IdeaProjects\\ck-project\\front-end\\src\\assets\\userAvatar\\";
            // 文件夹地址 + 名字 = 图片的路径
//            String fileAddress = filePath + id;
            String jwt = null;
            try {
                // 图片上传的工具类
                // 参数一：图片的编码格式的字节数组
                // 参数二：图片存放的文件夹地址
                // 参数三：图片的名字

//                FileUtil.uploadFile(file.getBytes(), filePath, id + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
                User user = customUser.getUser();
                user.setUserImg(imgPath);

                userMapper.update(user);
                user.setClassid(customUser.getUser().getClassid());

                ObjectMapper mapper = new ObjectMapper();
                String userJson;
                try {
                    userJson = mapper.writeValueAsString(user);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                jwt = JwtUtil.generateToken(userJson);
                redisTemplate.opsForValue().set("login:" + id, jwt, 7, TimeUnit.DAYS);

            } catch (Exception e) {
            }
            return ResponseEntity.ok(jwt);

    }



   /*   @PostMapping("/user/updateAvatar")
  public ResponseEntity<String> uploadImg(

            // 定义一个 MultipartFile 类型的参数 file
            @RequestParam("file") MultipartFile file,
            Model model, @AuthenticationPrincipal LoginUser customUser) {
        Integer id = customUser.getUser().getUserId();
        System.out.println("111 = " + 111);
        // 如果图片为空，显示error
        if ((file.getOriginalFilename().isEmpty())) {
            model.addAttribute("error", "error");
            return new ResponseEntity<>("index");
        } else {
//            Head head = new Head();
            // 获取图片的名字
//            String fileName = file.getOriginalFilename();
            // 图片存放的文件夹地址
            String filePath = "D:\\javaWork\\IdeaProjects\\ck-project\\front-end\\src\\assets\\userAvatar\\";
            // 文件夹地址 + 名字 = 图片的路径
//            String fileAddress = filePath + id;
            String jwt = null;
            try {
                // 图片上传的工具类
                // 参数一：图片的编码格式的字节数组
                // 参数二：图片存放的文件夹地址
                // 参数三：图片的名字

                FileUtil.uploadFile(file.getBytes(), filePath, id + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
                User user = customUser.getUser();
                user.setUserImg(id + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));

                userMapper.update(user);
                user.setClassid(customUser.getUser().getClassid());

                ObjectMapper mapper = new ObjectMapper();
                String userJson;
                try {
                    userJson = mapper.writeValueAsString(user);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                jwt = JwtUtil.generateToken(userJson);
                redisTemplate.opsForValue().set("login:" + id, jwt, 7, TimeUnit.DAYS);

            } catch (Exception e) {
            }
            return ResponseEntity.ok(jwt);
        }
    }


    */

}
