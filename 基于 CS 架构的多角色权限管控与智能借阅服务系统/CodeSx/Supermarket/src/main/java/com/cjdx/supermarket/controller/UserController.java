package com.cjdx.supermarket.controller;

import com.cjdx.supermarket.entity.User;
import com.cjdx.supermarket.entity.PageRequest;
import com.cjdx.supermarket.entity.PageResult;
import com.cjdx.supermarket.service.UserService;
import com.cjdx.supermarket.service.RoleService;
import com.cjdx.supermarket.util.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private JwtUtil jwtUtil;

    /**
     * 获取所有用户（管理员功能）
     */
    @GetMapping("/list")
    public ResponseEntity<?> listAllUsers(@RequestHeader("Authorization") String token) {
        try {
            // 验证管理员权限
            if (!isAdmin(token)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("message", "权限不足，只有管理员可以访问", "success", false));
            }

            List<User> users = userService.getNonAdminUsers();
            return ResponseEntity.ok(Map.of("data", users, "success", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "获取用户列表失败", "success", false));
        }
    }

    /**
     * 分页查询用户列表
     */
    @GetMapping("/page")
    public ResponseEntity<?> getUsersByPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sortField,
            @RequestParam(defaultValue = "desc") String sortOrder,
            @RequestHeader("Authorization") String token) {
        try {
            // 验证管理员权限
            if (!isAdmin(token)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("message", "权限不足，只有管理员可以访问", "success", false));
            }

            // 构建分页请求
            PageRequest pageRequest = new PageRequest(pageNum, pageSize, sortField, sortOrder);
            pageRequest.setKeyword(keyword);

            // 执行分页查询
            PageResult<User> result = userService.getUsersByPage(pageRequest);
            
            return ResponseEntity.ok(Map.of(
                "data", result,
                "success", true
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "获取用户列表失败", "success", false));
        }
    }

    /**
     * 添加用户（管理员功能）
     */
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user, @RequestHeader("Authorization") String token) {
        try {
            // 验证管理员权限
            if (!isAdmin(token)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("message", "权限不足，只有管理员可以访问", "success", false));
            }

            boolean success = userService.addUser(user);
            if (success) {
                return ResponseEntity.ok(Map.of("message", "用户添加成功", "success", true));
            } else {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "用户添加失败", "success", false));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage(), "success", false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "系统错误", "success", false));
        }
    }

    /**
     * 更新用户状态（启用/冻结）
     */
    @PutMapping("/status/{userId}")
    public ResponseEntity<?> updateUserStatus(
            @PathVariable Long userId,
            @RequestParam Integer status,
            @RequestHeader("Authorization") String token) {
        try {
            // 验证管理员权限
            if (!isAdmin(token)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("message", "权限不足，只有管理员可以访问", "success", false));
            }

            boolean success = userService.updateUserStatus(userId, status);
            if (success) {
                String message = status == 1 ? "用户启用成功" : "用户冻结成功";
                return ResponseEntity.ok(Map.of("message", message, "success", true));
            } else {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "状态更新失败", "success", false));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "系统错误", "success", false));
        }
    }

    /**
     * 获取角色列表（管理员功能）
     */
    @GetMapping("/roles")
    public ResponseEntity<?> getRoles(@RequestHeader("Authorization") String token) {
        try {
            // 验证管理员权限
            if (!isAdmin(token)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("message", "权限不足，只有管理员可以访问", "success", false));
            }

            List<Map<String, Object>> roles = roleService.getAllRoles().stream()
                    .map(role -> {
                        Map<String, Object> roleMap = new HashMap<>();
                        roleMap.put("id", role.getId());
                        roleMap.put("roleCode", role.getRoleCode());
                        roleMap.put("roleName", role.getRoleName());
                        return roleMap;
                    })
                    .toList();

            return ResponseEntity.ok(Map.of("data", roles, "success", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "获取角色列表失败", "success", false));
        }
    }

    /**
     * 修改用户资料
     */
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody User user, @RequestHeader("Authorization") String token) {
        try {
            // 验证token
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Token无效", "success", false));
            }

            // 获取当前用户ID
            Long currentUserId = jwtUtil.getUserIdFromToken(token);
            if (currentUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Token解析失败", "success", false));
            }

            // 确保只能修改自己的资料
            user.setId(currentUserId);
            user.setModifyBy(currentUserId);

            // 不允许修改敏感信息
            user.setUserPassword(null);
            user.setSalt(null);
            user.setUserCode(null);
            user.setUserRole(null);
            user.setStatus(null);

            boolean success = userService.updateUser(user);
            if (success) {
                return ResponseEntity.ok(Map.of("message", "资料更新成功", "success", true));
            } else {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "资料更新失败", "success", false));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "系统错误", "success", false));
        }
    }

    /**
     * 修改密码
     */
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> request,
            @RequestHeader("Authorization") String token) {
        try {
            String oldPassword = request.get("oldPassword");
            String newPassword = request.get("newPassword");

            if (oldPassword == null || newPassword == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "旧密码和新密码不能为空", "success", false));
            }

            // 验证token
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Token无效", "success", false));
            }

            // 获取当前用户ID
            Long currentUserId = jwtUtil.getUserIdFromToken(token);
            if (currentUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Token解析失败", "success", false));
            }

            // 验证旧密码
            User currentUser = userService.findById(currentUserId);
            if (currentUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "用户不存在", "success", false));
            }

            // 验证旧密码是否正确
            User validatedUser = userService.validateLogin(currentUser.getUserCode(), oldPassword);
            if (validatedUser == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "旧密码错误", "success", false));
            }

            // 更新密码
            boolean success = userService.updatePassword(currentUserId, newPassword);
            if (success) {
                return ResponseEntity.ok(Map.of("message", "密码修改成功", "success", true));
            } else {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "密码修改失败", "success", false));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "系统错误", "success", false));
        }
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User user = userService.findById(id);
            if (user != null) {
                // 清除敏感信息
                user.setUserPassword(null);
                user.setSalt(null);
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "获取用户信息失败", "success", false));
        }
    }

    /**
     * 验证管理员权限
     */
    private boolean isAdmin(String token) {
        try {
            String actualToken = token.replace("Bearer ", "");
            Long userId = jwtUtil.getUserIdFromToken(actualToken);
            return userService.isAdmin(userId);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从token获取用户ID
     */
    private Long getUserIdFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return jwtUtil.getUserIdFromToken(token);
    }
}
