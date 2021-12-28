package cn.gface.face.api.web.controller.v1;

import cn.gface.face.api.interceptor.annotation.Authorization;
import cn.gface.face.api.service.TbWebRecordService;
import cn.gface.face.api.web.dto.BaseResult;
import cn.gface.face.api.web.dto.UserRecordRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 1.查询所有通行记录 RecordList()
 * 2.查询数据返回给首页 RecordInfo()
 */
@RestController
@RequestMapping(value = "${api.path.v1}/recordWeb")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TbWebRecordController {
    @Autowired
    private TbWebRecordService tbWebRecordService;

    /**
     * 查询门禁通行记录
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "record/list", method = RequestMethod.GET)
    public BaseResult recordList(UserRecordRequestEntity requestEntity, HttpServletRequest req) {
        try {
            if (requestEntity == null) {
                return BaseResult.fail("不为空");
            }
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebRecordService.selectDoorRecordInfoBR(requestEntity, userName);
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
     * 查询所有访客通行记录
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "record_vis/list", method = RequestMethod.GET)
    public BaseResult recordVisList(UserRecordRequestEntity requestEntity, HttpServletRequest req) {
        try {
            if (requestEntity == null) {
                return BaseResult.fail("不为空");
            }
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebRecordService.selectVisRecordInfoBR(requestEntity, userName);
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
     * 查询所有黑名单通行记录
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "record_blacklist/list", method = RequestMethod.GET)
    public BaseResult recordBlacklistList(UserRecordRequestEntity requestEntity, HttpServletRequest req) {
        try {
            if (requestEntity == null) {
                return BaseResult.fail("不为空");
            }
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebRecordService.selectBlacklistRecordInfoBR(requestEntity, userName);
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
     * 查询所有陌生人通行记录
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "record_stranger/list", method = RequestMethod.GET)
    public BaseResult recordStrangerList(UserRecordRequestEntity requestEntity, HttpServletRequest req) {
        try {
            if (requestEntity == null) {
                return BaseResult.fail("不为空");
            }
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebRecordService.selectStrangerRecordInfoBR(requestEntity, userName);
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
