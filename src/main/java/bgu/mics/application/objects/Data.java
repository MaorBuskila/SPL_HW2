package bgu.mics.application.objects;

/**
 * Passive object representing a data used by a model.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Data {

    private Type type;
    private int processed;
    private int size;

    // Enum representing the Data type.
    enum Type {
        Images, Text, Tabular
    }

    //Constructor
    public Data(String sType, int size) {
        if (sType.equals("images"))
            this.type = Type.Images;
        if (sType.equals("text"))
            this.type = Type.Text;
        if (sType.equals("tabular"))
            this.type = Type.Tabular;
        this.size = size;
        this.processed = 0;
    }


    public void updateProcessed() {
        processed += 1000;
    }

    //////////////// Getters //////////////
    public Type getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public int getProcessed() {
        return processed;
    }
    ///////////////////////////////////////
}
