package bgu.mics.application.services;

import bgu.mics.MessageBusImpl;
import bgu.mics.MicroService;
import bgu.mics.application.messages.*;
import bgu.mics.application.objects.Student;

/**
 * Student is responsible for sending the {@link TrainModelEvent},
 * {@link TestModelEvent} and {@link PublishResultsEvent}.
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

        for(int i=0;i<student.getModels().length;i++)
        {
            MessageBusImpl.getInstance().sendEvent(new TrainModelEvent(student.getModels()[i], "TrainModel" + String.valueOf(i)));
            while(student.getModels()[i].getStatus()!="Trained")
            {}
           // if(student.getModels()[i].getStatus()=="Trained")
            MessageBusImpl.getInstance().sendEvent(new TestModelEvent(student.getModels()[i], "TestModel" + String.valueOf(i))));
            while(student.getModels()[i].getStatus()!="Tested")
            {}
            //if(student.getModels()[i].getStatus()=="Tested" && student.getModels()[i].isGood()) // TODO WHILE OR IF
            MessageBusImpl.getInstance().sendEvent(new PublishResultEvent(student.getModels()[i]));
        }

//        subscribeBroadcast(PublishConferenceBroadcast.class,(PublishConferenceBroadcast publish)->{
//            // TODO: Implement this
//        });
        subscribeBroadcast(TerminateBroadcast.class, (TerminateBroadcast terminateBroadcast) -> {
            this.terminate();
        });
        subscribeBroadcast(PublishConferenceBroadcast.class,(PublishConferenceBroadcast pub)->{


        });

    }
}
