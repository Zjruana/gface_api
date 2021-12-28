package cn.gface.face.api.dao;

import cn.gface.face.api.data.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;


@Repository
public interface TbWebDashboardDao {

    // 查询数据返回给首页
    TBDashboardInfo selectDashboardNum(String userName);

    List<LinkedHashMap<String, String>> selectDashboardLineData(String userName);

    List<TBRecordInfo> selectDashboardTableListData(RequestEntity entity);

    int selectDashboardTableListDataCount(RequestEntity entity);

    List<TBDashboardEarlyWarnInfo> selectDashboardEarlyWarnInfo(String userName);

    int insertEarlyWarnRecord(DeviceRecordInfo deviceRecordInfo);

    int updateDashboardEarlyWarnInfoByIds(@Param("ids") String[] ids, @Param("userName") String userName);

    int deleteDashboardEarlyWarnInfoByIds(@Param("ids") String[] ids, @Param("userName") String userName);

    int updateNewMsgDashboardEarlyWarnInfoByIds(@Param("ids") String[] ids, @Param("userName") String userName);
}
