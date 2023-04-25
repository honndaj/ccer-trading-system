package com.example.controller;

import com.example.common.Result;
import com.example.service.ICcerService;
import com.example.service.IHistoryService;
import com.example.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static cn.hutool.poi.excel.sax.AttributeName.t;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Resource
    private ICcerService ccerService;

    @Resource
    private IHistoryService historyService;

    @GetMapping("/ccerArea/{uid}")
    public Result ccerArea(@PathVariable Integer uid) {
        return Result.success(ccerService.ccerArea(uid));
    }

    @GetMapping("/ccerKind/{uid}")
    public Result ccerKind(@PathVariable Integer uid) {
        return Result.success(ccerService.ccerKind(uid));
    }

    @GetMapping("/line")
    public Result getLineData() {
        return Result.success(historyService.getLineData());
    }

    @GetMapping("/table")
    public Result getTableData() {
        return Result.success(historyService.getAreaLatest());
    }

    @GetMapping("/pieKind")
    public Result gePieKindData() {
        return Result.success(historyService.getKindCount());
    }
}
