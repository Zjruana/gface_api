package cn.gface.face.api.service.impl;

import cn.gface.face.api.dao.TbWebDeviceDao;
import cn.gface.face.api.dao.TbWebGroupDao;
import cn.gface.face.api.data.RequestEntity;
import cn.gface.face.api.data.TBDeviceInfo;
import cn.gface.face.api.data.TBGroupInfo;
import cn.gface.face.api.service.TbWebDeviceService;
import cn.gface.face.api.web.dto.BaseResult;
import cn.gface.face.api.web.dto.UserDeviceRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class TbWebDeviceServiceImpl implements TbWebDeviceService {
    @Autowired
    private TbWebDeviceDao tbWebDeviceDao;
    @Autowired
    private TbWebGroupDao tbWebGroupDao;

    @Override
    public BaseResult selectDoorDeviceInfoBR(UserDeviceRequestEntity requestEntity, String userName) {
        RequestEntity entity = new RequestEntity();
        entity.setDeviceCode(requestEntity.getDeviceCode());
        entity.setDeviceName(requestEntity.getDeviceName());
        entity.setDeviceLocation(requestEntity.getLocation());
        entity.setOpenType(requestEntity.getOpenType());
        entity.setStatus(requestEntity.getStatus());
        entity.setHeartbeatTime(new Date().getTime() - (60 * 1000));
        entity.setVendors(requestEntity.getVendors());
        entity.setBeginTime(requestEntity.getBeginTime());
        entity.setEndTime(requestEntity.getEndTime());
        entity.setUserName(userName);
        // 分页查询数据
        entity.setPage((requestEntity.getPage() - 1) * requestEntity.getLimit());
        entity.setLimit(requestEntity.getLimit());
        List<TBDeviceInfo> tbDeviceInfos = tbWebDeviceDao.selectDoorDeviceInfo(entity);
        for (TBDeviceInfo t : tbDeviceInfos) {
            if (t.getHeartbeatTime() == null || t.getCreateTime() == null) {
                t.setCreateTime("0");
                continue;
            }
            if (new Date().getTime() - Long.parseLong(t.getCreateTime()) >= (25 * 1000) && Long.parseLong(t.getHeartbeatTime()) == 0L) {
                // 超过25S未连接，只针对新添加设备
                t.setStatus(2);
            } else if (t.getStatus() == 0) {
                t.setStatus(0);
            } else if (new Date().getTime() - Long.parseLong(t.getHeartbeatTime()) >= (60 * 1000)) {
                // 超过60S未连接
                t.setStatus(2);
            } else {
                t.setStatus(1);
            }
        }
        return BaseResult.success("成功", new HashMap<String, Object>() {{
            put("total", tbWebDeviceDao.selectDoorDeviceInfoCount(entity));
            put("items", tbDeviceInfos);
        }});
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResult insertDoorDeviceInfoBR(UserDeviceRequestEntity requestEntity, String userName) {
        try {
            RequestEntity entity = new RequestEntity();
            entity.setOpenType(requestEntity.getOpenType());
            entity.setLocation(requestEntity.getLocation());
            entity.setDeviceName(requestEntity.getDeviceName());
            entity.setRemark(requestEntity.getRemark());
            entity.setVendors(requestEntity.getVendors());
            entity.setCreateTime(new Date().getTime());
            entity.setUserName(userName);
            entity.setDeviceCode(requestEntity.getDeviceCode());
            entity.setDeviceIP(requestEntity.getDeviceIP());

            // 检查设备组--如果有这个设备组--用id <--else--> 没有设备组--插入--返回id--用id
            TBGroupInfo groupInfo = new TBGroupInfo();
            groupInfo.setGName(requestEntity.getDeviceGroup());
            groupInfo.setUserName(userName);
            TBGroupInfo tbGroupInfo = tbWebGroupDao.selectGroupInfoByGNameUName(groupInfo);

            if (tbGroupInfo == null) {
                if (tbWebGroupDao.insertGroupInfo(groupInfo) != 1) {
                    return BaseResult.fail("新建组织失败");
                }
                System.out.println("integer = " + groupInfo.getGId());
                entity.setDeviceGroup(String.valueOf(groupInfo.getGId()));
            } else {
                entity.setDeviceGroup(String.valueOf(tbGroupInfo.getGId()));
            }
            tbWebDeviceDao.updateId(); // 自增id
            int i = tbWebDeviceDao.insertDoorDeviceInfo(entity); // 插入到设备
            tbWebDeviceDao.updateHeartbeatId(); // 自增id
            int j = tbWebDeviceDao.insertHeartbeatInfo(entity); // 插入到心跳状态

            if (i == 1 && j == 1) {
                return BaseResult.success("设备添加成功");
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return BaseResult.fail("设备添加失败，请检查设备ID号是否存在！");
        }
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return BaseResult.fail("设备添加失败，请检查设备ID号是否存在！");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResult deleteDoorDeviceInfoBR(String deviceCode, String userName) {
        int i = tbWebDeviceDao.deleteDoorDeviceInfo(deviceCode, userName);
        int j = tbWebDeviceDao.deleteHeartbeatDeviceInfo(deviceCode);
        if (i == 1 && j == 1) {
            return BaseResult.success("删除设备成功");
        }
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return BaseResult.fail("删除设备失败");
    }

    @Override
    public BaseResult updateDoorDeviceInfoBR(RequestEntity requestEntity, String userName) {
        RequestEntity r = new RequestEntity();
        r.setDeviceCode(requestEntity.getDeviceCode());
        r.setVendors(requestEntity.getVendors());
        r.setRemark(requestEntity.getRemark());
        r.setLocation(requestEntity.getLocation());
        r.setDeviceName(requestEntity.getDeviceName());
        r.setOpenType(requestEntity.getOpenType());
        r.setUserName(userName);
        r.setDeviceIP(requestEntity.getDeviceIP());

        // 检查设备组--如果有这个设备组--用id <--else--> 没有设备组--插入--返回id--用id
        TBGroupInfo groupInfo = new TBGroupInfo();
        groupInfo.setGName(requestEntity.getDeviceGroup());
        groupInfo.setUserName(userName);
        TBGroupInfo tbGroupInfo = tbWebGroupDao.selectGroupInfoByGNameUName(groupInfo);

        if (tbGroupInfo == null) {
            if (tbWebGroupDao.insertGroupInfo(groupInfo) != 1) {
                return BaseResult.fail("新建组织失败");
            }
            r.setDeviceGroup(String.valueOf(groupInfo.getGId()));
        } else {
            r.setDeviceGroup(String.valueOf(tbGroupInfo.getGId()));
        }
        int i = tbWebDeviceDao.updateDoorDeviceInfo(r);
        if (i == 1) {
            return BaseResult.success("修改成功");
        }
        return BaseResult.fail("修改失败");
    }

    //============================================= 访客 =====================================
    @Override
    public BaseResult selectVisDeviceInfoBR(UserDeviceRequestEntity requestEntity, String userName) {
        RequestEntity entity = new RequestEntity();
        entity.setDeviceCode(requestEntity.getDeviceCode());
        entity.setDeviceName(requestEntity.getDeviceName());
        entity.setDeviceLocation(requestEntity.getLocation());
        entity.setOpenType(requestEntity.getOpenType());
        entity.setStatus(requestEntity.getStatus());
        entity.setHeartbeatTime(new Date().getTime() - (60 * 1000));
        entity.setVendors(requestEntity.getVendors());
        entity.setBeginTime(requestEntity.getBeginTime());
        entity.setEndTime(requestEntity.getEndTime());
        entity.setUserName(userName);
        // 分页查询数据
        entity.setPage((requestEntity.getPage() - 1) * requestEntity.getLimit());
        entity.setLimit(requestEntity.getLimit());
        List<TBDeviceInfo> tbDeviceInfos = tbWebDeviceDao.selectVisDeviceInfo(entity);
        for (TBDeviceInfo t : tbDeviceInfos) {
            if (t.getHeartbeatTime() == null || t.getCreateTime() == null) {
                t.setCreateTime("0");
                continue;
            }
            if (new Date().getTime() - Long.parseLong(t.getCreateTime()) >= (25 * 1000) && Long.parseLong(t.getHeartbeatTime()) == 0L) {
                // 超过25S未连接，只针对新添加设备
                t.setStatus(2);
            } else if (t.getStatus() == 0) {
                t.setStatus(0);
            } else if (new Date().getTime() - Long.parseLong(t.getHeartbeatTime()) >= (60 * 1000)) {
                // 超过60S未连接
                t.setStatus(2);
            } else {
                t.setStatus(1);
            }
        }
        return BaseResult.success("成功", new HashMap<String, Object>() {{
            put("total", tbWebDeviceDao.selectVisDeviceInfoCount(entity));
            put("items", tbDeviceInfos);
        }});
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResult insertVisDeviceInfoBR(UserDeviceRequestEntity requestEntity, String userName) {

        try {
            RequestEntity entity = new RequestEntity();
            entity.setOpenType(requestEntity.getOpenType());
            entity.setLocation(requestEntity.getLocation());
            entity.setDeviceName(requestEntity.getDeviceName());
            entity.setRemark(requestEntity.getRemark());
            entity.setVendors(requestEntity.getVendors());
            entity.setCreateTime(new Date().getTime());
            entity.setUserName(userName);
            entity.setDeviceCode(requestEntity.getDeviceCode());
            entity.setDeviceIP(requestEntity.getDeviceIP());

            // 检查设备组--如果有这个设备组--用id <--else--> 没有设备组--插入--返回id--用id
            TBGroupInfo groupInfo = new TBGroupInfo();
            groupInfo.setGName(requestEntity.getDeviceGroup());
            groupInfo.setUserName(userName);
            TBGroupInfo tbGroupInfo = tbWebGroupDao.selectGroupVisInfoByGNameUName(groupInfo);

            if (tbGroupInfo == null) {
                if (tbWebGroupDao.insertGroupVisInfo(groupInfo) != 1) {
                    return BaseResult.fail("新建组织失败");
                }
                entity.setDeviceGroup(String.valueOf(groupInfo.getGId()));
            } else {
                entity.setDeviceGroup(String.valueOf(tbGroupInfo.getGId()));
            }

            tbWebDeviceDao.updateVisId(); // 自增id
            int i = tbWebDeviceDao.insertVisDeviceInfo(entity); // 插入到设备
            tbWebDeviceDao.updateHeartbeatId(); // 自增id
            int j = tbWebDeviceDao.insertHeartbeatInfo(entity); // 插入到心跳状态

            if (i == 1 && j == 1) {
                return BaseResult.success("设备添加成功");
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return BaseResult.fail("设备添加失败，请检查设备ID号是否存在！");
        }
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return BaseResult.fail("设备添加失败，请检查设备ID号是否存在！");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResult deleteVisDeviceInfoBR(String deviceCode, String userName) {
        int i = tbWebDeviceDao.deleteVisDeviceInfo(deviceCode, userName);
        int j = tbWebDeviceDao.deleteHeartbeatDeviceInfo(deviceCode);
        if (i == 1 && j == 1) {
            return BaseResult.success("删除设备成功");
        }
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return BaseResult.fail("删除设备失败");
    }

    @Override
    public BaseResult updateVisDeviceInfoBR(RequestEntity requestEntity, String userName) {
        RequestEntity r = new RequestEntity();
        r.setDeviceCode(requestEntity.getDeviceCode());
        r.setVendors(requestEntity.getVendors());
        r.setRemark(requestEntity.getRemark());
        r.setLocation(requestEntity.getLocation());
        r.setDeviceName(requestEntity.getDeviceName());
        r.setOpenType(requestEntity.getOpenType());
        r.setUserName(userName);
        r.setDeviceIP(requestEntity.getDeviceIP());


        // 检查设备组--如果有这个设备组--用id <--else--> 没有设备组--插入--返回id--用id
        TBGroupInfo groupInfo = new TBGroupInfo();
        groupInfo.setGName(requestEntity.getDeviceGroup());
        groupInfo.setUserName(userName);
        TBGroupInfo tbGroupInfo = tbWebGroupDao.selectGroupVisInfoByGNameUName(groupInfo);

        if (tbGroupInfo == null) {
            if (tbWebGroupDao.insertGroupVisInfo(groupInfo) != 1) {
                return BaseResult.fail("新建组织失败");
            }
            r.setDeviceGroup(String.valueOf(groupInfo.getGId()));
        } else {
            r.setDeviceGroup(String.valueOf(tbGroupInfo.getGId()));
        }
        int i = tbWebDeviceDao.updateVisDeviceInfo(r);
        if (i == 1) {
            return BaseResult.success("修改成功");
        }
        return BaseResult.fail("修改失败");
    }
}
