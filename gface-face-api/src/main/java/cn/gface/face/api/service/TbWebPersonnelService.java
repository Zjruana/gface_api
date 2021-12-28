package cn.gface.face.api.service;


import cn.gface.face.api.web.dto.BaseResult;
import cn.gface.face.api.web.dto.UserPersonnelRequestEntity;

public interface TbWebPersonnelService {

    BaseResult selectEmployeeInfoBR(UserPersonnelRequestEntity requestEntity, String userName);

    BaseResult selectVisitorInfoBR(UserPersonnelRequestEntity requestEntity, String userName);

    BaseResult insertUserBR(UserPersonnelRequestEntity requestEntity, String userName);

    BaseResult deleteUserBR(String personId, String userName);

    BaseResult updateUserBR(UserPersonnelRequestEntity requestEntity, String userName);

    BaseResult updateVisUserBR(UserPersonnelRequestEntity requestEntity, String userName);

    BaseResult deleteVisUserBR(String personId, String userName);

    BaseResult insertVisUserBR(UserPersonnelRequestEntity requestEntity, String userName);

    BaseResult selectBlacklistInfoBR(UserPersonnelRequestEntity requestEntity, String userName);

    BaseResult insertBlacklistUserBR(UserPersonnelRequestEntity requestEntity, String userName);

    BaseResult deleteBlacklistUserBR(String personId, String userName);

    BaseResult updateBlacklistUserBR(UserPersonnelRequestEntity requestEntity, String userName);
}
