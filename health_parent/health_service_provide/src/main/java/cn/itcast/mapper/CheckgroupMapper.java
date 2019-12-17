package cn.itcast.mapper;

import cn.itcast.pojo.CheckGroup;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @PackageName: cn.itcast.mapper
 * @ClassName: CheckgroupMapper
 * @Author: dongxiyaohui
 * @Date: 2019/12/1 14:45
 * @Description: //TODO
 */
public interface CheckgroupMapper {
    //添加检查组
    void addCheckgroup(CheckGroup checkGroup);
   //添加中间表数据
    void addCheckitemandCheckGroup(Map<String, Integer> map);
    //分页查询数据
    Page<CheckGroup> findPage(String queryString);
    //根据检查组id查找对应的检查组
    CheckGroup findById(Integer id);
    //根据检查组id查找中间表检查组对应的检查项id
    List<Integer> findCheckItemByCheckgroupId(Integer id);
    //修改检查组的信息
    void editCheckGroup(CheckGroup checkGroup);
    //根据检查组的id删除中间表 检查组和检查项对应的关系
    void deletCheckgroupAndCheckItem(Integer id);
    //根据检查组id删除对应的检查组数据
    void deletCheckGroupById(Integer id);
    CheckGroup findCheckgroupById(Integer[] id);
}
