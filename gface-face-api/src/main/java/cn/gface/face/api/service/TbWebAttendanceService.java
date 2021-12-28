package cn.gface.face.api.service;

import cn.gface.face.api.web.dto.AttendanceRequestEntity;
import cn.gface.face.api.web.dto.BaseResult;

import java.text.ParseException;

public interface TbWebAttendanceService {
    BaseResult selectAttendanceMonthBR(AttendanceRequestEntity requestEntity);

    BaseResult selectAttendanceBR(AttendanceRequestEntity requestEntity, String userName) throws ParseException;

    BaseResult selectAttendanceConfigBR(AttendanceRequestEntity requestEntity, String userName);
}
