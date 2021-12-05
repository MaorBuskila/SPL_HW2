package bgu.mics.application.objects;

import bgu.mics.MessageBusImpl;
import bgu.mics.MicroService;
import bgu.mics.application.services.CPUService;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class CPUTest {
    private CPU cpu;
    private DataBatch db;
    private Queue<DataBatch> queue;
    private Cluster cluster;
    private MessageBusImpl msgbus;


    @BeforeEach
    public void setUp() {
        msgbus = MessageBusImpl.getInstance();
        db = new DataBatch();
        queue = new LinkedList<DataBatch>();
        queue.add(db);
        cluster = new Cluster();
       // cpu = new CPUService("name");
        cpu = new CPU(16, queue , cluster, "name");
        msgbus.register(cpu);
    }

    @After
    public void tearDown() {
        msgbus.unregister(cpu);
    }

    @Test
    void testGetUnprocessed() {
        DataBatch tmp = cpu.getUnprocessed();
        assertEquals(tmp, db);
        DataBatch tmp2 = cpu.getUnprocessed();
        assertNull(tmp2);
    }

    @Test
    void testprocess() {
        assertFalse(db.isProcessed());
        cpu.process(db);
        assertTrue(db.isProcessed());
      //  assertThrows("Should throw exception (DataBatch is already Processed)",Exception.class, () -> cpu.process(db));
    }

    @Test
    void testsendToCluster() {
        cpu.process(db);
        cpu.sendToCluster(db);
        assertEquals(cluster.getprocessedBatch().peek(),db);


    }

    @Test
    void testIsBusy() {
        assertFalse(cpu.checkIfBusy());
        cpu.process(db);
        assertTrue
    }

    @Test
    void testUpdateTick() {
    }
}