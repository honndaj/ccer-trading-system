package com.example.service.impl;

import com.example.common.Constants;
import com.example.entity.History;
import com.example.mapper.HistoryMapper;
import com.example.service.IHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements IHistoryService {

    @Resource
    HistoryMapper historyMapper;

    @Override
    public List<List<Object>> getLineData() {
        List<Map<String, Object>> resultMapList = historyMapper.selectWeightedAvgPrice();
        List<List<Object>> resultList = new ArrayList<>();

        for (Map<String, Object> resultMap : resultMapList) {
            List<Object> row = new ArrayList<>();
            row.add(resultMap.get("date"));
            row.add(resultMap.get("area"));
            row.add(resultMap.get("weighted_avg_price"));
            resultList.add(row);
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> getAreaLatest() {
        List<Map<String, Object>> areaLatest = new ArrayList<>();
        for(String area : Constants.STRING_AREA) {
            areaLatest.add(historyMapper.getLatestByArea(area));
        }
        return areaLatest;
    }
}
