package com.bjfl.galaxymessage.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageUtil {

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
            return false;
        }

        if (msgArr[1] + msgArr[2] != msgArr.length) {
            return false;
        }

        int sum = 0;

        for (int i = 0; i < msgArr.length-2; i++) {
            sum += msgArr[i];
        }

        if (sum % 256 == msgArr[msgArr.length-2]) {
            return true;
        }else {
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

    public static boolean notExpired(long timestamp, long expireTime) {
        return System.currentTimeMillis() <= timestamp + expireTime;
    }

}