package bgu.mics.application.objects;

import bgu.mics.Event;
import bgu.mics.application.services.CPUService;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing a single CPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class CPU extends CPUService{

    private int numberOFCores;
    private Queue <DataBatch> unprocessedQueue;
    private Cluster cluster;
    private boolean isBusy = false;
    private DataBatch dataBatch;
    private Queue <DataBatch> processedQueue;



    public CPU(int numberOFCores, Queue <DataBatch> queue, Cluster cluster,String name) {
        super(name);
        this.numberOFCores = numberOFCores;
        this.unprocessedQueue = queue;
        this.cluster = cluster;
    }

    /**
     * @pre:none
     * @post: return value = @pre head of queue
     * @return
     */
    public DataBatch getUnprocessed() {
        if (!unprocessedQueue.isEmpty()) {
            return unprocessedQueue.remove();
            //    ProcessAndSendToCluster(dataBatch);
        }
        return null;
    }
    /**
     * @pre: dataBatch.isProcessed == false
     * @post: dataBatch.isProcessed == true
     */
    public void process( DataBatch dataBatch) {
//        int x=CPUService.getTicks(); // x=0
        //process... ticks...
 //       isBusy =true;
//        if(dataBatch.getData().getType().equals("Images")){
//            CPUService.
//        }

        dataBatch.process();
    //    isBusy = false;

    }

    /**
     *
     * @pre: dataBatch.isProcessed == true
     * @post: cluster.processedBatch != null
     * @param processedDataBatch
     */

    public void sendToCluster (DataBatch processedDataBatch){
        this.cluster.addToProcessed(processedDataBatch);
    }


    /**
     * @pre:
     * @post:
     * @return
     */
    public boolean checkIfBusy (){
        return isBusy;
    }
}


