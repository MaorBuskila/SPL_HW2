//package bgu.mics.application.objects;
//
//import bgu.mics.MessageBusImpl;
//import org.junit.After;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.LinkedList;
//import java.util.Queue;
//
//import static org.junit.Assert.assertThrows;
//import static org.junit.jupiter.api.Assertions.*;
//
//class CPUTest {
//    private CPU cpu;
//    private DataBatch db;
//    private Queue<DataBatch> queue;
//    private Cluster cluster;
//    private MessageBusImpl msgbus;
//
//
//    @BeforeEach
//    public void setUp() {
//        msgbus = MessageBusImpl.getInstance();
//        Data data=new Data(Data.Type.Images , 10000);
//        db = new DataBatch(data,0);
//        queue = new LinkedList<DataBatch>();
//        queue.add(db);
//        cluster = new Cluster();
//        // cpu = new CPUService("name");
//        cpu = new CPU(16, queue, cluster, "name");
//        msgbus.register(cpu);
//    }
//
//    @After
//    public void tearDown() {
//        msgbus.unregister(cpu);
//    }
//
//    @Test
//    void testGetUnprocessed() {
//        DataBatch tmp = cpu.getUnprocessed();
//        assertEquals(tmp, db);
//        DataBatch tmp2 = cpu.getUnprocessed();
//        assertNull(tmp2);
//    }
//
//    @Test
//    void testprocess() {
//        int x=db.getData().getProcessed();
//        assertFalse(db.isProcessed());
//        cpu.process(db);
//        assertTrue(db.isProcessed());
//        assertEquals(db.getData().getProcessed()-x,1000);
//        //  assertThrows("Should throw exception (DataBatch is already Processed)",Exception.class, () -> cpu.process(db));
//    }
//
//    @Test
//    void testsendToCluster() {
//        cpu.process(db);
//        cpu.sendToCluster(db);
//        assertEquals(cluster.getProcessedDataBatch().peek(), db);
//
//
//    }
//
//    //Todo: check if we need this?
//    @Test
//    void testcheckIfBusy() {
//        assertFalse(cpu.checkIfBusy());
//
//    }
//}