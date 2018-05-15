package com.bjfl.galaxymessage.util;

public class MessageUtil {

    public static String bytesToHexString(byte[] bytes,String offset) {
        String str="";
        if(offset!=null){
            str = new String(bytes,Integer.parseInt(offset),5);//这个方法可以精确的截取字符串
        }else{
            str= new String(bytes);//普通的字符串构造方法
        }
        return str;
    }

    public static String intsToHexString2(int[] src) {
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

}