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

    /**
     * 在买入大厅，对其中的买入申报进行回应，即卖出
     *
     * @param id
     * @param from
     * @param to
     * @return
     */
    @GetMapping("/buy/trade")
    public Result buyTrade(@RequestParam Integer id,
                           @RequestParam Integer from,
                           @RequestParam Integer to) {
        return Result.success(nowService.buyTrade(id, from, to));
    }

    /**
     * 在买入大厅，发布买入申报
     *
     * @param now
     * @return
     */
    @PostMapping("/buy")
    public Result saveBuy(@RequestBody Now now) {
        return Result.success(nowService.saveBuy(now));
    }

    /**
     * 获取买入大厅的买入申报数据
     *
     * @param pageNum
     * @param pageSize
     * @param uid
     * @return
     */
    @GetMapping("/buy/page")
    public Result findPageBuy(@RequestParam Integer pageNum,
                              @RequestParam Integer pageSize,
                              @RequestParam Integer uid) {
        QueryWrapper<Now> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("buy_sell", "buy");
        queryWrapper.ne("uid", uid);
        queryWrapper.orderByDesc("id");
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
