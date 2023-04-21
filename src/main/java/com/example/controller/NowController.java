package com.example.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.service.IUserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.Result;

import com.example.service.INowService;
import com.example.entity.Now;

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
@RequestMapping("/now")
public class NowController {

    @Resource
    private INowService nowService;

    @Resource
    private IUserService userService;

    @GetMapping("/buy/trade")
    public Result buyTrade(@RequestParam Integer id,
                           @RequestParam Integer from,
                           @RequestParam Integer to) {
        return Result.success(nowService.buyTrade(id, from, to));
    }

    @Transactional
    @PostMapping("/buy")//TODO 余额不足没处理&可以不用手写sql，对now set一下buyorsell即可
    public Result saveBuy(@RequestBody Now now) {
        nowService.saveBuy(now);
        userService.updateMoney(now.getUid(), now.getPrice().multiply(now.getCount()).negate());
        return Result.success();
    }

    @GetMapping("/buy/page")
    public Result findPageBuy(@RequestParam Integer pageNum,
                              @RequestParam Integer pageSize) {
        QueryWrapper<Now> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("buy_sell", "buy");
        return Result.success(nowService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Now now) {
        nowService.saveOrUpdate(now);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        nowService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        nowService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(nowService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(nowService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Now> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(nowService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

