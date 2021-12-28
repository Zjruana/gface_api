package cn.gface.face.api.web.controller.v1;

import cn.gface.face.api.interceptor.annotation.Authorization;
import cn.gface.face.api.service.TbWebAttendanceService;
import cn.gface.face.api.web.dto.AttendanceRequestEntity;
import cn.gface.face.api.web.dto.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 1.查询所有考勤报表记录 attendanceMonth()
 * 2.查询所有考勤管理信息 ()
 * 3.查询所有考勤配置信息 attendanceConfig()
 */
@RestController
@RequestMapping(value = "${api.path.v1}/attendanceWeb")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TbWebAttendanceController {
    @Autowired
    private TbWebAttendanceService tbWebAttendanceService;

    @Authorization
    @RequestMapping(value = "attendanceMonth/list", method = RequestMethod.GET)
    public BaseResult attendanceMonth(AttendanceRequestEntity requestEntity) {
        try {
            if (requestEntity == null) {
                return BaseResult.fail("不能为空");
            }



//            String userName;
//            try {
//                userName = (String) Objects.requireNonNull(TokenUtil.getToken(token)).get("username");
//            } catch (NullPointerException e) {
//                e.printStackTrace();
//                return BaseResult.fail(401, "获取用户信息失败");
//            }
            BaseResult baseResult = tbWebAttendanceService.selectAttendanceMonthBR(requestEntity);
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
    @RequestMapping(value = "attendance/list", method = RequestMethod.GET)
    public BaseResult attendance(AttendanceRequestEntity requestEntity, HttpServletRequest req) {
        try {
            if (requestEntity == null) {
                return BaseResult.fail("不能为空");
            }
            String userName = (String) req.getAttribute("username");
            if (requestEntity.getDataTime() == 0) {
                String format = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(new Date());
                requestEntity.setDataTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format).getTime());
            }
            BaseResult baseResult = tbWebAttendanceService.selectAttendanceBR(requestEntity, userName);
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
    @RequestMapping(value = "attendanceConfig/list", method = RequestMethod.GET)
    public BaseResult attendanceConfig(AttendanceRequestEntity requestEntity, HttpServletRequest req) {
        try {
            System.out.println("requestEntity = " + requestEntity);
            if (requestEntity == null) {
                return BaseResult.fail("不能为空");
            }
            String userName = (String) req.getAttribute("username");
            BaseResult baseResult = tbWebAttendanceService.selectAttendanceConfigBR(requestEntity, userName);
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
