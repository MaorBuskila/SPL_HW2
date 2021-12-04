package bgu.mics.application.objects;

import bgu.mics.Event;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing a single GPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class GPU {

    /**
     * Enum representing the type of the GPU.
     */
    enum Type {RTX3090, RTX2080, GTX1080}

    private Type type;
    private Model model;
    private Cluster cluster;
    private ConcurrentHashMap <Event, Model >  modelEvents;
    private ConcurrentHashMap<Integer, DataBatch> data; //seprated model data to databatch


    public GPU(Type type, Model model, Cluster cluster){
        this.type = type;
        this.model = model;
        this.cluster = cluster;

    }

    private void separateData(Model model, ConcurrentHashMap<Integer,DataBatch> data){

    }

    private void sendUnprocessedDataBatchToCluster(Cluster cluster){

    }
    private void getProcessedDataBatch(Cluster cluster){

    }

    private void trainDataBatchModel(DataBatch dataBatch){

    }



}
