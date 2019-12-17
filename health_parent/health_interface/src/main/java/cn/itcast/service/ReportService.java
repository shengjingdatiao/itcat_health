package cn.itcast.service;

import java.util.List;
import java.util.Map;

/**
 * @PackageName: cn.itcast.service
 * @ClassName: ReportService
 * @Author: dongxiyaohui
 * @Date: 2019/12/11 9:07
 * @Description: //TODO
 */
public interface ReportService {
    //统计会员数
    public List<Integer> findMemberCount(List<String> list);
   //统计套餐数
    List<Map<String, Object>> findSetMealNameAndCount();
  //获取运营统计数据
    Map<String, Object> findBusinessData() throws Exception;
}
