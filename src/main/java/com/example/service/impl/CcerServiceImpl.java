package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.entity.Ccer;
import com.example.entity.User;
import com.example.mapper.CcerMapper;
import com.example.service.ICcerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lzq
 * @since 2023-04-20
 */
@Service
public class CcerServiceImpl extends ServiceImpl<CcerMapper, Ccer> implements ICcerService {
    @Resource
    CcerMapper ccerMapper;

    @Override
    public void updateCcer(Integer to, BigDecimal count) {
        UpdateWrapper<Ccer> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("count = count + " + count)
                .eq("uid", to);
        ccerMapper.update(null, updateWrapper);
    }
}
