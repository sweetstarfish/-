package com.cjdx.supermarket.service;

import com.cjdx.supermarket.entity.Role;
import com.cjdx.supermarket.mapper.RoleMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

  @Resource
  private RoleMapper roleMapper;

  /**
   * 获取所有角色列表
   */
  public List<Role> getAllRoles() {
    return roleMapper.selectAllRoles();
  }

  /**
   * 根据ID获取角色
   */
  public Role getRoleById(Long id) {
    return roleMapper.selectRoleById(id);
  }
}
