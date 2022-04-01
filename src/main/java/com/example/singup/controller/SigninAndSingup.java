package com.example.singup.controller;


import com.example.singup.entity.user;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(value = "/v1")
@Api(value = "V1",description = "注册和登录")

public class SigninAndSingup {

    private static Logger logger = Logger.getLogger(String.valueOf(SigninAndSingup.class));

    @Autowired
    private    SqlSessionTemplate template;


    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ApiOperation(value = "HellWord",httpMethod = "GET")
    public String HellWord(){
        return "HellWord";
    }

    @RequestMapping(value = "/select",method = RequestMethod.POST)
    @ApiOperation(value = "查询" ,httpMethod = "POST")
    public  int  select(HttpServletResponse servletResponse, @RequestBody Integer phone){
        int i  = template.selectOne("select_phone_count",phone);
        logger.info("手机号"+phone+"的数量为："+i);
        return i;
    }


    @RequestMapping(value = "/Singup",method = RequestMethod.POST)
    @ApiOperation(value = "注册接口" ,httpMethod = "POST")
    public  String  Singup(HttpServletResponse servletResponse, @RequestBody user user){

            int count_phone = template.selectOne("select_phone_count",user.getPhone());
            if (count_phone >= 1){
                logger.info("注册失败，此手机号已注册!"+user.getPhone());
                return "注册失败，此手机号已注册!";
            }else {
                int i = template.insert("Sing_up",user);
                logger.info("注册成功"+user.getName()+user.getSex()+user.getPhone());
                return "注册成功!";
            }




    }





}
