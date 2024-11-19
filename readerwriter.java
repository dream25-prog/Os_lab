import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReaderWriter {
    private static final int MAX = 4; // Define the size of the matrix
    private static int[][] A = new int[MAX][MAX]; // Matrix A
    private static int[][] B = new int[MAX][MAX]; // Matrix B
    private static int[][] C = new int[MAX][MAX]; // Result matrix C

    private static Lock mutex = new ReentrantLock(); // Mutex for managing reader count
    private static int readerCount = 0; // Count of active readers

    private static Lock readWriteLock = new ReentrantLock(); // Lock for writing to matrix C

    static class WriterThread extends Thread {
        private int row;

        WriterThread(int row) {
            this.row = row;
        }

        @Override
        public void run() {
            readWriteLock.lock(); // Lock for writing to matrix C
            try {
                for (int j = 0; j < MAX; j++) {
                    C[row][j] = 0; // Initialize the value of C[row][j]
                    for (int k = 0; k < MAX; k++) {
                        C[row][j] += A[row][k] * B[k][j]; // Multiply and accumulate
                    }
                    System.out.println("Writer " + row + " updated C[" + row + "][" + j + "] to " + C[row][j]);
                }
            } finally {
                readWriteLock.unlock(); // Unlock after writing
            }
        }
    }

    static class ReaderThread extends Thread {
        private int row;

        ReaderThread(int row) {
            this.row = row;
        }

        @Override
        public void run() {
            mutex.lock(); // Lock to modify readerCount
            readerCount++;
            if (readerCount == 1) {
                readWriteLock.lock(); // First reader locks the resource
            }
            mutex.unlock();

            // Reading the shared matrix
            System.out.println("Reader " + row + " is reading matrix C:");
            for (int j = 0; j < MAX; j++) {
                System.out.println("C[" + row + "][" + j + "] = " + C[row][j]);
            }

            mutex.lock(); // Lock to modify readerCount
            readerCount--;
            if (readerCount == 0) {
                readWriteLock.unlock(); // Last reader unlocks the resource
            }
            mutex.unlock();
        }
    }

    public static void main(String[] args) {
        Random rand = new Random();

        // Initialize matrix A with random values
        System.out.println("Matrix A:");
        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                A[i][j] = rand.nextInt(10); // Random value between 0 and 9
                System.out.print(A[i][j] + " ");
            }
            System.out.println();
        }

        // Initialize matrix B with random values
        System.out.println("\nMatrix B:");
        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                B[i][j] = rand.nextInt(10); // Random value between 0 and 9
                System.out.print(B[i][j] + " ");
            }
            System.out.println();
        }

        // Create an array of writer threads (for each row)
        WriterThread[] writerThreads = new WriterThread[MAX];
        for (int i = 0; i < MAX; i++) {
            writerThreads[i] = new WriterThread(i);
            writerThreads[i].start(); // Start the writer threads
        }

        // Wait for all writer threads to finish
        for (int i = 0; i < MAX; i++) {
            try {
                writerThreads[i].join(); // Wait for the writer thread to finish
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Create an array of reader threads (for each row)
        ReaderThread[] readerThreads = new ReaderThread[MAX];
        for (int i = 0; i < MAX; i++) {
            readerThreads[i] = new ReaderThread(i);
            readerThreads[i].start(); // Start the reader threads
        }

        // Wait for all reader threads to finish
        for (int i = 0; i < MAX; i++) {
            try {
                readerThreads[i].join(); // Wait for the reader thread to finish
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}