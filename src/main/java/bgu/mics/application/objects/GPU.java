package bgu.mics.application.objects;

import bgu.mics.Event;
import bgu.mics.application.services.GPUService;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing a single GPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class GPU extends GPUService {

    /**
     * Enum representing the type of the GPU.
     */
    enum Type {RTX3090, RTX2080, GTX1080}

    private Type type;
    private Model model;
    private Cluster cluster;
    private ConcurrentHashMap <Event, Model >  modelEvents;
    private ConcurrentHashMap<Integer, DataBatch> data; //seprated model data to databatch


    public GPU(Type type, Model model, Cluster cluster, String name){
        super(name);
        this.type = type;
        this.model = model;
        this.cluster = cluster;

    }

    public void separateData(Model model, ConcurrentHashMap<Integer,DataBatch> data){

    }

    public void sendUnprocessedDataBatchToCluster(Cluster cluster){
        //fuction to decide how to send
        //cluster.add
    }
    public void getProcessedDataBatch(Cluster cluster){

    }

    public void trainDataBatchModel(DataBatch dataBatch){

    }



}
