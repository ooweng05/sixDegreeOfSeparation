import java.io.*;
import java.util.*;

/**
 * Adjacency matrix implementation for the FriendshipGraph interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2016.
 */
public class AdjMatrix<T extends Object> implements FriendshipGraph<T> {

    private ArrayList<T> Vs;
    private int[][] adj;
    private int disconnectedDist = -1;

    /**
     * Constructs empty graph.
     */
    public AdjMatrix() {
        Vs = new ArrayList<T>();
        adj = new int[4000][4000];
    } // end of AdjMatrix()

    public void addVertex(T vertLabel) {
        if (!Vs.contains(vertLabel)) Vs.add(vertLabel);
    } // end of addVertex()

    public void addEdge(T srcLabel, T tarLabel) {
        // Implement me!
        adj[Vs.indexOf(srcLabel)][Vs.indexOf(tarLabel)] = 1;
        adj[Vs.indexOf(tarLabel)][Vs.indexOf(srcLabel)] = 1;
    } // end of addEdge()

    public ArrayList<T> neighbours(T vertLabel) {
        ArrayList<T> neighbours = new ArrayList<T>();
        // Implement me!
        int[] vEdge = adj[Vs.indexOf(vertLabel)];
        for (int i = 0; i < vEdge.length; i++) {
            if (vEdge[i] == 1) {
                neighbours.add(Vs.get(i));
            }
        }
        return neighbours;
    } // end of neighbours()

    public void removeVertex(T vertLabel) {
        Vs.set(Vs.indexOf(vertLabel), null);
    } // end of removeVertex()

    public void removeEdge(T srcLabel, T tarLabel) {
        // Implement me!
        adj[Vs.indexOf(srcLabel)][Vs.indexOf(tarLabel)] = 0;
        adj[Vs.indexOf(tarLabel)][Vs.indexOf(srcLabel)] = 0;
    } // end of removeEdges()

    public void printVertices(PrintWriter os) {
        // Implement me!
        for (T v : Vs){
            if (v != null){
                os.print(v + " ");
            }
        }
        os.println();
    } // end of printVertices()


    public void printEdges(PrintWriter os) {
        // Implement me!
        for (T v : Vs) {
            if (v != null) {
                ArrayList<T> neighbours = neighbours(v);
                for (T neighbour : neighbours) {
                    if (neighbour != null) {
                        os.println(v + " " + neighbour);
                    }
                }
            }
        }
    } // end of printEdges()

    public int shortestPathDistance(T vertLabel1, T vertLabel2) {
        // Implement me!
        if (vertLabel1.equals(vertLabel2)){
            return 0;
        } else {
            Queue<T> que = new LinkedList<T>();
            Set<T> visited = new HashSet<T>();
            ArrayList<T> tempList;
            T tempT, swapT;

            //Add node to queue
            que.add(vertLabel1);

            int i = 0;
            while (i >= 0) {
                if (que.isEmpty()) {
                    break;
                }
                tempT = que.remove();
                visited.add(tempT);

                tempList = neighbours(tempT);
                while (!tempList.isEmpty()) {
                    swapT = tempList.remove(0);
                    if (swapT.equals(vertLabel2)){
                        return i + 1;
                    }
                    if (!visited.contains(swapT)) {
                        que.add(swapT);
                    }
                }
                i++;
            }
        }
        // if we reach this point, source and target are disconnected
        return disconnectedDist;
    } // end of shortestPathDistance()

} // end of class AdjMatrix