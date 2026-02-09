package com.cjdx.supermarket.mapper;

import com.cjdx.supermarket.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> selectAllUsers();

    /**
     * 获取非admin用户列表
     */
    List<User> selectNonAdminUsers();

    /**
     * 分页查询用户列表
     */
    List<User> selectUsersByPage(@Param("offset") Integer offset, 
                                @Param("pageSize") Integer pageSize,
                                @Param("keyword") String keyword,
                                @Param("sortField") String sortField,
                                @Param("sortOrder") String sortOrder);

    /**
     * 统计用户总数
     */
    Long countUsers(@Param("keyword") String keyword);

    User selectByUserCode(@Param("userCode") String userCode);

    User selectByUsername(@Param("username") String username);

    User selectById(@Param("id") Long id);

    /**
     * 添加用户
     */
    int insertUser(User user);

    int updateUser(User user);

    int updatePassword(@Param("userId") Long userId, @Param("password") String password);

    /**
     * 更新用户状态
     */
    int updateUserStatus(@Param("userId") Long userId, @Param("status") Integer status);
}
