package com.huxiaobai.adapter;

/**
 * 项  目 :  BaseRecyclerAdapter
 * 包  名 :  com.huxiaobai.adapter
 * 类  名 :  ${CLASS_NAME}
 * 作  者 :  胡庆岭
 * 时  间 : 2019/1/2
 * 描  述 :  ${TODO}
 *
 * @author ：
 */
public class DataUtils {
    public static <T>T checkDataNull(T t){
        if (t==null){
            throw new  NullPointerException("data not null!") ;
        }
        return t;
    }
}
