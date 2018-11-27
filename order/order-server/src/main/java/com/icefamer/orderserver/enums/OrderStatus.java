package com.icefamer.orderserver.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    NEW(0, "新订单"),
    FINISHED(1, "完结订单"),
    CANCEL(2, "取消订单")
    ;

    private Integer code;
    private String msg;
}
