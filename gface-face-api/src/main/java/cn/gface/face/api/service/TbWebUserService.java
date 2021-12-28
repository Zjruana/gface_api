package cn.gface.face.api.service;


import cn.gface.face.api.data.TBUserInfo;
import cn.gface.face.api.web.dto.BaseResult;
import cn.gface.face.api.web.dto.UserLoginRequestEntity;
import cn.gface.face.api.web.dto.UserUserRequestEntity;

public interface TbWebUserService {

    TBUserInfo selectUserInfo(UserLoginRequestEntity userLoginRequestEntity);

    BaseResult editUserInfo(UserUserRequestEntity requestEntity, String userName);

    BaseResult changePassword(UserUserRequestEntity requestEntity, String userName);

    TBUserInfo selectUserInfoByUserName(String username);

    BaseResult changeServerUrl(UserUserRequestEntity requestEntity, String userName);
}
