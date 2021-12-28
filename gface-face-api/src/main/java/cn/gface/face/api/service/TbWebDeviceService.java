package cn.gface.face.api.service;

import cn.gface.face.api.data.RequestEntity;
import cn.gface.face.api.web.dto.BaseResult;
import cn.gface.face.api.web.dto.UserDeviceRequestEntity;

public interface TbWebDeviceService {

    BaseResult selectDoorDeviceInfoBR(UserDeviceRequestEntity requestEntity, String userName);

    BaseResult insertDoorDeviceInfoBR(UserDeviceRequestEntity requestEntity, String userName);

    BaseResult deleteDoorDeviceInfoBR(String deviceCode, String userName);

    BaseResult updateDoorDeviceInfoBR(RequestEntity requestEntity, String userName);

    BaseResult selectVisDeviceInfoBR(UserDeviceRequestEntity requestEntity, String userName);

    BaseResult insertVisDeviceInfoBR(UserDeviceRequestEntity requestEntity, String userName);

    BaseResult deleteVisDeviceInfoBR(String deviceCode, String userName);

    BaseResult updateVisDeviceInfoBR(RequestEntity requestEntity, String userName);
}
