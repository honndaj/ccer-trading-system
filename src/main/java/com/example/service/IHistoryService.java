package com.example.service;

import com.example.entity.History;
import com.baomidou.mybatisplus.extension.service.IService;

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
public interface IHistoryService extends IService<History> {

    List<List<Object>> getLineData();

    List<Map<String, Object>> getAreaLatest();
}
