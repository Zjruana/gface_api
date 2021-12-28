package cn.gface.face.api.web.controller.v1;

import cn.gface.face.api.data.TBUserInfo;
import cn.gface.face.api.interceptor.annotation.Authorization;
import cn.gface.face.api.service.TbWebUserService;
import cn.gface.face.api.utils.StaticVariable;
import cn.gface.face.api.utils.TokenUtil;
import cn.gface.face.api.web.dto.BaseResult;
import cn.gface.face.api.web.dto.UserLoginRequestEntity;
import cn.gface.face.api.web.dto.UserUserRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 1.用户登录 Login()
 * 2.获取用户信息 GetInfo()
 * 3.退出登录 LogOut()
 */
@RestController
@RequestMapping(value = "${api.path.v1}/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TbWebUserController {
    @Autowired
    private TbWebUserService tbWebUserService;

    @Value("${server.api.url.path}")
    private String ServerApiUrlPath;

    /**
     * 用户登录接口
     *
     * @param requestEntity
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public BaseResult login(@RequestBody UserLoginRequestEntity requestEntity) {
        try {
            if (requestEntity == null) {
                return BaseResult.fail("请输入用户名或密码");
            }
            if (requestEntity.getUsername().equals("")) {
                return BaseResult.fail("请输入用户名");
            }
            if (requestEntity.getPassword().equals("")) {
                return BaseResult.fail("请输入密码");
            }
            TBUserInfo tbUserInfo = tbWebUserService.selectUserInfo(requestEntity);
            if (tbUserInfo == null) {
                return BaseResult.fail("请检查用户名或密码是否正确");
            }
            String token = TokenUtil.setToken(tbUserInfo);
            if (tbUserInfo != null || token != null) {
                return BaseResult.success("登录成功", token);
            }
            return BaseResult.fail("登录失败");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }

    /**
     * 获取用户详细信息
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "info", method = RequestMethod.GET)
    public BaseResult getRecordInfo(@RequestParam String token) {
        try {
            if (token.length() < 18) {
                BaseResult.fail(50008, "无效的token");
            }
            if (!TokenUtil.verifyToken(token)) {
                return BaseResult.fail(50014, "token已过期");
            }
            String username = (String) Objects.requireNonNull(TokenUtil.getToken(token)).get("username");
            TBUserInfo userInfo = tbWebUserService.selectUserInfoByUserName(username);
            if (userInfo.getServerUrl() == null || userInfo.getServerUrl().isEmpty() || "".equals(userInfo.getServerUrl())) {
                userInfo.setServerUrl(StaticVariable.getServerIp() + ServerApiUrlPath);
            }
            return BaseResult.success("请求成功", userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }

    /**
     * 退出登录
     *
     * @param
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public BaseResult logOut() {
        return BaseResult.success("退出登录成功");
    }


    /**
     * 修改信息
     *
     * @param requestEntity
     * @return
     */
    @Authorization
    @RequestMapping(value = "editUserInfo", method = RequestMethod.POST)
    public BaseResult editUserInfo(@RequestBody UserUserRequestEntity requestEntity, HttpServletRequest req) {
        try {
            String userName = (String) req.getAttribute("username");
            if (requestEntity == null) {
                return BaseResult.fail("参数错误");
            }
            BaseResult baseResult = tbWebUserService.editUserInfo(requestEntity, userName);

            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.fail("is null");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("error");
        }
    }

    /**
     * 修改密码
     *
     * @param requestEntity
     * @return
     */
    @Authorization
    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
    public BaseResult changePassword(@RequestBody UserUserRequestEntity requestEntity, HttpServletRequest req) {
        try {
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebUserService.changePassword(requestEntity, userName);

            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.fail("is null");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("error");
        }
    }

    /**
     * 修改服务器地址
     *
     * @param requestEntity
     * @return
     */
    @Authorization
    @RequestMapping(value = "changeServerUrl", method = RequestMethod.POST)
    public BaseResult changeServerUrl(@RequestBody UserUserRequestEntity requestEntity, HttpServletRequest req) {
        try {
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebUserService.changeServerUrl(requestEntity, userName);

            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.fail("is null");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("error");
        }
    }
}
