package com.njupt.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h2>创建商户响应对象</h2>
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMerchantsResponse {

    /** 商户id:创建失败则为-1 返回给商户一个id让商户记住，以后就是这个id了*/
    private Integer id;
}
