package bgu.mics.application.services;

import bgu.mics.MessageBusImpl;
import bgu.mics.MicroService;
import bgu.mics.application.messages.TerminateBroadcast;
import bgu.mics.application.messages.TestModelEvent;
import bgu.mics.application.messages.TickBroadCast;
import bgu.mics.application.messages.TrainModelEvent;
import bgu.mics.application.objects.Data;
import bgu.mics.application.objects.GPU;
import bgu.mics.application.objects.Model;


/**
 * GPU service is responsible for handling the
 * {@link TrainModelEvent} and {@link TestModelEvent},
 * in addition to sending t
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class GPUService extends MicroService {
    private GPU gpu;
    private TrainModelEvent trainModelEvent;

    public GPUService(String name , GPU gpu) {
        super(name);
        this.gpu = gpu;
        trainModelEvent=null;
        // TODO Implement this
    }




    // to doo simultaneity : sendToCluster,getFromCluster,Train on vRAM
    @Override
    protected void initialize() { // just need to take responsibilty on time
        MessageBusImpl.getInstance().register(this);
        subscribeBroadcast(TerminateBroadcast.class, (TerminateBroadcast terminateBroadcast) -> {
            this.terminate();
        });
        //System.out.println("GPU service running");
        subscribeBroadcast(TickBroadCast.class, (TickBroadCast tickBroadCast) -> {
            gpu.updateTick(tickBroadCast.getTick());
            if(gpu.getModel()!=null) {
                System.out.println("model status: "+gpu.getModel().getStatus());
                if (gpu.getModel().getStatus().equals("Trained")) {
                    complete(trainModelEvent, trainModelEvent.getModel());
                    trainModelEvent.getModel().getStudent().addToTrainedModel(trainModelEvent.getModel());
                    gpu.setModel(null);
                    System.out.println(trainModelEvent.getModel().getName() + "is done");
                }
            }
        });
        subscribeEvent(TrainModelEvent.class, (TrainModelEvent trainModelEvent) -> {
            this.trainModelEvent=trainModelEvent;
            Model model = trainModelEvent.getModel();
            if (this.gpu.getModel() == null)
                this.gpu.setModel(model);
            Data data = model.getData();
            gpu.divide((data));




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
            //System.out.println("im here");
                    model.setStatus("Tested");
                    complete(testModelEvent, model);
            System.out.println(model.getStatus());
                }

        );



        // ConfrenceInformation.addModel(trainModelEvent.getFutureModel()); //Todo : need change to static?

//                    Thread t1 = new Thread(() -> {
//                        while (gpu.getvRam().contains(null)) {
//

//                        System.out.print("DEBUG");


//                    if (model.getStatus()!="Trained"){
//                        try {
//                            wait();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                while(model.getStatus()!="Trained")
//                {}





    }
}
