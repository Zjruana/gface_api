package cn.gface.face.api.service;


import cn.gface.face.api.data.DeviceAccessVerificationConfigInfo;
import cn.gface.face.api.data.DeviceConfigInfo;
import cn.gface.face.api.data.DeviceRSAConfigInfo;
import cn.gface.face.api.web.dto.BaseResultDevice;
import cn.gface.face.api.web.dto.DeviceDeviceRequestEntity;
import cn.gface.face.api.web.dto.DeviceRecordRequestEntity;

import java.util.HashMap;

public interface TbDeviceDeviceService {
    // * 1.心跳
    HashMap<String, Object> heartbeatBR(DeviceDeviceRequestEntity requestEntity);

    // * 2.设置服务器通行权限验证参数
    DeviceAccessVerificationConfigInfo accessVerificationConfigBR(DeviceDeviceRequestEntity requestEntity);

    // * 3.服务器通行权限验证
    BaseResultDevice accessVerificationBR(DeviceRecordRequestEntity requestEntity);

    // * 4.设置RSA加密
    DeviceRSAConfigInfo updateRsaConfigBR(DeviceDeviceRequestEntity requestEntity);

    // * 5.设备参数修改
    DeviceConfigInfo updateDeviceConfigBR(DeviceDeviceRequestEntity requestEntity);
}
