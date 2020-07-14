package com.vpu.mp.controller.webservice;

import com.vpu.mp.service.cxf.CXFDemoService;
import com.vpu.mp.service.cxf.CXFDemoServiceGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/service/webService")
public class WebServiceController {

    @Autowired
    private CXFDemoServiceGet cxfDemoServiceGet;

    /**
     * 示例接口获取
     */
    @PostMapping("/get")
    public void testWebServiceGet(){

    }

}
