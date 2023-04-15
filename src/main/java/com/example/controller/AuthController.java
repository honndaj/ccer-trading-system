//package com.example.controller;
//
//import com.example.entity.User;
//import com.example.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpSession;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/login")
//    public Result login(@RequestBody User user, HttpSession session) {
//        String username = user.getUsername();
//        User _user = userService.selectByName(username);
//
//        if(_user != null) {
//            if(_user.getPassword().equals(user.getPassword())) {
//                //TODO 返回权限
//                String sessionId = session.getId();
//                return new Result(200, sessionId, "登录成功");
//            }
//            else {
//                return new Result(40602, "密码错误");
//            }
//        }else {
//            return new Result(40601, "用户名不存在");
//        }
//    }
//
//    @PostMapping("/register")
//    public Result register(@RequestBody User user, HttpSession session) {
//        if(userService.selectByName(user.getUsername()) != null) {
//            return new Result(40603, "用户已存在");
//        }else{
//            int count = userService.insertBasic(user);
//            return new Result(200, "注册成功");
//        }
//    }
//}
