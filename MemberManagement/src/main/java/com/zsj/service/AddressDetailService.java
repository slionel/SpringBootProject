package com.zsj.service;

import com.zsj.entity.AddressDetail;
import com.zsj.repository.AddressDetailRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

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

    public Optional<AddressDetail> getAddressDetailByAddressId(String addressId){
        return addressDetailRepository.findById(addressId);
    }

    public int updateAddressDetail(String id, String tel, String connector, String address){
        return addressDetailRepository.updateAddressDetail(address,tel,connector,id);
    }

    public int deleteAddressDetail(String id){
        return addressDetailRepository.deleteAddressDetail(id);
    }
}
