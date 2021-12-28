package cn.gface.face.api.dao;

import cn.gface.face.api.data.RequestEntity;
import cn.gface.face.api.data.TBRecordInfo;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TbWebRecordDao {
    // 查询所有通行记录
    List<TBRecordInfo> selectDoorRecordInfo(RequestEntity requestEntity);

    int selectDoorRecordInfoCount(RequestEntity entity);

    List<TBRecordInfo> selectVisRecordInfo(RequestEntity entity);

    int selectVisRecordInfoCount(RequestEntity entity);

    List<TBRecordInfo> selectBlacklistRecordInfo(RequestEntity entity);

    int selectBlacklistRecordInfoCount(RequestEntity entity);

    List<TBRecordInfo> selectStrangerRecordInfo(RequestEntity entity);

    int selectStrangerRecordInfoCount(RequestEntity entity);
}
