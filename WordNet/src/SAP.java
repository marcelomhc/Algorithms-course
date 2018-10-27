import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    private final Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph digraph) {
        if(digraph == null) {
            throw new IllegalArgumentException();
        }
        this.digraph = new Digraph(digraph);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        BreadthFirstDirectedPaths vSearch = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wSearch = new BreadthFirstDirectedPaths(digraph, w);

        int commonAncestor = getCommonAncestor(v, w, vSearch, wSearch);
        if(commonAncestor != -1) {
            return vSearch.distTo(commonAncestor) + wSearch.distTo(commonAncestor);
        }
        return -1;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths vSearch = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wSearch = new BreadthFirstDirectedPaths(digraph, w);

        return getCommonAncestor(v, w, vSearch, wSearch);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateSet(v);
        validateSet(w);
        BreadthFirstDirectedPaths vSearch = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wSearch = new BreadthFirstDirectedPaths(digraph, w);

        int commonAncestor = getCommonAncestor(v, w, vSearch, wSearch);
        if (commonAncestor != -1) {
            return vSearch.distTo(commonAncestor) + wSearch.distTo(commonAncestor);
        }
        return -1;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateSet(v);
        validateSet(w);
        BreadthFirstDirectedPaths vSearch = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wSearch = new BreadthFirstDirectedPaths(digraph, w);

        return getCommonAncestor(v, w, vSearch, wSearch);
    }

    private void validateSet(Iterable<Integer> iterable) {
        if(iterable == null) {
            throw new IllegalArgumentException("Argument cant be null");
        }
        for (Integer i : iterable) {
            validateInteger(i);
        }
    }

    private void validateInteger(Integer i) {
        if (i == null) {
            throw new IllegalArgumentException("Vertex cant be null");
        }
    }

    private int getCommonAncestor(int v, int w, BreadthFirstDirectedPaths vSearch, BreadthFirstDirectedPaths wSearch) {
        if(v == w) {
            return v;
        }
        boolean[] visited = new boolean[digraph.V()];
        visited[v] = true;
        visited[w] = true;

        Queue<Integer> qV = new Queue<>();
        Queue<Integer> qW = new Queue<>();
        qV.enqueue(v);
        qW.enqueue(w);

        return doubleBFS(vSearch, wSearch, visited, qV, qW);
    }

    private int getCommonAncestor(Iterable<Integer> sourcesV, Iterable<Integer> sourcesW, BreadthFirstDirectedPaths vSearch, BreadthFirstDirectedPaths wSearch) {
        boolean[] visited = new boolean[digraph.V()];
        Queue<Integer> qV = new Queue<>();
        Queue<Integer> qW = new Queue<>();

        for (int v : sourcesV) {
            visited[v] = true;
            qV.enqueue(v);
        }
        for (int w : sourcesW) {
            visited[w] = true;
            qW.enqueue(w);
        }

        return doubleBFS(vSearch, wSearch, visited, qV, qW);
    }

    private int doubleBFS(BreadthFirstDirectedPaths vSearch, BreadthFirstDirectedPaths wSearch, boolean[] visited, Queue<Integer> qV, Queue<Integer> qW) {
        int ancestor;
        int dist = Integer.MAX_VALUE;
        int steps = 0;
        int shortestAncestor = -1;
        while (!qV.isEmpty() || !qW.isEmpty()) {
            ancestor = singleBfsStep(wSearch, visited, qV);
            if(ancestor != -1 && wSearch.distTo(ancestor) < dist) {
                shortestAncestor = ancestor;
                dist = wSearch.distTo(shortestAncestor);
            }

            ancestor = singleBfsStep(vSearch, visited, qW);
            if(ancestor != -1 && vSearch.distTo(ancestor) < dist) {
                shortestAncestor = ancestor;
                dist = vSearch.distTo(shortestAncestor);
            }
            steps++;
            if (steps > dist) {
                return shortestAncestor;
            }
        }
        return shortestAncestor;
    }

    private int singleBfsStep(BreadthFirstDirectedPaths bfdp, boolean[] visited, Queue<Integer> queue) {
        if (queue.isEmpty()) {
            return -1;
        }
        int vertex = queue.dequeue();
        for (int adj : digraph.adj(vertex)) {
            if (!visited[adj]) {
                visited[adj] = true;
                queue.enqueue(adj);
            }
        }
        if (bfdp.hasPathTo(vertex)) {
            return vertex;
        }
        return -1;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph digraph = new Digraph(in);
        SAP sap = new SAP(digraph);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}