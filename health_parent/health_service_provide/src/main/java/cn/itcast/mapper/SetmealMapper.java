package cn.itcast.mapper;

import cn.itcast.pojo.CheckGroup;
import cn.itcast.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface SetmealMapper {
    //查询所有的检查组
    List<CheckGroup> findAll();

    Page<Setmeal> findAllSetMel(String queryString);
    //添加套餐
    void addSetMel(Setmeal setmeals);
    //添加中间表关系
    void addSetMealIdAndcheckGroupId(Map<String, Integer> map);

    Setmeal findSetMelById(Integer id);

    List<Integer> findAllSetMelAndCheckGroup(Integer id);

    void editSetMel(Setmeal setmeals);

    void deleteSetMel(Integer id);

    void deleteSetMealAndCheckgroup(int id);
//    移动端查询所有套餐信息
    List<Setmeal> findAllSetmeal();

    Setmeal findById(int id);

    List<Map<String, Object>> findSetmealNameAndCount();

}
