// extends runnable
class PrintSequenceRunnable implements Runnable {

    public static final int PRINT_LIMIT = 9;
    static final Lock lock = new Lock();
    static char[] values = new char[]{'A', 'B', 'C'};
    int sequence;

    PrintSequenceRunnable(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public void run() {
        synchronized (lock) {
            for (int i = 0; i < PRINT_LIMIT; i++) {
                while (lock.number % 3 != sequence) { // wait for numbers other than remainder
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(values[sequence]);
                lock.number++;
                lock.notifyAll();
            }
        }
    }
}

// using update shared status in lock object
class Lock {
    volatile int status = 0;
    volatile int number = 0;
}

class SequentialPrint extends Thread {
    static char[] values = new char[]{'A', 'B', 'C'};
    final Lock lock;
    public int PRINT_LIMIT;
    int printSequence;

    SequentialPrint(Lock lock, int printSequence, int PRINT_LIMIT) {
        this.lock = lock;
        this.printSequence = printSequence;
        this.PRINT_LIMIT = PRINT_LIMIT;
    }

    SequentialPrint(Lock lock, int printSequence) {
        this(lock, printSequence, 9);
    }

    @Override
    public void run() {
        synchronized (lock) {
            for (int i = 0; i < PRINT_LIMIT; i++) {
                while (lock.status != printSequence) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(values[printSequence]);
                lock.status = (printSequence + 1) % 3;
                lock.notifyAll();
            }
        }
    }
}

public class MultiThreading {

    public static void main(String[] args) throws InterruptedException {
        PrintSequenceRunnable runnable1 = new PrintSequenceRunnable(0);
        PrintSequenceRunnable runnable2 = new PrintSequenceRunnable(1);
        PrintSequenceRunnable runnable3 = new PrintSequenceRunnable(2);

        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);
        Thread t3 = new Thread(runnable3);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
        System.out.println();
        System.out.println();

        Lock lock = new Lock();
        SequentialPrint a = new SequentialPrint(lock, 0);
        SequentialPrint b = new SequentialPrint(lock, 1);
        SequentialPrint c = new SequentialPrint(lock, 2);

        a.start();
        b.start();
        c.start();

        a.join();
        b.join();
        c.join();
        System.out.println("\n");
    }
}
