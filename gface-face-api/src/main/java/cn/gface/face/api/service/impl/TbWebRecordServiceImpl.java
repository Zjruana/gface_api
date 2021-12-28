package cn.gface.face.api.service.impl;

import cn.gface.face.api.dao.TbWebRecordDao;
import cn.gface.face.api.data.RequestEntity;
import cn.gface.face.api.data.TBRecordInfo;
import cn.gface.face.api.service.TbWebRecordService;
import cn.gface.face.api.web.dto.BaseResult;
import cn.gface.face.api.web.dto.UserRecordRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TbWebRecordServiceImpl implements TbWebRecordService {
    @Autowired
    private TbWebRecordDao tbWebRecordDao;

    @Override
    public BaseResult selectDoorRecordInfoBR(UserRecordRequestEntity requestEntity, String userName) {
        RequestEntity entity = new RequestEntity();
        entity.setBeginTime(requestEntity.getBeginTime());
        entity.setEndTime(requestEntity.getEndTime());
        entity.setName(requestEntity.getName());
        entity.setOpenType(requestEntity.getOpenType());
        entity.setSort(requestEntity.getSort());
        entity.setTemp(requestEntity.getTemp());
        entity.setIcCard(requestEntity.getIcCard());
        entity.setOcclusion(requestEntity.getOcclusion());
        entity.setTitle(requestEntity.getTitle());
        entity.setUserName(userName);
        entity.setDeviceName(requestEntity.getDeviceName());
        entity.setHsjl(requestEntity.getHsjl());
        entity.setXcjl(requestEntity.getXcjl());
        entity.setYmjl(requestEntity.getYmjl());
        entity.setGuokangStatus(requestEntity.getGuokangStatus());
        // 分页查询数据
        entity.setPage((requestEntity.getPage() - 1) * requestEntity.getLimit());
        entity.setLimit(requestEntity.getLimit());
        List<TBRecordInfo> tbRecordInfos = tbWebRecordDao.selectDoorRecordInfo(entity);

        return BaseResult.success("成功", new HashMap<String, Object>() {{
            put("total", tbWebRecordDao.selectDoorRecordInfoCount(entity));
            put("items", tbRecordInfos);
        }});
    }

    @Override
    public BaseResult selectVisRecordInfoBR(UserRecordRequestEntity requestEntity, String userName) {
        RequestEntity entity = new RequestEntity();
        entity.setBeginTime(requestEntity.getBeginTime());
        entity.setEndTime(requestEntity.getEndTime());
        entity.setName(requestEntity.getName());
        entity.setOpenType(requestEntity.getOpenType());
        entity.setSort(requestEntity.getSort());
        entity.setTemp(requestEntity.getTemp());
        entity.setIcCard(requestEntity.getIcCard());
        entity.setOcclusion(requestEntity.getOcclusion());
        entity.setTitle(requestEntity.getTitle());
        entity.setUserName(userName);
        entity.setDeviceName(requestEntity.getDeviceName());
        entity.setHsjl(requestEntity.getHsjl());
        entity.setXcjl(requestEntity.getXcjl());
        entity.setYmjl(requestEntity.getYmjl());
        entity.setGuokangStatus(requestEntity.getGuokangStatus());
        // 分页查询数据
        entity.setPage((requestEntity.getPage() - 1) * requestEntity.getLimit());
        entity.setLimit(requestEntity.getLimit());
        List<TBRecordInfo> tbRecordInfos = tbWebRecordDao.selectVisRecordInfo(entity);

        return BaseResult.success("成功", new HashMap<String, Object>() {{
            put("total", tbWebRecordDao.selectVisRecordInfoCount(entity));
            put("items", tbRecordInfos);
        }});
    }

    @Override
    public BaseResult selectBlacklistRecordInfoBR(UserRecordRequestEntity requestEntity, String userName) {
        RequestEntity entity = new RequestEntity();
        entity.setBeginTime(requestEntity.getBeginTime());
        entity.setEndTime(requestEntity.getEndTime());
        entity.setName(requestEntity.getName());
        entity.setOpenType(requestEntity.getOpenType());
        entity.setSort(requestEntity.getSort());
        entity.setTemp(requestEntity.getTemp());
        entity.setIcCard(requestEntity.getIcCard());
        entity.setOcclusion(requestEntity.getOcclusion());
        entity.setTitle(requestEntity.getTitle());
        entity.setUserName(userName);
        entity.setDeviceName(requestEntity.getDeviceName());
        entity.setHsjl(requestEntity.getHsjl());
        entity.setXcjl(requestEntity.getXcjl());
        entity.setYmjl(requestEntity.getYmjl());
        entity.setGuokangStatus(requestEntity.getGuokangStatus());
        // 分页查询数据
        entity.setPage((requestEntity.getPage() - 1) * requestEntity.getLimit());
        entity.setLimit(requestEntity.getLimit());
        List<TBRecordInfo> tbRecordInfos = tbWebRecordDao.selectBlacklistRecordInfo(entity);

        return BaseResult.success("成功", new HashMap<String, Object>() {{
            put("total", tbWebRecordDao.selectBlacklistRecordInfoCount(entity));
            put("items", tbRecordInfos);
        }});
    }

    @Override
    public BaseResult selectStrangerRecordInfoBR(UserRecordRequestEntity requestEntity, String userName) {
        RequestEntity entity = new RequestEntity();
        entity.setBeginTime(requestEntity.getBeginTime());
        entity.setEndTime(requestEntity.getEndTime());
        entity.setName(requestEntity.getName());
        entity.setOpenType(requestEntity.getOpenType());
        entity.setSort(requestEntity.getSort());
        entity.setTemp(requestEntity.getTemp());
        entity.setIcCard(requestEntity.getIcCard());
        entity.setOcclusion(requestEntity.getOcclusion());
        entity.setTitle(requestEntity.getTitle());
        entity.setUserName(userName);
        entity.setDeviceName(requestEntity.getDeviceName());
        entity.setHsjl(requestEntity.getHsjl());
        entity.setXcjl(requestEntity.getXcjl());
        entity.setYmjl(requestEntity.getYmjl());
        entity.setGuokangStatus(requestEntity.getGuokangStatus());
        // 分页查询数据
        entity.setPage((requestEntity.getPage() - 1) * requestEntity.getLimit());
        entity.setLimit(requestEntity.getLimit());
        List<TBRecordInfo> tbRecordInfos = tbWebRecordDao.selectStrangerRecordInfo(entity);

        return BaseResult.success("成功", new HashMap<String, Object>() {{
            put("total", tbWebRecordDao.selectStrangerRecordInfoCount(entity));
            put("items", tbRecordInfos);
        }});

    }
}
