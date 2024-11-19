package os;

import java.util.Scanner;

public class NonPreemptiveSJF {

    // Method to calculate waiting time for all processes
    public static void findWaitingTime(int processes[], int n, int bt[], int wt[]) {
        int[] rt = new int[n]; // remaining time array
        System.arraycopy(bt, 0, rt, 0, n); // copying burst time to remaining time array

        int complete = 0, t = 0, minm = Integer.MAX_VALUE;
        int shortest = 0, finish_time;
        boolean check = false;

        // Loop until all processes get completed
        while (complete != n) {
            // Find process with minimum remaining time
            for (int j = 0; j < n; j++) {
                if ((rt[j] < minm) && rt[j] > 0) {
                    minm = rt[j];
                    shortest = j;
                    check = true;
                }
            }

            // If no process was found, move forward in time
            if (!check) {
                t++;
                continue;
            }

            // Decrement remaining time of the found shortest process
            rt[shortest]--;

            // Update the new minimum remaining time
            minm = rt[shortest];
            if (minm == 0)
                minm = Integer.MAX_VALUE;

            // If a process finishes its execution
            if (rt[shortest] == 0) {
                complete++;
                check = false;

                // Calculate the finish time of the process
                finish_time = t + 1;

                // Calculate waiting time for the current process
                wt[shortest] = finish_time - bt[shortest];

                if (wt[shortest] < 0)
                    wt[shortest] = 0;
            }

            // Move to the next unit of time
            t++;
        }
    }

    // Method to calculate turnaround time
    public static void findTurnAroundTime(int processes[], int n, int bt[], int wt[], int tat[]) {
        for (int i = 0; i < n; i++) {
            tat[i] = bt[i] + wt[i];
        }
    }

    // Method to calculate average waiting and turnaround times
    public static void findavgTime(int processes[], int n, int bt[]) {
        int[] wt = new int[n], tat = new int[n];
        int total_wt = 0, total_tat = 0;

        // Find waiting time for all processes
        findWaitingTime(processes, n, bt, wt);

        // Find turnaround time for all processes
        findTurnAroundTime(processes, n, bt, wt, tat);

        // Print all process information
        System.out.println("Processes " + " Burst time " + " Waiting time " + " Turn around time");
        for (int i = 0; i < n; i++) {
            total_wt += wt[i];
            total_tat += tat[i];
            System.out.println(" " + (i + 1) + "\t\t" + bt[i] + "\t\t" + wt[i] + "\t\t" + tat[i]);
        }

        // Print average waiting time and average turnaround time
        System.out.println("Average waiting time = " + (float) total_wt / n);
        System.out.println("Average turn around time = " + (float) total_tat / n);
    }

    // Main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Provide option to use default values or input manually
        System.out.print("Use default values? (yes/no): ");
        String choice = sc.nextLine().toLowerCase();

        int processes[];
        int burst_time[];
        int n;

        if (choice.equals("yes")) {
            // Use default values
            n = 3;
            processes = new int[] { 1, 2, 3 };
            burst_time = new int[] { 10, 5, 8 }; // Default burst times
        } else {
            // Take input manually
            System.out.print("Enter the number of processes: ");
            n = sc.nextInt();
            processes = new int[n];
            burst_time = new int[n];

            for (int i = 0; i < n; i++) {
                processes[i] = i + 1;
                System.out.print("Enter burst time for process " + (i + 1) + ": ");
                burst_time[i] = sc.nextInt();
            }
        }

        // Calculate and print average times
        findavgTime(processes, n, burst_time);

        sc.close();
    }
}