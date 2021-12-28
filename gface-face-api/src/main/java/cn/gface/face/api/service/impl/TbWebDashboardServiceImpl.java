package cn.gface.face.api.service.impl;

import cn.gface.face.api.dao.TbWebDashboardDao;
import cn.gface.face.api.data.RequestEntity;
import cn.gface.face.api.data.TBDashboardEarlyWarnInfo;
import cn.gface.face.api.data.TBDashboardInfo;
import cn.gface.face.api.data.TBRecordInfo;
import cn.gface.face.api.service.TbWebDashboardService;
import cn.gface.face.api.web.dto.BaseResult;
import cn.gface.face.api.web.dto.UserRecordRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class TbWebDashboardServiceImpl implements TbWebDashboardService {
    @Autowired
    private TbWebDashboardDao tbWebDashboardDao;

    @Override
    public BaseResult selectDashboardTableListBR(UserRecordRequestEntity requestEntity, String userName) {
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
        List<TBRecordInfo> tbRecordInfos = tbWebDashboardDao.selectDashboardTableListData(entity);

        return BaseResult.success("成功", new HashMap<String, Object>() {{
            put("total", tbWebDashboardDao.selectDashboardTableListDataCount(entity));
            put("items", tbRecordInfos);
        }});

    }

    @Override
    public BaseResult selectDashboardBR(String userName) {
        TBDashboardInfo recordInfo = tbWebDashboardDao.selectDashboardNum(userName);
        List<LinkedHashMap<String, String>> dashboardLineData = tbWebDashboardDao.selectDashboardLineData(userName);
        ArrayList<String> dataTime = new ArrayList<>();
        ArrayList<String> doorCount = new ArrayList<>();
        ArrayList<String> visCount = new ArrayList<>();
        ArrayList<String> blackCount = new ArrayList<>();
        ArrayList<String> strangerCount = new ArrayList<>();
        for (LinkedHashMap<String, String> lineData : dashboardLineData) {
            dataTime.add(String.valueOf(lineData.get("data_time")));
            doorCount.add(String.valueOf(lineData.get("door_count")));
            visCount.add(String.valueOf(lineData.get("vis_count")));
            blackCount.add(String.valueOf(lineData.get("black_count")));
            strangerCount.add(String.valueOf(lineData.get("stranger_count")));
        }
        return BaseResult.success("成功", new LinkedHashMap<String, Object>() {{
            put("recordInfo", recordInfo);
            put("dataTime", dataTime);
            put("doorCount", doorCount);
            put("visCount", visCount);
            put("blackCount", blackCount);
            put("strangerCount", strangerCount);
        }});
    }

    @Override
    public BaseResult selectDashboardEarlyWarnBR(String userName) {
        List<TBDashboardEarlyWarnInfo> earlyWarnInfos = tbWebDashboardDao.selectDashboardEarlyWarnInfo(userName);
        return BaseResult.success("success", earlyWarnInfos);
    }

    @Override
    public BaseResult readDashboardEarlyWarnBR(String[] ids, String userName) {
        int count = tbWebDashboardDao.updateDashboardEarlyWarnInfoByIds(ids, userName);
        return BaseResult.success("消息数: " + ids.length + "\t已读成功数:" + count);
    }

    @Override
    public BaseResult readDelDashboardEarlyWarnBR(String[] ids, String userName) {
        int count = tbWebDashboardDao.deleteDashboardEarlyWarnInfoByIds(ids, userName);
        return BaseResult.success("消息数: " + ids.length + "\t删除成功数:" + count);
    }

    @Override
    public BaseResult popOkDashboardEarlyWarnBR(String[] ids, String userName) {
        int count = tbWebDashboardDao.updateNewMsgDashboardEarlyWarnInfoByIds(ids, userName);
        return BaseResult.success("预警信息数: " + count + " ,弹出成功, 请查看! ");
    }
}
