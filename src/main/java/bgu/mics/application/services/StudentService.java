package bgu.mics.application.services;

import bgu.mics.Future;
import bgu.mics.MessageBusImpl;
import bgu.mics.MicroService;
import bgu.mics.application.messages.*;
import bgu.mics.application.objects.Model;
import bgu.mics.application.objects.Student;

import java.util.Vector;

/**
 * Student is responsible for sending the {@link TrainModelEvent},
 * {@link TestModelEvent} and
 * In addition, it must sign up for the conference publication broadcasts.
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class StudentService extends MicroService {

    private Student student;

    public StudentService(String name,Student student) {
        super(name);
        this.student=student;
        // TODO Implement this
    }


    @Override
    protected void initialize() {
        MessageBusImpl msgbus = MessageBusImpl.getInstance();
        msgbus.register(this);

        subscribeBroadcast(TerminateBroadcast.class, (TerminateBroadcast terminateBroadcast) -> {
            this.terminate();
        });

        subscribeBroadcast(TickBroadCast.class, (TickBroadCast tickBroadCast) -> {
            TrainModelEvent e = null;
            if (student.getFuture() == null) {
                e = new TrainModelEvent(student.getModels().firstElement(), this.getName());
              //  System.out.println(Thread.currentThread().getName() + " is sending: " + e.getClass());        ///////////////////////////////////////////////////////////////////////
            }

            student.setFuture(sendEvent(e));

        });
        subscribeBroadcast(PublishConferenceBroadcast.class, (PublishConferenceBroadcast pub) -> {
            Vector<Model> vecOfModels = pub.getModels();
            for (Model model : vecOfModels){
                if (model.getStudent() .equals(student))
                    student.incrementPublished();
                else
                    student.readPaper();
            }

        });

        for(int i=0;i<student.getModels().size();i++)
        {
            student.setFuture(sendEvent(new TrainModelEvent(student.getModels().elementAt(i), "TrainModel" + String.valueOf(i))));


            boolean b=true;
            while(b) {
                if (student.getFuture().get().getStatus().equals("Trained")) {
                    b = false;
                    System.out.println("send test model event");
                    student.setFuture(sendEvent(new TestModelEvent(student.getModels().elementAt(i), "TestModel" + String.valueOf(i)))) ;
                }
            }
            b=true;
            while (b) {
                if (student.getFuture().get().getStatus().equals("Tested")) {
                  //  System.out.println("im:      " + student.getModels().get(i).getStatus());
                    if(student.getFuture().get().isGood())
                        student.setFuture(sendEvent(new PublishResultEvent(student.getModels().elementAt(i)))) ;
                    b=false;
                }
            }

        }

    }
}
