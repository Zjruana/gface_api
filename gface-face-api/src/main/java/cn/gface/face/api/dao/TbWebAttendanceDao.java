package cn.gface.face.api.dao;

import cn.gface.face.api.data.TBAttendanceInfo;
import cn.gface.face.api.web.dto.AttendanceRequestEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbWebAttendanceDao {
    List<TBAttendanceInfo> selectAttendanceMonth(AttendanceRequestEntity requestEntity);

    List<TBAttendanceInfo> selectAttendance(@Param("userName") String userName, @Param("attendanceTime") long attendanceTime);

    List<TBAttendanceInfo> selectAttendanceConfig(AttendanceRequestEntity requestEntity);
}
