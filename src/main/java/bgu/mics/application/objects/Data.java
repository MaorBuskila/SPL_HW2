package bgu.mics.application.objects;

/**
 * Passive object representing a data used by a model.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Data {

    private Type type;
    private int processed;
    private int size;

    public Data(String sType, int size) {
        if (sType.equals("Images"))
            this.type = Type.Images;
        if (sType.equals("Text"))
            this.type = Type.Text;
        if (sType.equals("Tabular"))
            this.type = Type.Tabular;
        this.size = size;
        this.processed = 0;
    }

    public int getProcessed() {
        return processed;
    }

    public void updateProcessed() {
        processed += 1000;

    }

    /**
     * Enum representing the Data type.
     */
    enum Type {
        Images, Text, Tabular
    }

    public Type getType() {
        return type;
    }

    public int getSize() {
        return size;
    }
}
