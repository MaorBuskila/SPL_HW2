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
=======
import bgu.mics.Event;
import bgu.mics.application.objects.Model;

public class TrainModelEvent implements Event<Model> {

    private String senderName;
    private Model model;


    public TrainModelEvent(Model model, String senderName) {
        this.senderName = senderName;
        this.model = model;
    }

    public Model getModel() {
        return model;
    }
    public String getSenderName() {
        return senderName;
    }
}
>>>>>>> beta
