import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

/**
 * Your implementation of 4 different graph algorithms.
 *
 * @author John Blasco jblasco6
 * @version 1.0
 */
public class GraphAlgs {

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * {@code start} which represents the starting vertex. You will be
     * modifying the empty list passed in to contain the vertices in
     * visited order. The start vertex should be at the beginning of the list
     * and the last vertex visited should be at the end.  (You may assume the
     * list is empty in the beginning).
     *
     * This method should utilize the adjacency matrix represented graph.
     *
     * When deciding which neighbors to visit next from a vertex, visit starting
     * with the vertex at index 0 to the vertex at index |V| - 1. Failure to do
     * so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * most if not all points for this method.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.Map},
     * {@code java.util.List}, and any classes that implement the
     * aforementioned interfaces, as long as it is efficient.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph in an adjacency matrix format to search
     * @param dfsList the list of visited vertices in order. This list will be
     * empty initially. You will be adding to this list as you perform dfs.
     * @return true if the graph is connected (you were able to reach every
     * vertex and edge from {@code start}), false otherwise
     */
    public static <T> boolean depthFirstSearch(Vertex<T> start,
                                            GraphAdjMatrix<T> graph,
                                            List<Vertex<T>> dfsList) {

        if (start == null) {
            throw new IllegalArgumentException("Starting vertex can not be "
                    + "null.");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph to search can not be"
                    + " null.");
        }
        if (dfsList == null) {
            throw new IllegalArgumentException("A valid list for the visited "
                    + "vertices must be inputted");
        }
        if (start.getId() >= graph.getVertices().size() || start.getId() < 0) {
            throw new IllegalArgumentException("Starting vertex must exist in"
                    + " the graph being search");
        }
        // recursively add each vertex to the dfs list

        dfsHelper(start, graph, dfsList, new Stack<>(), new HashSet<>());

        //compare dfs list to a set of vertices if contains has false
        // return false indicating a non connected graph
        if (dfsList.size() == graph.getVertices().size()) {
            return true;
        }

        return false;
    }


    /**
     *  Helper method for depth first search. Used for recursicve calls
     * @param curr Current node search is observing
     * @param graph the graph in adjacency matrix form to search
     * @param dfsList List of visit vertices in order
     * @param toVisit stack of potential vertices to visit
     * @param visited set of visited vertices
     * @param <T> the generic type of the data
     */
    private static <T> void dfsHelper(Vertex<T> curr,
                               GraphAdjMatrix<T> graph,
                               List<Vertex<T>> dfsList,
                               Stack<Vertex<T>> toVisit,
                                      Set<Vertex<T>> visited) {

        if (!visited.contains(curr)) {
            // DFSList to maintain order
            dfsList.add(curr);
            // Visited set for quicker lookup
            visited.add(curr);

            for (int i = graph.getAdjMatrix().length - 1; i >= 0; --i) {
                Integer matrixValue = graph.getAdjMatrix()[curr.getId()][i];
                if (matrixValue != null
                        && !visited.contains(graph.getVertices().get(i))) {
                    // use set to compare in 0(1) value obtained from List
                    // in O(1)
                    toVisit.add(graph.getVertices().get(i));
                }
            }
        }
        // Pop value off the stack and recursively call
        if (!toVisit.empty() && dfsList.size() < graph.getVertices().size()) {
            dfsHelper(toVisit.pop(), graph, dfsList, toVisit, visited);
        }

    }

    /**
     * Find the single source shortest distance between the start vertex and
     * all vertices given a weighted graph using Dijkstra's shortest path
     * algorithm.
     *
     * Return a map of the shortest distances such that the key of each entry is
     * a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing infinity)
     * if no path exists. You may assume that going from a vertex to itself
     * has a shortest distance of 0.
     *
     * This method should utilize the adjacency list represented graph.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and {@code java.util.Set} and any class that
     * implements the aforementioned interfaces.
     *
     * You should implement CLASSIC Dijkstra's, which is the version of the
     * algorithm that terminates once you've "visited" all of the nodes.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if start doesn't exist in the graph.
     * @throws IllegalStateException if any of the edges are negative
     * @param <T> the generic typing of the data
     * @param start index representing which vertex to start at (source)
     * @param graph the Graph we are searching using an adjacency List
     * @return a map of the shortest distances from start to every other node
     *         in the graph
     */
    public static <T> Map<Vertex<T>, Integer> shortPathDijk(Vertex<T> start,
                                                      GraphAdjList<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Starting vertex "
                    + "can not be null.");
        }
        if (graph == null) {
            throw new IllegalArgumentException("Graph can not be null.");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Starting vertex does not exist"
                    + " in graph.");
        }

        Set<Vertex<T>> visitVertices = new HashSet<>();
        PriorityQueue<Edge<T>> queue = new PriorityQueue<>();
        Map<Vertex<T>, Integer> distances = new HashMap<>();

        for (Vertex<T> vertex: graph.getVertices()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }

        queue.add(new Edge<>(start, start, 0));

        int runningDistance = 0;
        while (!queue.isEmpty()
                && visitVertices.size() < graph.getVertices().size()) {

            Edge<T> tempEdge = queue.poll();
            Vertex<T> tempVert = tempEdge.getV();

            if (!visitVertices.contains(tempVert)) {
                visitVertices.add(tempVert);

                if (distances.get(tempEdge.getU()) != Integer.MAX_VALUE) {
                    runningDistance = distances.get(tempEdge.getU());
                }

                // add edges of this vertex to queue
                for (Edge<T> edge : graph.getAdjList().get(tempVert)) {
                    if (edge.getWeight() < 0) {
                        throw new IllegalStateException("An edge in the graph "
                                + "has a negative value");
                    }

                    if (!visitVertices.contains(edge.getV())
                            && runningDistance + edge.getWeight()
                            <  distances.get(edge.getV())) {
                        // add to priority queue
                        queue.add(edge);
                    }
                }

                distances.put(tempVert, runningDistance + tempEdge.getWeight());
            }
        }

        return distances;
    }

    /**
     * Run Prim's algorithm on the given graph and return the MST/MSF
     * in the form of a set of Edges.  If the graph is disconnected, and
     * therefore there is no valid MST, return a minimal spanning forest (MSF).
     *
     * This method should utilize the adjacency list represented graph.
     *
     * A minimal spanning forest (MSF) is just a generalized version of the MST
     * for disconnected graphs. After the MST algorithm finishes, just check to
     * see if there are still some vertices that are not connected to the
     * MST/MSF. If all vertices have been visited, you are done. If not, run
     * the algorithm again on an unvisited vertex.
     *
     * You may assume that all of the edge weights are unique (THIS MEANS THAT
     * THE MST/MSF IS UNIQUE FOR THE GRAPH, REGARDLESS OF STARTING VERTEX!!)
     * Although, if your algorithm works correctly, it should work even if the
     * MST/MSF is not unique, this is just for testing purposes.
     *
     * You should not allow for any self-loops in the MST/MSF. Additionally,
     * you may assume that the graph is undirected.
     *
     * You may import/use {@code java.util.PriorityQueue} and
     * {@code java.util.Set} and any class that
     * implements the aforementioned interfaces.
     *
     * @throws IllegalArgumentException if any input is null
     * @param <T> the generic typing of the data
     * @param graph the Graph we are searching using an adjacency list
     * @return the MST/MSF of the graph
     */
    public static <T> Set<Edge<T>> mstPrim(GraphAdjList<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph can not be null.");
        }

        PriorityQueue<Edge<T>> queue = new PriorityQueue<>();
        Iterator<Vertex<T>> it = graph.getVertices().iterator();
        Set<Edge<T>> msf = new HashSet<>();
        Set<Vertex<T>> visitedVertices = new HashSet<>();

        while (it.hasNext()
                && msf.size() < 2 * graph.getVertices().size() - 2) {

            Vertex<T> startingVertex = it.next();

            if (!visitedVertices.contains(startingVertex)) {

                queue.clear();
                queue.addAll(graph.getAdjList().get(startingVertex));
                visitedVertices.add(startingVertex);

                while (!queue.isEmpty()
                        && msf.size() < 2 * graph.getVertices().size() - 1) {

                    Edge<T> currentEdge = queue.poll();

                    if (!(visitedVertices.contains(currentEdge.getV())
                            && visitedVertices.contains(currentEdge.getU()))) {

                        msf.add(currentEdge);

                        for (Edge<T> edge
                                : graph.getAdjList().get(currentEdge.getV())) {

                            if (edge.getV().equals(currentEdge.getU())) {
                                // if current edge is a-->d
                                // add d-->a to minimum spanning tree
                                msf.add(edge);
                            } else {
                                queue.add(edge);
                            }
                        }

                        visitedVertices.add(currentEdge.getV());
                    }
                }
            }
        }

        return msf;
    }

    /**
     * Run Kruskal's algorithm on the given graph and return the MST/MSF
     * in the form of a set of Edges.  If the graph is disconnected, and
     * therefore there is no valid MST, return a minimal spanning forest (MSF).
     *
     * This method should utilize the adjacency list represented graph.
     *
     * A minimal spanning forest (MSF) is just a generalized version of the MST
     * for disconnected graphs. Unlike Prim's algorithm, Kruskal's algorithm
     * will naturally return a MSF if the graph is disconnected.
     *
     * You may assume that all of the edge weights are unique (THIS MEANS THAT
     * THE MST/MSF IS UNIQUE FOR THE GRAPH.) Although, if your algorithm works
     * correctly, it should work even if the MST/MSF is not unique, this is
     * just for testing purposes.
     *
     * You should not allow for any self-loops in the MST/MSF. Additionally,
     * you may assume that the graph is undirected.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you.  A Disjoint Set will keep track of which vertices are
     * connected to each other by the edges you've chosen for your MST/MSF.
     * Without a Disjoint Set, it is possible for Kruskal's to omit edges that
     * should be in the final MST/MSF.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface.
     *
     * @throws IllegalArgumentException if any input is null
     * @param <T> the generic typing of the data
     * @param graph the Graph we are searching using an adjacency list
     * @return the MST/MSF of the graph
     */
    public static <T> Set<Edge<T>> mstKruskal(GraphAdjList<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph can not be null.");
        }

        Set<Edge<T>> msf = new HashSet<>();
        DisjointSet<Vertex<T>> disjointSet
                = new DisjointSet<>(graph.getVertices());

        PriorityQueue<Edge<T>> queue = new PriorityQueue<>();
        queue.addAll(graph.getEdges());

        while (!queue.isEmpty()
                && msf.size() < 2 * graph.getVertices().size() - 2) {

            Edge<T> minEdge = queue.poll();

            if (disjointSet.find(minEdge.getU())
                    != disjointSet.find(minEdge.getV())) {
                msf.add(minEdge);

                for (Edge<T> edge : graph.getAdjList().get(minEdge.getV())) {
                    if (edge.getV().equals(minEdge.getU())) {
                        msf.add(edge);
                    }
                }

                disjointSet.union(minEdge.getU(), minEdge.getV());
            }
        }

        return msf;
    }
}