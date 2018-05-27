package com.bjfl.galaxymessage.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MessageUtil {

    private static final Logger logger = LoggerFactory.getLogger(MessageUtil.class);

    public static String intsToHexString(int[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static List<Integer> strTo16(String s) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            list.add((int) s.charAt(i));
        }
        return list;
    }

    public static boolean validate(int[] msgArr) {

        if(msgArr[0] != 0x8E || msgArr[msgArr.length-1] != 0xED) {
            logger.error("应该为：8e,ed" + ",实际为：" + msgArr[0] + "," + msgArr[msgArr.length-1] + "," + Arrays.toString(msgArr));
            return false;
        }

        if (msgArr[1] * 256 + msgArr[2] != msgArr.length) {
            logger.error("应该为："+ (msgArr[1] * 256 + msgArr[2]) + ",实际为" + msgArr.length + "," + Arrays.toString(msgArr));
            return false;
        }

        int sum = 0;

        for (int i = 0; i < msgArr.length-2; i++) {
            sum += msgArr[i];
        }

        if (sum % 256 == msgArr[msgArr.length-2]) {
            return true;
        }else {
            logger.error("应该为：" + msgArr[msgArr.length-2] + ",实际为：" + sum % 256 + "," + Arrays.toString(msgArr));
            return false;
        }
    }

    /**
     * 字符串转换成为16进制(无需Unicode编码)
     * @param str
     * @return
     */
    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            // sb.append(' ');
        }
        return sb.toString().trim();
    }

    public static byte[] intArrToByteArr(int[] arr) {
        byte[] bytes = new byte[arr.length];
        for (int i = 0; i < arr.length; i++) {
            bytes[i] = (byte) arr[i];
        }
        return bytes;
    }

    public static List<Integer> generateEmptyCode(int length) {
        return IntStream.range(0, length).mapToObj(a -> 0x00).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println(generateEmptyCode(8).toString());
    }

}