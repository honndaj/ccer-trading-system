package com.example.service;

import com.example.entity.Now;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lzq
 * @since 2023-04-20
 */
public interface INowService extends IService<Now> {

   void saveBuy(Now now);

    boolean buyTrade(Integer id, Integer from, Integer to);
}
