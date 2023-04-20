package com.example.service.impl;

import com.example.entity.Now;
import com.example.mapper.NowMapper;
import com.example.service.INowService;
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
public class NowServiceImpl extends ServiceImpl<NowMapper, Now> implements INowService {

}
