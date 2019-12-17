package cn.itcast.jobs;

import cn.itcast.constant.RedisConstant;
import cn.itcast.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @PackageName: cn.itcast.jobs
 * @ClassName: ClearImgJob
 * @Author: dongxiyaohui
 * @Date: 2019/12/3 19:40
 * @Description: //TODO
 */
@Component
public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;
    public void clearImg() {
      //获取两个set集合中的差值
        Set<String> sdiff = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,RedisConstant.SETMEAL_PIC_DB_RESOURCES);
     //遍历获取到差值的set集合
        for (String s : sdiff) {
              //调用七牛云服务删除，差值图片
            QiniuUtils.deleteFileFromQiniu(s);
            if(s!=null) {
                //移除redis数据库中的缓存数据（上传的照片未进行提交到数据库）
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,s);
            }
        }

    }

}
