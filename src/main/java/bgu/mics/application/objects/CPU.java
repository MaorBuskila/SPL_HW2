package bgu.mics.application.objects;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing a single CPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class CPU {

    private int numberOFCores;
    private ConcurrentHashMap <Integer , DataBatch> db;
    private Cluster cluster;
    private boolean isBusy = false;


    public CPU(int numberOFCores, ConcurrentHashMap<Integer, DataBatch> dataBatch, Cluster cluster) {
        this.numberOFCores = numberOFCores;
        this.db = dataBatch;
        this.cluster = cluster;
    }

    private DataBatch getUnprocessedDataBatch(ConcurrentHashMap<Integer, DataBatch> dataBatch){
        return null;
    }

    private void Process (ConcurrentHashMap dataBatch){
    }

    private DataBatch sendProcessedDataBatch(ConcurrentHashMap<Integer,DataBatch> dataBatch){
        return null;
    }

    private boolean isBusy (int numberOFCores){ return false; //Todo: mabey should be public and implement the getUnprocessedDataBatch from the cluster and the cluster should push it to the cpu when he is not busy
    }



}


