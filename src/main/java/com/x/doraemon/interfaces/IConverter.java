package com.x.doraemon.interfaces;

/**
 * @Desc
 * @Date 2020/10/15 00:18
 * @Author AD
 */
public interface IConverter<FROM, TO> {

    TO convert(FROM from) throws Exception;

    FROM reconvert(TO to) throws Exception;

}
