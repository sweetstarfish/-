package com.cjdx.supermarket.controller;

import com.cjdx.supermarket.service.DashboardService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Resource
    private DashboardService dashboardService;

    /**
     * 获取仪表板统计数据
     */
    @GetMapping("/stats")
    public ResponseEntity<?> getDashboardStats() {
        try {
            Map<String, Object> stats = dashboardService.getDashboardStats();
            return ResponseEntity.ok(Map.of(
                "data", stats,
                "success", true
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", "获取统计数据失败", "success", false));
        }
    }

    /**
     * 获取最近7天账单数量趋势
     */
    @GetMapping("/trend/quantity")
    public ResponseEntity<?> getQuantityTrend() {
        try {
            Map<String, Object> trend = dashboardService.getQuantityTrend();
            return ResponseEntity.ok(Map.of(
                "data", trend,
                "success", true
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", "获取数量趋势失败", "success", false));
        }
    }

    /**
     * 获取最近7天账单金额趋势
     */
    @GetMapping("/trend/amount")
    public ResponseEntity<?> getAmountTrend() {
        try {
            Map<String, Object> trend = dashboardService.getAmountTrend();
            return ResponseEntity.ok(Map.of(
                "data", trend,
                "success", true
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", "获取金额趋势失败", "success", false));
        }
    }
} 