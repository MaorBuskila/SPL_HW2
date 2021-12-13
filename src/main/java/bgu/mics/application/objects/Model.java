package bgu.mics.application.objects;

/**
 * Passive object representing a Deep Learning model.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Model {
    private String name;
    private Student student1;

    public String getName() {
        return name;
    }

    enum Status {
        PreTrained, Training,Trained,Tested
    }
    enum Result {
        None,Good,Bad
    }
    private Data data;
    private Result res;
    private Status status;

    public Model(String name, Data data,Student student1) {
        this.name=name;
        this.data = data;
        this.student1=student1;
        this.res=Result.None;
        this.status=Status.PreTrained;

    }

    public Data getData() {
        return data;
    }

    // private int id;
   // public int getId() {
    //    return id;
    //}
}
