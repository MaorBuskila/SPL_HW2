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
    private int ticksFromService = 0;
    private int processingTick;


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
    public DataBatch process(DataBatch dataBatch) {
        this.db = dataBatch;
        switch (db.getData().getType()) {
            case Images:
                processingTick = 32 / numberOfCores * 4 - ticksFromService;
                if (processingTick == 0) {
                    ticksFromService = 0;
                    dataBatch.process();
                }
                break;
            case Text:
                processingTick = 32 / numberOfCores * 2 - ticksFromService; //TODO Start Tick,not stop checkc
                if (processingTick == 0) {
                    ticksFromService = 0;
                    dataBatch.process();

                }
                break;
            case Tabular:
                processingTick = 32 / numberOfCores - ticksFromService;
                if (processingTick == 0) {
                    ticksFromService = 0;
                    dataBatch.process();
                }
                break;
        }
        dataBatch.getData().updateProcessed();
        this.db = null;
        isBusy = false;
        return dataBatch;
    }

    //////////// Getters ///////////

    public Cluster getCluster() {
        return cluster;
    }

    public int getNumberOfCores() {
        return numberOfCores;
    }

    ////////////////////////////////

    /**
     * @param processedDataBatch
     * @pre: dataBatch.isProcessed == true
     * @post: cluster.processedBatch != null
     */
    public void sendToCluster(DataBatch processedDataBatch) {
        this.cluster.sendToGPU(processedDataBatch);
    }

    public boolean checkIfBusy() {
        if (db != null)
            isBusy = true;
        else
            isBusy = false;
        return isBusy;
    }

    public void updateTick() {
        this.ticksFromService++;
    }

    public void updateProcessingTick() {
        processingTick++;
    }
}


