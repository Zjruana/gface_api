package cn.gface.face.api.web.controller.v1;

import cn.gface.face.api.data.DeviceAccessVerificationConfigInfo;
import cn.gface.face.api.data.DeviceConfigInfo;
import cn.gface.face.api.data.DeviceRSAConfigInfo;
import cn.gface.face.api.service.TbDeviceDeviceService;
import cn.gface.face.api.web.dto.BaseResult;
import cn.gface.face.api.web.dto.BaseResultDevice;
import cn.gface.face.api.web.dto.DeviceDeviceRequestEntity;
import cn.gface.face.api.web.dto.DeviceRecordRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * 1.心跳 heartbeat()
 * 2.设置服务器通行权限验证参数  accessVerificationConfig()
 * 3.服务器通行权限验证（可第三方服务器对接） accessVerification()
 * 4.设置RSA加密  updateRsaConfig()
 * 5.设备参数修改 updateDeviceConfig()
 */
@RestController
@RequestMapping(value = "${api.path.v1}/device")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TbDeviceDeviceController {

    @Autowired
    private TbDeviceDeviceService tbDeviceDeviceService;

    // * 1.心跳 heartbeat()
    @RequestMapping(value = "heartbeat", method = RequestMethod.POST)
    public Object heartbeat(@RequestBody DeviceDeviceRequestEntity requestEntity) {
        System.out.println("\n ======================== " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "(" + new Date().getTime() + ")" + " ======================== \n");
        System.out.println("设备请求连接 = " + requestEntity.getDeviceCode());
        System.out.println("设备版本 = " + requestEntity.getVersionName());
        System.out.println("设备状态 = " + requestEntity.getDeviceUpdateState());
        System.out.println("上次更新人员时间 = " + requestEntity.getLastUserUpdateTime());
        System.out.println("上次更新配置时间 = " + requestEntity.getLastDeviceConfigUpdateTime());
        System.out.println();
        if (requestEntity.getDeviceCode() == null) {
            return BaseResultDevice.fail();
        }
        HashMap<String, Object> deviceHeartbeatInfo = tbDeviceDeviceService.heartbeatBR(requestEntity);
        if (deviceHeartbeatInfo != null) {
            return deviceHeartbeatInfo;
        }
        return null;
    }

    // * 2.设置服务器通行权限验证参数  accessVerificationConfig()
    @RequestMapping(value = "accessVerificationConfig", method = RequestMethod.POST)
    public Object accessVerificationConfig(@RequestBody DeviceDeviceRequestEntity requestEntity) {
        System.out.println("accessVerificationConfig = " + requestEntity);
        if (requestEntity.getDeviceCode() == null) {
            return BaseResultDevice.fail();
        }
        DeviceAccessVerificationConfigInfo baseResultDevice = tbDeviceDeviceService.accessVerificationConfigBR(requestEntity);
        if (baseResultDevice != null) {
            return baseResultDevice;
        }
        return null;
    }

    // * 3.服务器通行权限验证（可第三方服务器对接） accessVerification()
    @RequestMapping(value = "accessVerification", method = RequestMethod.POST)
    public BaseResultDevice accessVerification(@RequestBody DeviceRecordRequestEntity requestEntity) {
        System.out.println("accessVerification = " + requestEntity);
        if (requestEntity == null) {
            return BaseResultDevice.fail();
        }
        BaseResultDevice baseResultDevice = tbDeviceDeviceService.accessVerificationBR(requestEntity);
        if (baseResultDevice != null) {
            return baseResultDevice;
        }
        return null;
    }

    // * 4.设置RSA加密  updateRsaConfig()
    @RequestMapping(value = "updateRsaConfig", method = RequestMethod.POST)
    public Object updateRsaConfig(@RequestBody DeviceDeviceRequestEntity requestEntity) {
        System.out.println("updateRsaConfig = " + requestEntity);
        if (requestEntity.getDeviceCode() == null || requestEntity.getPublicKey() == null) {
            return BaseResultDevice.fail();
        }
        DeviceRSAConfigInfo baseResultDevice = tbDeviceDeviceService.updateRsaConfigBR(requestEntity);
        if (baseResultDevice != null) {
            return baseResultDevice;
        }
        return null;
    }

    // * 5.设备参数修改 updateDeviceConfig()
    @RequestMapping(value = "updateDeviceConfig", method = RequestMethod.POST)
    public Object updateDeviceConfig(@RequestBody DeviceDeviceRequestEntity requestEntity) {
        System.out.println("updateDeviceConfig = " + requestEntity);
        if (requestEntity.getDeviceCode() == null) {
            return BaseResultDevice.fail();
        }
        DeviceConfigInfo baseResultDevice = tbDeviceDeviceService.updateDeviceConfigBR(requestEntity);
        if (baseResultDevice != null) {
            return baseResultDevice;
        }
        return BaseResult.success("配置为最新状态，无需更新");
    }
}
