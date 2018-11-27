package com.icefamer.productserver.vo;

import lombok.Data;

@Data
public class ResultVO<T> {
    /**
    * 错误码，正常返回0
    */
    private Integer code;
    /**
    * 提示信息
    */
    private String msg;
    /**
     * 内容
     */
    private T data;
}
