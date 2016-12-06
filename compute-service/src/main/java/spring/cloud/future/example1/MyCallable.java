package spring.cloud.future.example1;

import java.util.concurrent.Callable;

public class MyCallable implements Callable {
    private long waitTime; 
    public MyCallable(int timeInMillis){ 
        this.waitTime=timeInMillis;
    }
    @Override
    public String call() throws Exception {
        Thread.sleep(waitTime);
        //return the thread name executing this callable task
        return Thread.currentThread().getName();
    }
 
}
