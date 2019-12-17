package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.entify.Result;
import cn.itcast.pojo.OrderSetting;
import cn.itcast.service.OrderSettingService;
import cn.itcast.util.POIUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @PackageName: cn.itcast.controller
 * @ClassName: OrderSettingController
 * @Author: dongxiyaohui
 * @Date: 2019/12/4 14:43
 * @Description: //TODO
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
  @Reference
    private OrderSettingService orderSettingService;
   //excel表数据上传
    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile")MultipartFile multipartFile){
         //使用POI工具类，获取Excel文件
        try {
            //以字符串数组形式获取Excel表格数据
            List<String[]> strings = POIUtils.readExcel(multipartFile);
            if(strings !=null && strings.size()>0){
                List<OrderSetting> list = new ArrayList<>();
                //遍历list集合，获取到数据数组，进行重新赋值
                for (String[] string : strings) {
                    String orderDate = string[0];
                    String number = string[1];
                    OrderSetting orderSetting = new OrderSetting(new Date(orderDate), Integer.parseInt(number));
                    list.add(orderSetting);
                }
                orderSettingService.editOrderSetting(list);
                return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
            }
        } catch (IOException e) {
               e.printStackTrace();
        }
        return new Result(false,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }
   //预约数据的查询与展示
    @RequestMapping("/findOrderSettingBydate")
    public Result findOrderSettingBydate(String date){
     //页面需要的是一json数据类型，将查询的数据封装成list集合，返回页面
        //为什么不用orderSetting，页面需求的数据和ordersetting属性 不匹配，无法进行数据的展示
        try {
            List<Map> list = orderSettingService.findOrderSettingBydate(date);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }
    //预约人数的设置
    @RequestMapping("/setOrderSetting")
    public Result setOrderSetting(@RequestBody OrderSetting orderSetting){
        try {
            orderSettingService.setOrderSetting(orderSetting);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
