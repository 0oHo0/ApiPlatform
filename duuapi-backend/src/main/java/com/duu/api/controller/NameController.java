package com.duu.api.controller;

import com.duu.common.model.entity.User;
import org.springframework.web.bind.annotation.*;

/**
 * @author : duu
 * @data : 2023/11/22
 * @from ：https://github.com/0oHo0
 **/
@RestController
@RequestMapping("/name")
public class NameController {
    @GetMapping("/")
    public String getNameByGet(String name){
        return "Get 名字"+name;
    }
    @PostMapping("/post")
    public String getNameByPost(@RequestParam String name){
        return "Post 名字"+ name;
    }
    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user){

        return "Post 名字"+ user.getUserName();
    }
}
