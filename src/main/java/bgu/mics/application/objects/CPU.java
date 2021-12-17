package bgu.mics.application.objects;

/**
 * Passive object representing a single CPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class CPU {

    private int numberOfCores;
    private Cluster cluster;
    private boolean isBusy = false;
    private DataBatch db = null;
    private int ticksFromService;
    private int processingTick;

    //Constructor
    public CPU(int numberOfCores /**, Cluster cluster */) {
        this.numberOfCores = numberOfCores;
        this.cluster = Cluster.getInstance();
        this.processingTick = 0;
        cluster.addToCPUs(this);
    }

    /**
     * @return
     * @pre: dataBatch.isProcessed == false
     * @post: dataBatch.isProcessed == true AND  data.proccesed=@pre data.processed+1000
     */
    //////// MAIN FUNCTION: process the databatch with ticks ////////
    public DataBatch process(DataBatch dataBatch) { // TODO CHANGE TICKINGS
        this.db = dataBatch; //set the db the cpu currently working on
        switch (db.getData().getType()) { // why noy WHILE AND WAIT ?
            case Images:
                processingTick = 32 / numberOfCores * 4 - ticksFromService;
                if (processingTick == 0) {
                    ticksFromService = 0;
                    dataBatch.process();
                    Cluster.getInstance().addCpuTimeUnitUsed(32 / numberOfCores * 4);
                }
                break;
            case Text:
                processingTick = 32 / numberOfCores * 2 - ticksFromService; //TODO Start Tick,not stop checkcc
                if (processingTick == 0) {
                    ticksFromService = 0;
                    dataBatch.process();
                    Cluster.getInstance().addCpuTimeUnitUsed(32 / numberOfCores * 4);


                }
                break;
            case Tabular:
                processingTick = 32 / numberOfCores - ticksFromService;
                if (processingTick == 0) {
                    ticksFromService = 0;
                    dataBatch.process();
                    Cluster.getInstance().addCpuTimeUnitUsed(32 / numberOfCores * 4);

                }
                break;
        }
        dataBatch.getData().updateProcessed();
        this.db = null;
        isBusy = false;
        return dataBatch;
    }

    /////////////////////////////////////////////////////////////////////

    //////////// Getters ///////////

    public Cluster getCluster() {
        return cluster;
    }

    public int getNumberOfCores() {
        return numberOfCores;
    }

    ////////////////////////////////


    // send to cluster processed databatch and then send to relevant GPU //

    /**
     * @param processedDataBatch
     * @pre: dataBatch.isProcessed == true
     * @post: cluster.processedBatch != null
     */
    public void sendToCluster(DataBatch processedDataBatch) {
        this.cluster.sendToGPU(processedDataBatch);
    }
    ///////////////////////////////////////////////////////////

    /**
     * take databatch from cluster
     *
     * public void takeDataBatchCluster()
     * {
     *     if(cluster.
     * }
     *
     */

  //  {
    //    if(!cluster.getNotProccesedData.isEmpty())
   //         DataBatch db=cluster.getNotProccesedData.remove(0);
     //       db.process();
    //}
    public boolean checkIfBusy() {
        if (db != null)
            isBusy = true;
        else
            isBusy = false;
        return isBusy;
    }

    public void updateTick(int tick) {
        this.ticksFromService = tick;
    }

//    public void updateProcessingTick() {
//        processingTick++;
//    }
}


