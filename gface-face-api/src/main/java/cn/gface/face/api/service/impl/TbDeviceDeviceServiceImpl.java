package cn.gface.face.api.service.impl;

import cn.gface.face.api.dao.TbDeviceDeviceDao;
import cn.gface.face.api.dao.TbWebDeviceDao;
import cn.gface.face.api.data.*;
import cn.gface.face.api.service.TbDeviceDeviceService;
import cn.gface.face.api.web.dto.BaseResultDevice;
import cn.gface.face.api.web.dto.DeviceDeviceRequestEntity;
import cn.gface.face.api.web.dto.DeviceRecordRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.HashMap;

@Service
public class TbDeviceDeviceServiceImpl implements TbDeviceDeviceService {
    @Autowired
    private TbDeviceDeviceDao tbDeviceDeviceDao;
    @Autowired
    private TbWebDeviceDao tbWebDeviceDao;

    /**
     * 1.心跳 heartbeat()
     *
     * @param requestEntity
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public HashMap<String, Object> heartbeatBR(DeviceDeviceRequestEntity requestEntity) {
        // 不等于0，有其他操作正在处理，return空
        if (requestEntity.getDeviceUpdateState() != 0) {
            System.out.println("设备正在进行其他操作，不处理");
            return null;
        }
        // 判断是否存在该设备，不存在则不允许进行心跳操作
        RequestEntity entity = new RequestEntity(); // 记录心跳时间有关联，谨慎添加参数
        entity.setDeviceCode(requestEntity.getDeviceCode());
        DeviceHeartbeatInfo heartbeatInfo = tbDeviceDeviceDao.selectHeartbeat(requestEntity.getDeviceCode());
        // 装载需要返回的数据
        HashMap<String, Object> map = new HashMap<>();
        // 设备不存在
        if (heartbeatInfo == null) {
            System.out.println("设备不存在，禁止连接");
            map.put("msg", "未知设备信息");
            return map;
        }
        System.out.println("设备存在，允许连接");
        entity.setHeartbeatTime(new Date().getTime());
        int upDoor = tbWebDeviceDao.updateDoorDeviceInfo(entity);
        if (upDoor == 1) {
            System.out.println("记录门禁设备心跳时间正常");
        } else {
            int upVis = tbWebDeviceDao.updateVisDeviceInfo(entity);
            if (upVis == 1) {
                System.out.println("记录访客设备心跳时间正常");
            } else {
                System.err.println("记录心跳时间失败");
            }
        }
        // 查找数据库，是否需要做操作
        int state = heartbeatInfo.getState(); // 默认是数据库state的操作，优先级高的将会覆盖
        System.out.println("数据库的state = " + state);
        // 设备更新人员时间 与服务器时间不一样，进行更新人员操作
        if (requestEntity.getLastUserUpdateTime() < heartbeatInfo.getLastUserUpdateTime()) {
            state = 1;
        }
        // 设备最后更新设备配置的设置参数修改时间 与服务器时间不一样，进行更新设置操作
        if (requestEntity.getLastDeviceConfigUpdateTime() < heartbeatInfo.getLastDeviceConfigUpdateTime()) {
            state = 2;
        }
        // 特殊：需要更新apk
        if (state == 6) {
            map.put("apkUrl", heartbeatInfo.getApkUrl());
        }
        // 除更新apk之外，返回需要进行的操作码：updateState
        map.put("deviceCode", heartbeatInfo.getDeviceCode());
        map.put("updateState", state);
        System.out.println("最终返回的state = " + state);
        // state和原先数据库赋值相同：则可以将数据库的state变成不需要修改操作
        if (state == heartbeatInfo.getState() && state != 0) {
            // 做完了操作 将数据库state改为0
            DeviceHeartbeatInfo daoUsed = new DeviceHeartbeatInfo();
            daoUsed.setDeviceCode(requestEntity.getDeviceCode());
            daoUsed.setState(0);
            System.out.println("返回给数据库的daoUsed = " + daoUsed);
            int i = tbDeviceDeviceDao.updateHeartbeat(daoUsed);
            System.out.println("是否修改完成？i = " + i);
            if (i != 1) {
                // 当插入失败时--->回滚+撤销本次操作
                map = null;
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
        System.out.println("返回给设备的map = " + map);
        return map;
    }

    /**
     * 2.设置服务器通行权限验证参数  accessVerificationConfig()
     *
     * @param requestEntity
     * @return
     */
    @Override
    public DeviceAccessVerificationConfigInfo accessVerificationConfigBR(DeviceDeviceRequestEntity requestEntity) {
        DeviceAccessVerificationConfigInfo deviceAccessVerificationConfigInfo = tbDeviceDeviceDao.selectAccessVerificationConfig(requestEntity.getDeviceCode());

        return deviceAccessVerificationConfigInfo;
    }

    /**
     * 3.服务器通行权限验证（可第三方服务器对接） accessVerification()
     *
     * @param requestEntity
     * @return
     */
    @Override
    public BaseResultDevice accessVerificationBR(DeviceRecordRequestEntity requestEntity) {
        // TODO: 2021/4/26 接口未实现
        return null;
    }

    /**
     * 4.设置RSA加密  updateRsaConfig()
     *
     * @param requestEntity
     * @return
     */
    @Override
    public DeviceRSAConfigInfo updateRsaConfigBR(DeviceDeviceRequestEntity requestEntity) {
        DeviceRSAConfigInfo deviceRSAConfigInfo = tbDeviceDeviceDao.selectRsaConfig(requestEntity.getDeviceCode());
        return deviceRSAConfigInfo;
    }

    /**
     * 5.设备参数修改 updateDeviceConfig()
     *
     * @param deviceRequestEntity
     * @return
     */
    @Override
    public DeviceConfigInfo updateDeviceConfigBR(DeviceDeviceRequestEntity deviceRequestEntity) {
        DeviceConfigInfo deviceConfigInfo = tbDeviceDeviceDao.selectConfig(deviceRequestEntity.getDeviceCode());
        if (deviceConfigInfo.getUpdateTime() > deviceRequestEntity.getLastUpdateTime()) {
            // 需要更新配置
            return deviceConfigInfo;
        }
        return null;
    }
}
