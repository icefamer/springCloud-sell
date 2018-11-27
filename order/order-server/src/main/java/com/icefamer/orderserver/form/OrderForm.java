package com.icefamer.orderserver.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OrderForm {
    @NotEmpty(message = "姓名，必填")
    private String name;

    @NotEmpty(message = "手机号，必填")
    private String phone;

    @NotEmpty(message = "地址，必填")
    private String address;

    @NotEmpty(message = "openId，必填")
    private String openId;

    @NotEmpty(message = "购物车不能为空")
    private String items;
}
