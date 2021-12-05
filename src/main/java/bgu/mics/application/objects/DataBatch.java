package bgu.mics.application.objects;

/**
 * Passive object representing a data used by a model.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */

public class DataBatch  {
    private int id;
    private Data data;

    public boolean isProcessed() {
        return processed;
    }
    public Data getData()
    {
        return data;
    }

    private boolean processed = false;

    public void process() {
        if (!processed){
            processed = true;
        }
        else{
            System.out.println("already processed!");
        }
    }
}
