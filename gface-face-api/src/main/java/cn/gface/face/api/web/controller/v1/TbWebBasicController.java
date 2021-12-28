package cn.gface.face.api.web.controller.v1;

import cn.gface.face.api.interceptor.annotation.Authorization;
import cn.gface.face.api.service.TbWebBasicService;
import cn.gface.face.api.web.dto.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 1.请求接收 Type()
 */
@RestController
@RequestMapping(value = "${api.path.v1}/basic")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TbWebBasicController {
    @Autowired
    private TbWebBasicService tbWebBasicService;

    @Authorization
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public BaseResult type(@RequestParam String type) {
        try {
            if (type == null) {
                return BaseResult.fail("不能为空");
            }
            BaseResult baseResult = tbWebBasicService.selectBasicBR(type);
            if (baseResult != null) {
                return baseResult;
            }
            return BaseResult.success("返回结果为NULL");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }
}
