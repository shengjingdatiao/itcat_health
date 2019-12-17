package cn.itcast.mapper;

import cn.itcast.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingMapper {

    int findNumberByorderDate(Date orderDate);

    void updateOrderSetting(OrderSetting orderSetting);

    void editOrderSetting(OrderSetting orderSetting);

    List<OrderSetting> findOrderSettingBydate(Map map);
    //通过日期查找预约设置
    OrderSetting findOrderSettingByDate(Date date);
   //更新已经预约的人数
    void editReservationsByOrderDate(OrderSetting orderSetting);
   //删除过期日期
    void deleteOderSetting(String date);


    List<OrderSetting> findOderSetting(String date);
}
