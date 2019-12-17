package cn.itcast.service.impl;

import cn.itcast.constant.RedisConstant;
import cn.itcast.entify.PageResult;
import cn.itcast.entify.QueryPageBean;
import cn.itcast.mapper.SetmealMapper;
import cn.itcast.pojo.CheckGroup;
import cn.itcast.pojo.Setmeal;
import cn.itcast.service.SetmealService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PackageName: cn.itcast.service.impl
 * @ClassName: SetMealServiceImpl
 * @Author: dongxiyaohui
 * @Date: 2019/12/2 19:30
 * @Description: //TODO
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetMealServiceImpl implements SetmealService {
    @Autowired
     private SetmealMapper setmealMapper;
    @Autowired
     private JedisPool jedisPool;
    //查询所有的检查组
    @Override
    public List<CheckGroup> findAll() {
        List<CheckGroup> all = setmealMapper.findAll();
        return  all;
    }
    //分页查询
    @Override
    public PageResult findAllSetMel(QueryPageBean queryPageBean) {
//        分页助手查询
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Setmeal> page = setmealMapper.findAllSetMel(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }
// 套餐的添加
    @Override
    public void addSetMel(Setmeal setmeals, Integer[] ids) {
        //添加套餐
        setmealMapper.addSetMel(setmeals);
        if(ids != null && ids.length>0) {
            //添加套餐和检查组的中间关系表，遍历ids数组
            for (Integer id : ids) {
                Map<String,Integer> map = new HashMap<>();
                map.put("SetMealId",setmeals.getId());

                map.put("checkGroupId",id);

                setmealMapper.addSetMealIdAndcheckGroupId(map);
            }
            // 用户提交新建的套餐项的数据后，获取用户提交的图片名，保存到redis中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeals.getImg());
        }
    }
//套餐页面的查询
    @Override
    public Setmeal findSetMelById(Integer id) {
      Setmeal setmeal = setmealMapper.findSetMelById(id);
        return setmeal;
    }

    @Override
    public List<Integer> findSetMELById(Integer id) {
      List<Integer> list = setmealMapper.findAllSetMelAndCheckGroup(id);
        return list;
    }

//   编辑套餐
    @Override
    public void editSetMel(Setmeal setmeals, Integer[] ids) {
        //修改套餐数据
         setmealMapper.editSetMel(setmeals);
        //删除中间表对应关系（套餐和检查组的对应关系）
         deleteMiddle(setmeals.getId());
        //建立中间表对应的关系
        if(ids !=null && ids.length>0){
            Map<String,Integer> map = new HashMap<>();
            for (Integer id : ids) {
                map.put("SetMealId",setmeals.getId());
                map.put("checkGroupId",id);
                setmealMapper.addSetMealIdAndcheckGroupId(map);
            }
        }
    }
//删除套餐
    @Override
    public void deleteSetMel(Integer id) {
      //删除中间表对应的关系
      deleteMiddle(id);
      //删除套餐数据
        setmealMapper.deleteSetMel(id);
    }
    //查询所有套餐信息
    @Override
    public List<Setmeal> findAllSetmeal() {
       List<Setmeal> list= setmealMapper.findAllSetmeal();
        return list;
    }
    //通过id查询退套餐数据
    @Override
    public Setmeal findById(int id) {
    Setmeal setmeal = setmealMapper.findById(id);
      return setmeal;
    }

    //    删除中间表对应的关系
    private void deleteMiddle(int id ){
       setmealMapper.deleteSetMealAndCheckgroup(id);
    }
}
