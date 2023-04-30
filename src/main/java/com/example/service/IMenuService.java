package com.example.service;

import com.example.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lzq
 * @since 2023-04-17
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findAllMenus(String name);
}
