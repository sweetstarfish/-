-- 创建数据库
CREATE DATABASE IF NOT EXISTS smbms_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE smbms_db;

-- 创建用户表（如果不存在）
CREATE TABLE IF NOT EXISTS smbms_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    userCode VARCHAR(50) NOT NULL UNIQUE,
    userName VARCHAR(100) NOT NULL,
    userPassword VARCHAR(255) NOT NULL,
    salt VARCHAR(50) NOT NULL,
    status INT DEFAULT 1,
    gender INT DEFAULT 1,
    birthday DATE,
    phone VARCHAR(20),
    address VARCHAR(200),
    headUrl VARCHAR(200),
    userRole INT DEFAULT 1,
    createdBy BIGINT,
    creationDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    modifyBy BIGINT,
    modifyDate DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建角色表（如果不存在）
CREATE TABLE IF NOT EXISTS smbms_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    roleCode VARCHAR(15) NOT NULL,
    roleName VARCHAR(50) NOT NULL,
    createdBy BIGINT,
    creationDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    modifyBy BIGINT,
    modifyDate DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建供应商表（如果不存在）
CREATE TABLE IF NOT EXISTS smbms_provider (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    proCode VARCHAR(20) NOT NULL UNIQUE,
    proName VARCHAR(20) NOT NULL,
    proDesc VARCHAR(50),
    proContact VARCHAR(20),
    proPhone VARCHAR(20),
    proAddress VARCHAR(50),
    proFax VARCHAR(20),
    createdBy BIGINT,
    creationDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    modifyBy BIGINT,
    modifyDate DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建账单表（如果不存在）
CREATE TABLE IF NOT EXISTS smbms_bill (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    billCode VARCHAR(20) NOT NULL UNIQUE,
    productName VARCHAR(20),
    productDesc VARCHAR(50),
    productUnit VARCHAR(10),
    productCount DECIMAL(20,2),
    totalPrice DECIMAL(20,2),
    isPayment INT DEFAULT 1,
    createdBy BIGINT,
    creationDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    modifyBy BIGINT,
    modifyDate DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    providerId BIGINT,
    FOREIGN KEY (providerId) REFERENCES smbms_provider(id)
);

-- 插入角色数据
INSERT INTO smbms_role (roleCode, roleName, createdBy) VALUES 
('SMBMS_ADMIN', '系统管理员', 1),
('SMBMS_MANAGER', '经理', 1),
('SMBMS_EMPLOYEE', '普通员工', 1);

-- 插入测试用户数据
-- 密码: 123456, 盐值: test123
-- MD5(123456test123) = 5f4dcc3b5aa765d61d8327deb882cf99
-- Base64编码后的哈希值
INSERT INTO smbms_user (userCode, userName, userPassword, salt, status, gender, phone, address, userRole) 
VALUES 
('admin', '管理员', 'NWY0ZGNjM2I1YWE3NjVkNjFkODMyN2RlYjg4MmNmOTk=', 'test123', 1, 1, '13800138000', '北京市朝阳区', 1),
('user001', '张三', 'NWY0ZGNjM2I1YWE3NjVkNjFkODMyN2RlYjg4MmNmOTk=', 'test123', 1, 1, '13800138001', '上海市浦东新区', 2),
('user002', '李四', 'NWY0ZGNjM2I1YWE3NjVkNjFkODMyN2RlYjg4MmNmOTk=', 'test123', 1, 2, '13800138002', '广州市天河区', 2);

-- 插入测试供应商数据
INSERT INTO smbms_provider (proCode, proName, proDesc, proContact, proPhone, proAddress, proFax, createdBy) VALUES
('BJ_GYS001', '北京三木堂商贸有限公司', '长期合作伙伴，主营产品:茅台、五粮液、郎酒、酒鬼酒、泸州老窖、赖茅酒、法国红酒等', '张国强', '13566667777', '北京市丰台区育芳园北路', '010-58858787', 1),
('HB_GYS001', '石家庄帅益食品贸易有限公司', '长期合作伙伴，主营产品:饮料、水饮料、植物蛋白饮料、休闲食品、果汁饮料、功能饮料等', '王军', '13309094212', '河北省石家庄新华区', '0311-67738876', 1),
('GZ_GYS001', '深圳市泰香米业有限公司', '初次合作伙伴，主营产品：良记金轮米,龙轮香米等', '郑程瀚', '13402013312', '广东省深圳市福田区深南大道6006华丰大厦', '0755-67776212', 1);

-- 插入测试账单数据
INSERT INTO smbms_bill (billCode, productName, productDesc, productUnit, productCount, totalPrice, isPayment, createdBy, providerId) VALUES
('BILL2016_001', '洗发水、护发素', '日用品-洗发、护发', '瓶', 500.00, 25000.00, 2, 1, 1),
('BILL2016_002', '香皂、肥皂、药皂', '日用品-皂类', '块', 1000.00, 10000.00, 2, 1, 1),
('BILL2016_003', '大豆油', '食品-食用油', '斤', 300.00, 5890.00, 2, 1, 2),
('BILL2016_004', '橄榄油', '食品-进口食用油', '斤', 200.00, 9800.00, 2, 1, 2),
('BILL2016_005', '洗洁精', '日用品-厨房清洁', '瓶', 500.00, 7000.00, 2, 1, 3);

-- 验证数据导入
SELECT '数据库导入完成！' as message;
SELECT COUNT(*) as user_count FROM smbms_user;
SELECT COUNT(*) as role_count FROM smbms_role;
SELECT COUNT(*) as provider_count FROM smbms_provider;
SELECT COUNT(*) as bill_count FROM smbms_bill;
