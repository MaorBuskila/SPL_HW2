//package bgu.mics.application.objects;
//
//import bgu.mics.MessageBusImpl;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static bgu.mics.application.objects.GPU.Type.RTX3090;
//import static org.junit.jupiter.api.Assertions.*;
//
//class GPUTest {
//    private MessageBusImpl msgbus;
//    private GPU gpu;
//    private Cluster cluster;
//    private Model model;
//    @BeforeEach
//    void setUp() {
//        msgbus = MessageBusImpl.getInstance();
//        cluster = new Cluster();
//        model = new Model("name", );
//        gpu = new GPU("RTX3090", model , cluster , "gpu1");
//        msgbus.register(gpu);
//
//    }
//
//    @AfterEach
//    void tearDown() {
//        msgbus.unregister(gpu);
//
//    }
//
//    @Test
//    void separateData() {
//        Data tmpData = model.getData();
//        int id = model.getId();
//        int x = gpu.getDataMap.getValue(id).size();
//        assertEquals(x*1000,tmpData.getSize());
//
//
//    }
//
//    @Test
//    void sendUnprocessedDataBatchToCluster() {
//        DataBatch dataBatch = new DataBatch(model.getData(),0);
//        gpu.sendUnprocessedDataBatchToCluster(dataBatch);
//        assertTrue(cluster.getUnProcessedDataBatch().contains(dataBatch));
//
//    }
//
//    @Test
//    void getProcessedDataBatch() {
//        //assertThrows()
//        DataBatch dataBatch = new DataBatch(model.getData(),0);
//        dataBatch.process();
//        cluster.addToProcessed(dataBatch);
//        assertTrue(gpu.getVramArray().contains(dataBatch));
//
//    }
//
//    @Test
//    void trainDataBatchModel() {
//        assertTrue(VRAMArray.isEmpty());
//        DataBatch dataBatch = new DataBatch(model.getData(),0);
//        dataBatch.process();
//        cluster.addToProcessed(dataBatch);
//        gpu.reciveProcessedDataBatch();
//        gpu.trainDataBatch(dataBatch);
//        assertFalse(VRAMArray.contains(dataBatch));
//
//    }
//}