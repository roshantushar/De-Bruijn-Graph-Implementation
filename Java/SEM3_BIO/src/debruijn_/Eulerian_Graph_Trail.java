package debruijn_;

import java.util.ArrayList;
import java.util.List;

public class Eulerian_Graph_Trail {
	private int vertices;
	private ArrayList<Integer>[] adj;
	List<Edge> EulerianResult = new ArrayList<>();

	Eulerian_Graph_Trail(int numOfVertices) {
		this.vertices = numOfVertices;
		initGraph();
	}

	@SuppressWarnings("unchecked")
	public void initGraph() {
		adj = new ArrayList[vertices];
		for (int i = 0; i < vertices; i++) {
			adj[i] = new ArrayList<>();
		}
	}

	void addEdge(Integer u, Integer v) {
		adj[u].add(v);
		adj[v].add(u);
	}

	public void removeEdge(Integer u, Integer v) {
		adj[u].remove(v);
		adj[v].remove(u);
	}

	public List<Edge> printEulerTour(Integer u) {
		return printEulerUtil(u);
	}

	public List<Edge> printEulerUtil(Integer u) {
		for (int i = 0; i < adj[u].size(); i++) {
			Integer v = adj[u].get(i);
			if (isValidNextEdge(u, v)) {
				// System.out.print("("+u+ "-" + v + ")");
				EulerianResult.add(new Edge(u, v));
				removeEdge(u, v);
				printEulerUtil(v);
			}
		}
		return EulerianResult;
	}

	public boolean isValidNextEdge(Integer u, Integer v) {
		if (adj[u].size() == 1) {
			return true;
		}
		boolean[] isVisited = new boolean[this.vertices];
		int count1 = dfsCount(u, isVisited);
		removeEdge(u, v);
		isVisited = new boolean[this.vertices];
		int count2 = dfsCount(u, isVisited);
		addEdge(u, v);
		return (count1 > count2) ? false : true;
	}

	public int dfsCount(Integer v, boolean[] isVisited) {
		isVisited[v] = true;
		int count = 1;
		for (int adj : adj[v]) {
			if (!isVisited[adj]) {
				count = count + dfsCount(adj, isVisited);
			}
		}
		return count;
	}
}