package cn.itcast.service;

import cn.itcast.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    //编辑预约管理
    void editOrderSetting(List<OrderSetting> list);
    //获取预约数据，进行页面展示
    List<Map> findOrderSettingBydate(String date);
    //设置可预约人数
    void setOrderSetting(OrderSetting orderSetting);
   //删除过期数据
    void deleteOderSetting() throws Exception;
    List<OrderSetting> findOrderSettingByDate() throws Exception;
}
