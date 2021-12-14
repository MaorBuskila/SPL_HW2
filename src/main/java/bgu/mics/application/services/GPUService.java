package bgu.mics.application.services;

import bgu.mics.MicroService;
import bgu.mics.application.messages.TestModelEvent;
import bgu.mics.application.messages.TrainModelEvent;
import bgu.mics.application.objects.Data;
import bgu.mics.application.objects.GPU;
import bgu.mics.application.objects.Model;

/**
 * GPU service is responsible for handling the
 * {@link TrainModelEvent} and {@link TestModelEvent},
 * in addition to sending the {@link DataPreProcessEvent}.
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class GPUService extends MicroService {
    private GPU gpu;

    public GPUService(String name , GPU gpu) {
        super(name);
        this.gpu = gpu;
        // TODO Implement this
    }




    @Override
    protected void initialize() { // just need to take responsibilty on time
        subscribeEvent(TrainModelEvent.class , (TrainModelEvent trainModelEvent) -> {
            Model model = trainModelEvent.getModel();
            Data data = model.getData();
            gpu.divide((data));
            while(model.getData().getProcessed()<model.getData().getSize()) {
                int freeSpace = gpu.getvRam().size() - gpu.getCurrentProInVram();
                for (int i = 0; i<freeSpace;i++)
                {
                    gpu.sendUnprocessedDataBatchToCluster(gpu.getAllDataBatches().remove(0));
                }


            }
        });
        subscribeEvent(TestModelEvent.class , c -> {});

    }
}
