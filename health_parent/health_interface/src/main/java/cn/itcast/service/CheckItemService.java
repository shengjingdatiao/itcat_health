package cn.itcast.service;

import cn.itcast.entify.PageResult;
import cn.itcast.entify.QueryPageBean;
import cn.itcast.entify.Result;
import cn.itcast.pojo.CheckItem;

import java.util.List;

/**
 * @PackageName: cn.itcast.service
 * @ClassName: CheckItemService
 * @Author: dongxiyaohui
 * @Date: 2019/11/29 19:00
 * @Description: //TODO
 */
public interface CheckItemService {
    //添加检查项
   void add(CheckItem checkItem);
   //分页查询数据
    PageResult findPage(QueryPageBean queryPageBean);
    //删除检查项
    void deleteCheckItem(Integer id);

    CheckItem findByCheckItemId(Integer id);

    void editCheckItem(CheckItem checkItem);

    List<CheckItem> findAll();
}
