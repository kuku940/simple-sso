package cn.xiaoyu.simple.sso.server.service;

import cn.xiaoyu.simple.sso.client.domain.User;

public interface UserService {
    /**
     * 根据username和password查找数据库中的用户并返回
     */
    User find(User user);
}