package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.common.SystemString;
import com.example.entity.Ccer;
import com.example.mapper.CcerMapper;
import com.example.service.ICcerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void saveOrUpdateCcer(Integer to, BigDecimal count, String area, String kind) {
        //先判断有无
        Ccer ccer = ccerMapper.selectOne(new QueryWrapper<Ccer>().eq("uid", to).eq("area",area).eq("kind", kind));
        if(ccer != null) {
            UpdateWrapper<Ccer> updateWrapper = new UpdateWrapper<>();
            updateWrapper.setSql("count = count + " + count)
                    .eq("uid", to).eq("area",area).eq("kind", kind);
            ccerMapper.update(null, updateWrapper);
        }else {
            ccer = new Ccer();
            ccer.setArea(area);
            ccer.setKind(kind);
            ccer.setCount(count);
            ccer.setUid(to);
            ccerMapper.insert(ccer);
        }
    }

    @Override
    public List<Map<String, Object>> ccerArea(Integer uid) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        for(String areaName : SystemString.STRING_AREA) {
            Map<String, Object> data = new HashMap<>();
            data.put("name", areaName);
            data.put("value", ccerMapper.sumByArea(areaName) != null ? ccerMapper.sumByArea(areaName) : 0);
            dataList.add(data);
        }
        return dataList;
    }

    @Override
    public List<Map<String, Object>> ccerKind(Integer uid) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        for(String kindName : SystemString.STRING_KIND) {
            Map<String, Object> data = new HashMap<>();
            data.put("name", kindName);
            data.put("value", ccerMapper.sumByKind(kindName) != null ? ccerMapper.sumByKind(kindName) : 0);
            dataList.add(data);
        }
        return dataList;
    }
}
