package cn.gface.face.api.service.impl;

import cn.gface.face.api.dao.TbWebBasicDao;
import cn.gface.face.api.data.TBBasicInfo;
import cn.gface.face.api.service.TbWebBasicService;
import cn.gface.face.api.web.dto.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TbWebBasicServiceImpl implements TbWebBasicService {
    @Autowired
    private TbWebBasicDao tbWebBasicDao;

    @Override
    public BaseResult selectBasicBR(String type) {
        List<TBBasicInfo> tbBasicInfos = tbWebBasicDao.selectBasic(type);
        return BaseResult.success("成功", tbBasicInfos);
    }
}
