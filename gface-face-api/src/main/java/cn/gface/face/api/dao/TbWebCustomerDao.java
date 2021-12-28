package cn.gface.face.api.dao;

import cn.gface.face.api.data.RequestEntity;
import cn.gface.face.api.data.TBUserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbWebCustomerDao {
    // 获取客户管理信息
    List<TBUserInfo> selectCustomerInfo(RequestEntity tbRequestInfo);

    int selectCustomerInfoCount(RequestEntity tbRequestInfo);

    int updateCustomerInfo(RequestEntity tbRequestInfo);

    int deleteCustomerInfo(RequestEntity tbRequestInfo);

    int insertCustomerInfo(RequestEntity tbRequestInfo);

    int selectOnlyCustomerInfo(RequestEntity tbRequestInfo);
}
