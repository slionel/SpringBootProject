package com.zsj.controller;

import com.zsj.entity.AddressDetail;
import com.zsj.service.AddressDetailService;
import com.zsj.utils.Keyutils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("ac")
public class AddressDetailController {
    @Resource
    AddressDetailService addressDetailService;

    @RequestMapping("createaddressdetail")
    @ResponseBody
    public Map createAddressDetail(@RequestBody AddressDetail addressDetail){
        Map map = new HashMap();
        addressDetail.setId(Keyutils.genUniqueKey());
        if(addressDetailService.addAddressDetail(addressDetail) != null){
            map.put("rs","true");
        }
        else {
            map.put("rs","false");
        }
        return map;
    }
}
