package bgu.mics.application.objects;

import java.util.Queue;

/**
 * Passive object representing a single CPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class CPU {

    private int numberOfCores;
    private Queue<DataBatch> unprocessedQueue;
    private Cluster cluster;
    private boolean isBusy = false;
    //private DataBatch dataBatch;
    private Queue<DataBatch> processedQueue;


    public CPU(int numberOfCores /**, Cluster cluster */) {
        //super(name);

        this.numberOfCores = numberOfCores;
        this.unprocessedQueue = null;
        this.cluster = Cluster.getInstance();
        cluster.addToCPUS(this);
    }

    /**
     * @return
     * @pre:none
     * @post: return value = @pre head of queue
     */
    public DataBatch getUnprocessed() {
        synchronized (unprocessedQueue) {
            if (!unprocessedQueue.isEmpty()) {
                return unprocessedQueue.poll();
            }
        }
        return null;
    }

    /**
     * @return
     * @pre: dataBatch.isProcessed == false
     * @post: dataBatch.isProcessed == true AND  data.proccesed=@pre data.processed+1000
     */
    public DataBatch process(DataBatch dataBatch) {
//        int x=CPUService.getTicks(); // x=0
        //process... ticks...
        //       isBusy =true;
//        if(dataBatch.getData().getType().equals("Images")){
//            CPUService.
//        }
        dataBatch.process();
//        dataBatch.getData().updateProcessed();
        //    isBusy = false;

        return dataBatch;
    }

    /**
     * @param processedDataBatch
     * @pre: dataBatch.isProcessed == true
     * @post: cluster.processedBatch != null
     */

    public void sendToCluster(DataBatch processedDataBatch) {
        this.cluster.addToProcessed(processedDataBatch);
    }


    /**
     * @return
     * @pre:
     * @post:
     */
    public boolean checkIfBusy() {
        return isBusy;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public int getNumberOfCores() {
        return numberOfCores;
    }
}


