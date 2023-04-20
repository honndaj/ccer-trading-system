package com.example.service.impl;

import com.example.entity.History;
import com.example.mapper.HistoryMapper;
import com.example.service.IHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lzq
 * @since 2023-04-20
 */
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements IHistoryService {

}
