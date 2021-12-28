package cn.gface.face.api.service.impl;

import cn.gface.face.api.dao.TbWebCustomerDao;
import cn.gface.face.api.data.RequestEntity;
import cn.gface.face.api.data.TBUserInfo;
import cn.gface.face.api.service.TbWebCustomerService;
import cn.gface.face.api.web.dto.BaseResult;
import cn.gface.face.api.web.dto.UserCustomerRequestEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TbWebCustomerServiceImpl implements TbWebCustomerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TbWebCustomerServiceImpl.class);

    @Autowired
    private TbWebCustomerDao tbWebCustomerDao;


    @Override
    public BaseResult selectCustomerInfo(UserCustomerRequestEntity getRequestEntity, String userName, int pid, String role, int id) {
        RequestEntity tbRequestInfo = new RequestEntity();
        tbRequestInfo.setUserName(getRequestEntity.getUserName());
        tbRequestInfo.setPUserName(getRequestEntity.getPUserName());
        tbRequestInfo.setPid(pid);
        tbRequestInfo.setId(String.valueOf(id));
        if ("admin".equals(role)) {
            // 已经放入 pid 判断，所以不做操作
            // 管理员创建出来的账号必为 super-user
            tbRequestInfo.setRole(role);
            System.out.println("role = " + role);
        } else if ("super-user".equals(role)) {
            // 管理员创建的账号，查询子账号，子账号创建出来权限必是 user
            tbRequestInfo.setRole("user");
            System.out.println("role = " + role);
        } else {
            // 最低级子账号不允许查询
            return BaseResult.fail(401, "非法权限");
        }
        System.out.println("tbRequestInfo = " + tbRequestInfo);
        // 分页查询数据
        tbRequestInfo.setPage((getRequestEntity.getPage() - 1) * getRequestEntity.getLimit());
        tbRequestInfo.setLimit(getRequestEntity.getLimit());
        System.out.println("tbRequestInfo = " + tbRequestInfo);

        List<TBUserInfo> tbCustomerInfos = tbWebCustomerDao.selectCustomerInfo(tbRequestInfo);

        return BaseResult.success("success", new HashMap<String, Object>() {{
            put("total", tbWebCustomerDao.selectCustomerInfoCount(tbRequestInfo));
            put("items", tbCustomerInfos);
        }});
    }

    @Override
    public BaseResult updateCustomerInfo(UserCustomerRequestEntity getRequestEntity, String userName, int pid, String role) {
        RequestEntity tbRequestInfo = new RequestEntity();
        tbRequestInfo.setName(getRequestEntity.getName());
        tbRequestInfo.setAvatar(getRequestEntity.getAvatar());
        tbRequestInfo.setEmail(getRequestEntity.getEmail());
        if (!"".equals(getRequestEntity.getPassword()) && getRequestEntity.getPassword() != null) {
            tbRequestInfo.setPassword(DigestUtils.md5Hex(getRequestEntity.getPassword()).toUpperCase());
        } else {
            tbRequestInfo.setPassword(null);
        }
        tbRequestInfo.setRole(role);
        tbRequestInfo.setSex(getRequestEntity.getSex());
        tbRequestInfo.setCount(getRequestEntity.getCount());
        tbRequestInfo.setProfile(getRequestEntity.getProfile());
        tbRequestInfo.setUserName(getRequestEntity.getUserName());
        tbRequestInfo.setPhoneNumber(getRequestEntity.getPhoneNumber());
        int i = tbWebCustomerDao.updateCustomerInfo(tbRequestInfo);
        if (i == 1) {
            return BaseResult.success();
        }
        return BaseResult.fail("更新失败");
    }

    @Override
    public BaseResult deleteCustomerInfo(UserCustomerRequestEntity getRequestEntity, String userName, int pid, String role, int id) {
        RequestEntity tbRequestInfo = new RequestEntity();
        List<TBUserInfo> tbUserInfos = null;
        if (pid != -1) {
            // 非超级管理员先查自己账号下的账户
            tbRequestInfo.setId(String.valueOf(id));
//            FIXME 数据库逻辑修改
//            tbRequestInfo.setRole(role);
            tbRequestInfo.setRole("user");
            tbUserInfos = tbWebCustomerDao.selectCustomerInfo(tbRequestInfo);
            boolean can = false;
            for (TBUserInfo userInfo : tbUserInfos) {
                if (userInfo.getUserName().equals(getRequestEntity.getUserName())) {
                    can = true;
                    break;
                }
            }
            if (!can) {
                return BaseResult.fail("账号列表不存在该账户");
            }
        }
        tbRequestInfo.setUserName(getRequestEntity.getUserName());
        try {
            tbWebCustomerDao.deleteCustomerInfo(tbRequestInfo);
            return BaseResult.success();
        } catch (Exception e) {
            return BaseResult.fail("删除失败");
        }
    }

    @Override
    public BaseResult insertCustomerInfo(UserCustomerRequestEntity getRequestEntity, String userName, int pid, String role, int id) {
        RequestEntity tbRequestInfo = new RequestEntity();
        tbRequestInfo.setUserName(getRequestEntity.getUserName());
        tbRequestInfo.setPhoneNumber(getRequestEntity.getPhoneNumber());
        tbRequestInfo.setName(getRequestEntity.getName());
        tbRequestInfo.setAvatar(getRequestEntity.getAvatar());
        tbRequestInfo.setEmail(getRequestEntity.getEmail());
        if (getRequestEntity.getPassword().trim().isEmpty()) {
            return BaseResult.fail("请输入密码");
        }
        tbRequestInfo.setPassword(DigestUtils.md5Hex(getRequestEntity.getPassword()).toUpperCase());
        tbRequestInfo.setSex(getRequestEntity.getSex());
        tbRequestInfo.setProfile(getRequestEntity.getProfile());
//        tbRequestInfo.setCreateTime();
        tbRequestInfo.setPid(id);
        if (pid == -1 && "admin".equals(role)) {
            tbRequestInfo.setCount(getRequestEntity.getCount());
            tbRequestInfo.setRole("super-user");
        } else if ("super-user".equals(role)) {
            RequestEntity entity = new RequestEntity();
            entity.setUserName(userName);
            int onlyCustomerInfo = tbWebCustomerDao.selectOnlyCustomerInfo(entity);
            if (onlyCustomerInfo < 1) {
                return BaseResult.fail("账号创建已达上限");
            }
            tbRequestInfo.setCount(-1);
            tbRequestInfo.setRole("user");
        } else {
            return BaseResult.fail(401, "非法访问");
        }
        int i;
        try {
            i = tbWebCustomerDao.insertCustomerInfo(tbRequestInfo);
        } catch (DuplicateKeyException e) {
            return BaseResult.fail("失败，请检查账号是否已存在！");
        }
        if (i == 1) {
            return BaseResult.success();
        }
        return BaseResult.fail("新增失败");
    }

}
