package cn.gface.face.api.utils;

import cn.gface.face.api.data.TBBatchUploadPersonnelInfo;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);
    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";//定义全局的常量值

    public static List<TBBatchUploadPersonnelInfo> getPersonList(Path fileUnZipDir, String userName) {
        List<TBBatchUploadPersonnelInfo> list = new ArrayList<>();
        try {
            String personXls = fileUnZipDir.toString() + File.separator + "person.xls";
            File personXlsFile = new File(personXls);
            // 第一层目录没有找到 xls 文件时，再向下查找一层
            if (!personXlsFile.exists()) {
                File[] files = new File(fileUnZipDir.toString()).listFiles();
                if (files != null && files.length > 0) {
                    for (File f : files) {
                        if (f.isDirectory()) {
                            personXls = f.getPath() + File.separator + "person.xls";
                            fileUnZipDir = f.toPath();
                            if (new File(personXls).exists()) break;
                        }
                    }
                }
            }
            personXlsFile = new File(personXls);
            if (personXlsFile.exists()) {
                Workbook workbook = null;
                if (personXls.endsWith(XLS)) {
                    //2003
                    try {
                        workbook = new HSSFWorkbook(new FileInputStream(personXlsFile));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (personXls.endsWith(XLSX)) {
                    try {
                        //2007
                        workbook = new XSSFWorkbook(new FileInputStream(personXlsFile));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    throw new Exception("文件不是Excel文件");
                }
                Sheet sheet = workbook.getSheet("Sheet1");
                int rows = sheet.getLastRowNum();//指定行数。一共多少+
                if (rows == 0) {
                    throw new Exception("请填写行数");
                }

                for (int i = 1; i < rows + 1; i++) {
                    //读取左上端单元格
                    Row row = sheet.getRow(i);
                    //行不为空
                    if (row != null) {
                        //读取cell
                        TBBatchUploadPersonnelInfo entity = new TBBatchUploadPersonnelInfo();
                        //姓名
                        String name = getCellValue(row.getCell(0));
                        entity.setName(name);
                        //手机号
                        String mobilePhone = getCellValue(row.getCell(1));
                        entity.setMobilePhone(mobilePhone);
                        //工号
                        String jobNumber = getCellValue(row.getCell(2));
                        entity.setJobNumber(jobNumber);
                        //开门密码
                        String openDoorPassword = getCellValue(row.getCell(3));
                        entity.setOpenDoorPassword(openDoorPassword);
                        //组织
                        String deviceGroup = getCellValue(row.getCell(4));
                        if (deviceGroup.length() > 0 && deviceGroup.contains("，")) {
                            deviceGroup = deviceGroup.replace("，", ",");
                        }
                        entity.setDeviceGroup(deviceGroup);
                        //身份证号
                        String idCardNo = getCellValue(row.getCell(5));
                        entity.setIdCardNo(idCardNo);
                        //门禁卡号
                        String icCard = getCellValue(row.getCell(6));
                        entity.setIcCard(icCard);
                        //过期时间
                        String endTime = getCellValue(row.getCell(7));
                        entity.setEndTime("0");
                        if (endTime.length() == 0 || endTime.equals("永久")) {
                        } else {
                            if (endTime.length() == 8) {
                                try {
                                    long time = new SimpleDateFormat("yyyyMMdd").parse(endTime).getTime();
                                    entity.setEndTime(time + "");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        //备注
                        String remark = getCellValue(row.getCell(8));
                        entity.setRemark(remark);
                        //图片名
                        String imageUrl = getCellValue(row.getCell(9));

                        //根据图片名，找到图片文件
                        String imagePath = fileUnZipDir.toString() + File.separator + "faceImage" + File.separator + imageUrl;
                        File imageFile = new File(imagePath);
                        entity.setImageUrl("");
                        if (imageUrl.length() == 0 || !imageFile.exists()) {

                        } else if (!imageUrl.endsWith(".jpg") && !imageUrl.endsWith(".JPG")
                                && !imageUrl.endsWith(".png") && !imageUrl.endsWith(".PNG")) {
//                            return BaseResult.fail("上传头像图片只能是 JPG 或 PNG 格式!");
//                            if (file.getSize() / 1024 / 1024 > 5) {
//                                return BaseResult.fail("上传头像图片大小不能超过 5MB!");
//                            }
                        } else {
                            //保存图片
                            try {
                                FileInputStream fileInputStreamReader = new FileInputStream(imageFile);
                                byte[] bytes = new byte[(int) imageFile.length()];
                                int read = fileInputStreamReader.read(bytes);
                                String base64str = new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);
                                String imgPath = Base64Util.getImgPath(StaticVariable.PATH_WEB_FACE_IMG, base64str, userName);
                                entity.setImageUrl(StaticVariable.getServerIp() + StaticVariable.TOMCAT_STATIC_PATH + imgPath);
                            } catch (Exception e) {
                                LOGGER.error("保存图片异常: {}", e.getMessage());
                            }
                        }


                        //创建时间
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        String time = sdf.format(new Date());
//                        tblFixChange.setCreateTime(time);
//                        tblFixChange.setModifyTime(time);
                        if (entity.getName() == null || "".equals(entity.getName())) {
                            continue;
                        }
                        list.add(entity);  //把实数据放入集合里
                    }
                }
//                try {
//                    groupConfigDao.addBatchMembers(list);  //批量添加 (执行sql语句批量增加)
//                    rsultMap.put("status", 1);
//                    rsultMap.put("data", "导入数据成功");
//                } catch (Exception e) {
//                    rsultMap.put("status", -1);
//                    rsultMap.put("data", "导入数据异常");
//                }
            }
            throw new Exception("请确认目录结构: " + fileUnZipDir);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;

    }

    //获取Cell内容
    private static String getCellValue(Cell cell) {
        String value = "";
        if (cell != null) {
            //以下是判断数据的类型
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC://数字
                    value = cell.getNumericCellValue() + "";
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        if (date != null) {
                            value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                        } else {
                            value = "";
                        }
                    } else {
                        value = new DecimalFormat("0").format(cell.getNumericCellValue());
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING: //字符串
                    value = cell.getStringCellValue();
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN: //boolean
                    value = cell.getBooleanCellValue() + "";
                    break;
                case HSSFCell.CELL_TYPE_FORMULA: //公式
                    value = cell.getCellFormula() + "";
                    break;
                case HSSFCell.CELL_TYPE_BLANK: //空值
                    value = "";
                    break;
                case HSSFCell.CELL_TYPE_ERROR: //故障
                    value = "非法字符";
                    break;
                default:
                    value = "未知类型";
                    break;
            }
        }
        return value.trim();
    }


    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children == null) {
                return true;
            }
            //递归删除目录中的子目录下
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
