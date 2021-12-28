### 一、exe 版本

1. src\main\resources\gface.properties
    + server.url=可任意，自动识别环境
    + save.file.path=推荐使用  `/usr/local` 为linux和win共存版
    + server.api.url.path=打包为文件名 ROOT 时 `/api/v1` 否则是 `/文件名/api/v1`
2. src\main\resources\gface.properties
    + 数据库配置

### 二、部署 Linux 上
1. src\main\resources\gface.properties
    + server.url=服务器 url + port
    + save.file.path=推荐使用  `/usr/local` 在此目录下手动创建 `gwebstatic` 文件夹
    + server.api.url.path=部署 ROOT 时 `/api/v1` 否则是 `/文件名/api/v1`
2. src\main\resources\gface.properties
    + 数据库配置

### 二、部署 Windows Server 上
1. src\main\resources\gface.properties
    + server.url=服务器 url + port
    + save.file.path=默认在C盘下可修改`src\main\resources\spring-mvc.xml 32行左右 `推荐使用  `/usr/local`
    + server.api.url.path=部署 ROOT 时 `/api/v1` 否则是 `/文件名/api/v1`
2. src\main\resources\gface.properties
    + 数据库配置
