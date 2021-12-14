package bgu.mics.application.messages;

import bgu.mics.Broadcast;

public class TickBroadCast implements Broadcast {

//    private String senderId;
    private int tick;



    public TickBroadCast(String senderId, int tick) {
        this.tick = tick;
//      this.senderId = senderId;
    }

    public int getTick() {
        return tick;
    }


//    public String getSenderId() {
//        return senderId;
//    }
}
