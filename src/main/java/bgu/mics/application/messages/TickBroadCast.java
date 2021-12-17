package bgu.mics.application.messages;

import bgu.mics.Broadcast;

public class TickBroadCast implements Broadcast {
    private final static int priority = 0; //big letters
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

    public static void main(String[] args) {
        String s = new String("");
        s.getClass().getName();
    }

    @Override
    public int getPriority() {
        return priority;
    }

}
