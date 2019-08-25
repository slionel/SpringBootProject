package com.zsj.controller;

import com.zsj.entity.AddressDetail;
import com.zsj.service.AddressDetailService;
import com.zsj.utils.Keyutils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @RequestMapping("getaddressdetail")
    @ResponseBody
    public List<AddressDetail> getAddressDetail(HttpServletRequest request){
        String userId = request.getParameter("userId");
        List<AddressDetail> addressDetailList = new ArrayList<AddressDetail>();
        addressDetailList = addressDetailService.getAddressDetailById(userId);
        return addressDetailList;
    }
}
