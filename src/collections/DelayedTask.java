package collections;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedTask implements Delayed {
    private long executionTime;
    private Runnable task;

    public DelayedTask(long executionTime, Runnable task) {
        this.executionTime = executionTime;
        this.task = task;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(executionTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.compare(this.executionTime, ((DelayedTask) o).executionTime);
    }

    public void execute() {
        task.run();
    }
}
