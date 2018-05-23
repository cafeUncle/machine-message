package com.bjfl.galaxymessage.util;

import java.util.Map;

public class JsonUtil {

    public Map<String, Object> extractJsonStringToMap(String jsonStr){
        return null;
    }

    public static void main(String[] args) {
        //json字符串-简单对象型
        String  jsonSimpleObjectStr = "{\"studentName\":\"lily\",\"studentAge\":12}";

        //json字符串-数组类型
        String  JsonArrayObjectStr = "[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]";

        //复杂格式json字符串
        String  complexJsonStr = "{\"teacherName\":\"crystall\",\"teacherAge\":27,\"course\":{\"courseName\":\"english\",\"code\":1270},\"students\":[{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]}";


    }
}
