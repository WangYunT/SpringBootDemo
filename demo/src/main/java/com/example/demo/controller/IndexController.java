package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


/**
 * for hc
 */
@Slf4j
@RestController
@Api("swaggerDemoController相关的api")
public class IndexController {

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "It works";
    }

    @ApiOperation(value = "根据code返回code用来测试",notes = "返回code")
    @ApiImplicitParam(name="code", value="传入的code",paramType = "param",required = true,dataType = "String")
    @GetMapping("/getcode")
    public String getCode(@RequestParam String code){
        
        return code;
    }
}
