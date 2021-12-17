package bgu.mics.application.messages;

public class PublishConferenceBroadcast {
    private String senderName;
    private static int priority = 2;
    public PublishConferenceBroadcast(String senderName){
        this.senderName=senderName;

    }
    public String getSenderName() {
        return senderName;
    }
}
