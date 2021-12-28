package cn.gface.face.api.service.impl;

import cn.gface.face.api.dao.TbDeviceDeviceDao;
import cn.gface.face.api.dao.TbDeviceUserDao;
import cn.gface.face.api.dao.TbWebPersonnelDao;
import cn.gface.face.api.data.DeviceUserInfo;
import cn.gface.face.api.data.TBDeviceInfo;
import cn.gface.face.api.service.TbDeviceUserService;
import cn.gface.face.api.web.dto.DeviceUserRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TbDeviceUserServiceImpl implements TbDeviceUserService {
    List<DeviceUserInfo> deviceUserInfo = null;
    @Autowired
    private TbDeviceDeviceDao tbDeviceDeviceDao;
    @Autowired
    private TbDeviceUserDao tbDeviceUserDao;
    @Autowired
    private TbWebPersonnelDao tbWebPersonnelDao;

    @Override
    public HashMap<String, Object> selectUpdateUserBR(DeviceUserRequestEntity requestEntity) {
        TBDeviceInfo doorDevice = tbDeviceDeviceDao.selectDoorDevice(requestEntity.getDeviceCode());
        if (doorDevice != null) {
            // 设备在门禁机下
            deviceUserInfo = tbDeviceUserDao.selectDoorUpdateUser(requestEntity.getDeviceCode(), requestEntity.getLastUpdateTime());
            // 将数据库中 operation_type = 2的记录删除
            deviceUserInfo.stream().filter(i -> i.getOperationType() == 2).forEach(item -> tbWebPersonnelDao.deleteDoorUser(item.getPersonId()));


            List<DeviceUserInfo> blacklistUpdateUser = tbDeviceUserDao.selectBlacklistUpdateUser(requestEntity.getDeviceCode(), requestEntity.getLastUpdateTime());
            // 将数据库中 operation_type = 2的记录删除
            blacklistUpdateUser.stream().filter(i -> i.getOperationType() == 2).forEach(item -> tbWebPersonnelDao.deleteBlacklistUser(item.getPersonId()));

            // 固定用户 和 黑名单用户 合并这两个List
            deviceUserInfo.addAll(blacklistUpdateUser);
        } else {
            // 设备在访客机下
            TBDeviceInfo visDevice = tbDeviceDeviceDao.selectVisDevice(requestEntity.getDeviceCode());
            if (visDevice != null) {
                deviceUserInfo = tbDeviceUserDao.selectVisUpdateUser(requestEntity.getDeviceCode(), requestEntity.getLastUpdateTime());
                // 将数据库中 operation_type = 2的记录删除
                deviceUserInfo.stream().filter(i -> i.getOperationType() == 2).forEach(item -> tbWebPersonnelDao.deleteDoorUser(item.getPersonId()));

            }
        }

        return new HashMap<String, Object>() {
            {
                put("deviceCode", requestEntity.getDeviceCode());
                put("userList", deviceUserInfo);
            }
        };
    }

    @Override
    public HashMap<String, Object> uploadAllUserBR(DeviceUserRequestEntity requestEntity) {
        return new HashMap<String, Object>() {{
            put("deviceCode", requestEntity.getDeviceCode());
        }};
    }


}
