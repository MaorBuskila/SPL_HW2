package bgu.mics.application.objects;

import bgu.mics.Event;
import bgu.mics.application.services.GPUService;

import java.util.Vector;
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
   // private ConcurrentHashMap <Event, Model >  modelEvents;
    private ConcurrentHashMap<Integer,Vector<DataBatch>> dataMap; //seprated model data to databatch
    private Vector<DataBatch> vRam;


    public GPU(String sType, Model model, Cluster cluster, String name){
        super(name);
        if(sType=="RTX3090") {
            this.type = Type.RTX3090;
            vRam.setSize(32);

        }
        if(sType=="RTX2080") {
            this.type = Type.RTX2080;
            vRam.setSize(16);
        }
        if(sType=="GTX1080") {
            this.type = Type.GTX1080;
            vRam.setSize(8);
        }
        this.model = null;
        this.cluster = cluster;
        cluster.addToGPUS(this);

    }
    /**
     * @pre:none
     * @post: size.Vector<DataBatch>*1000  = Data.size
     * @return
     */
    public void separateData(Data data){
        int id = model.getId();
        Vector<DataBatch> v1= new Vector<>();
        for (int i = 0 ; i<data.getSize() ; i+=1000){
            DataBatch db = new DataBatch(data,i);
            v1.add(db);

        }
        dataMap.put(id,v1);
//        int size = data.getSize()/1000; //Todo: check if we can to assume its only divine in 1000


    }
    /**
     * @pre: none
     * @post: cluster.unProcessedData.contain()
     * @return
     */
    public void sendUnprocessedDataBatchToCluster(DataBatch db){
        //fuction to decide how to send
        cluster.addToUnprocessedBatch(db);
        cluster
    }
    /**
     * @pre: cluster.getProcessDataBatch != null
     * @post:  vram contains @pre head of queue.
     * @return
     */
//    public void reciveProcessedDataBatch(){
//        if(cluster.getProcessedDataBatch()!=null){
//
//        //VRAM.add(databatch)
//        }
//
//    }
    /**
     * @pre:the databatch is untrained and processed
     * @post: databatch is trained .
     * @return
     */
    //train
    // we will get the databatch from the vram
    public void trainDataBatchModel(DataBatch dataBatch){
        //training
        dataBatch.train();
        // VRAMarray.remove(dataBatch);


    }



}
