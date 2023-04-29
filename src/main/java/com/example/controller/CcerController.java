package com.example.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.Result;

import com.example.service.ICcerService;
import com.example.entity.Ccer;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lzq
 * @since 2023-04-20
 */
@RestController
@RequestMapping("/ccer")
public class CcerController {

    @Resource
    private ICcerService ccerService;

    @PostMapping
    public Result save(@RequestBody Ccer ccer) {
        ccerService.saveOrUpdate(ccer);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        ccerService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/multi")
    public Result deleteMultiple(@RequestBody List<Integer> ids) {
        ccerService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(ccerService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(ccerService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Ccer> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(ccerService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

