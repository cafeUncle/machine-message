package com.bjfl.galaxymessage.receiver;

import com.bjfl.galaxymessage.message.Message;

public class CellStatusMessage extends Message {

    public CellStatusMessage() {
    }

    public CellStatusMessage(int[] ints) {
        super(ints);
    }
}
