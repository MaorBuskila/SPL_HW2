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
    @BeforeEach
    void setUp() {
        msgbus = MessageBusImpl.getInstance();
        cluster = new Cluster();
        model = new Model();
        gpu = new GPU(RTX3090, model , cluster , "gpu1");
        msgbus.register(gpu);

    }

    @AfterEach
    void tearDown() {
        
    }

    @Test
    void separateData() {
    }

    @Test
    void sendUnprocessedDataBatchToCluster() {
    }

    @Test
    void getProcessedDataBatch() {
    }

    @Test
    void trainDataBatchModel() {
    }
}