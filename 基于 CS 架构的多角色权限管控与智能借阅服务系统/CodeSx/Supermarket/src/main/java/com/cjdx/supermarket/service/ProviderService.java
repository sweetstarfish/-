package com.cjdx.supermarket.service;

import com.cjdx.supermarket.entity.Provider;
import com.cjdx.supermarket.entity.PageRequest;
import com.cjdx.supermarket.entity.PageResult;
import com.cjdx.supermarket.mapper.ProviderMapper;
import com.cjdx.supermarket.mapper.BillMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProviderService {

  @Resource
  private ProviderMapper providerMapper;

  @Resource
  private BillMapper billMapper;

  // 获取供应商列表
  public List<Provider> getProviderList() {
    return providerMapper.selectAllProviders();
  }

  /**
   * 分页查询供应商列表
   */
  public PageResult<Provider> getProvidersByPage(PageRequest pageRequest) {
    // 验证分页参数
    pageRequest.validate();
    
    // 查询总数
    Long total = providerMapper.countProviders(pageRequest.getKeyword());
    
    // 查询数据
    List<Provider> providers = providerMapper.selectProvidersByPage(
      pageRequest.getOffset(),
      pageRequest.getPageSize(),
      pageRequest.getKeyword(),
      pageRequest.getSortField(),
      pageRequest.getSortOrder()
    );
    
    return new PageResult<>(providers, total, pageRequest.getPageNum(), pageRequest.getPageSize());
  }

  // 添加供应商
  public boolean addProvider(Provider provider) {
    // 1. 验证供应商编码唯一性
    Provider existingProvider = providerMapper.selectProviderByCode(provider.getProCode());
    if (existingProvider != null) {
      throw new RuntimeException("供应商编码已存在");
    }

    // 2. 设置创建信息
    provider.setCreationDate(new Date());
    provider.setModifyDate(new Date());

    return providerMapper.insertProvider(provider) > 0;
  }

  // 更新供应商
  public boolean updateProvider(Provider provider) {
    // 检查供应商是否存在
    Provider existingProvider = providerMapper.selectProviderById(provider.getId());
    if (existingProvider == null) {
      throw new RuntimeException("供应商不存在");
    }

    provider.setModifyDate(new Date());
    return providerMapper.updateProvider(provider) > 0;
  }

  // 删除供应商
  public boolean deleteProvider(Long id) {
    // 检查供应商是否存在
    Provider provider = providerMapper.selectProviderById(id);
    if (provider == null) {
      throw new RuntimeException("供应商不存在");
    }

    // 检查是否有关联的账单
    int billCount = billMapper.countBillsByProvider(id);
    if (billCount > 0) {
      throw new RuntimeException("该供应商下已有账单，无法删除");
    }

    return providerMapper.deleteProvider(id) > 0;
  }

  // 根据ID获取供应商
  public Provider getProviderById(Long id) {
    return providerMapper.selectProviderById(id);
  }

  // 根据编码获取供应商
  public Provider getProviderByCode(String proCode) {
    return providerMapper.selectProviderByCode(proCode);
  }
}
