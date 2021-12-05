package bgu.mics.application.objects;

/**
 * Passive object representing a Deep Learning model.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Model {
    private int id;
    private Data data;
    public int getId() {
        return id;
    }

    public Data getData() {
        return data;
    }
}
