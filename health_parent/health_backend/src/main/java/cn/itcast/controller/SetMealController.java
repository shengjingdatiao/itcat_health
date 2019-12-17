package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.constant.RedisConstant;
import cn.itcast.entify.PageResult;
import cn.itcast.entify.QueryPageBean;
import cn.itcast.entify.Result;
import cn.itcast.pojo.CheckGroup;
import cn.itcast.pojo.Setmeal;
import cn.itcast.service.SetmealService;
import cn.itcast.util.QiniuUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @PackageName: cn.itcast.controller
 * @ClassName: SetMealController
 * @Author: dongxiyaohui
 * @Date: 2019/12/2 19:22
 * @Description: //TODO
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {
    @Reference
    private SetmealService setmeal;
    @Autowired
    private JedisPool jedisPool;
    //文件上传
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile")MultipartFile imgFile){
//       获取原始上传文件名
        String originalFilename = imgFile.getOriginalFilename();
//        截取文件后缀
        int i = originalFilename.lastIndexOf(".");
        String substring = originalFilename.substring(i);
//        通过UUID获取唯一标识的文件名
        String s = UUID.randomUUID().toString();
//        拼接新的文件名
        String fileName = s+substring;
//      调用七牛云工具类，上传文件
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
//            文件上到七牛云后，采用set集合进行文件名的保存
         jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
          return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }
    //查询所有的检查组
    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<CheckGroup> all = setmeal.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, all);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    //分页查询
    @RequestMapping("/findSetMel")
    public PageResult findAllSetMel(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = setmeal.findAllSetMel(queryPageBean);
        return pageResult;
    }
    //套餐的添加
    @RequestMapping("/addSetMel")
    public Result addSetMel(@RequestBody Setmeal setmeals ,Integer[] ids){
        try {
            setmeal.addSetMel(setmeals,ids);
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }
    //套餐页面数据的回显
    @RequestMapping("/findSetMelById")
    public Result findSetMelById(Integer id){
        try {
            Setmeal setmealData = setmeal.findSetMelById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmealData);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
    //根据套餐id查询对应的检查组
    @RequestMapping("/findCheckGroupById")
    public Result findCheckGroupById(Integer id){
        try {
            List<Integer> list = setmeal.findSetMELById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
//  编辑套餐项
    @RequestMapping("/editSetMel")
    public Result editSetMel(@RequestBody Setmeal setmeals ,Integer [] ids){
        try {
            setmeal.editSetMel(setmeals,ids);
            return new Result(true,MessageConstant.EDIT_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }
//  删除套餐项
    @RequestMapping("/deleteSetMelById")
    public Result deleteSetMelById(Integer id){
        try {
            setmeal.deleteSetMel(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }
}
