package com.smm.scrapMetal.util;

import com.smm.scrapMetal.tools.CommonException;

public class ExceptionUtil {
    public static CommonException newException(String retCode, String retMsg){
        return new CommonException(retCode, retMsg);
    }

}