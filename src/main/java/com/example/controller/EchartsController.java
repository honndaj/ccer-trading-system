package com.example.controller;

import com.example.common.Result;
import com.example.service.ICcerService;
import com.example.service.IHistoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    public Result getPieKindData() {
        return Result.success(historyService.getKindCount());
    }

    @GetMapping("/barDay")
    public Result getCountDay(@RequestParam String area, @RequestParam String kind) {
        return Result.success(historyService.getCountDay(area, kind));
    }

    @GetMapping("/barMonth")
    public Result getCountMonth(@RequestParam String area, @RequestParam String kind) {
        return Result.success(historyService.getCountMonth(area, kind));
    }
}
