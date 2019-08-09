package com.njupt.passbook.service.impl;

import com.alibaba.fastjson.JSON;
import com.njupt.passbook.constant.ErrorCode;
import com.njupt.passbook.constant.constants;
import com.njupt.passbook.dao.MerchantsDao;
import com.njupt.passbook.entity.Merchants;
import com.njupt.passbook.service.IMerchantsServ;
import com.njupt.passbook.vo.CreatMerchantsRequest;
import com.njupt.passbook.vo.CreateMerchantsResponse;
import com.njupt.passbook.vo.PassTemplate;
import com.njupt.passbook.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <h1>商户服务接口实现</h1>
 *
 * */
@Slf4j
@Service
public class MerchantsServImpl implements IMerchantsServ {

    /** Merchants 数据库接口 */
    private final MerchantsDao merchantsDao;

    /** kafka客户端 */
    private final KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    public MerchantsServImpl(MerchantsDao merchantsDao,
                             KafkaTemplate<String,String> kafkaTemplate){
        this.merchantsDao=merchantsDao;
        this.kafkaTemplate=kafkaTemplate;
    }

    @Override
    @Transactional
    public Response createMerchants(CreatMerchantsRequest request) {

        Response response=new Response();
        CreateMerchantsResponse merchantsResponse=new CreateMerchantsResponse();

        ErrorCode errorCode=request.validate(merchantsDao);
        if (errorCode!=ErrorCode.SUCCESS){
            merchantsResponse.setId(-1);
            response.setErrorCode(errorCode.getCode());
            response.setErrorMsg(errorCode.getDesc());
        }else{
            merchantsResponse.setId(merchantsDao.save(request.toMerchants()).getId());
        }

        response.setData(merchantsResponse);

        return response;
    }

    @Override
    public Response buildMerchantsInfoById(Integer id) {

        Response response=new Response();

        Merchants merchants=merchantsDao.findById(id);
        if (null==merchants){
            response.setErrorCode(ErrorCode.MERCHANTS_NOT_EXIST.getCode());
            response.setErrorMsg(ErrorCode.MERCHANTS_NOT_EXIST.getDesc());
        }

        response.setData(merchants);

        return response;
    }

    @Override
    public Response dropPassTemplate(PassTemplate template) {
        Response response=new Response();
        ErrorCode errorCode=template.vaildate(merchantsDao);

        if (errorCode!=ErrorCode.SUCCESS){
            response.setErrorCode(errorCode.getCode());
            response.setErrorMsg(errorCode.getDesc());
        }else{
            String passTemplate= JSON.toJSONString(template);
            kafkaTemplate.send(
                    constants.TEMPLATE_TOPIC,
                    constants.TEMPLATE_TOPIC,
                    passTemplate
            );
            log.info("DropPassTemplates:{}",passTemplate);
        }

        return response;
    }
}
