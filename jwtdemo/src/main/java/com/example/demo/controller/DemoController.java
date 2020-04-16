package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("demoC")
public class DemoController {

    @RequestMapping(value = "one",method = RequestMethod.GET)
    public String one(String req) {
        return "this is one " + req;
    }
//8个Lambda表达式的练习题目及其答案
//https://blog.csdn.net/nullbull/article/details/81234250

    //https://blog.csdn.net/weixin_30920853/article/details/96772810
}
