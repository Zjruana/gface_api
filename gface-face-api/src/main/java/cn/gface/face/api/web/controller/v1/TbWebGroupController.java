package cn.gface.face.api.web.controller.v1;

import cn.gface.face.api.interceptor.annotation.Authorization;
import cn.gface.face.api.service.TbWebGroupService;
import cn.gface.face.api.web.dto.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 1.查询所有设备组信息 groupList()
 * 2.根据组名称查询组下所以设备 groupDeviceList()
 */
@RestController
@RequestMapping(value = "${api.path.v1}/groupWeb")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TbWebGroupController {
    @Autowired
    private TbWebGroupService tbWebGroupService;

    @Authorization
    @RequestMapping(value = "group/list", method = RequestMethod.GET)
    public BaseResult groupList(HttpServletRequest req) {
        try {
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebGroupService.selectGroupListBR(userName);
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
    @RequestMapping(value = "group_vis/list", method = RequestMethod.GET)
    public BaseResult groupVisList(HttpServletRequest req) {
        try {
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebGroupService.selectGroupVisListBR(userName);
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
