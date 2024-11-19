import java.util.Scanner;

public class FirstComeFirstServe {

    public static void findWaitingTime(int processes[], int n, int bt[], int wt[]) {
        wt[0] = 0;
        for (int i = 1; i < n; i++) {
            wt[i] = bt[i - 1] + wt[i - 1];
        }
    }

    public static void findTurnAroundTime(int processes[], int n, int bt[], int wt[], int tat[]) {

        for (int i = 0; i < n; i++) {
            tat[i] = bt[i] + wt[i];
        }
    }

    public static void findAvgTime(int processes[], int n, int bt[]) {
        int wt[] = new int[n], tat[] = new int[n];
        int total_wt = 0, total_tat = 0;

        findWaitingTime(processes, n, bt, wt);

        findTurnAroundTime(processes, n, bt, wt, tat);

        System.out.println("Processes " + " Burst time " + " Waiting time " + " Turn around time");

        for (int i = 0; i < n; i++) {
            total_wt += wt[i];
            total_tat += tat[i];
            System.out.println(" " + (i + 1) + "\t\t" + bt[i] + "\t\t" + wt[i] + "\t\t " + tat[i]);
        }

        System.out.println("Average waiting time = " + (float) total_wt / n);
        System.out.println("Average turn around time = " + (float) total_tat / n);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();
        int processes[] = new int[n];
        int burst_time[] = new int[n];

        for (int i = 0; i < n; i++) {
            processes[i] = i + 1;
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            burst_time[i] = sc.nextInt();
        }

        findAvgTime(processes, n, burst_time);

        sc.close();
    }
}