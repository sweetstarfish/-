package com.cjdx.supermarket.mapper;

import com.cjdx.supermarket.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

  /**
   * 获取所有角色
   */
  List<Role> selectAllRoles();

  /**
   * 根据ID获取角色
   */
  Role selectRoleById(Long id);
}
