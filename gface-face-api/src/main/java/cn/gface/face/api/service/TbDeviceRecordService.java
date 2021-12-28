package cn.gface.face.api.service;


import cn.gface.face.api.web.dto.BaseResultDevice;
import cn.gface.face.api.web.dto.DeviceRecordRequestEntity;

public interface TbDeviceRecordService {
    BaseResultDevice insertRecordBR(DeviceRecordRequestEntity requestEntity);
}
