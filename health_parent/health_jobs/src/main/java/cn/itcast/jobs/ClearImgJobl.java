package cn.itcast.jobs;

import cn.itcast.pojo.OrderSetting;
import cn.itcast.service.OrderSettingService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @PackageName: cn.itcast.jobs
 * @ClassName: ClearImgJobl
 * @Author: dongxiyaohui
 * @Date: 2019/12/12 22:38
 * @Description: //TODO
 */
@Component
public class ClearImgJobl {
     @Reference
     private OrderSettingService orderSetting;
    public void clearImgl() throws Exception {
        List<OrderSetting> orderSettingByDate = orderSetting.findOrderSettingByDate();
        if(orderSettingByDate !=null && orderSettingByDate.size()>0){
            orderSetting.deleteOderSetting();
            System.out.println("过期的预约数据已经被清除");
        }
    }
}
