package com.x.doraemon.util.converters;

import com.x.doraemon.interfaces.IConverter;

/**
 * @Desc
 * @Date 2021/2/1 17:50
 * @Author AD
 */
public class ByteHexConverter implements IConverter<Byte, String> {

    @Override
    public String convert(Byte b) throws Exception {
        String hex = Integer.toHexString(b & 0xFF).toUpperCase().trim();
        return hex.length() < 2 ? "0" + hex : hex;
    }

    @Override
    public Byte reconvert(String hex) throws Exception {
        if (hex.length() > 2) {
            hex = hex.substring(0, 2);
        }
        String fix = hex.length() % 2 == 0 ? hex : "0" + hex;
        int i = Integer.parseInt(fix, 16);
        return (byte) i;
    }

}
