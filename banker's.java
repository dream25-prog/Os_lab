import java.util.Scanner;

public class BankersAlgorithm {
    static boolean isSafe(int[][] max, int[][] allocation, int[][] need, int[] available, int processes,
            int resources) {
        int[] work = new int[resources];
        boolean[] finish = new boolean[processes];
        int[] safeSequence = new int[processes];
        int count = 0;

        // Initialize the work array with available resources
        System.arraycopy(available, 0, work, 0, resources);

        while (count < processes) {
            boolean foundProcess = false;

            for (int i = 0; i < processes; i++) {
                if (!finish[i]) {
                    boolean canAllocate = true;
                    for (int j = 0; j < resources; j++) {
                        if (need[i][j] > work[j]) {
                            canAllocate = false;
                            break;
                        }
                    }

                    if (canAllocate) {
                        for (int j = 0; j < resources; j++) {
                            work[j] += allocation[i][j];
                        }
                        finish[i] = true;
                        safeSequence[count++] = i;
                        foundProcess = true;
                    }
                }
            }

            if (!foundProcess) {
                System.out.println("The system is not in a safe state.");
                return false;
            }
        }

        System.out.println("The system is in a safe state.");
        System.out.print("Safe sequence: ");
        for (int i = 0; i < processes; i++) {
            System.out.print("P" + safeSequence[i] + " ");
        }
        System.out.println();
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask for the number of processes
        System.out.print("Enter the number of processes: ");
        int processes = scanner.nextInt();

        // Ask for the number of resources
        System.out.print("Enter the number of resources: ");
        int resources = scanner.nextInt();

        // Maximum demand matrix
        int[][] max = new int[processes][resources];
        System.out.println("Enter the maximum demand matrix: ");
        for (int i = 0; i < processes; i++) {
            System.out.print("For process " + i + ": ");
            for (int j = 0; j < resources; j++) {
                max[i][j] = scanner.nextInt();
            }
        }

        // Allocation matrix
        int[][] allocation = new int[processes][resources];
        System.out.println("Enter the allocation matrix: ");
        for (int i = 0; i < processes; i++) {
            System.out.print("For process " + i + ": ");
            for (int j = 0; j < resources; j++) {
                allocation[i][j] = scanner.nextInt();
            }
        }

        // Available resources
        int[] available = new int[resources];
        System.out.print("Enter the available resources: ");
        for (int i = 0; i < resources; i++) {
            available[i] = scanner.nextInt();
        }

        // Need matrix (calculated as Max - Allocation)
        int[][] need = new int[processes][resources];
        for (int i = 0; i < processes; i++) {
            for (int j = 0; j < resources; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }

        // Call the isSafe function to check if the system is in a safe state
        isSafe(max, allocation, need, available, processes, resources);
    }
}