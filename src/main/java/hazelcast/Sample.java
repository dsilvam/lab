package hazelcast;

import com.hazelcast.core.*;

import java.io.Serializable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Sample implements MessageListener<PushSucceededEvent> {

    public static void main( String[] args ) {
        Sample sample = new Sample();
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
        ITopic topic = hazelcastInstance.getTopic( "default" );
        ITopic secondTopic = hazelcastInstance.getTopic( "secondTopic" );
        topic.addMessageListener( sample );
        secondTopic.addMessageListener( new ItemAddedListener());
        topic.publish( new PushSucceededEvent() );
        secondTopic.publish( new ItemAddedToQueueEvent() );

        System.exit(0);
    }

    public void onMessage( Message<PushSucceededEvent> message ) {
        PushSucceededEvent myEvent = message.getMessageObject();
        System.out.println( "Message received = " + myEvent.toString() );
        if ( myEvent.isHeavyweight() ) {
            messageExecutor.execute( new Runnable() {
                public void run() {
                    doHeavyweightStuff( myEvent );
                }
            } );
        }
    }

    private void doHeavyweightStuff(PushSucceededEvent event) {
        System.out.printf("Doing some heavy weight stuff");
    }

    private final Executor messageExecutor = Executors.newSingleThreadExecutor();
}

class ItemAddedListener implements  MessageListener<ItemAddedToQueueEvent> {

    @Override
    public void onMessage(Message<ItemAddedToQueueEvent> message) {
        System.out.println("Notification sent for item added to queue");
    }
}

class ItemAddedToQueueEvent implements Serializable {

    @Override
    public String toString() {
        return "ItemAddedToQueue";
    }

    boolean isHeavyweight() {
        return true;
    }
}


class PushSucceededEvent implements Serializable {

    @Override
    public String toString() {
        return "Push Succeeded";
    }

    boolean isHeavyweight() {
        return true;
    }
}
