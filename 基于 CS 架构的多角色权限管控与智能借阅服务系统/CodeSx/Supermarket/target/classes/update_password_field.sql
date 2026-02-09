-- 更新userPassword字段长度以支持BCrypt加密
ALTER TABLE smbms_user MODIFY COLUMN userPassword VARCHAR(255) NOT NULL; 