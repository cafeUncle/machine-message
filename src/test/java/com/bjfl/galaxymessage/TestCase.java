package com.bjfl.galaxymessage;

import com.alibaba.fastjson.JSON;
import com.bjfl.galaxymessage.models.request.FourthShipmentRequest;
import org.junit.Test;

public class TestCase {

    @Test
    public void test1() {
        String jsonStr = "{\n" +
                "     \"theme\":\"4p.push/15978\",\n" +
                "     \"machineNo\" : \"129(1)test\",\n" +
                "     \"orderId\" : 15978,\n" +
                "     \"position\" : \"1\",\n" +
                "     \"xAxis\" : 50,\n" +
                "     \"yAxis\" : 50,\n" +
                "     \"zAxis\" : 50,\n" +
                "     \"timestamp\" : 1532740617199,\n" +
                "     \"expireTime\" : 30\n" +
                " }";

        FourthShipmentRequest fourthShipmentRequest = JSON.parseObject(jsonStr, FourthShipmentRequest.class);
        System.out.println(fourthShipmentRequest);
        System.out.println(fourthShipmentRequest.notExpired());
    }
}
