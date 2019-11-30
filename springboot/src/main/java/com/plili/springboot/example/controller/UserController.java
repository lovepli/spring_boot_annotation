package com.plili.springboot.example.controller;


import com.plili.springboot.example.Permission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lipan
 * @date: 2019-06-13
 * @description:
 */

@RestController
@Slf4j
public class UserController {

    //带注解，需要校验权限
    @GetMapping(value = "/user")
    @Permission
    public String user(@RequestParam String name) {
        return "你好:"+name+",您有管理权限";
    }

    //不带注解，不需要安全校验
    @GetMapping(value = "/user2")
    public String user2(@RequestParam String name) {
        return "不用检查权限，直接返回的数据";
    }

    /**
     * 然后通过url请求来验证结果
     * 1，http://localhost:8080/user?name=qcl2
     * 由于qcl2不在管理员数组里面，所以抛出异常
     * 2,http://localhost:8080/user?name=qiushi
     * qiushi是管理员，所以用户邱石可以访问到数据。
     * 3，http://localhost:8080/user2?name=qiushi
     * 由于接口/user2没有添加 @Permission注解，所以不用做安全校验，直接返回数据。
     *
     * 到这里我们就轻松实现通过 @Permission一个注解，就可以实现数据的安全校验。
     *
     * 博客：https://blog.csdn.net/qiushi_1990/article/details/87642108#commentsedit
     */

}

