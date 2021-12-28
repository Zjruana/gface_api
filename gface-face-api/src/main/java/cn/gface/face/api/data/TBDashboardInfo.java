package cn.gface.face.api.data;

import lombok.Data;

@Data
public class TBDashboardInfo {
    private int allRecord;
    private int allDevice;
    private int todayVisRecord;
    private int todayDoorRecord;
    private int todayStranger;
    private int todayBlacklist;
    private int todayGuokangRed;
    private int todayGuokangYellow;
    private int todayGuokangGreen;
}
