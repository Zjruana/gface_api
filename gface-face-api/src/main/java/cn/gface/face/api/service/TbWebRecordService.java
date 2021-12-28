package cn.gface.face.api.service;


import cn.gface.face.api.web.dto.BaseResult;
import cn.gface.face.api.web.dto.UserRecordRequestEntity;

public interface TbWebRecordService {

    BaseResult selectDoorRecordInfoBR(UserRecordRequestEntity requestEntity, String userName);

    BaseResult selectVisRecordInfoBR(UserRecordRequestEntity requestEntity, String userName);

    BaseResult selectBlacklistRecordInfoBR(UserRecordRequestEntity requestEntity, String userName);

    BaseResult selectStrangerRecordInfoBR(UserRecordRequestEntity requestEntity, String userName);
}
