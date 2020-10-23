package cn.xiaoyu.simple.sso.server.dao;

import cn.xiaoyu.simple.sso.client.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    User find(@Param("user") User user,
              @Param("table") String table,
              @Param("userid") String userid,
              @Param("username") String username,
              @Param("password") String password);
}