package cn.gface.face.api.dao;

import cn.gface.face.api.data.RequestEntity;
import cn.gface.face.api.data.TBDeviceInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbWebDeviceDao {
    // 查询所有员工信息
    List<TBDeviceInfo> selectDoorDeviceInfo(RequestEntity requestEntity);

    // 查询所有员工信息总数
    int selectDoorDeviceInfoCount(RequestEntity entity);

    // 添加设备
    int updateId();

    int insertDoorDeviceInfo(RequestEntity requestEntity);

    // 更新设备
    int updateDoorDeviceInfo(RequestEntity requestEntity);

    // 插入心跳状态
    int updateHeartbeatId();

    int insertHeartbeatInfo(RequestEntity requestEntity);

    // 删除设备
    int deleteDoorDeviceInfo(@Param("deviceCode") String deviceCode, @Param("userName") String userName);

    // 删除设备->删除心跳
    int deleteHeartbeatDeviceInfo(@Param("deviceCode") String deviceCode);

    // =============================================================== 访客 ====================================================================

    // 查询所有访客信息
    List<TBDeviceInfo> selectVisDeviceInfo(RequestEntity requestEntity);

    int selectVisDeviceInfoCount(RequestEntity entity);

    void updateVisId();

    int insertVisDeviceInfo(RequestEntity requestEntity);

    int deleteVisDeviceInfo(@Param("deviceCode") String deviceCode, @Param("userName") String userName);

    int updateVisDeviceInfo(RequestEntity requestEntity);
}
