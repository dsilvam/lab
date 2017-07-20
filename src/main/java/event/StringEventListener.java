package event;

import com.google.common.eventbus.Subscribe;

public class StringEventListener {

    @Subscribe
    public void processStringEvent(String event) {
        System.out.println("TID: " + Thread.currentThread().getId() + ", Event = " + event);
    }
}
