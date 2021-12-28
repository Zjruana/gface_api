package cn.gface.face.api.service.impl;

import cn.gface.face.api.dao.TbWebAttendanceDao;
import cn.gface.face.api.data.TBAttendanceInfo;
import cn.gface.face.api.service.TbWebAttendanceService;
import cn.gface.face.api.web.dto.AttendanceRequestEntity;
import cn.gface.face.api.web.dto.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class TbWebAttendanceServiceImpl implements TbWebAttendanceService {
    @Autowired
    private TbWebAttendanceDao tbWebAttendanceDao;

    @Override
    public BaseResult selectAttendanceMonthBR(AttendanceRequestEntity requestEntity) {
        List<TBAttendanceInfo> attendanceInfos = tbWebAttendanceDao.selectAttendanceMonth(requestEntity);
        return BaseResult.success("成功", attendanceInfos);
    }

    @Override
    public BaseResult selectAttendanceBR(AttendanceRequestEntity requestEntity, String userName) throws ParseException {
        // 保存数据给前端
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        // 通行记录
        List<TBAttendanceInfo> attendanceInfos = tbWebAttendanceDao.selectAttendance(userName, requestEntity.getDataTime());
        // 遍历通行记录
        for (TBAttendanceInfo t : attendanceInfos) {
            HashMap<String, Object> user = new HashMap<>();
            // 遍历日期
            String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date(t.getRecordTime()));
            // 上班区间
            long startTimeA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format + " " + t.getToWorkTime()).getTime() - t.getBeforeWorkTime() * 60 * 1000;
            long startTimeB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format + " " + t.getToWorkTime()).getTime() + t.getAfterWorkTime() * 60 * 1000;
            // 下班区间
            long endTimeA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format + " " + t.getOffWorkTime()).getTime() - t.getAllowRetreat() * 60 * 1000;
            long endTimeB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format + " " + t.getOffWorkTime()).getTime() + t.getAllowLate() * 60 * 1000;
            // 判断有效打卡
            Long toWorkSignTime = -1L;
            Long offWorkSignTime = -1L;
            if (t.getRecordTime() >= startTimeA && t.getRecordTime() <= startTimeB) {
                // 上班有效打卡--取第一个
                if (toWorkSignTime.equals(-1L)) {
                    toWorkSignTime = t.getRecordTime();
                }
            }
            if (t.getRecordTime() >= endTimeA && t.getRecordTime() <= endTimeB) {
                // 下班有效打卡--取第一个
                if (offWorkSignTime.equals(-1L)) {
                    offWorkSignTime = t.getRecordTime();
                }
            }
            user.put("name", t.getName());
            user.put("date", format);
            user.put("toWorkSignTime", toWorkSignTime);
            user.put("offWorkSignTime", offWorkSignTime);
            user.put("workName", t.getWorkName());
            user.put("deviceGroup", t.getDeviceGroup());
            user.put("note", t.getNote());
            user.put("id", t.getId());
            user.put("personId", t.getPersonId());
            boolean name = false;
            for (HashMap<String, Object> h : list) {
                // 当有记录但是没有上下班时间记录时，就将后面记录的时间放入进去
                if (h.get("personId").equals(t.getPersonId()) && h.get("date").equals(format) && !toWorkSignTime.equals(-1L) && Long.parseLong(h.get("toWorkSignTime").toString()) == -1L) {
                    h.put("toWorkSignTime", toWorkSignTime);
                }
                if (h.get("personId").equals(t.getPersonId()) && h.get("date").equals(format) && !offWorkSignTime.equals(-1L) && Long.parseLong(h.get("offWorkSignTime").toString()) == -1L) {
                    h.put("offWorkSignTime", offWorkSignTime);
                }
                // 用户和日期筛选
                if (h.get("personId").equals(t.getPersonId()) && h.get("date").equals(format)) {
                    name = true;
                }
            }
            if (!name) {
                list.add(user);
            }
        }
        return BaseResult.success("成功", new HashMap<String, Object>() {{
            put("total", list.size());
            put("items", list);
        }});
    }

    @Override
    public BaseResult selectAttendanceConfigBR(AttendanceRequestEntity requestEntity, String userName) {
        requestEntity.setUserName(userName);
        List<TBAttendanceInfo> attendanceInfos = tbWebAttendanceDao.selectAttendanceConfig(requestEntity);
        return BaseResult.success("成功", new HashMap<String, Object>() {{
            put("total", 2);
            put("items", attendanceInfos);
        }});
    }
}
