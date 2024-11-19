import java.util.*;

class Process {
    int pid;        // Process ID
    int burst;      // Burst Time
    int remaining;  // Remaining Burst Time (used during RR)
    int waiting;    // Waiting Time
    int turnaround; // Turnaround Time

    Process(int pid, int burst) {
        this.pid = pid;
        this.burst = burst;
        this.remaining = burst;
        this.waiting = 0;
        this.turnaround = 0;
    }
}

public class RoundRobin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input the number of processes
        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        List<Process> processes = new ArrayList<>();

        // Input process burst times
        for (int i = 0; i < n; i++) {
            System.out.print("Enter burst time for Process P" + (i + 1) + ": ");
            int burst = sc.nextInt();
            processes.add(new Process(i + 1, burst));
        }

        // Input time quantum
        System.out.print("Enter time quantum: ");
        int quantum = sc.nextInt();

        int currentTime = 0;

        // Round Robin Scheduling
        while (true) {
            boolean allFinished = true;

            for (Process p : processes) {
                if (p.remaining > 0) {
                    allFinished = false;

                    // If the process burst is larger than quantum, reduce it
                    if (p.remaining > quantum) {
                        p.remaining -= quantum;
                        currentTime += quantum;
                    } else {
                        // If the process finishes within this quantum
                        currentTime += p.remaining;
                        p.waiting = currentTime - p.burst;   // Waiting Time = Completion Time - Burst Time
                        p.turnaround = p.waiting + p.burst;  // Turnaround Time = Waiting Time + Burst Time
                        p.remaining = 0;
                    }
                }
            }

            if (allFinished) {
                break;
            }
        }

        // Display results
        System.out.println("\nProcess\tBurst Time\tWaiting Time\tTurnaround Time");
        for (Process p : processes) {
            System.out.println("P" + p.pid + "\t" + p.burst + "\t\t" + p.waiting + "\t\t" + p.turnaround);
        }

        // Calculate and display average waiting and turnaround time
        double avgWT = processes.stream().mapToInt(p -> p.waiting).average().orElse(0.0);
        double avgTAT = processes.stream().mapToInt(p -> p.turnaround).average().orElse(0.0);

        System.out.printf("\nAverage Waiting Time: %.2f\n", avgWT);
        System.out.printf("Average Turnaround Time: %.2f\n", avgTAT);
    }
}