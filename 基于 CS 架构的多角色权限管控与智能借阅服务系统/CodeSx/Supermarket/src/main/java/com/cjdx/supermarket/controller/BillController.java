package com.cjdx.supermarket.controller;

import com.cjdx.supermarket.entity.Bill;
import com.cjdx.supermarket.entity.PageRequest;
import com.cjdx.supermarket.entity.PageResult;
import com.cjdx.supermarket.service.BillService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bill")
public class BillController {

    @Resource
    private BillService billService;

    // 获取账单列表
    @GetMapping("/list")
    public ResponseEntity<?> getBillList() {
        try {
            List<Bill> bills = billService.getBillList();
            return ResponseEntity.ok(new HashMap<String, Object>() {
                {
                    put("data", bills);
                }
            });
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    /**
     * 分页查询账单列表
     */
    @GetMapping("/page")
    public ResponseEntity<?> getBillsByPage(
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
            PageResult<Bill> result = billService.getBillsByPage(pageRequest);
            
            return ResponseEntity.ok(Map.of(
                "data", result,
                "success", true
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "获取账单列表失败", "success", false));
        }
    }

    // 添加账单
    @PostMapping("/add")
    public ResponseEntity<?> addBill(@RequestBody Bill bill) {
        try {
            boolean success = billService.addBill(bill);
            if (success) {
                return ResponseEntity.ok(Map.of("message", "账单添加成功", "success", true));
            } else {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "账单添加失败", "success", false));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage(), "success", false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "系统错误", "success", false));
        }
    }

    // 更新账单
    @PutMapping("/update")
    public ResponseEntity<?> updateBill(@RequestBody Bill bill) {
        try {
            boolean success = billService.updateBill(bill);
            if (success) {
                return ResponseEntity.ok(Map.of("message", "账单更新成功", "success", true));
            } else {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "账单更新失败", "success", false));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage(), "success", false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "系统错误", "success", false));
        }
    }

    // 删除账单
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBill(@PathVariable Long id) {
        try {
            boolean success = billService.deleteBill(id);
            if (success) {
                return ResponseEntity.ok(Map.of("message", "账单删除成功", "success", true));
            } else {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "账单删除失败", "success", false));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage(), "success", false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "系统错误", "success", false));
        }
    }

    // 获取单个账单
    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable Long id) {
        try {
            Bill bill = billService.getBillById(id);
            if (bill != null) {
                return ResponseEntity.ok(bill);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
