package com.cjdx.supermarket.service;

import com.cjdx.supermarket.entity.Bill;
import com.cjdx.supermarket.entity.Provider;
import com.cjdx.supermarket.entity.PageRequest;
import com.cjdx.supermarket.entity.PageResult;
import com.cjdx.supermarket.mapper.BillMapper;
import com.cjdx.supermarket.mapper.ProviderMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BillService {

    @Resource
    private BillMapper billMapper;

    @Resource
    private ProviderMapper providerMapper;

    // 获取账单列表（包含供应商信息）
    public List<Bill> getBillList() {
        return billMapper.selectBillsWithProvider();
    }

    /**
     * 分页查询账单列表
     */
    public PageResult<Bill> getBillsByPage(PageRequest pageRequest) {
        // 验证分页参数
        pageRequest.validate();
        
        // 查询总数
        Long total = billMapper.countBills(pageRequest.getKeyword());
        
        // 查询数据
        List<Bill> bills = billMapper.selectBillsByPage(
            pageRequest.getOffset(),
            pageRequest.getPageSize(),
            pageRequest.getKeyword(),
            pageRequest.getSortField(),
            pageRequest.getSortOrder()
        );
        
        return new PageResult<>(bills, total, pageRequest.getPageNum(), pageRequest.getPageSize());
    }

    // 添加账单
    public boolean addBill(Bill bill) {
        // 1. 验证账单编码唯一性
        Bill existingBill = billMapper.selectBillByCode(bill.getBillCode());
        if (existingBill != null) {
            throw new RuntimeException("账单编码已存在");
        }

        // 2. 验证供应商是否存在
        Provider provider = providerMapper.selectProviderById(bill.getProviderId());
        if (provider == null) {
            throw new RuntimeException("供应商不存在");
        }

        // 3. 设置创建信息
        bill.setCreationDate(new Date());
        bill.setModifyDate(new Date());

        return billMapper.insertBill(bill) > 0;
    }

    // 更新账单
    public boolean updateBill(Bill bill) {
        // 检查账单是否存在
        Bill existingBill = billMapper.selectBillById(bill.getId());
        if (existingBill == null) {
            throw new RuntimeException("账单不存在");
        }

        // 检查是否已支付
        if (existingBill.getIsPayment() == 2) {
            throw new RuntimeException("已支付的账单不能修改");
        }

        // 验证供应商是否存在
        if (bill.getProviderId() != null) {
            Provider provider = providerMapper.selectProviderById(bill.getProviderId());
            if (provider == null) {
                throw new RuntimeException("供应商不存在");
            }
        }

        // 设置修改信息
        bill.setModifyDate(new Date());
        if (bill.getModifyBy() == null) {
            bill.setModifyBy(1L); // 默认修改者ID
        }

        return billMapper.updateBill(bill) > 0;
    }

    // 删除账单
    public boolean deleteBill(Long id) {
        // 检查账单是否存在
        Bill bill = billMapper.selectBillById(id);
        if (bill == null) {
            throw new RuntimeException("账单不存在");
        }

        // 检查是否已支付
        if (bill.getIsPayment() == 2) {
            throw new RuntimeException("已支付的账单不能删除");
        }

        return billMapper.deleteBill(id) > 0;
    }

    // 根据ID获取账单
    public Bill getBillById(Long id) {
        return billMapper.selectBillById(id);
    }
}
