package cn.gface.face.api.service;


import cn.gface.face.api.web.dto.BaseResult;
import cn.gface.face.api.web.dto.UserRecordRequestEntity;

public interface TbWebDashboardService {

    BaseResult selectDashboardBR(String userName);

    BaseResult selectDashboardTableListBR(UserRecordRequestEntity requestEntity, String userName);

    BaseResult selectDashboardEarlyWarnBR(String userName);

    BaseResult readDashboardEarlyWarnBR(String[] ids, String userName);

    BaseResult readDelDashboardEarlyWarnBR(String[] ids, String userName);

    BaseResult popOkDashboardEarlyWarnBR(String[] ids, String userName);
}
