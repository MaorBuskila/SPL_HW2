package bgu.mics.application.objects;

import bgu.mics.MessageBusImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bgu.mics.application.objects.GPU.Type.RTX3090;
import static org.junit.jupiter.api.Assertions.*;

class GPUTest {
    private MessageBusImpl msgbus;
    private GPU gpu;
    private Cluster cluster;
    private Model model;
    private Data data;
    private  Student student;
    @BeforeEach
    void setUp() {
        msgbus = MessageBusImpl.getInstance();
        cluster = Cluster.getInstance();
        data =  new Data("Images" , 20000);
        student = new Student("Student1" , "bgu" , "PhD");
        model = new Model("model1" ,data , student );
        gpu = new GPU("RTX3090");
        msgbus.register(gpu);

    }

    @AfterEach
    void tearDown() {
        msgbus.unregister(gpu);

    }
    @Test
    void divide() {
        //Data data=  new Data("Images" , 20000);
        gpu.divide(data);
        int x = gpu.getDataMap().get(model).size();
        assertEquals(x*1000,data.getSize());
    }



    @Test
    void sendUnprocessedDataBatchToCluster() {
        DataBatch dataBatch = new DataBatch(model.getData(),0);
        gpu.sendUnprocessedDataBatchToCluster(dataBatch);
        assertTrue(cluster.getUnProcessedDataBatch().contains(dataBatch));

    }

    @Test
    void getProcessedDataBatch() {
        //assertThrows()
        DataBatch dataBatch = new DataBatch(model.getData(),0);
        dataBatch.process();
        cluster.addToProcessed(dataBatch);
        assertTrue(gpu.getvRam().contains(dataBatch));

    }

//    @Test
//    void trainDataBatchModel() {
//        assertTrue(gpu.getvRam().isEmpty());
//        DataBatch dataBatch = new DataBatch(model.getData(),0);
//        dataBatch.process();
//        cluster.addToProcessed(dataBatch);
//        gpu.reciveProcessedDataBatch();
//        gpu.trainDataBatch(dataBatch);
//        assertFalse(VRAMArray.contains(dataBatch));
//
//    }


    @Test
    void testSendUnprocessedDataBatchToCluster() {
    }

    @Test
    void reciveProcessedDataBatch() {
    }

    @Test
    void testTrainDataBatchModel() {
    }
}