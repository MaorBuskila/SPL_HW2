package bgu.mics.application.services;

import bgu.mics.MicroService;
import bgu.mics.application.messages.PublishConferenceBroadcast;

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

    public StudentService(String name) {
        super(name);
        // TODO Implement this
    }

    @Override
    protected void initialize() {

        subscribeBroadcast(PublishConferenceBroadcast.class,(PublishConferenceBroadcast publish)->{
            // TODO: Implement this
        });

    }
}
