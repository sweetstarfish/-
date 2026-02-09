# 超市管理系统

这是一个基于Vue 3 + Spring Boot的超市管理系统，包含用户登录、供应商管理、账单管理等功能。

## 技术栈

### 前端
- Vue 3
- Element Plus
- Vue Router
- Axios

### 后端
- Spring Boot 3.5.4
- MyBatis
- MySQL
- 简单Token认证（替代JWT）

## 功能特性

### 用户登录系统
- 用户名密码登录
- 简单Token认证（内存存储）
- 密码加密存储
- 路由守卫保护

### 测试账号
- 用户名: `admin` 密码: `123456`
- 用户名: `user001` 密码: `123456`
- 用户名: `user002` 密码: `123456`

## 快速开始

### 1. 启动后端服务

```bash
cd Supermarket
mvn spring-boot:run
```

后端服务将在 `http://localhost:8080` 启动

### 2. 启动前端服务

```bash
cd Sxvue
npm install
npm run dev
```

前端服务将在 `http://localhost:5173` 启动

### 3. 数据库配置

确保MySQL服务已启动，并在 `Supermarket/src/main/resources/application.yml` 中配置正确的数据库连接信息。

## API接口

### 用户登录
- **POST** `/user/login`
- 请求体: `{"username": "admin", "password": "123456"}`
- 响应: `{"token": "jwt_token", "user": {...}, "message": "登录成功"}`

### 获取用户列表
- **GET** `/user/list`
- 需要认证头: `Authorization: Bearer <token>`

## 项目结构

```
CodeSx/
├── Supermarket/          # 后端Spring Boot项目
│   ├── src/main/java/
│   │   └── com/cjdx/supermarket/
│   │       ├── controller/    # 控制器
│   │       ├── service/       # 服务层
│   │       ├── mapper/        # 数据访问层
│   │       ├── entity/        # 实体类
│   │       └── util/          # 工具类
│   └── src/main/resources/
│       ├── application.yml    # 配置文件
│       └── mapper/           # MyBatis映射文件
└── Sxvue/                # 前端Vue项目
    ├── src/
    │   ├── api/          # API接口
    │   ├── views/        # 页面组件
    │   └── router/       # 路由配置
    └── package.json
```

## 开发说明

### 密码加密
系统使用MD5 + 盐值的方式对密码进行加密存储，提高安全性。

### Token认证
使用简单Token进行用户认证，Token有效期为24小时，存储在内存中。

### 路由守卫
前端使用Vue Router的导航守卫功能，保护需要登录的页面。

## 注意事项

1. 确保MySQL数据库已正确配置
2. 后端服务启动前需要先创建数据库表
3. 前端开发时注意跨域配置
4. 生产环境部署时需要修改JWT密钥

## 许可证

MIT License 