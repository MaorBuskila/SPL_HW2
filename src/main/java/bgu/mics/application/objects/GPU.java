package bgu.mics.application.objects;

import bgu.mics.Event;
import bgu.mics.MessageBus;
import bgu.mics.MessageBusImpl;
import bgu.mics.application.services.GPUService;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing a single GPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class GPU {


    public Vector<DataBatch> getvRam() {
        return vRam;
    }

    public int getCurrentProInVram() {
        return currentProInVram;
    }

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
   // private ConcurrentHashMap <Event, Model >  modelEvents;
    private Vector<DataBatch> allDataBatches; //seprated model data to databatch
    private Vector<DataBatch> vRam;
    private int currentProInVram;


    public GPU(String sType){

        super();
        vRam = new Vector<>();
        if(sType.equals("RTX3090")) {
            this.type = Type.RTX3090;
            vRam.setSize(32);
        }
        if(sType.equals("RTX2080")) {
            this.type = Type.RTX2080;
            vRam.setSize(16);
        }
        if(sType.equals("GTX1080")) {
            this.type = Type.GTX1080;
            vRam.setSize(8);
        }
        this.model = null;
        this.cluster = Cluster.getInstance();
        cluster.addToGPUS(this);
        currentProInVram=0;

    }

    public Vector<DataBatch> getAllDataBatches() {
        return allDataBatches;
    }
//    public void setCluster(Cluster cluster) {
//        this.cluster = cluster;
//    }
    /**
     * @pre:none
     * @post: size.Vector<DataBatch>*1000  = Data.size
     * @return
     */
    public void divide(Data data){
        allDataBatches= new Vector<>();

        for (int i = 0 ; i<data.getSize() ; i+=1000) {
            DataBatch db = new DataBatch(data, i);
            allDataBatches.addElement(db);

        }
        int size = data.getSize()/1000; //Todo: check if we can to assume its only divine in 1000

   }
    /**
     * @pre: none
     * @post: cluster.unProcessedData.contain()
     * @return
     */
    public void sendUnprocessedDataBatchToCluster(DataBatch db){
//        fuction to decide how to send
        cluster.addToUnprocessedBatch(db,this);
    }
    /**
     * @pre: cluster.getProcessDataBatch != null
     * @post:  vram contains @pre head of queue.
     * @return
     */
    public void reciveProcessedDataBatch(DataBatch ProDB){
        //TODO: check if we need to check if vram have space or send unpro db when only we have space in vram like in tigbur.

        vRam.add(ProDB);
        currentProInVram+=1;
        notifyAll();


    }

    /**
     * @pre:the databatch is untrained and processed
     * @post: databatch is trained .
     * @return
     */
    //train
    // we will get the databatch from the vram

    public void trainDataBatchModel(){

        while(model.getData().getProcessed()<model.getData().getSize())
        {
            //need to synchorized vRAM ?
            //try to take nonstop
            while(vRam.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            DataBatch dataBatch=vRam.remove(0);
            //training...
            dataBatch.train();
            dataBatch.getData().updateProcessed();
            currentProInVram-=1;


        }



    }



}
