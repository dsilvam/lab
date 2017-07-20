package event;

import com.google.common.eventbus.Subscribe;

public class PPListener {

    @Subscribe
    public void ppStarted(PPStartedEvent e) throws InterruptedException {
//        Thread.sleep(30000);
        System.out.println("TID: " + Thread.currentThread().getId() + ", " + e);
    }

    @Subscribe
    public void ppFinished(PPFinishedEvent e) throws InterruptedException {
//        Thread.sleep(30000);
        System.out.println("TID: " + Thread.currentThread().getId() + ", " + e);
    }
}

