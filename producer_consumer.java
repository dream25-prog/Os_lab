import java.util.ArrayList;

import java.util.List;

import java.util.Random;

import java.util.concurrent.Semaphore;



public class ProducerConsumer {

    private static List<Integer> buffer = new ArrayList<>();

    private static final int BUFFER_SIZE = 5;

    private static final int TOTAL_ITEMS = 10; // Total items to be produced and consumed

    private static Semaphore empty = new Semaphore(BUFFER_SIZE);

    private static Semaphore full = new Semaphore(0);

    private static Semaphore mutex = new Semaphore(1);



    public static void main(String[] args) {

        Thread producerThread = new Thread(new Producer());

        Thread consumerThread = new Thread(new Consumer());



        producerThread.start();

        consumerThread.start();

    }



    static class Producer implements Runnable {

        @Override

        public void run() {

            Random random = new Random();

            for (int I = 0; I < TOTAL_ITEMS; I++) { // Produce a finite number of items

                try {

                    int item = random.nextInt(100) + 1;

                    empty.acquire();

                    mutex.acquire();



                    buffer.add(item);

                    System.out.println("Producer produced: " + item);



                    mutex.release();

                    full.release();



                    Thread.sleep((long) (Math.random() * 1000));

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

            }

            // After producing all items, signal the consumer to stop

            try {

                empty.acquire(); // Ensure the buffer is not full before stopping

                mutex.acquire();

                System.out.println("Producer finished producing items.");

                mutex.release();

                full.release(); // Release one full semaphore to allow the consumer to finish

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

        }

    }



    static class Consumer implements Runnable {

        @Override

        public void run() {

            for (int I = 0; I < TOTAL_ITEMS; I++) { // Consume a finite number of items

                try {

                    full.acquire();

                    mutex.acquire();



                    int item = buffer.remove(0);

                    System.out.println("Consumer consumed: " + item);



                    mutex.release();

                    empty.release();



                    Thread.sleep((long) (Math.random() * 1000));

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

            }

            // After consuming all items, signal the producer to stop

            System.out.println("Consumer finished consuming items.");

        }

    }

}