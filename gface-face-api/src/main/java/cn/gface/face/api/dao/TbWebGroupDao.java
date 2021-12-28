package cn.gface.face.api.dao;

import cn.gface.face.api.data.RequestEntity;
import cn.gface.face.api.data.TBGroupInfo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface TbWebGroupDao {
    List<HashMap<String, Object>> selectGroupList(String userName);

    List<HashMap<String, Object>> selectGroupListInfo(RequestEntity requestEntity);

    TBGroupInfo selectGroupInfoByGNameUName(TBGroupInfo tbGroupInfo);

    Integer insertGroupInfo(TBGroupInfo tbGroupInf);

//    ------------------------ 访客 --------------------------

    List<HashMap<String, Object>> selectGroupVisList(String userName);

    List<HashMap<String, Object>> selectGroupVisListInfo(RequestEntity requestEntity);

    TBGroupInfo selectGroupVisInfoByGNameUName(TBGroupInfo tbGroupInfo);

    Integer insertGroupVisInfo(TBGroupInfo tbGroupInf);
}
