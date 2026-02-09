package com.cjdx.supermarket.mapper;

import com.cjdx.supermarket.entity.Bill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BillMapper {
    // 基础CRUD
    List<Bill> selectAllBills();

    Bill selectBillById(Long id);

    int insertBill(Bill bill);

    int updateBill(Bill bill);

    int deleteBill(Long id);

    // 业务查询
    Bill selectBillByCode(String billCode);

    List<Bill> selectBillsWithProvider(); // 关联查询

    /**
     * 分页查询账单列表（包含供应商信息）
     */
    List<Bill> selectBillsByPage(@Param("offset") Integer offset,
                                 @Param("pageSize") Integer pageSize,
                                 @Param("keyword") String keyword,
                                 @Param("sortField") String sortField,
                                 @Param("sortOrder") String sortOrder);

    /**
     * 统计账单总数
     */
    Long countBills(@Param("keyword") String keyword);

    /**
     * 统计指定日期的账单数量
     */
    Integer countBillsByDate(@Param("date") String date);

    /**
     * 统计指定日期范围的账单数量
     */
    Integer countBillsByDateRange(@Param("startDate") String startDate, 
                                  @Param("endDate") String endDate);

    /**
     * 统计指定日期范围的账单金额
     */
    Double sumBillsAmountByDateRange(@Param("startDate") String startDate, 
                                    @Param("endDate") String endDate);

    /**
     * 获取最近7天的账单数量统计
     */
    List<Map<String, Object>> getBillsCountByLast7Days();

    /**
     * 获取最近7天的账单金额统计
     */
    List<Map<String, Object>> getBillsAmountByLast7Days();

    int countBillsByProvider(Long providerId); // 统计供应商关联账单

}
