package cn.gface.face.api.service.impl;

import cn.gface.face.api.dao.TbWebGroupDao;
import cn.gface.face.api.service.TbWebGroupService;
import cn.gface.face.api.web.dto.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TbWebGroupServiceImpl implements TbWebGroupService {
    @Autowired
    private TbWebGroupDao tbWebGroupDao;


    @Override
    public BaseResult selectGroupListBR(String userName) {
        List<HashMap<String, Object>> groupList = tbWebGroupDao.selectGroupList(userName);
//        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
//        for (TBDeviceInfo t : groupList) {
//            if (!"".equals(t.getDeviceGroup())) {
//                list.add(new HashMap<String, Object>() {{
//                    put("deviceGroup", t.getDeviceGroup());
//                    put("deviceCode", t.getDeviceCode());
//                }});
//            }
//        }
        return BaseResult.success("成功", groupList);
    }

    @Override
    public BaseResult selectGroupVisListBR(String userName) {
        List<HashMap<String, Object>> groupList = tbWebGroupDao.selectGroupVisList(userName);
        return BaseResult.success("成功", groupList);
    }
}
