//  Create threads to print A and then B, then C. Output format requirements: ABCABCABCABCABCABCABCABCABC in a line.


public class MultiThreading2 {

    volatile int status = 0;

    public static void main(String[] args) {
        MultiThreading2 lock = new MultiThreading2();

        SequentialPrint a = new SequentialPrint(lock, 0);
        SequentialPrint b = new SequentialPrint(lock, 1);
        SequentialPrint c = new SequentialPrint(lock, 2);

        a.start();
        b.start();
        c.start();
    }
}

class SequentialPrint extends Thread {
    static char[] values = new char[]{'A', 'B', 'C'};
    final MultiThreading2 lock;
    public int PRINT_LIMIT = 9;
    int printSequence;

    SequentialPrint(MultiThreading2 lock, int printSequence) {
        this.lock = lock;
        this.printSequence = printSequence;
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

