package bgu.mics.application.services;

import bgu.mics.Future;
import bgu.mics.MessageBusImpl;
import bgu.mics.MicroService;
import bgu.mics.application.messages.*;
import bgu.mics.application.objects.Model;
import bgu.mics.application.objects.Student;

import javax.jws.WebParam;
import java.util.NoSuchElementException;
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
    private Future myFuture;
    private Model myModel;

    public StudentService(String name, Student student) {
        super(name);
        this.student = student;
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
            myFuture = student.getFuture();
            if (myFuture == null) {
               // System.out.println(student.getName() + " my future is null");
                if (student.getTestedCounter() < student.getModels().size()) {
                //   System.out.println(student.getName() + " tested counetr " + student.getTestedCounter() + " VS " + "student.getModels().size(): " + student.getModels().size() );
                    TrainModelEvent trainModelEvent = new TrainModelEvent(student.getModels().get(student.getTestedCounter()), "TrainModel" + String.valueOf(student.getTestedCounter()));
                    Future f = (sendEvent(trainModelEvent));
                    student.setFuture(f);
                 //   System.out.println("set the future to: " +student.getName());
                }
                 // System.out.println(Thread.currentThread().getName() + " is sending: " + e.getClass());        ///////////////////////////////////////////////////////////////////////
            } else {
                if (myFuture.isDone()) {
                   // System.out.println("id done");
                    myModel = (Model) myFuture.get();
                    if (myModel.getStatus().equals("Trained")) {
                //        System.out.println("done trained");
                        System.out.println(myModel.getName() + " is Trained");
                        TestModelEvent testModelEvent = new TestModelEvent(myModel, "TestModel" + String.valueOf(student.getTestedCounter()));
                        student.setFuture(sendEvent(testModelEvent));
                        //student.setFuture(sendEvent(new TestModelEvent(student.getModels().elementAt(student.getTestedCounter()), "TestModel" + String.valueOf(student.getTestedCounter()))));
                    } else if (myModel.getStatus().equals("Tested")) {
                        System.out.println(myModel.getName() + " is Tested");
                        if (myModel.getRes().equals("Good")) {
                            try {
                                student.setFuture(sendEvent(new PublishResultEvent(myModel)));
                            } catch (NoSuchElementException ex) {
                                student.getFuture().resolve(student.getModels().elementAt(student.getTestedCounter()));
                            }
                        }
                        student.setFuture(null);
                      //  System.out.println("done tested");
                    }

                }

            }

        });
        subscribeBroadcast(PublishConferenceBroadcast.class, (PublishConferenceBroadcast pub) -> {
            Vector<Model> vecOfModels = pub.getModels();
            for (Model model : vecOfModels) {
                if (model.getStudent().equals(student))
                    student.incrementPublished();
                else
                    student.readPaper();
            }

        });


        }

}
