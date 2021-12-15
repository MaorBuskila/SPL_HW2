package bgu.mics.application.objects;

/**
 * Passive object representing a Deep Learning model.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Model {
    private String name;
    private Student student;
    private Data data;
    private Result res;
    private Status status;

    enum Status {
        PreTrained, Training, Trained, Tested
    }

    enum Result {
        None, Good, Bad
    }


    //Constructor
    public Model(String name, Data data, Student student) {
        this.name = name;
        this.data = data;
        this.student = student;
        this.res = Result.None;
        this.status = Status.PreTrained;

    }

    public String getName() {
        return name;
    }

    public Data getData() {
        return data;
    }
}
