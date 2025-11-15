import java.io.*;
import java.util.*;

public class SixDegreeOfSeparation {
    private Map<Integer, Set<Integer>> graph = new HashMap<>();

    public void loadGraph(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) continue;
            String[] parts = line.split("\\s+");
            if (parts.length != 2) continue;
            int u = Integer.parseInt(parts[0]);
            int v = Integer.parseInt(parts[1]);
            graph.computeIfAbsent(u, k -> new HashSet<>()).add(v);
            graph.computeIfAbsent(v, k -> new HashSet<>()).add(u);
        }
        br.close();
    }

    public int degreeOfSeparation(int src, int dest) {
        if (src == dest) return 0;
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        Map<Integer, Integer> degree = new HashMap<>();
        queue.add(src);
        visited.add(src);
        degree.put(src, 0);

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            int d = degree.get(curr);
            for (int neighbor : graph.getOrDefault(curr, Collections.emptySet())) {
                if (!visited.contains(neighbor)) {
                    degree.put(neighbor, d + 1);
                    if (neighbor == dest)
                        return d + 1;
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return -1;
    }

    // NEW METHOD TO GET THE PATH
    public List<Integer> getPath(int src, int dest) {
        if (src == dest) return Arrays.asList(src);
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        Map<Integer, Integer> prev = new HashMap<>(); // for backtracking path
        queue.add(src);
        visited.add(src);
        prev.put(src, null);

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int neighbor : graph.getOrDefault(curr, Collections.emptySet())) {
                if (!visited.contains(neighbor)) {
                    prev.put(neighbor, curr);
                    if (neighbor == dest) {
                        // Build path
                        LinkedList<Integer> path = new LinkedList<>();
                        Integer node = dest;
                        while (node != null) {
                            path.addFirst(node);
                            node = prev.get(node);
                        }
                        return path;
                    }
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return Collections.emptyList(); // unreachable
    }
}