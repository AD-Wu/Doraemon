package com.x.doraemon.util.converters;

import com.x.doraemon.interfaces.IConverter;
import com.x.doraemon.util.ArrayHelper;
import com.x.doraemon.util.ConvertHelper;
import com.x.doraemon.util.LogHelper;
import com.x.doraemon.util.StringHelper;

import java.io.*;

import static com.x.doraemon.util.ArrayHelper.EMPTY_BYTE;

/**
 * @Desc
 * @Date 2021/2/1 17:39
 * @Author AD
 */
public class BytesObjectConverter implements IConverter<byte[], Serializable> {

    @Override
    public Serializable convert(byte[] bytes) throws Exception {
        if (ArrayHelper.isEmpty(bytes)) {
            return null;
        }
        try (ByteArrayInputStream bsIn = new ByteArrayInputStream(bytes);
             ObjectInputStream in = new ObjectInputStream(bsIn)) {
            Object o = in.readObject();
            return (Serializable) o;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public byte[] reconvert(Serializable o) throws Exception {
        if (o == null) {
            return EMPTY_BYTE;
        } else {
            try (ByteArrayOutputStream bsOut = new ByteArrayOutputStream();
                 ObjectOutputStream out = new ObjectOutputStream(bsOut)) {
                out.writeObject(o);
                out.flush();
                return bsOut.toByteArray();
            } catch (IOException e) {
                LogHelper.getLogger(ConvertHelper.class).error(StringHelper.getExceptionTrace(e));
                return EMPTY_BYTE;
            }
        }
    }

}
