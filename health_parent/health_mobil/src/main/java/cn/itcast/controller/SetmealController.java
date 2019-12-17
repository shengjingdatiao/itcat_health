package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.entify.Result;
import cn.itcast.pojo.Setmeal;
import cn.itcast.service.SetmealService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @PackageName: cn.itcast.controller
 * @ClassName: SetmealController
 * @Author: dongxiyaohui
 * @Date: 2019/12/6 13:38
 * @Description: //TODO
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
   @Reference
    private SetmealService setmealService;
   //查询所有套餐数据
    @RequestMapping("/findAllSetmeal")
    public Result findAllSetmeal(){
        try {
        List<Setmeal>list = setmealService.findAllSetmeal();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
    //根据id查询套餐
    @RequestMapping("/findById")
    public Result findById(int id){
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
