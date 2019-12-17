package cn.itcast.service.impl;


import cn.itcast.entify.PageResult;
import cn.itcast.entify.QueryPageBean;
import cn.itcast.mapper.CheckItemMapper;
import cn.itcast.pojo.CheckItem;
import cn.itcast.service.CheckItemService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @PackageName: cn.itcast.service.impl
 * @ClassName: CheckItemServiceImpl
 * @Author: dongxiyaohui
 * @Date: 2019/11/29 19:02
 * @Description: //TODO
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemMapper checkItemMapper;
    //添加检查项
    @Override
    public void  add(CheckItem checkItem) {

        checkItemMapper.add(checkItem);//添加检查项数据
    }
    //分页查询
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        Integer currentPage = queryPageBean.getCurrentPage();//获取当前页码数
        Integer pageSize = queryPageBean.getPageSize(); //获取当前页码显示的条数
        String queryString = queryPageBean.getQueryString(); //获取当前查询的条件
        PageHelper.startPage(currentPage,pageSize); //进行分页
        Page<CheckItem> page = checkItemMapper.findPage(queryString); //
        long total = page.getTotal();//获取总条数
        List<CheckItem> result = page.getResult();//获取检查项数据

        return new PageResult(total,result);
    }
  //删除检查项
    @Override
    public void deleteCheckItem(Integer id) {
        //删除检查项的时候涉及到检查组的多对多关系，先对检查项和检查组的中间表进行查询，如果查询到的纪录数大于0，则抛出异常，否则进行删除
        Long checkItem_checkgroup = checkItemMapper.findCheckItem_Checkgroup(id);
        if(checkItem_checkgroup>0){
            throw new RuntimeException();
        }else {
            checkItemMapper.deleteCheckItem(id);
        }

    }
   //根据id查询检查项，进行页面数据回写
    @Override
    public CheckItem findByCheckItemId(Integer id) {
        CheckItem checkItemById = checkItemMapper.findCheckItemById(id);
        return checkItemById;
    }
  //编辑检查项
    @Override
    public void editCheckItem(CheckItem checkItem) {
        checkItemMapper.editCheckItemByid(checkItem);

    }

    @Override
    public List<CheckItem> findAll() {
        //查询所有检查项，进行对新增检查组中检查项信息的回写
        return checkItemMapper.findAll();

    }
}
