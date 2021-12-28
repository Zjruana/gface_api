package cn.gface.face.api.web.controller.v1;

import cn.gface.face.api.interceptor.annotation.Authorization;
import cn.gface.face.api.service.TbWebDashboardService;
import cn.gface.face.api.web.dto.BaseResult;
import cn.gface.face.api.web.dto.UserRecordRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 查询数据返回给首页
 */
@RestController
@RequestMapping(value = "${api.path.v1}/dashboardWeb")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TbWebDashboardController {
    @Autowired
    private TbWebDashboardService tbWebDashboardService;

    /**
     * 查询数据返回给首页()
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "dashboard/info", method = RequestMethod.GET)
    public BaseResult RecordInfo(UserRecordRequestEntity requestEntity, HttpServletRequest req) {
        try {
            if (requestEntity == null) {
                return BaseResult.fail("不为空");
            }
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebDashboardService.selectDashboardBR(userName);
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
     * 查询数据(表格记录)返回给首页
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "dashboard/list", method = RequestMethod.GET)
    public BaseResult DashboardRecordInfo(UserRecordRequestEntity requestEntity, HttpServletRequest req) {
        try {
            if (requestEntity == null) {
                return BaseResult.fail("不为空");
            }
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebDashboardService.selectDashboardTableListBR(requestEntity, userName);
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
     * 实时预警(此 api 会被轮询调用)
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "dashboard/EarlyWarn/{operation}", method = RequestMethod.GET)
    public BaseResult DashboardEarlyWarnInfo(@RequestParam(value = "ids", required = false) String[] ids, HttpServletRequest req, @PathVariable("operation") String operation) {
        try {
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult;
            if ("get".equals(operation)) {
                // 获取消息列表
                baseResult = tbWebDashboardService.selectDashboardEarlyWarnBR(userName);
            } else if ("read".equals(operation)) {
                // 点 已读
                baseResult = tbWebDashboardService.readDashboardEarlyWarnBR(ids, userName);
            } else if ("readDel".equals(operation)) {
                // 点 删除记录
                baseResult = tbWebDashboardService.readDelDashboardEarlyWarnBR(ids, userName);
            } else if ("popOk".equals(operation)) {
                // 弹窗成功后调用
                baseResult = tbWebDashboardService.popOkDashboardEarlyWarnBR(ids, userName);
            } else {
                return BaseResult.fail("api invalid!");
            }
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
