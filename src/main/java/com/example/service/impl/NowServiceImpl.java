package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.Constants;
import com.example.entity.Ccer;
import com.example.entity.History;
import com.example.entity.Now;
import com.example.entity.User;
import com.example.exception.ServiceException;
import com.example.mapper.CcerMapper;
import com.example.mapper.NowMapper;
import com.example.service.ICcerService;
import com.example.service.IHistoryService;
import com.example.service.INowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

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
    @Resource
    NowMapper nowMapper;

    @Resource
    ICcerService ccerService;

    @Resource
    IUserService userService;

    @Resource
    IHistoryService historyService;

    @Transactional
    @Override
    public boolean saveBuy(Now now) {
        now.setBuySell("buy");
        nowMapper.insert(now);
        if(isMoneyEnough(now.getUid(), now.getCount())) {
            userService.updateMoney(now.getUid(), now.getPrice().multiply(now.getCount()).negate());
        }else {
            throw new ServiceException(Constants.CODE_600, "您的资金不足");
        }
        return true;
    }

    @Transactional
    @Override
    public boolean buyTrade(Integer id, Integer from, Integer to) {
        //获取当前订单信息
        Now now = nowMapper.selectById(id);
        //检测from ccer是否足够
        if(isCcerEnough(from, now.getArea(), now.getKind(), now.getCount())) {
            //若足够
            //to就加上ccer
            ccerService.updateCcer(to, now.getCount());
            //from就减去cccer, 并给from加上money
            ccerService.updateCcer(from, now.getCount().negate());
            userService.updateMoney(from, now.getPrice().multiply(now.getCount()));
        } else {
            throw new ServiceException(Constants.CODE_600, "您的"+now.getArea()+now.getKind()+"CCER不足");
        }
        //删掉now
        nowMapper.deleteById(id);
        //加入histroy
        History history = new History();
        history.setCcerFrom(from);
        history.setCcerTo(to);
        history.setArea(now.getArea());
        history.setKind(now.getKind());
        history.setPrice(now.getPrice());
        history.setCount(now.getCount());
        historyService.save(history);
        return true;
    }

    private boolean isCcerEnough(Integer uid, String area, String kind, BigDecimal count) {
        QueryWrapper<Ccer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        queryWrapper.eq("area", area);
        queryWrapper.eq("kind", kind);
        Ccer ccer = ccerService.getOne(queryWrapper);
        return ccer.getCount().compareTo(count) >= 0;
    }

    private boolean isMoneyEnough(Integer uid, BigDecimal money) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        QueryWrapper<User> id = queryWrapper.eq("id", uid);
         User user = userService.getOne(queryWrapper);
        return user.getMoney().compareTo(money) >= 0;
    }
}
