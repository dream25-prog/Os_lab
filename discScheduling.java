

        // Move right (towards the end of the disk)
        for (int i = index; i < requests.size(); i++) {
            seekTime += Math.abs(head - requests.get(i));
            head = requests.get(i);
        }

        // Move back left (towards the spindle)
        for (int i = index - 1; i >= 0; i--) {
            seekTime += Math.abs(head - requests.get(i));
            head = requests.get(i);
        }

        return seekTime;
    }

    // Function to calculate the total seek time for C-LOOK (Circular Look
    // Algorithm)
    public static int cLook(List<Integer> requests, int head) {
        int seekTime = 0;
        Collections.sort(requests);

        // Find the index where the head starts
        int index = 0;
        while (index < requests.size() && requests.get(index) < head) {
            index++;
        }

        // Move right (towards the end of the disk)
        for (int i = index; i < requests.size(); i++) {
            seekTime += Math.abs(head - requests.get(i));
            head = requests.get(i);
        }

        // Jump to the first request (circular move) and move right again
        for (int i = 0; i < index; i++) {
            seekTime += Math.abs(head - requests.get(i));
            head = requests.get(i);
        }

        return seekTime;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of disk requests: ");
        int n = scanner.nextInt();

        List<Integer> requests = new ArrayList<>();
        System.out.print("Enter the disk requests (positions on the disk): ");
        for (int i = 0; i < n; i++) {
            requests.add(scanner.nextInt());
        }

        System.out.print("Enter the initial head position: ");
        int head = scanner.nextInt();

        System.out.print("Enter the disk size: ");
        int diskSize = scanner.nextInt();

        System.out.println("\nSSTF Total Seek Time: " + sstf(requests, head));
        System.out.println("SCAN Total Seek Time: " + scan(requests, head, diskSize));
        System.out.println("C-LOOK Total Seek Time: " + cLook(requests, head));

        scanner.close();
    }
}
