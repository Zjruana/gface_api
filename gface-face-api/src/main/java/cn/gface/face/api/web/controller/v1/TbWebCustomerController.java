package cn.gface.face.api.web.controller.v1;

import cn.gface.face.api.interceptor.annotation.Authorization;
import cn.gface.face.api.service.TbWebCustomerService;
import cn.gface.face.api.web.dto.BaseResult;
import cn.gface.face.api.web.dto.UserCustomerRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "${api.path.v1}/system")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TbWebCustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TbWebCustomerController.class);

    @Autowired
    private TbWebCustomerService tbWebCustomerService;

    /**
     * 获取客户管理信息
     *
     * @param requestEntity
     * @return
     */
    @Authorization
    @RequestMapping(value = "getCustomerInfo", method = RequestMethod.GET)
    public BaseResult getCustomerInfo(UserCustomerRequestEntity requestEntity, HttpServletRequest req) {
        try {
            String userName = (String) req.getAttribute("userName");
            String role = (String) req.getAttribute("role");
            int pid = (int) req.getAttribute("pid");
            int id = (int) req.getAttribute("id");
            BaseResult baseResult = tbWebCustomerService.selectCustomerInfo(requestEntity, userName, pid, role, id);

            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.fail("is null");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("error");
        }
    }

    /**
     * 修改客户管理信息
     *
     * @param requestEntity
     * @return
     */
    @Authorization
    @RequestMapping(value = "editCustomerInfo", method = RequestMethod.POST)
    public BaseResult editCustomerInfo(@RequestBody UserCustomerRequestEntity requestEntity, HttpServletRequest req) {
        try {
            String userName = (String) req.getAttribute("username");
            String role = (String) req.getAttribute("role");
            int pid = (int) req.getAttribute("pid");
            BaseResult baseResult = tbWebCustomerService.updateCustomerInfo(requestEntity, userName, pid, role);

            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.fail("is null");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("error");
        }
    }

    /**
     * 删除客户管理信息
     *
     * @param requestEntity
     * @return
     */
    @Authorization
    @RequestMapping(value = "deleteCustomerInfo", method = RequestMethod.DELETE)
    public BaseResult deleteCustomerInfo(@RequestBody UserCustomerRequestEntity requestEntity, HttpServletRequest req) {
        try {
            String userName = (String) req.getAttribute("username");
            String role = (String) req.getAttribute("role");
            int pid = (int) req.getAttribute("pid");
            int id = (int) req.getAttribute("id");
            if (!"admin".equals(role) && !"super-user".equals(role)) {
                return BaseResult.fail(401, "非法访问");
            }
            BaseResult baseResult = tbWebCustomerService.deleteCustomerInfo(requestEntity, userName, pid, role, id);

            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.fail("is null");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("error");
        }
    }

    /**
     * 新增客户管理信息
     *
     * @param requestEntity
     * @return
     */
    @Authorization
    @RequestMapping(value = "addCustomerInfo", method = RequestMethod.POST)
    public BaseResult addCustomerInfo(@RequestBody UserCustomerRequestEntity requestEntity, HttpServletRequest req) {
        try {
            String userName = (String) req.getAttribute("username");
            String role = (String) req.getAttribute("role");
            int pid = (int) req.getAttribute("pid");
            int id = (int) req.getAttribute("id");
            BaseResult baseResult = tbWebCustomerService.insertCustomerInfo(requestEntity, userName, pid, role, id);

            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.fail("is null");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("error");
        }
    }
}
