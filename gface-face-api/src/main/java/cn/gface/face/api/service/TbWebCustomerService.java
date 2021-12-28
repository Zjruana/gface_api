package cn.gface.face.api.service;

import cn.gface.face.api.web.dto.BaseResult;
import cn.gface.face.api.web.dto.UserCustomerRequestEntity;

public interface TbWebCustomerService {
    // 获取客户管理信息
    BaseResult selectCustomerInfo(UserCustomerRequestEntity getRequestEntity, String userName, int pid, String role, int id);

    BaseResult updateCustomerInfo(UserCustomerRequestEntity requestEntity, String userName, int pid, String role);

    BaseResult deleteCustomerInfo(UserCustomerRequestEntity requestEntity, String userName, int pid, String role, int id);

    BaseResult insertCustomerInfo(UserCustomerRequestEntity requestEntity, String userName, int pid, String role, int id);
}
