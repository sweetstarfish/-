package com.cjdx.supermarket.controller;

import com.cjdx.supermarket.entity.Provider;
import com.cjdx.supermarket.entity.PageRequest;
import com.cjdx.supermarket.entity.PageResult;
import com.cjdx.supermarket.service.ProviderService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/provider")
public class ProviderController {

  @Resource
  private ProviderService providerService;

  // 获取供应商列表
  @GetMapping("/list")
  public ResponseEntity<?> getProviderList() {
    try {
      List<Provider> providers = providerService.getProviderList();
      return ResponseEntity.ok(Map.of(
        "data", providers,
        "success", true
      ));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(Map.of("message", "获取供应商列表失败", "success", false));
    }
  }

  /**
   * 分页查询供应商列表
   */
  @GetMapping("/page")
  public ResponseEntity<?> getProvidersByPage(
          @RequestParam(defaultValue = "1") Integer pageNum,
          @RequestParam(defaultValue = "10") Integer pageSize,
          @RequestParam(required = false) String keyword,
          @RequestParam(required = false) String sortField,
          @RequestParam(defaultValue = "desc") String sortOrder) {
    try {
      // 构建分页请求
      PageRequest pageRequest = new PageRequest(pageNum, pageSize, sortField, sortOrder);
      pageRequest.setKeyword(keyword);

      // 执行分页查询
      PageResult<Provider> result = providerService.getProvidersByPage(pageRequest);
      
      return ResponseEntity.ok(Map.of(
        "data", result,
        "success", true
      ));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(Map.of("message", "获取供应商列表失败", "success", false));
    }
  }

  // 添加供应商
  @PostMapping("/add")
  public ResponseEntity<?> addProvider(@RequestBody Provider provider) {
    try {
      boolean success = providerService.addProvider(provider);
      if (success) {
        return ResponseEntity.ok(Map.of("message", "供应商添加成功", "success", true));
      } else {
        return ResponseEntity.badRequest()
                .body(Map.of("message", "供应商添加失败", "success", false));
      }
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest()
              .body(Map.of("message", e.getMessage(), "success", false));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Map.of("message", "系统错误", "success", false));
    }
  }

  // 更新供应商
  @PutMapping("/update")
  public ResponseEntity<?> updateProvider(@RequestBody Provider provider) {
    try {
      boolean success = providerService.updateProvider(provider);
      if (success) {
        return ResponseEntity.ok(Map.of("message", "供应商更新成功", "success", true));
      } else {
        return ResponseEntity.badRequest()
                .body(Map.of("message", "供应商更新失败", "success", false));
      }
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest()
              .body(Map.of("message", e.getMessage(), "success", false));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Map.of("message", "系统错误", "success", false));
    }
  }

  // 删除供应商
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteProvider(@PathVariable Long id) {
    try {
      boolean success = providerService.deleteProvider(id);
      if (success) {
        return ResponseEntity.ok(Map.of("message", "供应商删除成功", "success", true));
      } else {
        return ResponseEntity.badRequest()
                .body(Map.of("message", "供应商删除失败", "success", false));
      }
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest()
              .body(Map.of("message", e.getMessage(), "success", false));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Map.of("message", "系统错误", "success", false));
    }
  }

  // 根据ID获取供应商
  @GetMapping("/{id}")
  public ResponseEntity<Provider> getProviderById(@PathVariable Long id) {
    try {
      Provider provider = providerService.getProviderById(id);
      if (provider != null) {
        return ResponseEntity.ok(provider);
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(null);
    }
  }
}
