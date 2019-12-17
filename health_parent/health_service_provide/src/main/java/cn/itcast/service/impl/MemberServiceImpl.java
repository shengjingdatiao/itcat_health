package cn.itcast.service.impl;

import cn.itcast.mapper.MemberMapper;
import cn.itcast.pojo.Member;
import cn.itcast.service.MemberService;
import cn.itcast.util.MD5Utils;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @PackageName: cn.itcast.service.impl
 * @ClassName: MemberServiceImpl
 * @Author: dongxiyaohui
 * @Date: 2019/12/8 16:34
 * @Description: //TODO
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService{
   @Autowired
    private MemberMapper memberMapper;
    //根据手机号查找会员
    @Override
    public Member findBytelephone(String telephone) {
        Member byTelephone = memberMapper.findByTelephone(telephone);
        return byTelephone;
    }
    //添加会员
    @Override
    public void add(Member member) {
        //对添加会员前，先获取会员的登录密码，判断会员的登录密码是否为空，如果不为空对会员的密码的进行md5加密储存
        String password = member.getPassword();
        if(password !=null){
             password = MD5Utils.md5(password);
             member.setPassword(password);
        }
        memberMapper.add(member);
    }
}
