package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.example.common.Result;
import com.example.entity.User;
import com.example.service.ICcerService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Resource
    private IUserService userService;

    @Resource
    private ICcerService ccerService;

    @GetMapping("/ccerArea/{uid}")
    public Result ccerArea(@PathVariable Integer uid) {
        return Result.success(ccerService.ccerArea(uid));
    }

    @GetMapping("/ccerKind/{uid}")
    public Result ccerKind(@PathVariable Integer uid) {
        return Result.success(ccerService.ccerKind(uid));
    }

}
