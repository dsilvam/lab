package concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class AtomicIntegerTest {

    private AtomicInteger counter = new AtomicInteger(0);
    private int mortalCounter;

    public void increment() {
        mortalCounter++;
        counter.getAndIncrement(); // Not atomic, multiple threads could get the same result

    }

    public int get() {
        return counter.get();
    }

    public int getMortalCounter() {
        return mortalCounter;
    }

    public static void main(String[] args) {

        final AtomicIntegerTest test = new AtomicIntegerTest();

        ExecutorService service = Executors.newFixedThreadPool(2);

        IntStream.range(0,1000).forEach(i -> service.submit(new AdddCaller(test)));

        stop(service);

        System.out.println(test.get());
        System.out.println(test.getMortalCounter());

    }

    static class AdddCaller implements Callable<Object> {

        AtomicIntegerTest test;

        public AdddCaller(AtomicIntegerTest test) {
            this.test = test;
        }

        @Override
        public Object call() throws Exception {
            test.increment();
            return null;
        }
    }

    public static void stop(ExecutorService executor) {
        try {
            executor.shutdown();
            executor.awaitTermination(60, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            System.err.println("termination interrupted");
        }
        finally {
            if (!executor.isTerminated()) {
                System.err.println("killing non-finished tasks");
            }
            executor.shutdownNow();
        }
    }
}
