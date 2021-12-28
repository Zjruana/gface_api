package cn.gface.face.api.web.controller.v1;

import cn.gface.face.api.data.RequestEntity;
import cn.gface.face.api.interceptor.annotation.Authorization;
import cn.gface.face.api.service.TbWebDeviceService;
import cn.gface.face.api.web.dto.BaseResult;
import cn.gface.face.api.web.dto.UserDeviceRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 1.查询所有访客设备记录 deviceList()
 * 2.查询所有门禁设备信息 doorDeviceList()
 * 3.新增门禁设备 addDoorDevice()
 * 4.删除门禁设备 deleteDoorDevice()
 * 5.修改门禁设备 updateDoorDevice()
 */
@RestController
@RequestMapping(value = "${api.path.v1}/deviceWeb")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TbWebDeviceController {
    @Autowired
    private TbWebDeviceService tbWebDeviceService;

    @Authorization
    @RequestMapping(value = "doordevice/list", method = RequestMethod.GET)
    public BaseResult doorDeviceList(UserDeviceRequestEntity requestEntity, HttpServletRequest req) {
        try {
            if (requestEntity == null) {
                return BaseResult.fail("不能为空");
            }
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebDeviceService.selectDoorDeviceInfoBR(requestEntity, userName);
            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.success("返回结果为NULL");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }

    @Authorization
    @RequestMapping(value = "addDoorDevice", method = RequestMethod.POST)
    public BaseResult addDoorDevice(@RequestBody UserDeviceRequestEntity requestEntity, HttpServletRequest req) {
        try {
            if (requestEntity == null) {
                return BaseResult.fail("不能为空");
            }
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebDeviceService.insertDoorDeviceInfoBR(requestEntity, userName);
            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.success("返回结果为NULL");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }

    @Authorization
    @RequestMapping(value = "deleteDoorDevice", method = RequestMethod.GET)
    public BaseResult deleteDoorDevice(@RequestParam String deviceCode, HttpServletRequest req) {
        try {
            if (deviceCode == null) {
                return BaseResult.fail("不能为空");
            }
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebDeviceService.deleteDoorDeviceInfoBR(deviceCode, userName);
            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.success("返回结果为NULL");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }

    @Authorization
    @RequestMapping(value = "updateDoorDevice", method = RequestMethod.POST)
    public BaseResult updateDoorDevice(@RequestBody RequestEntity requestEntity, HttpServletRequest req) {
        try {
            if (requestEntity == null) {
                return BaseResult.fail("不能为空");
            }
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebDeviceService.updateDoorDeviceInfoBR(requestEntity, userName);
            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.success("返回结果为NULL");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }


    @Authorization
    @RequestMapping(value = "visDevice/list", method = RequestMethod.GET)
    public BaseResult visDeviceList(UserDeviceRequestEntity requestEntity, HttpServletRequest req) {
        try {
            if (requestEntity == null) {
                return BaseResult.fail("不能为空");
            }
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebDeviceService.selectVisDeviceInfoBR(requestEntity, userName);
            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.success("返回结果为NULL");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }

    @Authorization
    @RequestMapping(value = "addVisDevice", method = RequestMethod.POST)
    public BaseResult addVisDevice(@RequestBody UserDeviceRequestEntity requestEntity, HttpServletRequest req) {
        try {
            if (requestEntity == null) {
                return BaseResult.fail("不能为空");
            }
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebDeviceService.insertVisDeviceInfoBR(requestEntity, userName);
            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.success("返回结果为NULL");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }

    @Authorization
    @RequestMapping(value = "deleteVisDevice", method = RequestMethod.GET)
    public BaseResult deleteVisDevice(@RequestParam String deviceCode, HttpServletRequest req) {
        try {
            if (deviceCode == null) {
                return BaseResult.fail("不能为空");
            }
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebDeviceService.deleteVisDeviceInfoBR(deviceCode, userName);
            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.success("返回结果为NULL");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }

    @Authorization
    @RequestMapping(value = "updateVisDevice", method = RequestMethod.POST)
    public BaseResult updateVisDevice(@RequestBody RequestEntity requestEntity, HttpServletRequest req) {
        try {
            if (requestEntity == null) {
                return BaseResult.fail("不能为空");
            }
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebDeviceService.updateVisDeviceInfoBR(requestEntity, userName);
            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.success("返回结果为NULL");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }
}
