package cn.gface.face.api.service.impl;

import cn.gface.face.api.dao.TbDeviceDeviceDao;
import cn.gface.face.api.dao.TbWebDeviceDao;
import cn.gface.face.api.dao.TbWebPersonnelDao;
import cn.gface.face.api.data.DeviceHeartbeatInfo;
import cn.gface.face.api.data.RequestEntity;
import cn.gface.face.api.data.TBDeviceInfo;
import cn.gface.face.api.data.TBPersonnelInfo;
import cn.gface.face.api.service.TbWebPersonnelService;
import cn.gface.face.api.web.dto.BaseResult;
import cn.gface.face.api.web.dto.UserPersonnelRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
public class TbWebPersonnelServiceImpl implements TbWebPersonnelService {
    @Autowired
    private TbWebPersonnelDao tbWebPersonnelDao;
    @Autowired
    private TbDeviceDeviceDao tbDeviceDeviceDao;
    @Autowired
    private TbWebDeviceDao tbWebDeviceDao;


    @Override
    public BaseResult selectEmployeeInfoBR(UserPersonnelRequestEntity requestEntity, String userName) {
        RequestEntity entity = new RequestEntity();
        entity.setName(requestEntity.getName());
        entity.setMobilePhone(requestEntity.getMobilePhone());
        entity.setSex(requestEntity.getSex());
        entity.setIcCard(requestEntity.getIcCard());
        entity.setBeginTime(requestEntity.getBeginTime());
        entity.setEndTime(requestEntity.getEndTime());
        entity.setIdCardNo(requestEntity.getIdCardNo());
        entity.setUserName(userName);
        // 分页查询数据
        entity.setPage((requestEntity.getPage() - 1) * requestEntity.getLimit());
        entity.setLimit(requestEntity.getLimit());
        List<TBPersonnelInfo> tbPersonnelInfos = tbWebPersonnelDao.selectEmployeeInfo(entity);
        return BaseResult.success("成功", new HashMap<String, Object>() {{
            put("total", tbWebPersonnelDao.selectEmployeeInfoCount(entity));
            put("items", tbPersonnelInfos);
        }});
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResult insertUserBR(UserPersonnelRequestEntity requestEntity, String userName) {
        System.out.println("requestEntity = " + requestEntity);
        if (requestEntity.getJobNumber() != null && !"".equals(requestEntity.getJobNumber()) && requestEntity.getOpenDoorPassword() != null && !"".equals(requestEntity.getOpenDoorPassword())) {
            if (requestEntity.getOpenDoorPassword().length() < 6) {
                return BaseResult.fail("密码不小于6位");
            }
            if (requestEntity.getJobNumber().length() < 6) {
                return BaseResult.fail("工号长度大于6位");
            }
        }
        if ((requestEntity.getJobNumber() == null || "".equals(requestEntity.getJobNumber()) && requestEntity.getOpenDoorPassword() != null && !"".equals(requestEntity.getOpenDoorPassword()))
                || (requestEntity.getOpenDoorPassword() == null || "".equals(requestEntity.getOpenDoorPassword()) && requestEntity.getJobNumber() != null && !"".equals(requestEntity.getJobNumber()))) {
            return BaseResult.fail("工号或密码不能同时为空");
        }
        RequestEntity entity = new RequestEntity();
        long now = new Date().getTime();
        // 用户ID采用 MD5 对用户姓名+当前时间戳加密
        String s = DigestUtils.md5DigestAsHex((requestEntity.getName() + now).getBytes());
        entity.setPersonId(s);
        entity.setName(requestEntity.getName());
        entity.setRemark(requestEntity.getRemark());
        entity.setMobilePhone(requestEntity.getMobilePhone());
        entity.setIcCard(requestEntity.getIcCard());
        entity.setIdCardNo(requestEntity.getIdCardNo());
        if ("0".equals(requestEntity.getEndTime())) {
            entity.setValidDateEnd(null);
        } else {
            entity.setValidDateEnd(requestEntity.getEndTime());
        }
        entity.setSex(requestEntity.getSex());
        entity.setImageUrl(requestEntity.getImageUrl());
        entity.setOpenDoorPassword(requestEntity.getOpenDoorPassword());
        entity.setJobNumber(requestEntity.getJobNumber());
        entity.setDeviceGroup(requestEntity.getDeviceGroup());
        entity.setValidDateStart(String.valueOf(now));
        entity.setUpdateTime(now);
        entity.setUserName(userName);
        // id自增
        tbWebPersonnelDao.updateId();
        // 开始插入
        int i = tbWebPersonnelDao.insertUser(entity);
        if (i == 1) { // 插入用户成功
            RequestEntity rsEntity = new RequestEntity();
            rsEntity.setUserName(userName);
            rsEntity.setDeviceGroup(requestEntity.getDeviceGroup());
            List<TBDeviceInfo> tbDeviceInfos = tbWebDeviceDao.selectDoorDeviceInfo(rsEntity);
            String[] deviceCodes = tbDeviceInfos.stream().map(TBDeviceInfo::getDeviceCode).toArray(String[]::new);
            DeviceHeartbeatInfo deviceHeartbeatInfo = new DeviceHeartbeatInfo();
            deviceHeartbeatInfo.setLastUserUpdateTime(now);
            deviceHeartbeatInfo.setDeviceCodes(deviceCodes);
            int intBatchUpdateHeartbeat = tbDeviceDeviceDao.batchUpdateHeartbeat(deviceHeartbeatInfo);

            if (intBatchUpdateHeartbeat == deviceCodes.length) {
                return BaseResult.success("新增用户成功");
            }
            // 心跳状态中写入最新的时间异常时，撤销本次新增人员
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return BaseResult.fail();
        }
        return BaseResult.fail();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResult deleteUserBR(String personId, String userName) {
        long time = new Date().getTime();
        // 更新记录
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setPersonId(personId);
        requestEntity.setOperationType(2);
        requestEntity.setUpdateTime(time);
        // 查询该用户所关联的组 再更新对应设备的心跳
        TBPersonnelInfo tbPersonnelInfo = tbWebPersonnelDao.selectUserOnly(personId);
        String deviceGroup = tbPersonnelInfo.getDeviceGroup();
//        TODO 改为批量更新--下面好几个地方
        int m = 0; // 组下所有设备数
        int k = 0; // 成功修改心跳数
        RequestEntity r = new RequestEntity();
        r.setUserName(userName);
        r.setDeviceGroup(deviceGroup);
        List<TBDeviceInfo> tbDeviceInfos = tbWebDeviceDao.selectDoorDeviceInfo(r);
        // 遍历组下所有设备
        for (TBDeviceInfo t : tbDeviceInfos) {
            m++;
            // 更新心跳时间
            DeviceHeartbeatInfo deviceHeartbeatInfo = new DeviceHeartbeatInfo();
            deviceHeartbeatInfo.setDeviceCode(t.getDeviceCode());
            deviceHeartbeatInfo.setLastUserUpdateTime(time);
            int i = tbDeviceDeviceDao.updateHeartbeat(deviceHeartbeatInfo);
            if (i == 1) {
                k++;
            }
        }
        if (m == k) {
            // 更新人员
            tbWebPersonnelDao.updateUser(requestEntity);
            return BaseResult.success("删除成功");
        }
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return BaseResult.fail("删除失败");
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResult updateUserBR(UserPersonnelRequestEntity requestEntity, String userName) {
        if (requestEntity.getJobNumber() != null && !"".equals(requestEntity.getJobNumber()) && requestEntity.getOpenDoorPassword() != null && !"".equals(requestEntity.getOpenDoorPassword())) {
            if (requestEntity.getOpenDoorPassword().length() < 6) {
                return BaseResult.fail("密码不小于6位");
            }
            if (requestEntity.getJobNumber().length() < 6) {
                return BaseResult.fail("工号长度大于6位");
            }
        }
        if ((requestEntity.getJobNumber() == null || "".equals(requestEntity.getJobNumber()) && requestEntity.getOpenDoorPassword() != null && !"".equals(requestEntity.getOpenDoorPassword()))
                || (requestEntity.getOpenDoorPassword() == null || "".equals(requestEntity.getOpenDoorPassword()) && requestEntity.getJobNumber() != null && !"".equals(requestEntity.getJobNumber()))) {
            return BaseResult.fail("工号或密码不能同时为空");
        }
        long time = new Date().getTime();
        RequestEntity request = new RequestEntity();
        request.setName(requestEntity.getName());
        request.setPersonId(requestEntity.getPersonId());
        if ("0".equals(requestEntity.getEndTime())) {
            request.setValidDateEnd(null);
        } else {
            request.setValidDateEnd(requestEntity.getEndTime());
        }
        request.setIcCard(requestEntity.getIcCard());
        request.setIdCardNo(requestEntity.getIdCardNo());
        request.setImageUrl(requestEntity.getImageUrl());
        request.setMobilePhone(requestEntity.getMobilePhone());
        request.setSex(requestEntity.getSex());
        request.setRemark(requestEntity.getRemark());
        request.setUpdateTime(time);
        request.setValidDateStart(String.valueOf(time));
        request.setOperationType(1);
        request.setJobNumber(requestEntity.getJobNumber());
        request.setOpenDoorPassword(requestEntity.getOpenDoorPassword());
        request.setDeviceGroup(requestEntity.getDeviceGroup());
        request.setUserName(userName);
        // 查询该用户所关联的组 再更新对应设备的心跳
        int m = 0; // 组下所有设备数
        int k = 0; // 成功修改心跳数
        // 根据组 查询组下所有设备
        RequestEntity r = new RequestEntity();
        r.setUserName(userName);
        r.setDeviceGroup(requestEntity.getDeviceGroup());
        List<TBDeviceInfo> tbDeviceInfos = tbWebDeviceDao.selectDoorDeviceInfo(r);
        // 遍历组下所有设备
        for (TBDeviceInfo t : tbDeviceInfos) {
            m++;
            String deviceCode = t.getDeviceCode();
            // 更新心跳时间
            DeviceHeartbeatInfo deviceHeartbeatInfo = new DeviceHeartbeatInfo();
            deviceHeartbeatInfo.setDeviceCode(deviceCode);
            deviceHeartbeatInfo.setLastUserUpdateTime(time);
            int i = tbDeviceDeviceDao.updateHeartbeat(deviceHeartbeatInfo);
            if (i == 1) {
                k++;
            }
        }
        if (m == k) {
            // 更新人员
            tbWebPersonnelDao.updateUser(request);
            return BaseResult.success("信息修改成功");
        }
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return BaseResult.fail("信息修改失败");
    }

    //============================== 访客 ===========================

    @Override
    public BaseResult selectVisitorInfoBR(UserPersonnelRequestEntity requestEntity, String userName) {
        RequestEntity entity = new RequestEntity();
        entity.setName(requestEntity.getName());
        entity.setMobilePhone(requestEntity.getMobilePhone());
        entity.setSex(requestEntity.getSex());
        entity.setIcCard(requestEntity.getIcCard());
        entity.setBeginTime(requestEntity.getBeginTime());
        entity.setEndTime(requestEntity.getEndTime());
        entity.setUserName(userName);
        // 分页查询数据
        entity.setPage((requestEntity.getPage() - 1) * requestEntity.getLimit());
        entity.setLimit(requestEntity.getLimit());
        List<TBPersonnelInfo> tbPersonnelInfos = tbWebPersonnelDao.selectVisitorInfo(entity);

        return BaseResult.success("成功", new HashMap<String, Object>() {{
            put("total", tbWebPersonnelDao.selectVisitorInfoCount(entity));
            put("items", tbPersonnelInfos);
        }});
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResult updateVisUserBR(UserPersonnelRequestEntity requestEntity, String userName) {
        if (requestEntity.getJobNumber() != null && !"".equals(requestEntity.getJobNumber()) && requestEntity.getOpenDoorPassword() != null && !"".equals(requestEntity.getOpenDoorPassword())) {
            if (requestEntity.getOpenDoorPassword().length() < 6) {
                return BaseResult.fail("密码不小于6位");
            }
            if (requestEntity.getJobNumber().length() < 6) {
                return BaseResult.fail("工号长度大于6位");
            }
        }
        if ((requestEntity.getJobNumber() == null || "".equals(requestEntity.getJobNumber()) && requestEntity.getOpenDoorPassword() != null && !"".equals(requestEntity.getOpenDoorPassword()))
                || (requestEntity.getOpenDoorPassword() == null || "".equals(requestEntity.getOpenDoorPassword()) && requestEntity.getJobNumber() != null && !"".equals(requestEntity.getJobNumber()))) {
            return BaseResult.fail("工号或密码不能同时为空");
        }
        long time = new Date().getTime();
        RequestEntity request = new RequestEntity();
        request.setName(requestEntity.getName());
        request.setPersonId(requestEntity.getPersonId());
        if ("0".equals(requestEntity.getEndTime())) {
            request.setValidDateEnd(null);
        } else {
            request.setValidDateEnd(requestEntity.getEndTime());
        }
        request.setIcCard(requestEntity.getIcCard());
        request.setImageUrl(requestEntity.getImageUrl());
        request.setMobilePhone(requestEntity.getMobilePhone());
        request.setSex(requestEntity.getSex());
        request.setRemark(requestEntity.getRemark());
        request.setUpdateTime(time);
        request.setValidDateStart(String.valueOf(time));
        request.setOperationType(1);
        request.setJobNumber(requestEntity.getJobNumber());
        request.setOpenDoorPassword(requestEntity.getOpenDoorPassword());
        request.setDeviceGroup(requestEntity.getDeviceGroup());
        request.setUserName(userName);
        request.setIdCardNo(requestEntity.getIdCardNo());
        // 查询该用户所关联的组 再更新对应设备的心跳
        int m = 0; // 组下所有设备数
        int k = 0; // 成功修改心跳数
        // 根据组 查询组下所有设备
        RequestEntity r = new RequestEntity();
        r.setUserName(userName);
        r.setDeviceGroup(requestEntity.getDeviceGroup());
        List<TBDeviceInfo> tbDeviceInfos = tbWebDeviceDao.selectVisDeviceInfo(r);
        // 遍历组下所有设备
        for (TBDeviceInfo t : tbDeviceInfos) {
            m++;
            String deviceCode = t.getDeviceCode();
            // 更新心跳时间
            DeviceHeartbeatInfo deviceHeartbeatInfo = new DeviceHeartbeatInfo();
            deviceHeartbeatInfo.setDeviceCode(deviceCode);
            deviceHeartbeatInfo.setLastUserUpdateTime(time);
            int i = tbDeviceDeviceDao.updateHeartbeat(deviceHeartbeatInfo);
            if (i == 1) {
                k++;
            }
        }
        if (m == k) {
            // 更新人员
            tbWebPersonnelDao.updateVisUser(request);
            return BaseResult.success("信息修改成功");
        }
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return BaseResult.fail("信息修改失败");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResult deleteVisUserBR(String personId, String userName) {
        long time = new Date().getTime();
        // 更新记录
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setPersonId(personId);
        requestEntity.setOperationType(2);
        requestEntity.setUpdateTime(time);
        // 查询该用户所关联的组 再更新对应设备的心跳
        TBPersonnelInfo tbPersonnelInfo = tbWebPersonnelDao.selectVisUserOnly(personId);
        String deviceGroup = tbPersonnelInfo.getDeviceGroup();
        int m = 0; // 组下所有设备数
        int k = 0; // 成功修改心跳数
        RequestEntity r = new RequestEntity();
        r.setUserName(userName);
        r.setDeviceGroup(deviceGroup);
        List<TBDeviceInfo> tbDeviceInfos = tbWebDeviceDao.selectVisDeviceInfo(r);
        // 遍历组下所有设备
        for (TBDeviceInfo t : tbDeviceInfos) {
            m++;
            // 更新心跳时间
            DeviceHeartbeatInfo deviceHeartbeatInfo = new DeviceHeartbeatInfo();
            deviceHeartbeatInfo.setDeviceCode(t.getDeviceCode());
            deviceHeartbeatInfo.setLastUserUpdateTime(time);
            int i = tbDeviceDeviceDao.updateHeartbeat(deviceHeartbeatInfo);
            if (i == 1) {
                k++;
            }
        }
        if (m == k) {
            // 更新人员
            tbWebPersonnelDao.updateVisUser(requestEntity);
            return BaseResult.success("删除成功");
        }
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return BaseResult.fail("删除失败");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResult insertVisUserBR(UserPersonnelRequestEntity requestEntity, String userName) {
        System.out.println("requestEntity = " + requestEntity);
        if (requestEntity.getJobNumber() != null && requestEntity.getOpenDoorPassword() != null) {
            if (requestEntity.getOpenDoorPassword().length() < 6) {
                return BaseResult.fail("密码不小于6位");
            }
            if (requestEntity.getJobNumber().length() < 6) {
                return BaseResult.fail("工号长度大于6位");
            }
        }
        if ((requestEntity.getJobNumber() == null && requestEntity.getOpenDoorPassword() != null) ||
                (requestEntity.getJobNumber() != null && requestEntity.getOpenDoorPassword() == null)) {
            return BaseResult.fail("工号或密码不能为空");
        }
        RequestEntity entity = new RequestEntity();
        long now = new Date().getTime();
        // 用户ID采用 MD5 对用户姓名+当前时间戳加密
        String s = DigestUtils.md5DigestAsHex((requestEntity.getName() + now).getBytes());
        entity.setPersonId(s);
        entity.setName(requestEntity.getName());
        entity.setRemark(requestEntity.getRemark());
        entity.setMobilePhone(requestEntity.getMobilePhone());
        entity.setIcCard(requestEntity.getIcCard());
        if ("0".equals(requestEntity.getEndTime())) {
            entity.setValidDateEnd(null);
        } else {
            entity.setValidDateEnd(requestEntity.getEndTime());
        }
        entity.setSex(requestEntity.getSex());
        entity.setImageUrl(requestEntity.getImageUrl());
        entity.setOpenDoorPassword(requestEntity.getOpenDoorPassword());
        entity.setJobNumber(requestEntity.getJobNumber());
        entity.setDeviceGroup(requestEntity.getDeviceGroup());
        entity.setValidDateStart(String.valueOf(now));
        entity.setIdCardNo(requestEntity.getIdCardNo());
        entity.setUpdateTime(now);
        entity.setUserName(userName);
        // id自增
        tbWebPersonnelDao.updateVisId();
        // 开始插入
        int i = tbWebPersonnelDao.insertVisUser(entity);
        if (i == 1) { // 插入用户成功
            RequestEntity rsEntity = new RequestEntity();
            rsEntity.setUserName(userName);
            rsEntity.setDeviceGroup(requestEntity.getDeviceGroup());
            List<TBDeviceInfo> tbDeviceInfos = tbWebDeviceDao.selectVisDeviceInfo(rsEntity);
            String[] deviceCodes = tbDeviceInfos.stream().map(TBDeviceInfo::getDeviceCode).toArray(String[]::new);
            DeviceHeartbeatInfo deviceHeartbeatInfo = new DeviceHeartbeatInfo();
            deviceHeartbeatInfo.setLastUserUpdateTime(now);
            deviceHeartbeatInfo.setDeviceCodes(deviceCodes);
            int intBatchUpdateHeartbeat = tbDeviceDeviceDao.batchUpdateHeartbeat(deviceHeartbeatInfo);

            if (intBatchUpdateHeartbeat == deviceCodes.length) {
                return BaseResult.success("新增用户成功");
            }
            // 心跳状态中写入最新的时间异常时，撤销本次新增人员
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return BaseResult.fail();
        }
        return BaseResult.fail();
    }

    //============================== 黑名单 ===========================

    @Override
    public BaseResult selectBlacklistInfoBR(UserPersonnelRequestEntity requestEntity, String userName) {
        RequestEntity entity = new RequestEntity();
        entity.setName(requestEntity.getName());
        entity.setMobilePhone(requestEntity.getMobilePhone());
        entity.setSex(requestEntity.getSex());
        entity.setIcCard(requestEntity.getIcCard());
        entity.setIdCardNo(requestEntity.getIdCardNo());
        entity.setBeginTime(requestEntity.getBeginTime());
        entity.setEndTime(requestEntity.getEndTime());
        entity.setUserName(userName);
        // 分页查询数据
        entity.setPage((requestEntity.getPage() - 1) * requestEntity.getLimit());
        entity.setLimit(requestEntity.getLimit());
        List<TBPersonnelInfo> tbPersonnelInfos = tbWebPersonnelDao.selectBlacklistInfo(entity);

        return BaseResult.success("成功", new HashMap<String, Object>() {{
            put("total", tbWebPersonnelDao.selectBlacklistInfoCount(entity));
            put("items", tbPersonnelInfos);
        }});
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResult insertBlacklistUserBR(UserPersonnelRequestEntity requestEntity, String userName) {
        System.out.println("requestEntity = " + requestEntity);
        if (requestEntity.getJobNumber() != null && requestEntity.getOpenDoorPassword() != null) {
            if (requestEntity.getOpenDoorPassword().length() < 6) {
                return BaseResult.fail("密码不小于6位");
            }
            if (requestEntity.getJobNumber().length() < 6) {
                return BaseResult.fail("工号长度大于6位");
            }
        }
        if ((requestEntity.getJobNumber() == null && requestEntity.getOpenDoorPassword() != null) ||
                (requestEntity.getJobNumber() != null && requestEntity.getOpenDoorPassword() == null)) {
            return BaseResult.fail("工号或密码不能为空");
        }
        RequestEntity entity = new RequestEntity();
        long now = new Date().getTime();
        // 用户ID采用 MD5 对用户姓名+当前时间戳加密
        String s = DigestUtils.md5DigestAsHex((requestEntity.getName() + now).getBytes());
        entity.setPersonId(s);
        entity.setName(requestEntity.getName());
        entity.setRemark(requestEntity.getRemark());
        entity.setMobilePhone(requestEntity.getMobilePhone());
        entity.setIcCard(requestEntity.getIcCard());
        entity.setIdCardNo(requestEntity.getIdCardNo());
        entity.setValidDateStart(String.valueOf(now));
        entity.setUpdateTime(now);
        if ("0".equals(requestEntity.getEndTime())) {
            entity.setValidDateEnd(null);
        } else {
            entity.setValidDateEnd(requestEntity.getEndTime());
        }
        entity.setSex(requestEntity.getSex());
        entity.setImageUrl(requestEntity.getImageUrl());
        entity.setDeviceGroup(requestEntity.getDeviceGroup());
        entity.setCreateTime(now);
        entity.setUserName(userName);
        // id自增
        tbWebPersonnelDao.updateBlacklistId();
        // 开始插入
        int i = tbWebPersonnelDao.insertBlacklistUser(entity);
        if (i == 1) { // 插入用户成功
            RequestEntity rsEntity = new RequestEntity();
            rsEntity.setUserName(userName);
            rsEntity.setDeviceGroup(requestEntity.getDeviceGroup());
            List<TBDeviceInfo> tbDeviceInfos = tbWebDeviceDao.selectDoorDeviceInfo(rsEntity);
            String[] deviceCodes = tbDeviceInfos.stream().map(TBDeviceInfo::getDeviceCode).toArray(String[]::new);
            DeviceHeartbeatInfo deviceHeartbeatInfo = new DeviceHeartbeatInfo();
            deviceHeartbeatInfo.setLastUserUpdateTime(now);
            deviceHeartbeatInfo.setDeviceCodes(deviceCodes);
            int intBatchUpdateHeartbeat = tbDeviceDeviceDao.batchUpdateHeartbeat(deviceHeartbeatInfo);

            if (intBatchUpdateHeartbeat == deviceCodes.length) {
                return BaseResult.success("新增用户成功");
            }
            // 心跳状态中写入最新的时间异常时，撤销本次新增人员
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return BaseResult.fail();
        }
        return BaseResult.fail();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResult deleteBlacklistUserBR(String personId, String userName) {
        long time = new Date().getTime();
        // 更新记录
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setPersonId(personId);
        requestEntity.setOperationType(2);
        requestEntity.setUpdateTime(time);
        // 查询该用户所关联的组 再更新对应设备的心跳
        TBPersonnelInfo tbPersonnelInfo = tbWebPersonnelDao.selectBlacklistUserOnly(personId);
        String deviceGroup = tbPersonnelInfo.getDeviceGroup();
        int m = 0; // 组下所有设备数
        int k = 0; // 成功修改心跳数
        RequestEntity r = new RequestEntity();
        r.setUserName(userName);
        r.setDeviceGroup(deviceGroup);
        List<TBDeviceInfo> tbDeviceInfos = tbWebDeviceDao.selectDoorDeviceInfo(r);
        // 遍历组下所有设备
        for (TBDeviceInfo t : tbDeviceInfos) {
            m++;
            // 更新心跳时间
            DeviceHeartbeatInfo deviceHeartbeatInfo = new DeviceHeartbeatInfo();
            deviceHeartbeatInfo.setDeviceCode(t.getDeviceCode());
            deviceHeartbeatInfo.setLastUserUpdateTime(time);
            int i = tbDeviceDeviceDao.updateHeartbeat(deviceHeartbeatInfo);
            if (i == 1) {
                k++;
            }
        }
        if (m == k) {
            // 更新人员
            tbWebPersonnelDao.updateBlacklistUser(requestEntity);
            return BaseResult.success("删除成功");
        }
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return BaseResult.fail("删除失败");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResult updateBlacklistUserBR(UserPersonnelRequestEntity requestEntity, String userName) {
        if (requestEntity.getJobNumber() != null && requestEntity.getOpenDoorPassword() != null) {
            if (requestEntity.getOpenDoorPassword().length() < 6) {
                return BaseResult.fail("密码不小于6位");
            }
            if (requestEntity.getJobNumber().length() < 6) {
                return BaseResult.fail("工号长度大于6位");
            }
        }
        if ((requestEntity.getJobNumber() == null && requestEntity.getOpenDoorPassword() != null) ||
                (requestEntity.getJobNumber() != null && requestEntity.getOpenDoorPassword() == null)) {
            return BaseResult.fail("工号或密码不能为空");
        }
        long time = new Date().getTime();
        RequestEntity request = new RequestEntity();
        request.setName(requestEntity.getName());
        request.setPersonId(requestEntity.getPersonId());
        if ("0".equals(requestEntity.getEndTime())) {
            request.setValidDateEnd(null);
        } else {
            request.setValidDateEnd(requestEntity.getEndTime());
        }
        request.setIcCard(requestEntity.getIcCard());
        request.setIdCardNo(requestEntity.getIdCardNo());
        request.setImageUrl(requestEntity.getImageUrl());
        request.setMobilePhone(requestEntity.getMobilePhone());
        request.setSex(requestEntity.getSex());
        request.setRemark(requestEntity.getRemark());
        request.setUpdateTime(time);
        request.setValidDateStart(String.valueOf(time));
        request.setOperationType(1);
        request.setDeviceGroup(requestEntity.getDeviceGroup());
        request.setUserName(userName);
        // 查询该用户所关联的组 再更新对应设备的心跳
        int m = 0; // 组下所有设备数
        int k = 0; // 成功修改心跳数
        // 根据组 查询组下所有设备
        RequestEntity r = new RequestEntity();
        r.setUserName(userName);
        r.setDeviceGroup(requestEntity.getDeviceGroup());
        List<TBDeviceInfo> tbDeviceInfos = tbWebDeviceDao.selectDoorDeviceInfo(r);
        // 遍历组下所有设备
        for (TBDeviceInfo t : tbDeviceInfos) {
            m++;
            String deviceCode = t.getDeviceCode();
            // 更新心跳时间
            DeviceHeartbeatInfo deviceHeartbeatInfo = new DeviceHeartbeatInfo();
            deviceHeartbeatInfo.setDeviceCode(deviceCode);
            deviceHeartbeatInfo.setLastUserUpdateTime(time);
            int i = tbDeviceDeviceDao.updateHeartbeat(deviceHeartbeatInfo);
            if (i == 1) {
                k++;
            }
        }
        if (m == k) {
            // 更新人员
            tbWebPersonnelDao.updateBlacklistUser(request);
            return BaseResult.success("信息修改成功");
        }
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return BaseResult.fail("信息修改失败");
    }

}
