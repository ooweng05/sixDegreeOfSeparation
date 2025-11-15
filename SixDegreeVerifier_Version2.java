import java.util.*;
import java.io.*;

public class SixDegreeVerifier {
    private Map<Integer, List<Integer>> graph = new HashMap<>();

    // Add connection between two nodes (undirected)
    public void addConnection(int node1, int node2) {
        graph.computeIfAbsent(node1, k -> new ArrayList<>()).add(node2);
        graph.computeIfAbsent(node2, k -> new ArrayList<>()).add(node1);
    }

    // Load edges from a txt file
    public void loadEdgesFromFile(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 2) {
                    int u = Integer.parseInt(parts[0]);
                    int v = Integer.parseInt(parts[1]);
                    addConnection(u, v);
                }
            }
        }
    }

    // Shortest distance using BFS
    public int shortestDistance(int start, int target) {
        if (!graph.containsKey(start) || !graph.containsKey(target)) return Integer.MAX_VALUE;
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        queue.add(start);
        visited.add(start);
        int degree = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int current = queue.poll();
                if (current == target) return degree;
                for (int neighbor : graph.get(current)) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.add(neighbor);
                    }
                }
            }
            degree++;
            if (degree > 6) break;
        }
        return Integer.MAX_VALUE;
    }

    // Verify six degrees condition
    public boolean verifySixDegrees() {
        for (int node1 : graph.keySet()) {
            for (int node2 : graph.keySet()) {
                if (node1 != node2 && shortestDistance(node1, node2) > 6) {
                    System.out.println(node1 + " and " + node2 + " are more than 6 degrees apart.");
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        SixDegreeVerifier verifier = new SixDegreeVerifier();
        verifier.loadEdgesFromFile("edges.txt");
        System.out.println("Six degrees verified: " + verifier.verifySixDegrees());
    }
}