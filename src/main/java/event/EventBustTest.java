package event;

import com.google.common.eventbus.EventBus;

public class EventBustTest {

    private static class EventBusHolder {
        static final EventBus EVENT_BUS = new EventBus();
    }

    public static EventBus getEventBus() {
        return EventBusHolder.EVENT_BUS;
    }

    public void runPP() {
        getEventBus().post(new PPStartedEvent());

        // do some heavyweight lifting

        getEventBus().post(new PPFinishedEvent());

        System.out.println("Exiting PP Proccess");
    }

    public static void main(String[] args) {
        final EventBus eventBus = EventBustTest.getEventBus();
        eventBus.register(new PPListener());

        EventBustTest test = new EventBustTest();
        test.runPP();
    }

}
