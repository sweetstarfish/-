package com.cjdx.supermarket.mapper;

import com.cjdx.supermarket.entity.Provider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProviderMapper {

    // 基础CRUD
    List<Provider> selectAllProviders();

    /**
     * 分页查询供应商列表
     */
    List<Provider> selectProvidersByPage(@Param("offset") Integer offset,
                                        @Param("pageSize") Integer pageSize,
                                        @Param("keyword") String keyword,
                                        @Param("sortField") String sortField,
                                        @Param("sortOrder") String sortOrder);

    /**
     * 统计供应商总数
     */
    Long countProviders(@Param("keyword") String keyword);

    Provider selectProviderById(Long providerId);

    int insertProvider(Provider provider);

    int updateProvider(Provider provider);

    int deleteProvider(Long id);

    // 业务查询
    Provider selectProviderByCode(String proCode);
}
