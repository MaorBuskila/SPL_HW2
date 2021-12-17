package bgu.mics.application.objects;

import java.util.Vector;

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


    public Type getType() {
        return type;
    }

    private Type type;
    private Model model;
    private Cluster cluster;
    private int trainingTime;
    private Vector<DataBatch> allDataBatches; //seprated model data to databatch
    private int totalCurrentModelTrained;
    private Vector<DataBatch> vRam;
    private int currentProcessInVram;
    private int ticksFromService;
    private int totalTicks;

    public GPU(String sType) {
        super();
        vRam = new Vector<>();
        if (sType.equals("RTX3090")) {
            this.type = Type.RTX3090;
            vRam.setSize(32);
        }
        if (sType.equals("RTX2080")) {
            this.type = Type.RTX2080;
            vRam.setSize(16);
        }
        if (sType.equals("GTX1080")) {
            this.type = Type.GTX1080;
            vRam.setSize(8);
        }
        this.model = null;
        this.cluster = Cluster.getInstance();
        cluster.addToGPUS(this);
        currentProcessInVram = 0;
        ticksFromService=0;
        totalCurrentModelTrained = 0;
    }

    /////////////////Getters///////////////////

    public Vector<DataBatch> getAllDataBatches() {
        return allDataBatches;
    }

    public Vector<DataBatch> getvRam() {
        return vRam;
    }

    public int getCurrentProcessInVram() {
        return currentProcessInVram;
    }

    ////////////////////////////////////////////

    /**
     * @return
     * @pre:none
     * @post: size.Vector<DataBatch>*1000  = Data.size
     */
    public void divide(Data data) {
        allDataBatches = new Vector<>();

        for (int i = 0; i < data.getSize(); i += 1000) {
            DataBatch db = new DataBatch(data, i);
            allDataBatches.addElement(db);

        }
        int size = data.getSize() / 1000; //Todo: check if we can to assume its only divine in 1000
    }


    //////////////// Send to Cluster ///////////

    /**
     * @return
     * @pre: none
     * @post: cluster.unProcessedData.contain()
     */
    public void sendUnprocessedDataBatchToCluster(DataBatch db) {
//      Decide how to send - ALREADY DONE IN GPU SERVICE!
        cluster.addToUnprocessedMap(db, this);
    }

    ////////////////////////////////////////////

    /////////////// Get Processed DataBatch to VRAM ////////////////////

    /**
     * @return
     * @pre: cluster.getProcessDataBatch != null
     * @post: vram contains @pre head of queue.
     */
    public void reciveProcessedDataBatch(DataBatch proDB) {
        vRam.add(proDB);
        currentProcessInVram += 1;
        notifyAll();
    }
    public void updateTick(int tick) {
        this.ticksFromService = tick;
    }

    /////////////////////////////////////////////////////////////////


    ////////////////////////Train the processed Databatch ////////////

    /**
     * @return
     * @pre:the databatch is untrained and processed
     * @post: databatch is trained .
     */
    public void trainDataBatchModel() {

        while (model.getData().getProcessed() < model.getData().getSize()) {
            //need to synchorized vRAM ?
            while (vRam.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            DataBatch dataBatch = vRam.remove(0);
            switch (this.type){
                case RTX3090:
                    trainingTime = 1 - ticksFromService;
                    if (trainingTime == 0){
                        trainFunction(dataBatch);
                    }
                case RTX2080:
                    trainingTime = 2 - ticksFromService;
                    if (trainingTime == 0){
                        trainFunction(dataBatch);
                    }
                case GTX1080:
                    trainingTime = 4 - ticksFromService;
                    if (trainingTime == 0){
                        trainFunction(dataBatch);
                    }



            }



            //training...
//            if(model.getStatus()=="PreTrained")
//                model.setStatus("Training");
//            if(this.getType()==Type.RTX3090) {
//                int trainingTime=1;
//            }
//            else if (this.getType()==Type.RTX2080) {
//                int trainingTime=2;
//            }
//            else {
//                int trainingTime=4;
//            }
//            this.ticksFromService=0;
//            dataBatch.train();
        }

    }
    public void trainFunction(DataBatch dataBatch){
        ticksFromService =0;
        vRam.firstElement().train();
        vRam.removeElementAt(0);
        dataBatch.getData().updateProcessed();
        currentProcessInVram -= 1;
        totalCurrentModelTrained +=1;
    }

    public boolean finishTrain(){
        if(totalCurrentModelTrained == allDataBatches.size()) {
            totalCurrentModelTrained = 0;
            return true;
        }
        else
            return false;
    }
    ///////////////////////////////////////////////////////////////////


}
