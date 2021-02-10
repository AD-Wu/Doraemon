package com.x.doraemon.util;

import com.x.doraemon.util.converters.BytesObjectConverter;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.StringJoiner;

/**
 * @Desc TODO
 * @Date 2020/9/24 20:16
 * @Author AD
 */
class ConvertsTest {

    private static byte[] bs = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 45};

    String hex = "1234567890abcde";

    @Test
    void arrayToString() {

        String s = ConvertHelper.arrayToString(new int[]{1, 2, 3, 4, 5});
        System.out.println(s);
    }

    @Test
    void byteToHex() {
        String s = ConvertHelper.byteToHex((byte) 15);
        System.out.println(s);
    }

    @Test
    void bytesToHex() {
        String s = ConvertHelper.bytesToHex(bs);
        System.out.println(s);
    }

    @Test
    void bytesToHexWithDelimiter() {
        String s = ConvertHelper.bytesToHexWithDelimiter(bs, "哈哈");
        System.out.println(s);
    }

    @Test
    void hexToBytes() {
        byte[] bytes = ConvertHelper.hexToBytes(null);
        System.out.println(ConvertHelper.bytesToHexWithDelimiter(bytes, "|"));
    }

    @Test
    void hexToByte() {
        String hex = ConvertHelper.byteToHex((byte) 65);
        byte b = ConvertHelper.hexToByte(hex);
        System.out.println("byte=65,hex=" + hex);
        System.out.println("hex=" + hex + ",byte=" + b);
    }

    @Test
    void hexWithDelimiterToBytes() {
        byte[] bs = {1, 2, 3, 4, 98, 76, 65, 54, 43};
        String hex = ConvertHelper.bytesToHexWithDelimiter(bs, "|");
        byte[] bytes = ConvertHelper.hexWithDelimiterToBytes(hex, "|");
        System.out.println("bs=" + Arrays.toString(bs));
        System.out.println("hex=" + hex);
        System.out.println("===============================");
        System.out.println("bytes=" + Arrays.toString(bytes));
    }

    @Test
    void objectToBytes() throws Exception {
        Serial s = new Serial();
        s.setName("AD");
        s.setAge(2);
        s.setSex(true);
        s.setBirthday(LocalDateTime.now());

        BytesObjectConverter converter = new BytesObjectConverter();
        byte[] reconvert = converter.reconvert(s);
        System.out.println(Arrays.toString(reconvert));
        System.out.println(reconvert.length);
        System.out.println(s);
        System.out.println("============================");
        Serializable convert = converter.convert(reconvert);
        System.out.println(convert);
        System.out.println((Serial)convert);
        //byte[] bytes = Converts.objectToBytes(s);
        //System.out.println(Arrays.toString(bytes));
        //System.out.println(bytes.length);
        //System.out.println(s);
        //System.out.println("============================");
        //Serial serial = Converts.bytesToObject(bytes, Serial.class);
        //System.out.println(serial);
    }

    @Test
    void bytesToObject() {
    }

    public static class Serial implements Serializable {

        private String name;

        private int age;

        private boolean sex;

        private LocalDateTime birthday;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public boolean isSex() {
            return sex;
        }

        public void setSex(boolean sex) {
            this.sex = sex;
        }

        public LocalDateTime getBirthday() {
            return birthday;
        }

        public void setBirthday(LocalDateTime birthday) {
            this.birthday = birthday;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Serial.class.getSimpleName() + "[", "]")
                    .add("name='" + name + "'")
                    .add("age=" + age)
                    .add("sex=" + sex)
                    .add("birthday=" + birthday)
                    .toString();
        }

    }

}