package com.example.service;

import com.example.entity.Ccer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lzq
 * @since 2023-04-20
 */
public interface ICcerService extends IService<Ccer> {

    void saveOrUpdateCcer(Integer to, BigDecimal count, String area, String kind);

    List<Map<String, Object>> ccerArea(Integer uid);

    List<Map<String, Object>> ccerKind(Integer uid);
}
