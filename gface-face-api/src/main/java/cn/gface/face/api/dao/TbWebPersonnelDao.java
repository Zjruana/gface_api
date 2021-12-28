package cn.gface.face.api.dao;

import cn.gface.face.api.data.RequestEntity;
import cn.gface.face.api.data.TBPersonnelInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TbWebPersonnelDao {
    // 查询所有员工信息
    List<TBPersonnelInfo> selectEmployeeInfo(RequestEntity requestEntity);

    int selectEmployeeInfoCount(RequestEntity entity);

    // 查询所有访客信息
    int updateId();

    int insertUser(RequestEntity requestEntity);

    // 删除固定用户
    int deleteDoorUser(@Param("personId") String personId);

    // 删除访客用户
    int deleteVisUser(@Param("personId") String personId);

    // 删除黑名单用户
    int deleteBlacklistUser(@Param("personId") String personId);

    // 更新用户
    int updateUser(RequestEntity requestEntity);

    // 根据person_id 查询用户信息
    TBPersonnelInfo selectUserOnly(@Param("personId") String personId);

    //    ------------------ 访客 ---------------

    // 查询所有访客信息
    List<TBPersonnelInfo> selectVisitorInfo(RequestEntity requestEntity);

    int selectVisitorInfoCount(RequestEntity entity);

    int updateVisUser(RequestEntity request);

    TBPersonnelInfo selectVisUserOnly(@Param("personId") String personId);

    int updateVisId();

    int insertVisUser(RequestEntity entity);

    //    ------------------ 黑名单 ---------------
    List<TBPersonnelInfo> selectBlacklistInfo(RequestEntity requestEntity);

    int selectBlacklistInfoCount(RequestEntity entity);

    void updateBlacklistId();

    int insertBlacklistUser(RequestEntity entity);

    TBPersonnelInfo selectBlacklistUserOnly(String personId);

    void updateBlacklistUser(RequestEntity request);
}
