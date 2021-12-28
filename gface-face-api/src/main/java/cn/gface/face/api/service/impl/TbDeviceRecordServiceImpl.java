package cn.gface.face.api.service.impl;

import cn.gface.face.api.dao.TbDeviceDeviceDao;
import cn.gface.face.api.dao.TbDeviceRecordDao;
import cn.gface.face.api.dao.TbWebDashboardDao;
import cn.gface.face.api.data.DeviceHeartbeatInfo;
import cn.gface.face.api.data.DeviceRecordInfo;
import cn.gface.face.api.service.TbDeviceRecordService;
import cn.gface.face.api.utils.Base64Util;
import cn.gface.face.api.utils.StaticVariable;
import cn.gface.face.api.web.dto.BaseResultDevice;
import cn.gface.face.api.web.dto.DeviceRecordRequestEntity;
import cn.gface.face.api.web.dto.IdCardInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Objects;

@Service
public class TbDeviceRecordServiceImpl implements TbDeviceRecordService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TbDeviceRecordServiceImpl.class);
    @Autowired
    private TbDeviceRecordDao tbDeviceRecordDao;

    @Autowired
    private TbDeviceDeviceDao tbDeviceDeviceDao;

    @Autowired
    private TbWebDashboardDao tbWebDashboardDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResultDevice insertRecordBR(DeviceRecordRequestEntity requestEntity) {

        DeviceHeartbeatInfo heartbeatInfo = tbDeviceDeviceDao.selectHeartbeat(requestEntity.getDeviceCode());
        // 设备不存在
        if (heartbeatInfo == null) {
            return BaseResultDevice.fail("设备不存在");
        }
        DeviceRecordInfo deviceRecordInfo = new DeviceRecordInfo();
        deviceRecordInfo.setConfidence(requestEntity.getConfidence());
        deviceRecordInfo.setDeviceCode(requestEntity.getDeviceCode());
        deviceRecordInfo.setIcCard(requestEntity.getIcCard());
        deviceRecordInfo.setPicUrl(Base64Util.getImgPath(StaticVariable.PATH_DEVICE_FACE_IMG, requestEntity.getImageBase64(), requestEntity.getDeviceCode()));
        deviceRecordInfo.setName(requestEntity.getName());
        deviceRecordInfo.setOcclusion(requestEntity.getOcclusion());
        deviceRecordInfo.setPersonId(requestEntity.getPersonId());
        deviceRecordInfo.setPersonType(requestEntity.getPersonType());
        deviceRecordInfo.setQrCode(requestEntity.getQrCode());
        deviceRecordInfo.setRecordTime(requestEntity.getRecordTime());
        deviceRecordInfo.setRecordType(requestEntity.getRecordType());
        deviceRecordInfo.setTemperature(requestEntity.getTemperature());
        deviceRecordInfo.setHsjl(requestEntity.getHsjl());
        deviceRecordInfo.setYmjl(requestEntity.getYmjl());
        deviceRecordInfo.setXcjl(requestEntity.getXcjl());
        System.out.println("deviceRecordInfo = " + deviceRecordInfo);

        // 健康码 info
        if (requestEntity.getGuokangCode() != null) {
            deviceRecordInfo.setGuokangCode(requestEntity.getGuokangCode().getCode());
            deviceRecordInfo.setGuokangCardId(requestEntity.getGuokangCode().getCardId());
            deviceRecordInfo.setGuokangCardName(requestEntity.getGuokangCode().getCardName());
            deviceRecordInfo.setGuokangCode(requestEntity.getGuokangCode().getCode());
            deviceRecordInfo.setGuokangOverCity(requestEntity.getGuokangCode().getOverCity());
            deviceRecordInfo.setGuokangStatus(requestEntity.getGuokangCode().getStatus());
        }

        // 身份证 info
        if (requestEntity.getIdCardInfo() != null) {
            IdCardInfo idCardInfo = requestEntity.getIdCardInfo();
            idCardInfo.setAddress(idCardInfo.getAddress());
            idCardInfo.setBirth(idCardInfo.getBirth());
            idCardInfo.setCountry(idCardInfo.getCountry());
            idCardInfo.setIdCardPicBase64(Base64Util.getImgPath(StaticVariable.PATH_DEVICE_ID_CARD_IMG, idCardInfo.getIdCardPicBase64(), "IdNo@" + idCardInfo.getIdCardNo() + "@" + requestEntity.getDeviceCode()));
            idCardInfo.setIssuingAuthority(idCardInfo.getIssuingAuthority());
            idCardInfo.setName(idCardInfo.getName());
            idCardInfo.setNation(idCardInfo.getNation());
            idCardInfo.setSex(idCardInfo.getSex());
            idCardInfo.setValidityTime(idCardInfo.getValidityTime());
            // 有身份证号码, 填入数据库
            deviceRecordInfo.setIdCardNo(idCardInfo.getIdCardNo());
            try {
                if (tbDeviceRecordDao.insertRecordIdCard(idCardInfo) != 1) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return BaseResultDevice.fail();
                }
            } catch (Exception e) {
                LOGGER.info("插入身份证异常, 可能已存在：{}", e.getMessage());
            }
        }

        // 预警信息插入
        if (requestEntity.getTemperature() >= 37.2 || requestEntity.getGuokangCode() != null
                        && (requestEntity.getGuokangCode().getStatus() == 3
                        || requestEntity.getGuokangCode().getStatus() == 2)
        ) { // 红码1, 黄码2，高温37.2 => 红色预警
            deviceRecordInfo.setDegree(1); // 1红色预警，2黄色预警，-1一般预警(默认)
            String guokangStatus = "无";
            if (requestEntity.getGuokangCode() != null){
                guokangStatus = String.valueOf(requestEntity.getGuokangCode().getStatus());
            }
            if (tbWebDashboardDao.insertEarlyWarnRecord(deviceRecordInfo) == 1) {
                LOGGER.info("插入预警信息成功 - 人员id:{}, 姓名:{}, 国康码:{}, 体温{}, 记录时间: {}", requestEntity.getPersonId(), requestEntity.getName(), guokangStatus, requestEntity.getTemperature(), requestEntity.getRecordTime());
            } else {
                LOGGER.error("插入预警信息失败 - 人员id:{}, 姓名:{}, 国康码:{}, 体温{}, 记录时间: {}", requestEntity.getPersonId(), requestEntity.getName(), guokangStatus, requestEntity.getTemperature(), requestEntity.getRecordTime());
            }
        }

        if ("".equals(requestEntity.getPersonId()) || requestEntity.getPersonId() == null) {
            // 陌生人记录优先处理
            if (tbDeviceRecordDao.insertStrangerRecord(deviceRecordInfo) == 1) {
                return BaseResultDevice.success();
            }
        } else if (requestEntity.getPersonType() == 0) {
            // 固定用户
            if (tbDeviceRecordDao.insertDoorRecord(deviceRecordInfo) == 1) {
                return BaseResultDevice.success();
            }
        } else if (requestEntity.getPersonType() == 1) {
            // 访客
            if (tbDeviceRecordDao.insertVisRecord(deviceRecordInfo) == 1) {
                return BaseResultDevice.success();
            }
        } else if (requestEntity.getPersonType() == 2) {
            // 黑名单
            if (tbDeviceRecordDao.insertBlacklistRecord(deviceRecordInfo) == 1) {
                return BaseResultDevice.success();
            }
        }
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return BaseResultDevice.fail();
    }
}
