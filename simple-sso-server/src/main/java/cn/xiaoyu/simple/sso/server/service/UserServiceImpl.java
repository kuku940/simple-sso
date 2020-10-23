package cn.xiaoyu.simple.sso.server.service;

import cn.xiaoyu.simple.sso.client.domain.User;
import cn.xiaoyu.simple.sso.server.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Value("${db.table}")
    private String table;
    @Value("${db.table.userid}")
    private String userid;
    @Value("${db.table.username}")
    private String username;
    @Value("${db.table.password}")
    private String password;

    public User find(User user) {
        return userDao.find(user, table, userid, username, password);
    }
}