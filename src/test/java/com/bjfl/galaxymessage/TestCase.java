package com.bjfl.galaxymessage;

import com.alibaba.fastjson.JSON;
import com.bjfl.galaxymessage.models.request.ForthShipmentRequest;
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

        ForthShipmentRequest forthShipmentRequest = JSON.parseObject(jsonStr, ForthShipmentRequest.class);
        System.out.println(forthShipmentRequest);
        System.out.println(forthShipmentRequest.notExpired());
    }
}
