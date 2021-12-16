package bgu.mics.application.services;

import bgu.mics.MessageBusImpl;
import bgu.mics.MicroService;
import bgu.mics.application.messages.TestModelEvent;
import bgu.mics.application.messages.TickBroadCast;
import bgu.mics.application.messages.TrainModelEvent;
import bgu.mics.application.objects.Data;
import bgu.mics.application.objects.GPU;
import bgu.mics.application.objects.Model;

impo

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
    public void updateTick(TickBroadCast t) {
        gpu.updateTick(t.getTick());
        //ticks++;
    }



    // to do simultaneity : sendToCluster,getFromCluster,Train on vRAM
    @Override
    protected void initialize() { // just need to take responsibilty on time
        subscribeEvent(TrainModelEvent.class , (TrainModelEvent trainModelEvent) -> {
            Model model = trainModelEvent.getModel();
            Data data = model.getData();
            gpu.divide((data));
            Thread t1=new Thread(()->{
                while(!gpu.getAllDataBatches().isEmpty()) {
                    int freeSpace = gpu.getvRam().size() - gpu.getCurrentProcessInVram();
                    for (int i = 0; i<freeSpace;i++)
                    {
                        gpu.sendUnprocessedDataBatchToCluster(gpu.getAllDataBatches().remove(0)); // TODO CHECK IF REMOVEWORK
                    }
                }
            });
            Thread t2=new Thread(()->{
                gpu.trainDataBatchModel();
            });
           t1.start();
           t2.start();

           if(!t1.isAlive() && !t2.isAlive())
                MessageBusImpl.getInstance().complete(trainModelEvent,model);

        });
        System.out.println("GPU service running");
        subscribeBroadcast(TickBroadCast.class, (TickBroadCast tickBroadCast) -> {
            updateTick(tickBroadCast);
        });

        subscribeEvent(TestModelEvent.class , (TestModelEvent testModelEvent) ->{
           //process instanly

            Model model=testModelEvent.getModel();
            double x=Math.random();
            //0.6 MSC
            if(model.getStudent().getStatus()==MSc)
            {
                if(x<=0.6)
                    model.setRes("Good");
            }
            else
            {
                if(x<=0.8)
                    model.setRes("Good");
            }
            model.setStatus("Tested");
            MessageBusImpl.getInstance().complete(testModelEvent,model.getRes());
                }

        );
        //TODO: implement lambda
    }
}
