import java.util.Scanner;

public class MatrixOperationsMultithreading {
    private static final int MATRIX_SIZE = 3; // Define the size of the matrix
    private static int[][] matrixA = new int[MATRIX_SIZE][MATRIX_SIZE];
    private static int[][] matrixB = new int[MATRIX_SIZE][MATRIX_SIZE];
    private static int[][] resultAdd = new int[MATRIX_SIZE][MATRIX_SIZE];
    private static int[][] resultMultiply = new int[MATRIX_SIZE][MATRIX_SIZE];

    public static void main(String[] args) throws InterruptedException {
        // Create a scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // Accept matrix values from the user
        System.out.println("Enter values for Matrix A:");
        takeMatrixInput(scanner, matrixA);
        System.out.println("Enter values for Matrix B:");
        takeMatrixInput(scanner, matrixB);

        // Display the matrices entered by the user
        System.out.println("Matrix A:");
        printMatrix(matrixA);
        System.out.println("Matrix B:");
        printMatrix(matrixB);

        // Threads for addition
        Thread additionThread = new Thread(new MatrixAdditionTask());
        // Threads for multiplication
        Thread multiplicationThread = new Thread(new MatrixMultiplicationTask());

        // Start threads
        additionThread.start();
        multiplicationThread.start();

        // Wait for threads to finish
        additionThread.join();
        multiplicationThread.join();

        // Display the results
        System.out.println("Matrix Addition Result:");
        printMatrix(resultAdd);

        System.out.println("Matrix Multiplication Result:");
        printMatrix(resultMultiply);

        // Close the scanner
        scanner.close();
    }

    // Method to take matrix input from the user
    private static void takeMatrixInput(Scanner scanner, int[][] matrix) {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                System.out.print("Enter element [" + i + "][" + j + "]: ");
                matrix[i][j] = scanner.nextInt();
            }
        }
    }

    // Print a matrix
    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    // Task for matrix addition
    static class MatrixAdditionTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < MATRIX_SIZE; i++) {
                for (int j = 0; j < MATRIX_SIZE; j++) {
                    resultAdd[i][j] = matrixA[i][j] + matrixB[i][j];
                }
            }
            System.out.println("Addition Completed by Thread: " + Thread.currentThread().getName());
        }
    }

    // Task for matrix multiplication
    static class MatrixMultiplicationTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < MATRIX_SIZE; i++) {
                for (int j = 0; j < MATRIX_SIZE; j++) {
                    for (int k = 0; k < MATRIX_SIZE; k++) {
                        resultMultiply[i][j] += matrixA[i][k] * matrixB[k][j];
                    }
                }
            }
            System.out.println("Multiplication Completed by Thread: " + Thread.currentThread().getName());
        }
    }
}