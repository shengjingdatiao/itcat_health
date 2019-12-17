package cn.itcast.service.impl;

import cn.itcast.entify.PageResult;
import cn.itcast.entify.QueryPageBean;
import cn.itcast.mapper.CheckgroupMapper;
import cn.itcast.pojo.CheckGroup;
import cn.itcast.service.CheckgroupService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PackageName: cn.itcast.service.impl
 * @ClassName: CheckgroupServiceImpl
 * @Author: dongxiyaohui
 * @Date: 2019/12/1 14:46
 * @Description: //TODO
 */
@Service(interfaceClass = CheckgroupService.class)
@Transactional
public class CheckgroupServiceImpl implements CheckgroupService {
    @Autowired
    private CheckgroupMapper checkgroupMapper;


    @Override
    public void addCheckgroup(CheckGroup checkGroup, Integer[] ids) {
        //添加检查组的时候，要对中间表，进行一个添加，建立一个多对多的关系
        checkgroupMapper.addCheckgroup(checkGroup);
       //建立中间关系表
        addMiddle(checkGroup,ids);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<CheckGroup> page = checkgroupMapper.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    //通过id查询检查组
    @Override
    public CheckGroup findCheckgroupById(Integer id) {
      CheckGroup checkGroup = checkgroupMapper.findById(id);
        return checkGroup;
    }
   //通过检查组id回显对应的检查项
    @Override
    public List<Integer> findCheckItemByCheckgroupId(Integer id) {
       List<Integer> checkItemIds= checkgroupMapper.findCheckItemByCheckgroupId(id);
        return checkItemIds;
    }
   //编辑检查组
    @Override
    public void editCheckGroup(CheckGroup checkGroup, Integer[] ids) {
        //修改检查组数据
        checkgroupMapper.editCheckGroup(checkGroup);
        //删除中间表检查组对应的检查项信息
        checkgroupMapper.deletCheckgroupAndCheckItem(checkGroup.getId());
        //建立中间表检查组对应的检查项信息
         addMiddle(checkGroup,ids);
    }
    //根据id删除检查组
    @Override
    public void deleteGroupById(Integer id) {
        //删除检查组之前，要删除中间表的对应关系
        checkgroupMapper.deletCheckgroupAndCheckItem(id);
        //根据id删除对应的检查组
        checkgroupMapper.deletCheckGroupById(id);
    }
    private void addMiddle(CheckGroup checkGroup ,Integer[] ids){
        //建立中间表检查组对应的检查项信息
        if (ids !=null && ids.length>0){
            for (Integer id : ids) {
                Map<String,Integer> map = new HashMap<>();
                map.put("CheckgroupId",checkGroup.getId());
                map.put("CheckItemId",id);
                checkgroupMapper.addCheckitemandCheckGroup(map);
            }
        }
    }
}
