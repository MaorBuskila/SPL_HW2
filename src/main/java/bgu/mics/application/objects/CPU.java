package bgu.mics.application.objects;

/**
 * Passive object representing a single CPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class CPU {

    private String name;
    private int numberOfCores;
    private Cluster cluster;
    private boolean isBusy = false;
    private DataBatch db = null;
    private int ticksFromService;
    private int processingTick;

    //Constructor
    public CPU(String name, int numberOfCores /**, Cluster cluster */) {
        this.name = name;
        this.numberOfCores = numberOfCores;
        this.cluster = Cluster.getInstance();
        this.processingTick = Integer.MAX_VALUE;
        this.ticksFromService = 0;
        cluster.addToCPUs(this);
    }

    /**
     * @return
     * @pre: dataBatch.isProcessed == false
     * @post: dataBatch.isProcessed == true AND  data.proccesed=@pre data.processed+1000
     */
    //////// MAIN FUNCTION: process the databatch with ticks ////////
    public DataBatch process2() { // TODO CHANGE TICKINGS
        System.out.println("trying to Process");
        if (isBusy()) {
                try {
                    this.db = cluster.getUnProcessedQueue(this).take();
                } catch (InterruptedException e) {
                    System.out.println("nothing in " + getName() + " queue");
                }
            }
        else{
            System.out.println("im busy");
        }
        switch (db.getData().getType()) { // why noy WHILE AND WAIT ?
            case Images:
                processingTick = 32 / numberOfCores * 4 - ticksFromService;

                if (processingTick == 0) {
                    ticksFromService = 0;
                    db.process();
                    System.out.println(getName() + " is done processing dataBatch number " + getProcessingDataBatch().getData().getProcessed());
                    db.getData().updateProcessed();
                    db = null;
                    isBusy =false;
                    Cluster.getInstance().addCpuTimeUnitUsed(32 / numberOfCores * 4);
                }
                break;
            case Text:
                processingTick = 32 / numberOfCores * 2 - ticksFromService;
                if (processingTick == 0) {
                    ticksFromService = 0;
                    db.process();
                    doneProcessThisBatch(db);
                    Cluster.getInstance().addCpuTimeUnitUsed(32 / numberOfCores * 4);


                }
                break;
            case Tabular:
                processingTick = 32 / numberOfCores - ticksFromService;
                if (processingTick == 0) {
                    ticksFromService = 0;
                    db.process();
                    doneProcessThisBatch(db);
                    Cluster.getInstance().addCpuTimeUnitUsed(32 / numberOfCores * 4);

                }
                break;
        }
        return db;
    }
    //////// MAIN FUNCTION: process the databatch with ticks ////////
    public  synchronized DataBatch process() {
        //System.out.println("trying to Process");
        if (isBusy()) {
            switch (db.getData().getType()) {
                case Images:
                    processingTick = 32 / numberOfCores * 4 - ticksFromService;
                    System.out.println("processingTick " + processingTick);
                    System.out.println("ticksFromService " + ticksFromService);
                    if (processingTick == 0) {
                        ticksFromService = 0;
                        db.process();
                        doneProcessThisBatch(db);
                        Cluster.getInstance().addCpuTimeUnitUsed(32 / numberOfCores * 4);
                    }
                    break;
                case Text:
                    processingTick = 32 / numberOfCores * 2 - ticksFromService;
                    if (processingTick == 0) {
                        ticksFromService = 0;
                        db.process();
                        doneProcessThisBatch(db);
                        Cluster.getInstance().addCpuTimeUnitUsed(32 / numberOfCores * 4);


                    }
                    break;
                case Tabular:
                    processingTick = 32 / numberOfCores - ticksFromService;
                    if (processingTick == 0) {
                        ticksFromService = 0;
                        db.process();
                        doneProcessThisBatch(db);
                        Cluster.getInstance().addCpuTimeUnitUsed(32 / numberOfCores * 4);

                    }
                    break;
            }

        }
        else{
            try {
                this.db = cluster.getUnProcessedQueue(this).take();
            } catch (InterruptedException e) {
                System.out.println("nothing in " + getName() + " queue");
            }
        }
        return db;
    }


    public  synchronized void doneProcessThisBatch(DataBatch dataBatch) {
        System.out.println(getName() + " is done processing dataBatch number " + getProcessingDataBatch().getData().getProcessed());
        db.getData().updateProcessed();
        db = null;
        isBusy = false;
    }
    /////////////////////////////////////////////////////////////////////




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

    public boolean isBusy() {
     return db!=null;
    }

    public synchronized void updateTick(int tick) {
        ticksFromService = tick;
        process();
    }



    //////////// Getters ///////////

    public Cluster getCluster() {
        return cluster;
    }

    public int getNumberOfCores() {
        return numberOfCores;
    }

    public String getName() {
        return name;
    }

    public DataBatch getProcessingDataBatch() {
        return db;
    }

    ////////////////////////////////
}


