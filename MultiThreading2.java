//
//    //Create threads to print A and then B, then C. Output format requirements: ABCABCABCABCABCABCABCABCABC in a line.
//    @AllArgsConstructor
//    public class MultiThreading2 implements Runnable {
//
//        //  There will be lock Change the object to  Lock(ReentrantLock)  Conduct lock/unlock It's OK, too
//        private static final Object lock = new Object();
//        private static final int MAX = 30;
//        private static int current = 0;
//
//        private int index;
//
//        @Override
//        public void run() {
//            while (current < MAX) {
//                synchronized (lock) {
//                    if ((current < MAX) && (current % 3 == index)) {
//                        System.out.println((char) ('A' + current % 3));
//                        current++;
//                    }
//                }
//            }
//        }
//
//        public static void main(String[] args) throws Exception {
//            List<Thread> threadList = Arrays.asList(
//                    new Thread(new MultiThreading2(0)),
//                    new Thread(new MultiThreading2(1)),
//                    new Thread(new MultiThreading2(2))
//            );
//
//            threadList.forEach(Thread::start);
//        }
//    }

public class MultiThreading2 {

    volatile int status = 0;
    public static void main(String[] args) {
        MultiThreading2 p = new MultiThreading2();

        SequentialPrint a = new SequentialPrint(p, 0);
        SequentialPrint b = new SequentialPrint(p, 1);
        SequentialPrint c = new SequentialPrint(p, 2);

        a.start();
        b.start();
        c.start();
    }
}

class SequentialPrint extends Thread{
    static char[] values = new char[] {'A', 'B', 'C'};
    MultiThreading2 p;
    int printSequence;
    SequentialPrint(MultiThreading2 p, int printSequence){
        this.p = p;
        this.printSequence = printSequence;
    }
    @Override
    public void run() {
        try{
            synchronized (p) {
                for (int i = 0; i < 9; i++) {
                    while(p.status != printSequence){
                        p.wait();
                    }
                    System.out.print(values[printSequence]);
                    p.status = (printSequence + 1) % 3;
                    p.notifyAll();
                }
            }
        }catch (Exception e) {
            System.out.println("Exception 1 :" + e.getMessage());
        }
    }
}

