package bgu.mics.application.messages;

import bgu.mics.Broadcast;

public class TerminateBroadcast implements Broadcast {

    private static int priority = 0;
    private String senderName;
    public TerminateBroadcast() {
    }
    public int getPriority()
    {
        return this.priority;
    }



}