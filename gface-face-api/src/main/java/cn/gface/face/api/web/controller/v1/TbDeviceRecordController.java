package cn.gface.face.api.web.controller.v1;

import cn.gface.face.api.service.TbDeviceRecordService;
import cn.gface.face.api.web.dto.BaseResultDevice;
import cn.gface.face.api.web.dto.DeviceRecordRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 1.上传通行记录 uploadRecord()
 */
@RestController
@RequestMapping(value = "${api.path.v1}/record")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TbDeviceRecordController {
    @Autowired
    private TbDeviceRecordService tbDeviceRecordService;

    @RequestMapping(value = "uploadRecord", method = RequestMethod.POST)
    public BaseResultDevice uploadRecord(@RequestBody DeviceRecordRequestEntity recordRequestEntity) {
        try {
            if (recordRequestEntity == null) {
                return BaseResultDevice.fail("不能为空");
            }
            BaseResultDevice deviceResult = tbDeviceRecordService.insertRecordBR(recordRequestEntity);
            if (deviceResult != null) {
                return deviceResult;
            }
            return BaseResultDevice.fail();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResultDevice.fail();
        }
    }
}
