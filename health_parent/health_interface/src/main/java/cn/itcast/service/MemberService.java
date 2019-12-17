package cn.itcast.service;

import cn.itcast.pojo.Member;

public interface MemberService {
  //通过手机号查找用户是否为为会员
    Member findBytelephone(String telephone);
    //注册会员
    void  add(Member member);
}
