package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.entify.PageResult;
import cn.itcast.entify.QueryPageBean;
import cn.itcast.entify.Result;
import cn.itcast.pojo.CheckGroup;
import cn.itcast.service.CheckgroupService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @PackageName: cn.itcast.controller
 * @ClassName: CheckgroupController
 * @Author: dongxiyaohui
 * @Date: 2019/12/1 14:43
 * @Description: //TODO
 */
@RestController
@RequestMapping("/checkGroup")
public class CheckgroupController {
 @Reference
    private CheckgroupService checkgroupService;
// 添加检查组
 @RequestMapping("/addCheckGroup")
 @PreAuthorize("hasAuthority('CHECKGROUP_ADD')")
    public Result addCheckGroup(@RequestBody CheckGroup checkGroup,Integer[] ids){
     try {
         checkgroupService.addCheckgroup(checkGroup,ids);
         return  new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
     } catch (Exception e) {
         e.printStackTrace();
         return new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
     }
 }
 //分页查询
    @RequestMapping("/findPage")
    @PreAuthorize("hasAuthority('CHECKGROUP_QUERY')")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
       return  checkgroupService.findPage(queryPageBean);
    }
    //根据id查询当前检查项的信息
    @RequestMapping("/findCheckGroupById")

    public Result findCheckgroupById(Integer id){
        try {
        CheckGroup checkGroup =checkgroupService.findCheckgroupById(id);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_FAIL,checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
    //根据检查组的id对应的回显检查项
    @RequestMapping("/CheckGroupIdsByCheckGroupIds")
    public Result findCheckItemByCheckGroup(Integer id){
        try {
            List<Integer> checkItemIds = checkgroupService.findCheckItemByCheckgroupId(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_FAIL,checkItemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_SUCCESS);
        }
    }
    //重新编辑检查组
    @RequestMapping("/editCheckGroup")
    @PreAuthorize("hasAuthority('CHECKGROUP_EDIT')")
    public Result editCheckGroup(@RequestBody CheckGroup checkGroup,Integer[] ids){
        try {
            checkgroupService.editCheckGroup(checkGroup,ids);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }
//  根据id删除检查组
    @RequestMapping("/deleteCheckGroup")
    @PreAuthorize("hasAuthority('CHECKGROUP_DELETE')")
    public Result deleteCheckGroup(Integer id) {
        try {
            checkgroupService.deleteGroupById(id);
          return   new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
          return   new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }
    //git 代码冲突了
//测试git
}
