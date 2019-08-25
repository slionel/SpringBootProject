package com.zsj.repository;

import com.zsj.entity.AddressDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author zsj55
 */
public interface AddressDetailRepository extends JpaRepository<AddressDetail, String> {
    /**
     * 通过登录人的id找到在address_detail表中所对应的信息
     * @param userId
     * @return
     */
    public List<AddressDetail> findByUserId(String userId);
}
