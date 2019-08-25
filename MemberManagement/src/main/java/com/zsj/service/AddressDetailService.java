package com.zsj.service;

import com.zsj.entity.AddressDetail;
import com.zsj.repository.AddressDetailRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AddressDetailService {
    @Resource
    AddressDetailRepository addressDetailRepository;

    public AddressDetail addAddressDetail(AddressDetail addressDetail){
        return addressDetailRepository.save(addressDetail);
    }

    public List<AddressDetail> getAddressDetailById(String userId){
        return addressDetailRepository.findByUserId(userId);
    }
}
