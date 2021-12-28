package cn.gface.face.api.service.impl;

import cn.gface.face.api.dao.TbWebUserDao;
import cn.gface.face.api.data.RequestEntity;
import cn.gface.face.api.data.TBUserInfo;
import cn.gface.face.api.service.TbWebUserService;
import cn.gface.face.api.web.dto.BaseResult;
import cn.gface.face.api.web.dto.UserLoginRequestEntity;
import cn.gface.face.api.web.dto.UserUserRequestEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TbWebUserServiceImpl implements TbWebUserService {
    @Autowired
    private TbWebUserDao tbWebUserDao;

    @Override
    public TBUserInfo selectUserInfo(UserLoginRequestEntity userLoginRequestEntity) {
        return tbWebUserDao.selectUserInfo(userLoginRequestEntity);
    }

    @Override
    public BaseResult editUserInfo(UserUserRequestEntity requestEntity, String userName) {
        RequestEntity tbRequestInfo = new RequestEntity();
        tbRequestInfo.setUserName(userName);
        tbRequestInfo.setName(requestEntity.getName());
        tbRequestInfo.setEmail(requestEntity.getEmail());
        tbRequestInfo.setSex(requestEntity.getSex());
        tbRequestInfo.setProfile(requestEntity.getProfile());
        tbRequestInfo.setPhoneNumber(requestEntity.getPhoneNumber());
        int i = tbWebUserDao.updateUserInfo(tbRequestInfo);
        if (i == 1) {
            return BaseResult.success("修改成功");
        }
        return BaseResult.fail();
    }

    @Override
    public BaseResult changePassword(UserUserRequestEntity requestEntity, String userName) {
        if (requestEntity.getOldPassword() == null) {
            return BaseResult.fail("请输入旧密码");
        } else if (requestEntity.getNewPassword() == null) {
            return BaseResult.fail("请输入新密码");
        } else if (requestEntity.getCheckPassword() == null) {
            return BaseResult.fail("请输入确认密码");
        }
        if (!requestEntity.getNewPassword().equals(requestEntity.getCheckPassword())) {
            return BaseResult.fail("两次密码不一致");
        }
        RequestEntity tbRequestInfo = new RequestEntity();
        tbRequestInfo.setUserName(userName);
        tbRequestInfo.setPassword(DigestUtils.md5Hex(requestEntity.getOldPassword()).toUpperCase());
        tbRequestInfo.setNewPassword(DigestUtils.md5Hex(requestEntity.getNewPassword()).toUpperCase());
        int i = tbWebUserDao.updateUserPassword(tbRequestInfo);
        if (i == 1) {
            return BaseResult.success();
        }
        return BaseResult.fail("与原密码不匹配，多次失败请联系管理员重置密码");
    }

    @Override
    public TBUserInfo selectUserInfoByUserName(String username) {
        return tbWebUserDao.selectUserInfoByUserName(username);
    }

    @Override
    public BaseResult changeServerUrl(UserUserRequestEntity requestEntity, String userName) {
        RequestEntity entity = new RequestEntity();
        entity.setServerUrl(requestEntity.getServerUrl());
        entity.setUserName(userName);
        int i = tbWebUserDao.changeServerUrl(entity);
        if (i == 1) {
            return BaseResult.success();
        }
        return BaseResult.fail("与原密码不匹配，多次失败请联系管理员重置密码");
    }
}
