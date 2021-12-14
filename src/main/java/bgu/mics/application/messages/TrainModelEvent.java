package bgu.mics.application.messages;

public class TrainModelEvent {

    private String senderName;

    public TrainModelEvent(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderName() {
        return senderName;
    }

}
