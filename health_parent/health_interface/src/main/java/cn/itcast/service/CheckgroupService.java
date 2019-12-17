package cn.itcast.service;

import cn.itcast.entify.PageResult;
import cn.itcast.entify.QueryPageBean;
import cn.itcast.pojo.CheckGroup;

import java.util.List;

public interface CheckgroupService {
    //添加检查项
    void addCheckgroup(CheckGroup checkGroup, Integer[] ids);
    //分页查询
    PageResult findPage(QueryPageBean queryPageBean);

    CheckGroup findCheckgroupById(Integer id);

    List<Integer> findCheckItemByCheckgroupId(Integer id);

    void editCheckGroup(CheckGroup checkGroup, Integer[] ids);

    void deleteGroupById(Integer id);
}
