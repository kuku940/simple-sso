package cn.xiaoyu.simple.sso.server.controller;

import cn.xiaoyu.simple.sso.client.constant.AuthConst;
import cn.xiaoyu.simple.sso.client.domain.User;
import cn.xiaoyu.simple.sso.client.storage.SessionStorage;
import cn.xiaoyu.simple.sso.client.util.HTTPUtil;
import cn.xiaoyu.simple.sso.server.service.UserService;
import cn.xiaoyu.simple.sso.server.storage.ClientStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 登录和注销控制器
 */
@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    /**
     * 登录
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request, User input, Model model) {
        // 验证用户
        User user = userService.find(input);
        if (user == null) {
            model.addAttribute("error", "username or password is wrong.");
            return "redirect:/";
        }

        // 授权
        String token = UUID.randomUUID().toString();
        request.getSession().setAttribute(AuthConst.IS_LOGIN, true);
        request.getSession().setAttribute(AuthConst.TOKEN, token);

        // 存储，用于注销
        SessionStorage.INSTANCE.set(token, request.getSession());

        // 子系统跳转过来的登录请求，授权、存储后，跳转回去
        String clientUrl = request.getParameter(AuthConst.CLIENT_URL);
        if (clientUrl != null && !"".equals(clientUrl)) {
            // 存储，用于注销
            ClientStorage.INSTANCE.set(token, clientUrl);
            return "redirect:" + clientUrl + "?" + AuthConst.TOKEN + "=" + token;
        }

        return "redirect:/";
    }

    /**
     * 注销
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String token = request.getParameter(AuthConst.LOGOUT_REQUEST);

        // token存在于请求中，表示从客户端发起的注销；token存在于会话中，表示从认证中心发起的注销
        if (token != null && !"".equals(token)) {
            session = SessionStorage.INSTANCE.get(token);
        } else {
            token = (String) session.getAttribute(AuthConst.TOKEN);
        }

        if (session != null) {
            session.invalidate();
        }

        // 注销子系统
        List<String> list = ClientStorage.INSTANCE.get(token);
        if (list != null && !list.isEmpty()) {
            Map<String, String> params = new HashMap<>();
            params.put(AuthConst.LOGOUT_REQUEST, token);
            for (String url : list) {
                HTTPUtil.post(url, params);
            }
        }

        return "redirect:/";
    }
}