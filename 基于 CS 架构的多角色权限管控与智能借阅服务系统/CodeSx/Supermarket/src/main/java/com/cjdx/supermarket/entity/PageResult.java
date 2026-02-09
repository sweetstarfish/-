package com.cjdx.supermarket.entity;

import java.util.List;

/**
 * 分页结果
 */
public class PageResult<T> {
    private List<T> records;        // 数据列表
    private Long total;             // 总记录数
    private Integer pageNum;        // 当前页码
    private Integer pageSize;       // 页大小
    private Integer totalPages;     // 总页数
    private Boolean hasNext;        // 是否有下一页
    private Boolean hasPrev;        // 是否有上一页

    public PageResult() {}

    public PageResult(List<T> records, Long total, Integer pageNum, Integer pageSize) {
        this.records = records;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.calculatePages();
    }

    /**
     * 计算分页信息
     */
    private void calculatePages() {
        if (total != null && pageSize != null && pageSize > 0) {
            this.totalPages = (int) Math.ceil((double) total / pageSize);
            this.hasNext = pageNum < totalPages;
            this.hasPrev = pageNum > 1;
        } else {
            this.totalPages = 0;
            this.hasNext = false;
            this.hasPrev = false;
        }
    }

    /**
     * 创建空的分页结果
     */
    public static <T> PageResult<T> empty(Integer pageNum, Integer pageSize) {
        PageResult<T> result = new PageResult<>();
        result.setRecords(List.of());
        result.setTotal(0L);
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        result.setTotalPages(0);
        result.setHasNext(false);
        result.setHasPrev(false);
        return result;
    }

    // Getters and Setters
    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
        this.calculatePages();
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
        this.calculatePages();
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        this.calculatePages();
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public Boolean getHasPrev() {
        return hasPrev;
    }

    public void setHasPrev(Boolean hasPrev) {
        this.hasPrev = hasPrev;
    }
} 