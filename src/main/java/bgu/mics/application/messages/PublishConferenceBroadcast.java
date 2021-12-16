package bgu.mics.application.messages;

public class PublishConferenceBroadcast {
    private String senderName;
    public PublishConferenceBroadcast(String senderName){
        this.senderName=senderName;

    }
    public String getSenderName() {
        return senderName;
    }
}
