package cn.xiaoyu.simple.sso.client.domain;

import java.util.Date;

/**
 * 授权对象
 */
public class Auth {
    /**
     * 授权用户
     */
    private User user;
    /**
     * 随机码
     */
    private String code;
    /**
     * 过期时间
     */
    private Date expire;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }
}