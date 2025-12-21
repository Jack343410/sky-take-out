package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.entity.Orders;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WorkSpaceServiceImpl implements WorkspaceService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 今日数据
     * @param begin
     * @param end
     * @return
     */
    @Override
    public BusinessDataVO bussinessData(LocalDateTime begin, LocalDateTime end) {
        Map map = new HashMap();
        map.put("begin",begin);
        map.put("end",end);
        map.put("status",Orders.COMPLETED);
        Double turnover = orderMapper.sumByMap(map);
        turnover = turnover==null?0:turnover;
        map.put("status",Orders.COMPLETED);
        Integer validOrderCount = orderMapper.sumOrderByMap(map);
        map.put("status",null);
        Integer totalOrderCount = orderMapper.sumOrderByMap(map);
        Double orderCompletionRate = 0.0;
        Double unitPrice = 0.0;
        if(validOrderCount!=0&&totalOrderCount!=0){
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
            unitPrice = turnover/validOrderCount;
        }
        Integer newUsers = orderMapper.getUserCount(begin, end);
        return BusinessDataVO.builder()
                .turnover(turnover)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice)
                .newUsers(newUsers).build();
    }

    /**
     * 订单管理
     * @return
     */
    @Override
    public OrderOverViewVO overviewOrders() {
        Map map = new HashMap();
        LocalDateTime begin = LocalDateTime.now().with(LocalTime.MIN);
        map.put("begin",begin);
        map.put("status", Orders.TO_BE_CONFIRMED);
        Integer waitingOrders = orderMapper.sumOrderByMap(map);
        map.put("status",Orders.CONFIRMED);
        Integer deliveredOrders = orderMapper.sumOrderByMap(map);
        map.put("status",Orders.COMPLETED);
        Integer completedOrders = orderMapper.sumOrderByMap(map);
        map.put("status",Orders.CANCELLED);
        Integer cancelledOrders = orderMapper.sumOrderByMap(map);
        map.put("status",null);
        Integer allOrders = orderMapper.sumOrderByMap(map);
        return OrderOverViewVO.builder()
                .waitingOrders(waitingOrders)
                .deliveredOrders(deliveredOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(cancelledOrders)
                .allOrders(allOrders).build();
    }

    /**
     * 菜品总览
     * @return
     */
    @Override
    public DishOverViewVO overviewDishes() {
        Map map = new HashMap();
        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = dishMapper.countByMap(map);
        map.put("status", StatusConstant.ENABLE);
        Integer sold = dishMapper.countByMap(map);
        return DishOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued).build();
    }

    /**
     * 套餐总览
     * @return
     */
    @Override
    public SetmealOverViewVO overviewSetmeals() {
        Map map = new HashMap();
        map.put("status", StatusConstant.ENABLE);
        Integer sold = setmealMapper.countByMap(map);
        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = setmealMapper.countByMap(map);
        return SetmealOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued).build();
    }


}
