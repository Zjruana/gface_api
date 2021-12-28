package cn.gface.face.api.web.controller.v1;

import cn.gface.face.api.service.TbDeviceUserService;
import cn.gface.face.api.web.dto.BaseResultDevice;
import cn.gface.face.api.web.dto.DeviceUserRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 1.更新人员 updateUser()
 * 2.上传全部人员 uploadAllUser()
 */
@RestController
@RequestMapping(value = "${api.path.v1}/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TbDeviceUserController {
    @Autowired
    private TbDeviceUserService tbDeviceUserService;


    @RequestMapping(value = "updateUser", method = RequestMethod.POST)
    public Serializable updateUser(@RequestBody DeviceUserRequestEntity requestEntity) {
        System.out.println("updateUser = " + requestEntity);
        if (requestEntity.getDeviceCode() == null) {
            return BaseResultDevice.fail();
        }
        HashMap<String, Object> map = tbDeviceUserService.selectUpdateUserBR(requestEntity);
        if (map != null) {
            return map;
        }
        return BaseResultDevice.fail();
    }

    @RequestMapping(value = "uploadAllUser", method = RequestMethod.POST)
    public Serializable uploadAllUser(@RequestBody DeviceUserRequestEntity requestEntity) {
        System.out.println("uploadAllUser = " + requestEntity);
        HashMap<String, Object> baseResultDevice = tbDeviceUserService.uploadAllUserBR(requestEntity);
        if (baseResultDevice != null) {
            return baseResultDevice;
        }
        return BaseResultDevice.fail();
    }
}
