package com.example.service;

import com.example.entity.Now;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lzq
 * @since 2023-04-20
 */
public interface INowService extends IService<Now> {

    boolean saveSell(Now now);

    boolean sellTrade(Integer id, Integer from, Integer to);

    boolean saveBuy(Now now);

    boolean buyTrade(Integer id, Integer from, Integer to);

    boolean withDrawMoney(Integer id);

    boolean withDrawCcer(Integer id);
}
