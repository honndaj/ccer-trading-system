package com.example.service;

import com.example.entity.Ccer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lzq
 * @since 2023-04-20
 */
public interface ICcerService extends IService<Ccer> {

    void updateCcer(Integer to, BigDecimal count);
}
