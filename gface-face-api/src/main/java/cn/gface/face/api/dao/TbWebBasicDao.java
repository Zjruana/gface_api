package cn.gface.face.api.dao;

import cn.gface.face.api.data.TBBasicInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbWebBasicDao {
    // 根据类型查询数据
    List<TBBasicInfo> selectBasic(String type);
}
