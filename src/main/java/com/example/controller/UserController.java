package com.example.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.service.IUserService;
import com.example.entity.User;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lzq
 * @since 2023-04-16
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    // 新增或者更新
    @PostMapping
    public boolean save(@RequestBody User user) {
            return userService.saveOrUpdate(user);
            }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
            return userService.removeById(id);
            }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
            return userService.removeByIds(ids);
            }

    @GetMapping
    public List<User> findAll() {
            return userService.list();
            }

    @GetMapping("/{id}")
    public User findOne(@PathVariable Integer id) {
            return userService.getById(id);
            }

    @GetMapping("/page")
    public Page<User> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String companyName,
                                @RequestParam(defaultValue = "") String address) {
        Page<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!"".equals(username)) {
            queryWrapper.like("username", username);
        }
        if (!"".equals(companyName)) {
            queryWrapper.like("company_name", companyName);
        }
        if (!"".equals(address)) {
            queryWrapper.like("address", address);
        }
        queryWrapper.orderByDesc("id");
        return userService.page(page, queryWrapper);
    }
}

