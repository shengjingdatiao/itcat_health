package cn.itcast.service.impl;

import cn.itcast.constant.MessageConstant;
import cn.itcast.entify.Result;
import cn.itcast.mapper.MemberMapper;
import cn.itcast.mapper.OrderMapper;
import cn.itcast.mapper.OrderSettingMapper;
import cn.itcast.pojo.Member;
import cn.itcast.pojo.Order;
import cn.itcast.pojo.OrderSetting;
import cn.itcast.service.OrderService;
import cn.itcast.util.DateUtils;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @PackageName: cn.itcast.service.impl
 * @ClassName: OrderServiceImpl
 * @Author: dongxiyaohui
 * @Date: 2019/12/7 18:58
 * @Description: //TODO
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderSettingMapper orderSettingMapper;
    @Autowired
    MemberMapper memberMapper;
     @Autowired
    OrderMapper orderMapper;
    //用户提交预约体检预约信息
    @Override
    public Result submit(Map map) throws Exception {
        //体检预约时可能存在以下问题：
        // 1.用户预约的当前日期，没有设置预约服务
           //获取用户预约日期
        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);
        OrderSetting orderSettingByDate = orderSettingMapper.findOrderSettingByDate(DateUtils.parseString2Date(orderDate));
        if(orderSettingByDate==null){
            //当期日期为非预约日期
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //2.用户预约当前的日期里，已经预约的人数大于可预约的人数
        if(orderSettingByDate.getReservations()>=orderSettingByDate.getNumber()){
            return new Result(false,MessageConstant.ORDER_FULL);
        }
        //4.检查用户是否为会员，如果是会员直接进行预约，如果不是会员，添加会员后，进行体检的预约
        //根据用户的电话查询用户是否为会员
         String  telephone = (String) map.get("telephone");
         Member member = memberMapper.findByTelephone(telephone);
        //该用户为会员
        if(member!=null){
            //获取会员的id和套餐的id，预约日期进行查询
            Integer memberId = new Member().getId(); //获取会员id
            //获取套餐id
            String  setmealId = (String) map.get("setmealId");
            //获取用户提交的日期
            Date orderdate1 = DateUtils.parseString2Date(orderDate);
            //根据会员的id和套餐的id，预约日期进行查询
            Order order = new Order(memberId, orderdate1, null, null, Integer.parseInt(setmealId));
            List<Order> byCondition = orderMapper.findByCondition(order);
            if(byCondition !=null&&byCondition.size()>0){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }
        //预约人数加一
        orderSettingByDate.setReservations(orderSettingByDate.getReservations()+1);
        orderSettingMapper.editReservationsByOrderDate(orderSettingByDate);
        //当前用户未进行会员注册
      if(member == null){
          //将该用户注册为会员
          member = new Member();
          System.out.println((String)map.get("name"));
          member.setName((String)map.get("name"));//设置会员姓名
          member.setIdCard((String)map.get("IdCard")); //设置会员身份证信息
          member.setSex((String)map.get("sex")); //设置会员的性别
          member.setPhoneNumber((String)map.get("telephone"));
          member.setRegTime(new Date());
          memberMapper.add(member);
      }
        //5,更新预约设置表
        Order order = new Order(member.getId(),date,(String)map.get("orderType"),Order.ORDERSTATUS_NO,Integer.parseInt((String) map.get("setmealId")));
        orderMapper.add(order);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
    }

    //通过预约者id查询预约信息，并且回显页面
    @Override
    public Map findById(Integer id) throws Exception {
//     <p>体检人：{{orderInfo.member}}</p>
//                            <p>体检套餐：{{orderInfo.setmeal}}</p>
//                            <p>体检日期：{{orderInfo.orderDate}}</p>
//                            <p>预约类型：{{orderInfo.orderType}}</p>
        Map byId4Detail = orderMapper.findById4Detail(id);
        if(byId4Detail!=null){
            //将数据库的日期格式改为规定的日期格式
            Date orderDate = (Date) byId4Detail.get("orderDate");
            byId4Detail.put("orderDate",DateUtils.parseDate2String(orderDate));
        }
        return byId4Detail;
    }
}
