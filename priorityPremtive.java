import java.util.*;

class Process {
    int pid; // Process ID
    int arrival; // Arrival Time
    int burst; // Burst Time
    int priority; // Priority (lower value = higher priority)
    int remaining; // Remaining Time (used for preemption)
    int finish; // Finish Time
    int tat; // Turnaround Time
    int waiting; // Waiting Time

    Process(int pid, int arrival, int burst, int priority) {
        this.pid = pid;
        this.arrival = arrival;
        this.burst = burst;
        this.priority = priority;
        this.remaining = burst;
    }
}

public class PriorityPreemptive {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input the number of processes
        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        List<Process> processes = new ArrayList<>();

        // Input process details
        for (int i = 0; i < n; i++) {
            System.out.println("Enter details for process P" + (i + 1) + ":");
            System.out.print("Arrival Time: ");
            int arrival = sc.nextInt();
            System.out.print("Burst Time: ");
            int burst = sc.nextInt();
            System.out.print("Priority: ");
            int priority = sc.nextInt();
            processes.add(new Process(i + 1, arrival, burst, priority));
        }

        // Sort by Arrival Time
        processes.sort(Comparator.comparingInt(p -> p.arrival));

        int currentTime = 0;
        int completed = 0;

        // Loop until all processes are completed
        while (completed < n) {
            Process currentProcess = null;

            // Find the highest priority process that has arrived
            for (Process p : processes) {
                if (p.arrival <= currentTime && p.remaining > 0) {
                    if (currentProcess == null || p.priority < currentProcess.priority ||
                            (p.priority == currentProcess.priority && p.arrival < currentProcess.arrival)) {
                        currentProcess = p;
                    }
                }
            }

            if (currentProcess != null) {
                // Execute the process for 1 unit of time
                currentProcess.remaining--;
                currentTime++;

                // If the process is completed
                if (currentProcess.remaining == 0) {
                    completed++;
                    currentProcess.finish = currentTime;
                    currentProcess.tat = currentProcess.finish - currentProcess.arrival;
                    currentProcess.waiting = currentProcess.tat - currentProcess.burst;
                }
            } else {
                // If no process is ready, increment time
                currentTime++;
            }
        }

        // Display results
        System.out.println("\nProcess\tArrival\tBurst\tPriority\tFinish\tTAT\tWT");
        for (Process p : processes) {
            System.out.println("P" + p.pid + "\t" + p.arrival + "\t" + p.burst + "\t" +
                    p.priority + "\t\t" + p.finish + "\t" + p.tat + "\t" + p.waiting);
        }

        // Calculate and display average TAT and WT
        double avgTAT = processes.stream().mapToDouble(p -> p.tat).average().orElse(0.0);
        double avgWT = processes.stream().mapToDouble(p -> p.waiting).average().orElse(0.0);

        System.out.printf("\nAverage Turnaround Time: %.2f\n", avgTAT);
        System.out.printf("Average Waiting Time: %.2f\n", avgWT);
    }
}