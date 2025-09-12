import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedPrinter {
    private final Lock lock = new ReentrantLock();

    public void print(String s) {
        lock.lock();
        try {
            System.out.print(s);
            System.out.flush();
        } finally {
            lock.unlock();
        }
    }

    public void println(String s) {
        lock.lock();
        try {
            System.out.println(s);
            System.out.flush();
        } finally {
            lock.unlock();
        }
    }
}
