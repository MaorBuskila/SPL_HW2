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

    public Result getRes() {
        return res;
    }

    public void setRes(String res) {
        if(res=="Good")
            this.res = Result.Good;
        else if (res=="Bad")
            this.res=Result.Bad;
        else
            this.res=Result.None;
    }

    public Student getStudent() {
        return student;
    }

    enum Status {
        PreTrained, Training, Trained, Tested
    }

    public String getStatus() {
        if (status == Status.PreTrained)
            return "PreTrained";

        if (status == Status.Training)
            return "PreTrained";

        if (status == Status.Trained)
            return "PreTrained";

        else
            return "Tested";
    }

    public void setStatus(String sStatus) {

        if (sStatus.equals("PreTrained"))
            status = Status.PreTrained;
        if (sStatus.equals("Training"))
            status = Status.Training;
        if (sStatus.equals("Trained"))
            status = Status.Trained;
        else
            status = Status.Tested;

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
    public boolean isGood()
    {
        return this.res==Result.Good;
    }


    public String getName() {
        return name;
    }

    public Data getData() {
        return data;
    }
}
