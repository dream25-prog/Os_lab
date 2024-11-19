import java.util.*;

public class PageReplacement {
    // Function to check if a page is present in the frame
    private static boolean isPresent(List<Integer> frames, int page) {
        return frames.contains(page);
    }

    // FIFO Page Replacement Algorithm
    public static int fifo(List<Integer> pages, int capacity) {
        Queue<Integer> queue = new LinkedList<>();
        List<Integer> frames = new ArrayList<>();
        int pageFaults = 0;

        for (int page : pages) {
            if (!isPresent(frames, page)) {
                pageFaults++;
                if (frames.size() < capacity) {
                    frames.add(page);
                } else {
                    // Remove the first inserted page (FIFO logic)
                    int removedPage = queue.poll();
                    frames.remove(Integer.valueOf(removedPage));
                    frames.add(page);
                }
                queue.add(page);
            }
        }
        return pageFaults;
    }

    // LRU Page Replacement Algorithm
    public static int lru(List<Integer> pages, int capacity) {
        Map<Integer, Integer> recent = new HashMap<>();
        List<Integer> frames = new ArrayList<>();
        int pageFaults = 0;

        for (int i = 0; i < pages.size(); i++) {
            int page = pages.get(i);
            if (!isPresent(frames, page)) {
                pageFaults++;
                if (frames.size() < capacity) {
                    frames.add(page);
                } else {
                    // Find the least recently used page
                    int lruPage = pages.size();
                    int removePage = -1;
                    for (int frame : frames) {
                        if (recent.get(frame) < lruPage) {
                            lruPage = recent.get(frame);
                            removePage = frame;
                        }
                    }
                    frames.remove(Integer.valueOf(removePage));
                    frames.add(page);
                }
            }
            recent.put(page, i);
        }
        return pageFaults;
    }

    // Optimal Page Replacement Algorithm
    public static int optimal(List<Integer> pages, int capacity) {
        List<Integer> frames = new ArrayList<>();
        int pageFaults = 0;

        for (int i = 0; i < pages.size(); i++) {
            int page = pages.get(i);
            if (!isPresent(frames, page)) {
                pageFaults++;
                if (frames.size() < capacity) {
                    frames.add(page);
                } else {
                    // Predict the future and replace the page not needed for the longest time
                    int furthest = i, removePage = -1;
                    for (int frame : frames) {
                        int nextUse = -1;
                        for (int j = i + 1; j < pages.size(); j++) {
                            if (pages.get(j) == frame) {
                                nextUse = j;
                                break;
                            }
                        }
                        if (nextUse == -1) { // This page is not needed in the future
                            removePage = frame;
                            break;
                        } else if (nextUse > furthest) {
                            furthest = nextUse;
                            removePage = frame;
                        }
                    }
                    frames.remove(Integer.valueOf(removePage));
                    frames.add(page);
                }
            }
        }
        return pageFaults;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of pages in the reference string: ");
        int n = sc.nextInt();
        List<Integer> pages = new ArrayList<>();
        System.out.print("Enter the page reference string (space-separated integers): ");
        for (int i = 0; i < n; i++) {
            pages.add(sc.nextInt());
        }
        System.out.print("Enter the number of page frames: ");
        int capacity = sc.nextInt();

        System.out.print("Reference String: ");
        for (int page : pages) {
            System.out.print(page + " ");
        }
        System.out.println();

        System.out.println("FIFO Page Faults: " + fifo(pages, capacity));
        System.out.println("LRU Page Faults: " + lru(pages, capacity));
        System.out.println("Optimal Page Faults: " + optimal(pages, capacity));

        sc.close();
    }
}