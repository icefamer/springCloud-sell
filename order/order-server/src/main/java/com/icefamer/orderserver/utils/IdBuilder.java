package com.icefamer.orderserver.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class IdBuilder {
    public static String orderIdBuild(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String dateString = formatter.format(new Date());
        return dateString+(new Random().nextInt(8999)+1000);
    }
}
