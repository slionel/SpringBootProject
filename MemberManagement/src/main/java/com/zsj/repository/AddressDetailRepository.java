package com.zsj.repository;

import com.zsj.entity.AddressDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

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

    @Modifying
    @Transactional
    @Query(value = "update address_detail set address=?1,tel=?2,connector=?3 where id=?4",nativeQuery = true)
    public int updateAddressDetail(String address, String tel,String connector,String id);

    @Modifying
    @Transactional
    @Query(value = "delete from address_detail where id=?1",nativeQuery = true)
    public int deleteAddressDetail(String id);
}
