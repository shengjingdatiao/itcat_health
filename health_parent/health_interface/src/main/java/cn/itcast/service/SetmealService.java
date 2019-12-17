package cn.itcast.service;

import cn.itcast.entify.PageResult;
import cn.itcast.entify.QueryPageBean;
import cn.itcast.pojo.CheckGroup;
import cn.itcast.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
  List<CheckGroup> findAll();
  //分页查询
  PageResult findAllSetMel(QueryPageBean queryPageBean);
 //套餐的添加
    void addSetMel(Setmeal setmeals, Integer[] ids);

    Setmeal findSetMelById(Integer id);

    List<Integer> findSetMELById(Integer id);

    void editSetMel(Setmeal setmeals, Integer[] ids);

    void deleteSetMel(Integer id);
    //查询所有套餐数据
    List<Setmeal> findAllSetmeal();
    //通过id查询套餐数据
    Setmeal findById(int id);
}
