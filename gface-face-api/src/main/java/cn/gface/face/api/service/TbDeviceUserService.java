package cn.gface.face.api.service;


import cn.gface.face.api.web.dto.DeviceUserRequestEntity;

import java.util.HashMap;

public interface TbDeviceUserService {
    HashMap<String, Object> selectUpdateUserBR(DeviceUserRequestEntity requestEntity);

    // * 2.上传全部人员
    HashMap<String, Object> uploadAllUserBR(DeviceUserRequestEntity requestEntity);
}
