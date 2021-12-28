package cn.gface.face.api.dao;

import cn.gface.face.api.data.RequestEntity;
import cn.gface.face.api.data.TBUserInfo;
import cn.gface.face.api.web.dto.UserLoginRequestEntity;
import org.springframework.stereotype.Repository;


@Repository
public interface TbWebUserDao {
    // 根据用户账户密码，返回用户信息
    TBUserInfo selectUserInfo(UserLoginRequestEntity userLoginRequestEntity);

    int updateUserInfo(RequestEntity tbRequestInfo);

    int updateUserPassword(RequestEntity tbRequestInfo);

    TBUserInfo selectUserInfoByUserName(String username);

    int changeServerUrl(RequestEntity entity);
}
