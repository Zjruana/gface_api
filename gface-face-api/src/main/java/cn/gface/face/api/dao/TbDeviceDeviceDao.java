package cn.gface.face.api.dao;

import cn.gface.face.api.data.*;
import org.springframework.stereotype.Repository;


@Repository
public interface TbDeviceDeviceDao {
    // 1 更新主键--插入的时候用
    int updateId();

    // 2.1 5.设备参数修改 updateDeviceConfig()---修改配置
    int updateConfig(DeviceConfigInfo deviceConfigInfo);

    // 2.2 5.设备参数修改 updateDeviceConfig()---获取配置
    DeviceConfigInfo selectConfig(String deviceCode);

    // 3 2.设置服务器通行权限验证参数  accessVerificationConfig()---获取服务器通行权限验证配置
    DeviceAccessVerificationConfigInfo selectAccessVerificationConfig(String deviceCode);

    // 4 4.设置RSA加密  updateRsaConfig()---获取RSA配置
    DeviceRSAConfigInfo selectRsaConfig(String deviceCode);

    // 5.1 1.心跳 heartbeat()---获取心跳配置
    DeviceHeartbeatInfo selectHeartbeat(String deviceCode);

    // 5.1 1.心跳 heartbeat()---更新心跳状态
    int updateHeartbeat(DeviceHeartbeatInfo deviceHeartbeatInfo);

    // 5.1 1.心跳 heartbeat()---批量更新心跳状态
    int batchUpdateHeartbeat(DeviceHeartbeatInfo deviceHeartbeatInfo);

    TBDeviceInfo selectDoorDevice(String deviceCode);

    TBDeviceInfo selectVisDevice(String deviceCode);
}
