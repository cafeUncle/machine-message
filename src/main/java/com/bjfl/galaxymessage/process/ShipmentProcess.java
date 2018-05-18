package com.bjfl.galaxymessage.process;

import com.bjfl.galaxymessage.messages.Message;
import org.springframework.stereotype.Service;

@Service
public class ShipmentProcess {

    // 注入 mqtt的通信，及下发的ctx。根据返回状态选择性使用。

    public void deal(Message msg) {

    }
}
