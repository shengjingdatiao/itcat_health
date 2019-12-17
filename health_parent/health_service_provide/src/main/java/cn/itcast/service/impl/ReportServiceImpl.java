package cn.itcast.service.impl;

import cn.itcast.mapper.MemberMapper;
import cn.itcast.mapper.OrderMapper;
import cn.itcast.mapper.SetmealMapper;
import cn.itcast.service.ReportService;
import cn.itcast.util.DateUtils;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @PackageName: cn.itcast.service.impl
 * @ClassName: ReportServiceImpl
 * @Author: dongxiyaohui
 * @Date: 2019/12/11 9:10
 * @Description: //TODO
 */
@Service(interfaceClass= ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    private MemberMapper memberMapper;
    //根据月份查询每月的会员总数
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public List<Integer> findMemberCount(List<String> list) {
        List<Integer> memberCount = new ArrayList<>();
        for (String time : list) {
            String date = time + "-31";
            Integer member = memberMapper.findMemberCountBeforeDate(date);
            memberCount.add(member);
        }
        return memberCount;
    }

    @Override
    public List<Map<String, Object>> findSetMealNameAndCount() {
        List<Map<String, Object>> list =  setmealMapper.findSetmealNameAndCount();
        return list;
    }
   //获取运营统计数据
    @Override
    public Map<String, Object> findBusinessData() throws Exception {
        //获取当天日期
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        //获取当天新增会员数
        Integer todayNewMember = memberMapper.findMemberCountByDate(today);
        //获取会员总数
        Integer totalMember = memberMapper.findMemberTotalCount();
       //获取本周新增会员数
        Date thisWeekMonday = DateUtils.getThisWeekMonday();
        String WeekMonday = DateUtils.parseDate2String(thisWeekMonday);
        Integer thisWeekNewMember = memberMapper.findMemberCountAfterDate(WeekMonday);
        //获取本月新增会员数
        Date firstDay4ThisMonth = DateUtils.getFirstDay4ThisMonth();
        String date2String = DateUtils.parseDate2String(firstDay4ThisMonth);
        Integer thisMonthNewMember = memberMapper.findMemberCountAfterDate(date2String);
        //获取今日预约数
        Integer orderCountByDate = orderMapper.findOrderCountByDate(today);
        //获取今日到诊数目
        Integer visitsCountAfterDate = orderMapper.findVisitsCountAfterDate(today);
        //获取本周预约数
        Integer orderCountAfterDate = orderMapper.findOrderCountAfterDate(WeekMonday);
        //获取本周到诊数
        Integer visitsCountAfterDate1 = orderMapper.findVisitsCountAfterDate(WeekMonday);
        //获取本月预约诊数
        Integer orderCountAfterDate1 = orderMapper.findOrderCountAfterDate(date2String);
        //获取本月到诊数
        Integer visitsCountAfterDate2 = orderMapper.findVisitsCountAfterDate(date2String);
        //获取当前热门套餐
        List<Map> hotSetmeal = orderMapper.findHotSetmeal();

        Map<String, Object> map = new HashMap<>();
        map.put("reportDate",today);
        map.put("todayNewMember",todayNewMember);
        map.put("totalMember",totalMember);
        map.put("thisWeekNewMember",thisWeekNewMember);
        map.put("thisMonthNewMember",thisMonthNewMember);
        map.put("todayOrderNumber",orderCountByDate);
        map.put("todayVisitsNumber",visitsCountAfterDate);
        map.put("thisWeekOrderNumber",orderCountAfterDate);
        map.put("thisWeekVisitsNumber",visitsCountAfterDate1);
        map.put("thisMonthOrderNumber",orderCountAfterDate1);
        map.put("thisMonthVisitsNumber",visitsCountAfterDate2);
        map.put("hotSetmeal",hotSetmeal);
        return map;
    }
}
