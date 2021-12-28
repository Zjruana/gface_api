package cn.gface.face.api.dao;

import cn.gface.face.api.data.DeviceUserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TbDeviceUserDao {
    // 将新的固定人员返回给设备
    List<DeviceUserInfo> selectDoorUpdateUser(@Param("deviceCode") String deviceCode, @Param("lastUpdateTime") long lastUpdateTime);

    // 将新的访客人员返回给设备
    List<DeviceUserInfo> selectVisUpdateUser(@Param("deviceCode") String deviceCode, @Param("lastUpdateTime") long lastUpdateTime);

    // 将新的黑名单人员返回给设备
    List<DeviceUserInfo> selectBlacklistUpdateUser(@Param("deviceCode") String deviceCode, @Param("lastUpdateTime") long lastUpdateTime);
}
