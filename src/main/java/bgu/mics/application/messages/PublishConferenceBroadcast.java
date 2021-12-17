package bgu.mics.application.messages;

import bgu.mics.Broadcast;

public class PublishConferenceBroadcast implements Broadcast {
    private String senderName;
    private static int priority = 2;
    public PublishConferenceBroadcast(String senderName){
        this.senderName=senderName;

    }
    public String getSenderName() {
        return senderName;
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
