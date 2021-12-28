package cn.gface.face.api.web.dto;

import lombok.Data;

@Data
public class UserGroupRequestEntity {
    private int page; // 页码
    private int limit; // 页数
    private String sort; // 排序
    private String deviceGroup;
}
