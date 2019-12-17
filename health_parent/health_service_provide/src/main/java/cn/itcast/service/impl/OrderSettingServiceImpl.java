package cn.itcast.service.impl;

import cn.itcast.mapper.OrderSettingMapper;
import cn.itcast.pojo.OrderSetting;
import cn.itcast.service.OrderSettingService;
import cn.itcast.util.DateUtils;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @PackageName: cn.itcast.service.impl
 * @ClassName: OrderSettingServiceImpl
 * @Author: dongxiyaohui
 * @Date: 2019/12/4 15:54
 * @Description: //TODO
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingMapper orderSettingMapper;
   //编辑预约管理
    @Override
    public void editOrderSetting(List<OrderSetting> list) {
        //遍历集合，进行日期的查询，如果可以查询到日期，则进行人数的更新，如果没有查询到日期，则进行日期的编辑
         if(list != null && list.size()>0){
             for (OrderSetting orderSetting : list) {
                   //通过日期查询相对应的人数
             int number = orderSettingMapper.findNumberByorderDate(orderSetting.getOrderDate());
             //人数大于零更新操作
             if(number>0){
                 orderSettingMapper.updateOrderSetting(orderSetting);
              } else{
                 orderSettingMapper.editOrderSetting(orderSetting);
             }
             }
         }
    }
    //获取预约设置数据，进行页面展示
    @Override
    public List<Map> findOrderSettingBydate(String date) {
      //通过日期，对t_orderSetting进行一个范围性的查找，获取到每一天的数据，将数据封装到map集合进行返回
        //date的日期格式为 2019-01 ，需要对日期格式标准化为2019-01-1的格式类型
        String begin = date+"-1";
        String end = date+"-31";
        //将日期定义的日期格式设置到map集合中，进行查询
        Map map = new HashMap();
        map.put("begin",begin);
        map.put("end",end);

        List<OrderSetting> list = orderSettingMapper.findOrderSettingBydate(map);
        List<Map> list1 = new ArrayList<>();
        //遍历list集合，获取到每一个orderSetting对象
        for (OrderSetting orderSetting : list) {
            HashMap hashMap = new HashMap();
            //
            Calendar instance = Calendar.getInstance();//获取当前日期对象
            instance.setTime(orderSetting.getOrderDate());//设置指定的日期
            int i = instance.get(Calendar.DAY_OF_MONTH);
            hashMap.put("date",i);  //设置当前天
            hashMap.put("number",orderSetting.getNumber()); //设置可预约人数
            hashMap.put("reservations",orderSetting.getReservations()); //设置已经预约人数
            list1.add(hashMap);
        }
        return list1;
    }
    //预约人数的设置
    @Override
    public void setOrderSetting(OrderSetting orderSetting) {
        //编辑预约人数，涉及到对当前人数的编辑，还有可能对未定义的进行编辑
       //编辑前进行一个查询，查询当前是否有数据，如果没有数据，进行数据的添加，如果有数据，则根据日期修改可预约人数
       //根据日期查询
        int numberByorderDate = orderSettingMapper.findNumberByorderDate(orderSetting.getOrderDate());

        if(numberByorderDate>0){
            //数据库中有数据，根据当前日期进行数据的更新
            orderSettingMapper.updateOrderSetting(orderSetting);
        }else{
            //数据库中没有数据，根据当前日期进行一个数据添加
            orderSettingMapper.editOrderSetting(orderSetting);
        }

    }
    //删除过期数据
    @Override
    public void deleteOderSetting() throws Exception {
        String date = DateUtils.parseDate2String(new Date());
        orderSettingMapper.deleteOderSetting(date);
    }

    @Override
    public List<OrderSetting> findOrderSettingByDate() throws Exception {
         String date = DateUtils.parseDate2String(new Date());
         List<OrderSetting> list=orderSettingMapper.findOderSetting(date);
         return list;
    }
}
