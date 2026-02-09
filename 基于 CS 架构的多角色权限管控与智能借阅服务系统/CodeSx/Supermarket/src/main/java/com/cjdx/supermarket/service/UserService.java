package com.cjdx.supermarket.service;

import com.cjdx.supermarket.entity.User;
import com.cjdx.supermarket.entity.PageRequest;
import com.cjdx.supermarket.entity.PageResult;
import com.cjdx.supermarket.mapper.UserMapper;
import com.cjdx.supermarket.util.PasswordUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordUtil passwordUtil;

    public List<User> listAllUsers() {
        return userMapper.selectAllUsers();
    }

    /**
     * 获取非admin用户列表（用于管理员管理）
     */
    public List<User> getNonAdminUsers() {
        return userMapper.selectNonAdminUsers();
    }

    /**
     * 分页查询用户列表
     */
    public PageResult<User> getUsersByPage(PageRequest pageRequest) {
        // 验证分页参数
        pageRequest.validate();
        
        // 查询总数
        Long total = userMapper.countUsers(pageRequest.getKeyword());
        
        // 查询数据
        List<User> users = userMapper.selectUsersByPage(
            pageRequest.getOffset(),
            pageRequest.getPageSize(),
            pageRequest.getKeyword(),
            pageRequest.getSortField(),
            pageRequest.getSortOrder()
        );
        
        return new PageResult<>(users, total, pageRequest.getPageNum(), pageRequest.getPageSize());
    }

    /**
     * 根据用户名查找用户
     */
    public User findByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    /**
     * 根据用户ID查找用户
     */
    public User findById(Long id) {
        return userMapper.selectById(id);
    }

    /**
     * 验证用户登录
     */
    public User validateLogin(String username, String password) {
        User user = findByUsername(username);
        if (user == null) {
            return null;
        }

        // 验证密码 - 尝试多种格式
        if (passwordUtil.matches(password, user.getUserPassword())) {
            return user;
        }

        // 兼容旧密码格式 - Base64编码的MD5
        if (passwordUtil.matchesLegacy(password, user.getUserPassword(), user.getSalt())) {
            return user;
        }

        // 兼容数据库中的MD5格式 - 十六进制字符串
        if (passwordUtil.matchesDatabaseMD5(password, user.getUserPassword(), user.getSalt())) {
            return user;
        }

        // 兼容数据库中的特殊MD5格式 - 盐值插入到特定位置
        if (passwordUtil.matchesCustomMD5(password, user.getUserPassword(), user.getSalt())) {
            return user;
        }

        return null;
    }

    /**
     * 添加用户
     */
    public boolean addUser(User user) {
        // 检查用户编码是否已存在
        User existingUser = userMapper.selectByUserCode(user.getUserCode());
        if (existingUser != null) {
            throw new RuntimeException("用户编码已存在");
        }

        // 生成盐值
        String salt = passwordUtil.generateSalt();
        user.setSalt(salt);

        // 加密密码
        String encodedPassword = passwordUtil.encodePassword(user.getUserPassword());
        user.setUserPassword(encodedPassword);

        // 设置创建信息
        user.setCreationDate(new Date());
        user.setModifyDate(new Date());
        user.setStatus(1); // 默认启用

        return userMapper.insertUser(user) > 0;
    }

    /**
     * 更新用户信息
     */
    public boolean updateUser(User user) {
        return userMapper.updateUser(user) > 0;
    }

    /**
     * 更新用户密码
     */
    public boolean updatePassword(Long userId, String newPassword) {
        String encodedPassword = passwordUtil.encodePassword(newPassword);
        return userMapper.updatePassword(userId, encodedPassword) > 0;
    }

    /**
     * 更新用户状态（启用/冻结）
     */
    public boolean updateUserStatus(Long userId, Integer status) {
        return userMapper.updateUserStatus(userId, status) > 0;
    }

    /**
     * 检查是否为管理员
     */
    public boolean isAdmin(Long userId) {
        User user = findById(userId);
        return user != null && "admin".equals(user.getUserCode());
    }
}
