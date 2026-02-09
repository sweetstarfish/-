# IDEA配置说明

## 在IDEA中配置项目

### 1. 导入项目

1. **打开IDEA**
2. **导入后端项目**：
   - 选择 `File` → `Open`
   - 选择 `Supermarket` 文件夹
   - 等待IDEA自动识别Maven项目并下载依赖

3. **导入前端项目**（可选）：
   - 在IDEA中，选择 `File` → `Open`
   - 选择 `Sxvue` 文件夹
   - 或者直接在IDEA中打开整个 `CodeSx` 文件夹

### 2. 配置JDK

1. **设置项目JDK**：
   - 打开 `File` → `Project Structure` (快捷键: `Ctrl+Alt+Shift+S`)
   - 在 `Project` 选项卡中：
     - 设置 `Project SDK` 为 `Java 17`
     - 设置 `Project language level` 为 `17`
   - 在 `Modules` 选项卡中：
     - 确保 `Language level` 为 `17`

2. **验证JDK配置**：
   - 在IDEA的右下角状态栏应该显示Java 17
   - 或者在 `File` → `Project Structure` → `Project` 中确认

### 3. 配置Maven

1. **检查Maven设置**：
   - 打开 `File` → `Settings` (快捷键: `Ctrl+Alt+S`)
   - 导航到 `Build, Execution, Deployment` → `Build Tools` → `Maven`
   - 确认Maven home path和User settings file配置正确

2. **刷新Maven项目**：
   - 在IDEA右侧找到Maven工具窗口
   - 点击刷新按钮（⟳）重新下载依赖

### 4. 配置数据库

1. **修改数据库配置**：
   - 打开 `Supermarket/src/main/resources/application.yml`
   - 修改数据库连接信息：
   ```yaml
   spring:
     datasource:
       username: 你的MySQL用户名
       password: 你的MySQL密码
   ```

2. **确保MySQL服务运行**：
   - 启动MySQL服务
   - 创建数据库：`CREATE DATABASE smbms_db;`
   - 导入数据：执行 `data.sql` 文件

### 5. 创建运行配置

#### 后端运行配置

1. **创建Spring Boot运行配置**：
   - 点击IDEA顶部工具栏的 `Run` → `Edit Configurations...`
   - 点击左上角的 `+` 号，选择 `Spring Boot`
   - 配置如下：
     - **Name**: `Supermarket Backend`
     - **Main class**: `com.cjdx.supermarket.SupermarketApplication`
     - **Module**: `Supermarket`
     - **JRE**: `17`
     - **VM options**: `-Dspring.profiles.active=dev`

2. **配置环境变量**（可选）：
   - 在运行配置中添加环境变量：
     - `SPRING_PROFILES_ACTIVE=dev`
     - `SERVER_PORT=4399`

#### 前端运行配置

1. **创建npm运行配置**：
   - 点击 `Run` → `Edit Configurations...`
   - 点击 `+` 号，选择 `npm`
   - 配置如下：
     - **Name**: `Vue Frontend`
     - **package.json**: 选择 `Sxvue/package.json`
     - **Command**: `run`
     - **Scripts**: `dev`

### 6. 配置调试

1. **后端调试配置**：
   - 在Spring Boot运行配置中勾选 `Debug`
   - 设置断点后使用 `Debug` 模式启动

2. **前端调试配置**：
   - 安装Vue DevTools浏览器扩展
   - 在浏览器中打开开发者工具进行调试

### 7. 配置热重载

1. **后端热重载**：
   - Spring Boot默认支持热重载
   - 确保在 `pom.xml` 中有 `spring-boot-devtools` 依赖

2. **前端热重载**：
   - Vite默认支持热重载
   - 修改代码后会自动刷新浏览器

### 8. 常用快捷键

- `Ctrl+Shift+F10`: 运行当前文件
- `Shift+F10`: 运行当前配置
- `Shift+F9`: 调试当前配置
- `Ctrl+F2`: 停止运行
- `Ctrl+Shift+F12`: 最大化/恢复工具窗口

### 9. 项目结构视图

在IDEA中，你可以看到以下项目结构：

```
Supermarket (Maven项目)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/cjdx/supermarket/
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       ├── entity/
│   │   │       └── mapper/
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── data.sql
│   │       └── mapper/
│   └── test/
└── pom.xml

Sxvue (Node.js项目)
├── src/
│   ├── views/
│   ├── router/
│   ├── api/
│   └── utils/
├── public/
└── package.json
```

### 10. 常见问题解决

#### 问题1: Maven依赖下载失败
**解决方案**：
- 检查网络连接
- 配置Maven镜像源
- 清理并重新下载：`mvn clean install`

#### 问题2: 端口被占用
**解决方案**：
- 修改 `application.yml` 中的端口号
- 或者终止占用端口的进程

#### 问题3: 数据库连接失败
**解决方案**：
- 检查MySQL服务是否启动
- 验证数据库用户名和密码
- 确认数据库已创建

#### 问题4: 前端依赖安装失败
**解决方案**：
- 检查Node.js版本
- 清理缓存：`npm cache clean --force`
- 删除 `node_modules` 并重新安装

### 11. 开发建议

1. **使用IDEA的集成功能**：
   - 在IDEA中同时打开前后端项目
   - 使用IDEA的终端功能运行命令
   - 利用IDEA的代码补全和错误检查

2. **配置代码风格**：
   - 导入项目的代码风格设置
   - 配置自动格式化快捷键

3. **使用版本控制**：
   - 在IDEA中配置Git
   - 使用IDEA的版本控制功能

4. **配置代码检查**：
   - 启用IDEA的代码检查功能
   - 配置ESLint（前端）和CheckStyle（后端） 