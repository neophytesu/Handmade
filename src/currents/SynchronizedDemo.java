package currents;

public class SynchronizedDemo {
    public void method(){
        synchronized (this){
            System.out.println("Synchronized block");
        }
    }
}