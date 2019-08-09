package com.njupt.passbook.service;

import com.alibaba.fastjson.JSON;
import com.njupt.passbook.vo.CreatMerchantsRequest;
import com.njupt.passbook.vo.PassTemplate;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * <h1>商户服务测试类</h1>
 * */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MerchantsServTest {

    @Autowired
    private IMerchantsServ merchantsServ;

    @Test
    public void testCreateMerchantsServ(){

        CreatMerchantsRequest request=new CreatMerchantsRequest();
        request.setName("慕课");
        request.setLogoUrl("www.imooc.com");
        request.setBusinessLicenseUrl("www.imooc.com");
        request.setPhone("123456789012");
        request.setAddress("南京市");

        System.out.println(JSON.toJSONString(merchantsServ.createMerchants(request)));

    }

    @Test
    public void testBuildMerchantsInfoById(){
        System.out.println(JSON.toJSONString(merchantsServ.buildMerchantsInfoById(3)));
    }

    @Test
    public void testDropPassTemplate(){

        PassTemplate passTemplate=new PassTemplate();
        passTemplate.setId(3);
        passTemplate.setTitle("title:慕课");
        passTemplate.setSummary("简介：慕课");
        passTemplate.setDesc("详情：慕课");
        passTemplate.setLimit(10000L);
        passTemplate.setHasToken(false);
        passTemplate.setBackground(2);
        passTemplate.setStart(new Date());
        passTemplate.setEnd(DateUtils.addDays(new Date(),10));

        System.out.println(JSON.toJSONString(
                merchantsServ.dropPassTemplate(passTemplate)
        ));
    }
}
