package cn.gface.face.api.service;

import cn.gface.face.api.web.dto.BaseResult;

public interface TbWebGroupService {
    BaseResult selectGroupListBR(String userName);

    BaseResult selectGroupVisListBR(String userName);
}
