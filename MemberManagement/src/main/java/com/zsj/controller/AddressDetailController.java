package com.zsj.controller;

import com.zsj.entity.AddressDetail;
import com.zsj.service.AddressDetailService;
import com.zsj.utils.Keyutils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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

    @RequestMapping("getaddressdetailbyaddressid")
    @ResponseBody
    public AddressDetail getAddressDetailById(HttpServletRequest request){
        String addressId = request.getParameter("addressId");
        Optional<AddressDetail> addressDetail = addressDetailService.getAddressDetailByAddressId(addressId);
        AddressDetail addressDetail1 = addressDetail.get();
        return addressDetail1;
    }

    @RequestMapping("updateaddressdetail")
    @ResponseBody
    public Map updateAddressDetail(HttpServletRequest request){
        Map map = new HashMap();
        String addressId = request.getParameter("addressId");
        String tel = request.getParameter("tel");
        String address = request.getParameter("address");
        String connector = request.getParameter("connector");
        int num = addressDetailService.updateAddressDetail(addressId,tel,connector,address);
        map.put("rs",num);
        return map;
    }

    @RequestMapping("deleteaddressdetail")
    @ResponseBody
    public Map deleteAddressDetail(@RequestParam("addressId") String addressId){
        Map map = new HashMap();
        int num = addressDetailService.deleteAddressDetail(addressId);
        map.put("rs",num);
        return map;
    }
}
