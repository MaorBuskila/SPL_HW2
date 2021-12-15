package bgu.mics.application.messages;

import bgu.mics.Broadcast;

public class TickBroadCast implements Broadcast {

    private int tick;

    public TickBroadCast(int tick) {
        this.tick = tick;
    }

    public int getTick() {
        return tick;
    }


//    public String getSenderId() {
//        return senderId;
//    }
}
