package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.entify.PageResult;
import cn.itcast.entify.QueryPageBean;
import cn.itcast.entify.Result;
import cn.itcast.pojo.CheckItem;
import cn.itcast.service.CheckItemService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PackageName: cn.itcast.controller
 * @ClassName: CheckItemController
 * @Author: dongxiyaohui
 * @Date: 2019/11/29 18:53
 * @Description: //TODO
 */
@RestController
@RequestMapping("/checkItem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;
//     添加检查项数据
    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }

        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }
    //分页查询数据
    @RequestMapping("/findPage")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult page = checkItemService.findPage(queryPageBean);
        return page;
    }
    //检查项的删除操作
    @RequestMapping("/deleteCheckItem")
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    public Result deleteCheckItem(Integer id){
        try {
            checkItemService.deleteCheckItem(id);
            return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }
    //根据id查询检查项，进行页面数据回写
    @RequestMapping("/findByCheckItemId")
    public Result findByCheckItemId (Integer id){
        try {
            CheckItem byCheckItemId = checkItemService.findByCheckItemId(id);//将查询到的结果通过result的有参构造方法，进行数据的封装，回写到页面。
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,byCheckItemId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
    //编辑检查项，进行数据的修改
    @RequestMapping("/editCheckItem")
    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")
    public Result editCheckItem(@RequestBody CheckItem checkItem){
        try {
            checkItemService.editCheckItem(checkItem);
            return  new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }
//  查询所有键查项，进行数据的回显
    @RequestMapping("/findAll")
    public Result findAllCheckItem(){
        try {
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemService.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
    //添加测试方法
    // 赵东东
}
