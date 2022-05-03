class PrintSequenceRunnable implements Runnable{

    public int PRINT_LIMIT = 26;
    static int  number = 0;
    int remainder;
    static final Object lock = new Object();
    static char[] values = new char[] {'A', 'B', 'C'};

    PrintSequenceRunnable(int remainder)
    {
        this.remainder = remainder;
    }

    @Override
    public void run() {
        while (number < PRINT_LIMIT - 1) {
            synchronized (lock) {
                while (number % 3 != remainder) { // wait for numbers other than remainder
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(values[number % 3]);
                number++;
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

        Thread t1=new Thread(runnable1);
        Thread t2=new Thread(runnable2);
        Thread t3=new Thread(runnable3);

        t1.start();
        t2.start();
        t3.start();
    }
}
