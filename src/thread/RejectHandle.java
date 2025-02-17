package thread;

public interface RejectHandle {
    void reject(Runnable rejectCommand,MyThreadPool threadPool);
}
