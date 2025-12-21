package com.sky.service;

import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;

import java.time.LocalDateTime;

public interface WorkspaceService {
    /**
     * 今日数据
     * @param begin
     * @param end
     * @return
     */
    BusinessDataVO bussinessData(LocalDateTime begin, LocalDateTime end);

    /**
     * 订单管理
     * @return
     */
    OrderOverViewVO overviewOrders();

    /**
     * 菜品总览
     * @return
     */
    DishOverViewVO overviewDishes();

    /**
     * 套餐总览
     * @return
     */
    SetmealOverViewVO overviewSetmeals();
}
