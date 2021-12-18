package bgu.mics.application.services;

import bgu.mics.MessageBusImpl;
import bgu.mics.MicroService;
import bgu.mics.application.messages.TerminateBroadcast;
import bgu.mics.application.messages.TestModelEvent;
import bgu.mics.application.messages.TickBroadCast;
import bgu.mics.application.messages.TrainModelEvent;
import bgu.mics.application.objects.ConfrenceInformation;
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
    public void updateTick(TickBroadCast t) {
        gpu.updateTick(t.getTick());
        //ticks++;
    }



    // to doo simultaneity : sendToCluster,getFromCluster,Train on vRAM
    @Override
    protected void initialize() { // just need to take responsibilty on time
        MessageBusImpl.getInstance().register(this);
        subscribeEvent(TrainModelEvent.class , (TrainModelEvent trainModelEvent) -> {
            Model model = trainModelEvent.getModel();
            gpu.setModel(model); // add 13:08
            if (this.gpu.getModel() == null)
                this.gpu.setModel(model);
            Data data = model.getData();
           // ConfrenceInformation.addModel(trainModelEvent.getFutureModel()); //Todo : need change to static?
            gpu.divide((data));
            Thread t1=new Thread(()->{
                while(!gpu.getAllDataBatches().isEmpty()) {
                    int freeSpace = gpu.getvRam().size() - gpu.getCurrentProcessInVram();
                    for (int i = 0; i<freeSpace;i++) {
                        gpu.sendUnprocessedDataBatchToCluster(gpu.getAllDataBatches().remove(i)); // TODO CHECK IF REMOVE WORK?
                    }
                }
            });
            Thread t2=new Thread(()->{

                    gpu.trainDataBatchModel();
                    if (gpu.finishTrain()) {
                        gpu.getModel().setStatus("Trained");
                        gpu.setModel(null);
                        complete(trainModelEvent, model);
                    }


            });
           t1.start();
           t2.start();



        });
        subscribeBroadcast(TerminateBroadcast.class, (TerminateBroadcast terminateBroadcast) -> {
            this.terminate();
        });
        //System.out.println("GPU service running");
        subscribeBroadcast(TickBroadCast.class, (TickBroadCast tickBroadCast) -> {
            updateTick(tickBroadCast);
        });


        subscribeEvent(TestModelEvent.class , (TestModelEvent testModelEvent) ->{
           //process instanly

            Model model=testModelEvent.getModel();
            double x=Math.random();
            //0.6 MSC
            if(model.getStudent().getStatus().equals("Msc"))
            {
                if(x<=0.6)
                    model.setRes("Good");
                else
                    model.setRes("Bad");
            }
            else
            {
                if(x<=0.8)
                    model.setRes("Good");
                else
                    model.setRes("Bad");
            }
            model.setStatus("Tested");
            complete(testModelEvent, model);
                }

        );



    }
}
