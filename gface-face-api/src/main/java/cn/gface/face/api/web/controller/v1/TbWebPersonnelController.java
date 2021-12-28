package cn.gface.face.api.web.controller.v1;

import cn.gface.face.api.dao.TbWebGroupDao;
import cn.gface.face.api.data.RequestEntity;
import cn.gface.face.api.interceptor.annotation.Authorization;
import cn.gface.face.api.service.TbWebPersonnelService;
import cn.gface.face.api.web.dto.BaseResult;
import cn.gface.face.api.web.dto.UserPersonnelRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 1.查询所有员工记录 employeeList()
 * 2.查询所有访客信息 visitorList()
 * 3.新增人员 addUser()
 * 4.上传图片 uploadImg()
 * 5.加载设备列表 loadAllDevice()
 * 6.删除人员 deleteUser()
 * 7.更新人员 updateUser()
 */
@RestController
@RequestMapping(value = "${api.path.v1}/personnelWeb")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TbWebPersonnelController {
    @Autowired
    private TbWebPersonnelService tbWebPersonnelService;
    @Autowired
    private TbWebGroupDao tbWebGroupDao; // 加载设备列表时的查询

    /**
     * 查询所有员工记录
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "employee/list", method = RequestMethod.GET)
    public BaseResult employeeList(UserPersonnelRequestEntity requestEntity, HttpServletRequest req) {
        try {
            if (requestEntity == null) {
                return BaseResult.fail("不能为空");
            }
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebPersonnelService.selectEmployeeInfoBR(requestEntity, userName);
            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.success("返回结果为NULL");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }

    /**
     * 新增人员
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public BaseResult addUser(@RequestBody UserPersonnelRequestEntity requestEntity, HttpServletRequest req) {
        try {
            if (requestEntity == null) {
                return BaseResult.fail("不能为空");
            }
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebPersonnelService.insertUserBR(requestEntity, userName);
            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.success("返回结果为NULL");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }


    /**
     * 加载设备列表
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "loadAllDevice", method = RequestMethod.POST)
    public Serializable loadAllDevice(HttpServletRequest req) {
        try {
            String userName = (String) req.getAttribute("username");
            RequestEntity requestEntity = new RequestEntity();
            requestEntity.setUserName(userName);
            List<HashMap<String, Object>> hashMaps = tbWebGroupDao.selectGroupListInfo(requestEntity);
            System.out.println("tbDeviceInfos = " + hashMaps);
            return BaseResult.success("success", hashMaps);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }

    /**
     * 删除人员
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "deleteUser", method = RequestMethod.GET)
    public Serializable deleteUser(@RequestParam String personId, HttpServletRequest req) {
        try {
            if (null == personId) {
                return BaseResult.fail("请输入合法参数");
            }
            String userName = (String) req.getAttribute("username");
            // 删除流程：修改字段 operation_type = 2 ---> 根据心跳查询这条数据保存后 删除记录
            BaseResult baseResult = tbWebPersonnelService.deleteUserBR(personId, userName);
            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.fail("返回结果为NULL");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }

    /**
     * 更新人员
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "updateUser", method = RequestMethod.POST)
    public BaseResult updateUser(@RequestBody UserPersonnelRequestEntity requestEntity, HttpServletRequest req) {
        try {
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebPersonnelService.updateUserBR(requestEntity, userName);
            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.fail("返回结果为NULL");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }
//    =========================   访客 ================================

    /**
     * 查询所有访客信息
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "visitor/list", method = RequestMethod.GET)
    public BaseResult visitorList(UserPersonnelRequestEntity requestEntity, HttpServletRequest req) {
        try {
            if (requestEntity == null) {
                return BaseResult.fail("不能为空");
            }
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebPersonnelService.selectVisitorInfoBR(requestEntity, userName);
            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.success("返回结果为NULL");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }

    /**
     * 新增访客人员
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "addVisUser", method = RequestMethod.POST)
    public BaseResult addVisUser(@RequestBody UserPersonnelRequestEntity requestEntity, HttpServletRequest req) {
        try {
            if (requestEntity == null) {
                return BaseResult.fail("不能为空");
            }
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebPersonnelService.insertVisUserBR(requestEntity, userName);
            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.success("返回结果为NULL");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }


    /**
     * 加载访客设备列表
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "loadAllVisDevice", method = RequestMethod.POST)
    public Serializable loadAllVisDevice(HttpServletRequest req) {
        try {
            String userName = (String) req.getAttribute("username");
            RequestEntity requestEntity = new RequestEntity();
            requestEntity.setUserName(userName);
            List<HashMap<String, Object>> hashMaps = tbWebGroupDao.selectGroupVisListInfo(requestEntity);
            System.out.println("tbDeviceInfos = " + hashMaps);
            return BaseResult.success("success", hashMaps);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }

    /**
     * 删除访客人员
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "deleteVisUser", method = RequestMethod.GET)
    public Serializable deleteVisUser(@RequestParam String personId, HttpServletRequest req) {
        try {
            if (null == personId) {
                return BaseResult.fail("请输入合法参数");
            }
            String userName = (String) req.getAttribute("username");
            // 删除流程：修改字段 operation_type = 2 ---> 根据心跳查询这条数据保存后 删除记录
            BaseResult baseResult = tbWebPersonnelService.deleteVisUserBR(personId, userName);
            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.fail("返回结果为NULL");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }

    /**
     * 更新访客人员
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "updateVisUser", method = RequestMethod.POST)
    public BaseResult updateVisUser(@RequestBody UserPersonnelRequestEntity requestEntity, HttpServletRequest req) {
        try {
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebPersonnelService.updateVisUserBR(requestEntity, userName);
            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.fail("返回结果为NULL");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }

//    =========================   黑名单 ================================

    /**
     * 查询所有黑名单信息
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "blacklist/list", method = RequestMethod.GET)
    public BaseResult blacklistList(UserPersonnelRequestEntity requestEntity, HttpServletRequest req) {
        try {
            if (requestEntity == null) {
                return BaseResult.fail("不能为空");
            }
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebPersonnelService.selectBlacklistInfoBR(requestEntity, userName);
            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.success("返回结果为NULL");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }

    /**
     * 新增黑名单人员
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "addBlacklistUser", method = RequestMethod.POST)
    public BaseResult addBlacklistUser(@RequestBody UserPersonnelRequestEntity requestEntity, HttpServletRequest req) {
        try {
            if (requestEntity == null) {
                return BaseResult.fail("不能为空");
            }
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebPersonnelService.insertBlacklistUserBR(requestEntity, userName);
            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.success("返回结果为NULL");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }


    /**
     * 删除黑名单人员
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "deleteBlacklistUser", method = RequestMethod.GET)
    public Serializable deleteBlacklistUser(@RequestParam String personId, HttpServletRequest req) {
        try {
            if (null == personId) {
                return BaseResult.fail("请输入合法参数");
            }
            String userName = (String) req.getAttribute("username");
            // 删除流程：修改字段 operation_type = 2 ---> 根据心跳查询这条数据保存后 删除记录
            BaseResult baseResult = tbWebPersonnelService.deleteBlacklistUserBR(personId, userName);
            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.fail("返回结果为NULL");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }

    /**
     * 更新黑名单人员
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "updateBlacklistUser", method = RequestMethod.POST)
    public BaseResult updateBlacklistUser(@RequestBody UserPersonnelRequestEntity requestEntity, HttpServletRequest req) {
        try {
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebPersonnelService.updateBlacklistUserBR(requestEntity, userName);
            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.fail("返回结果为NULL");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }
}
