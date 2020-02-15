package com.kongbig.concurrent.deadlock;

/**
 * 死锁例子
 *
 * @author kongbig
 * @date 2020/2/15 13:53
 */
public class DeadLock {

    public static class Obj1 {

    }

    public static class Obj2 {

    }

    /**
     * 运行代码，可以通过jstack查看到死锁信息（或VisualVM查看线程dump）
     * <p>
     * thread1持有Obj1的锁，等待获取Obj2的锁
     * thread2持有Obj2的锁，等待获取Obj1的锁，
     * 两个线程相互等待获取对方持有的锁，出现死锁。
     *
     * @param args
     */
    public static void main(String[] args) {
        Obj1 obj1 = new Obj1();
        Obj2 obj2 = new Obj2();

        Thread thread1 = new Thread(new SyncAddRunnable(obj1, obj2, 1, 2, true));
        thread1.setName("thread1");
        thread1.start();

        Thread thread2 = new Thread(new SyncAddRunnable(obj1, obj2, 2, 1, false));
        thread2.setName("thread2");
        thread2.start();
    }

    /**
     * 线程死锁等待演示
     */
    public static class SyncAddRunnable implements Runnable {
        Obj1 obj1;
        Obj2 obj2;
        int a, b;
        boolean flag;

        public SyncAddRunnable(Obj1 obj1, Obj2 obj2, int a, int b, boolean flag) {
            this.obj1 = obj1;
            this.obj2 = obj2;
            this.a = a;
            this.b = b;
            this.flag = flag;
        }

        @Override
        public void run() {
            try {
                if (flag) {
                    synchronized (obj1) {
                        Thread.sleep(100);
                        synchronized (obj2) {
                            System.out.println(a + b);
                        }
                    }
                } else {
                    synchronized (obj2) {
                        Thread.sleep(100);
                        synchronized (obj1) {
                            System.out.println(a + b);
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
