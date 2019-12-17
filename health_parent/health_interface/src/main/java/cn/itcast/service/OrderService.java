package cn.itcast.service;

import cn.itcast.entify.Result;

import java.util.Map;

public interface OrderService {
    //用户提交预约信息
    Result submit(Map map) throws Exception;
    //通过id查询预约者信息
    Map findById(Integer id) throws Exception;
}
