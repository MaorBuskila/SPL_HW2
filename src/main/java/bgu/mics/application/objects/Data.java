package bgu.mics.application.objects;

import bgu.mics.MicroService;

/**
 * Passive object representing a data used by a model.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Data {

    private Type type;
    private int processed;
    private int size;

    public Data(Type type, int processed, int size) {
        this.type = type;
        this.processed = processed;
        this.size = size;
    }

    public int getProccessed() {
        return processed;
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
