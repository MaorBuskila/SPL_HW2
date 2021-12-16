package bgu.mics.application.messages;

import bgu.mics.application.objects.Model;

public class PublishResultEvent {
    private Model m;
    public PublishResultEvent(Model m)
    {
        this.m=m;
    }
}
