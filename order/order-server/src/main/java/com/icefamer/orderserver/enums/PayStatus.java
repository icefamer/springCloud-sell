package com.icefamer.orderserver.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayStatus {

    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功")
    ;

    private Integer code;
    private String msg;
}
