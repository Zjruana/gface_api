package cn.gface.face.api.dao;

import cn.gface.face.api.data.DeviceRecordInfo;
import cn.gface.face.api.web.dto.IdCardInfo;
import org.springframework.stereotype.Repository;


@Repository
public interface TbDeviceRecordDao {
    // 上传通行记录 -- 门禁
    int insertDoorRecord(DeviceRecordInfo recordInfo);

    // 上传通行记录 -- 访客
    int insertVisRecord(DeviceRecordInfo recordInfo);

    // 上传通行记录 -- 黑名单
    int insertBlacklistRecord(DeviceRecordInfo recordInfo);

    // 上传通行记录 -- 陌生人
    int insertStrangerRecord(DeviceRecordInfo deviceRecordInfo);

    // 身份证信息统一处理
    int insertRecordIdCard(IdCardInfo idCardInfo);
}
