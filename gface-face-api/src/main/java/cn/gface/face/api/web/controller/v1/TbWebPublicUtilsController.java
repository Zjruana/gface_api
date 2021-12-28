package cn.gface.face.api.web.controller.v1;

import cn.gface.face.api.data.TBBatchUploadPersonnelInfo;
import cn.gface.face.api.interceptor.annotation.Authorization;
import cn.gface.face.api.utils.Base64Util;
import cn.gface.face.api.utils.FileUtil;
import cn.gface.face.api.utils.StaticVariable;
import cn.gface.face.api.utils.ZipUtil;
import cn.gface.face.api.web.dto.BaseResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping(value = "${api.path.v1}/publicWeb")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TbWebPublicUtilsController {
    /**
     * 上传图片
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "uploadImg", method = RequestMethod.POST)
    public BaseResult uploadImg(MultipartFile file, HttpServletRequest req) {
        try {
            if (file.isEmpty() && !"image/jpeg".equals(file.getContentType()) && !"image/png".equals(file.getContentType())) {
                return BaseResult.fail("上传头像图片只能是 JPG 或 PNG 格式!");
            }
            if (file.getSize() / 1024 / 1024 > 5) {
                return BaseResult.fail("上传头像图片大小不能超过 5MB!");
            }
            //保存图片
            byte[] fileByte;
            try {
                fileByte = file.getBytes();
            } catch (IOException e) {
                return BaseResult.fail("图像文件转码失败！");
            }
            String voiceBase64 = Base64.getEncoder().encodeToString(fileByte);
            String imgPath = StaticVariable.getServerIp() + StaticVariable.TOMCAT_STATIC_PATH + Base64Util.getImgPath(StaticVariable.PATH_WEB_FACE_IMG, voiceBase64, String.valueOf(req.getAttribute("id")));
            return BaseResult.success("上传成功", imgPath);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }

    /**
     * 解析指定导入文件为 json
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "batchUploadPersonnel", method = RequestMethod.POST)
    public BaseResult batchUploadPersonnel(MultipartFile file, HttpServletRequest req) {
        try {
            if (file.isEmpty() || file.getSize() / 1024 / 1024 > 100 || !("application/x-zip-compressed".equals(file.getContentType()) || "application/zip".equals(file.getContentType()))) {
                return BaseResult.fail("1.文件不为空 2.文件大小 100MB 之内 3.仅支持 zip 文件");
            }
            String userName = (String) req.getAttribute("username");
            // 创建临时文件存 zip
            Path fileZip = Files.createTempFile(file.getOriginalFilename().replace(".zip", ""), ".zip");
            // 写入前端上传的 zip 到临时文件
            Path toFileZip = Files.write(fileZip, file.getBytes());
            // 解压 1.创建临时文件夹
            Path fileUnZipDir = Files.createTempDirectory(file.getOriginalFilename().replace(".zip", ""));
            // 2.将解压的文件放入文件夹(没报错就表示正常)
            ZipUtil.unZip(toFileZip.toFile(), fileUnZipDir.toString());
            List<TBBatchUploadPersonnelInfo> personList = FileUtil.getPersonList(fileUnZipDir, userName);
            // 操作完成->1.结束删除临时文件
            Files.deleteIfExists(fileZip);
            // 操作完成->2.结束删除临时文件夹
            FileUtil.deleteDir(fileUnZipDir.toFile());
            return BaseResult.success("解析成功", personList);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.fail("服务器异常");
        }
    }
}
