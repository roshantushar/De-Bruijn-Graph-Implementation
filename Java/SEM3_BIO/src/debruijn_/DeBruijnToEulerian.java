package debruijn_;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DeBruijnToEulerian {
//list to store repSequences
	List<String> repSequences = new ArrayList<>();

	// function to print adjacency matrix
	public void printAdjacency(int nodes, int[][] adjacencymatrix) {
		System.out.println("\nAdjacency Matrix =\n");
		for (int i = 0; i <= nodes; i++) {
			for (int j = 0; j <= nodes; j++) {
				System.out.print(adjacencymatrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	// function to print the indegree from the adjacency matrix
	public int[] InDegree(int nodes, int[][] adjacencymatrix) {
		int[] indeg = new int[nodes + 1];
		int nodenum = 0;
		int temp = 0;
		for (int i = 0; i <= nodes; i++) {
			for (int j = 0; j <= nodes; j++) {
				if (adjacencymatrix[j][i] == 1)
					temp = temp + 1;
			}
			nodenum = nodenum + 1;
			indeg[i] = temp;
			temp = 0;
		}
		return indeg;
	}

	// function to print the outdegree from the adjacency matrix
	public int[] OutDegree(int nodes, int[][] adjacencymatrix) {
		int[] outdeg = new int[nodes + 1];
		int nodenum = 0;
		int temp = 0;
		for (int i = 0; i <= nodes; i++) {
			for (int j = 0; j <= nodes; j++) {
				if (adjacencymatrix[i][j] == 1)
					temp = temp + 1;
			}
			nodenum = nodenum + 1;
			// System.out.println("Out Degree of " + nodenum + " = " + temp);
			outdeg[i] = temp;
			temp = 0;
		}
		return outdeg;
	}

	// adjacent matrix creation from edge list
	public int[][] AdjacencyMatrix(List<Edge> edgeList, int nodes) {
		int[][] adjacencymatrix = new int[nodes + 1][nodes + 1];
		for (int i = 0; i < edgeList.size(); i++) {
			Edge currentEdge = edgeList.get(i);
			int fromnode = currentEdge.fromnode;
			int tonode = currentEdge.tonode;
			adjacencymatrix[fromnode][tonode] = 1;
		}
		return adjacencymatrix;
	}

	// function to print an integer array
	public void printArray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
	}

	// function to break into kmers
	public List<String> kmer(String sequence, int kmerlen) {
		if (kmerlen < sequence.length()) {
			int windowstart = 0;
			int windowend = kmerlen;
			int looptime = sequence.length() - kmerlen + 1;
			List<String> kmerlist = new ArrayList<String>();
			for (int i = 0; i < looptime; i++) {
				// System.out.println(sequence.substring(windowstart,windowend));
				kmerlist.add(sequence.substring(windowstart, windowend));
				windowstart++;
				windowend++;
			}

			return kmerlist;
		} else
			throw new IndexOutOfBoundsException("Kmer Length is greater the Sequence Length");
	}

	// function to print the string list
	public void printStringList(List<String> kmerlist) {
		for (String kmer : kmerlist)
			System.out.print(kmer + " ");
	}

	// function to print the integer list
	public void printIntegerList(List<Integer> numberlist) {
		for (Integer element : numberlist)
			System.out.print(element + " ");
	}

	// function to sort in alphabetical order
	public List<String> lexographic_order(String sequence, int kmerlen) {
		List<String> before_sort = kmer(sequence, kmerlen);
		Object[] arraylist = before_sort.toArray();
		int length = arraylist.length;
		String[] stringArray = new String[length];
		for (int i = 0; i < length; i++) {
			stringArray[i] = (String) arraylist[i];
		}
		for (int i = 0; i < length - 1; i++) {
			for (int j = i + 1; j < stringArray.length; j++) {
				if (stringArray[i].compareTo(stringArray[j]) > 0) {
					String temp = stringArray[i];

					stringArray[i] = stringArray[j];
					stringArray[j] = temp;
				}
			}
		}
		LinkedList<String> sortedkmerlist = new LinkedList<String>(Arrays.asList(stringArray));
		return sortedkmerlist;
	}

	public void prinIndices(List<Edge> indices) {
		for (Edge element : indices)
			System.out.println("Left : " + element.fromnode + "------> Right  : " + element.tonode);
	}

	public void printEulerIndices(List<Edge> indices) {
		for (Edge element : indices)
			System.out.print("(" + element.fromnode + "-" + element.tonode + ")");
	}

	public void printSequenceGenerated(List<Edge> indicesafter, List<String> newReferenceList) {
		// System.out.print("\nRegenerated Sequence: ");
		String sequencereg = newReferenceList.get(indicesafter.get(0).fromnode);
		// System.out.print(newReferenceList.get(indicesafter.get(0).fromnode));
		for (int i = 1; i < indicesafter.size() - 1; i++) {
			// System.out.print(newReferenceList.get(indicesafter.get(i).tonode).substring(0,newReferenceList.get(indicesafter.get(0).fromnode).length()-1));
			sequencereg = sequencereg.concat(newReferenceList.get(indicesafter.get(i).tonode).substring(0,
					newReferenceList.get(indicesafter.get(0).fromnode).length() - 1));
		}
		// System.out.print(newReferenceList.get(indicesafter.get(indicesafter.size()-1).tonode));
		sequencereg = sequencereg.concat(newReferenceList.get(indicesafter.get(indicesafter.size() - 1).tonode));
		repSequences.add(sequencereg);
	}

	public ArrayList<Edge> indexCreateBefore(int leftrightsplitlen) {
		ArrayList<Edge> edgeList = new ArrayList<Edge>();
		int len = 0;
		while (len < leftrightsplitlen) {
			// System.out.println(len);
			edgeList.add(new Edge(len, len + 1));
			len += 2;
		}
		return edgeList;
	}

	public List<String> indexReferencenew(List<String> leftrightpairs) {
		List<String> referencelist = new ArrayList<>();
		for (int i = 0; i < leftrightpairs.size(); i++)
			for (int j = 0; j < leftrightpairs.size(); j++)
				if (leftrightpairs.get(i) != leftrightpairs.get(j))
					referencelist.add(leftrightpairs.get(i));
		Set<String> s = new LinkedHashSet<String>(referencelist);
		int n = s.size();
		List<String> newreferencelist = new ArrayList<String>(n);
		for (String x : s)
			newreferencelist.add(x);
		return newreferencelist;
	}

	public Hashtable<String, Integer> makeDictref(List<String> newReferenceList) {

		Hashtable<String, Integer> Dictionary = new Hashtable<String, Integer>();
		int index = 0;
		for (String element : newReferenceList) {
			Dictionary.put(element, index);
			index++;
		}
		return Dictionary;
	}

	public List<Edge> indexCreateAfter(List<String> leftrightpairs, List<Edge> indicesbefore,
			List<String> newReferenceList, Hashtable<String, Integer> Dictionary) {
		for (int i = 0; i < indicesbefore.size(); i++) {
			indicesbefore.get(i).fromnode = Dictionary.get(leftrightpairs.get(indicesbefore.get(i).fromnode));
			indicesbefore.get(i).tonode = Dictionary.get(leftrightpairs.get(indicesbefore.get(i).tonode));
		}

		return indicesbefore;
	}

	public List<String> LeftRightList(List<String> sortedkmerslist, int kmerlen) {
		System.out.println();
		List<String> leftrightpairs = new ArrayList<>();
		for (int i = 0; i < sortedkmerslist.size(); i++) {
			leftrightpairs.add(sortedkmerslist.get(i).substring(0, kmerlen - 1));
			leftrightpairs.add(sortedkmerslist.get(i).substring(kmerlen - (kmerlen - 1), kmerlen));
		}
		return leftrightpairs;
	}

	public void leftrightprint(List<String> leftrightpairs, List<Edge> indices) {
		for (int i = 0; i < indices.size(); i++)
			System.out.println("Left : " + leftrightpairs.get(indices.get(i).fromnode) + "------> Right : "
					+ leftrightpairs.get(indices.get(i).tonode));
	}

	public void printNewGraph(List<Edge> indicesafter, List<String> newReferenceList) {
		System.out.println("Edges : ");
		for (int i = 0; i < indicesafter.size(); i++) {
			System.out.println(newReferenceList.get(indicesafter.get(i).fromnode) + "---------------->"
					+ newReferenceList.get(indicesafter.get(i).tonode));
		}
	}

	public List<Integer> ifEulerPathExit(int[] indeg, int[] outdeg) {
		int flag1 = 0;
		int flag2 = 0;
		boolean flag3 = false;
		List<Integer> possiblestarts = new ArrayList<Integer>();
		for (int i = 0; i < indeg.length; i++) {
			if (indeg[i] - outdeg[i] == 1) {
				++flag1;
			} else if (Math.abs(outdeg[i] - indeg[i]) == 1) {
				flag2++;
				possiblestarts.add(i);
			} else if (indeg[i] == outdeg[i])
				flag3 = true;
			else
				flag3 = false;
		}
		if (flag1 == 1 & flag2 == 1 & flag3)
			return possiblestarts;
		else
			return null;
	}

	public boolean checkEulerianCycle(int[] indeg, int[] outdeg) {
		boolean flag = true;
		for (int i = 0; i < indeg.length; i++) {
			if (indeg[i] != outdeg[i])
				flag = false;
		}
		return flag;
	}

	public static void main(String[] args) {
		DeBruijnToEulerian obj = new DeBruijnToEulerian();

		long startTime = System.nanoTime();
		String[] sequence = { "GCGGTAATGCAGTTGAC" };
		for (int i = 0; i < sequence.length; i++) {
			System.out.println("Input Sequence       " + "                   : " + sequence[i]);
			int kmerlen = 10;
			List<String> kmerlist = new ArrayList<String>();
			System.out.print("Kmer List              " + "                 : ");
			kmerlist = obj.kmer(sequence[i], kmerlen);
			obj.printStringList(kmerlist);
			List<String> sortedkmerslist = new ArrayList<String>();
			System.out.print("\nKmer List Sorted in Lexicographic Order : ");
			sortedkmerslist = obj.lexographic_order(sequence[i], kmerlen);
			obj.printStringList(sortedkmerslist);

			List<String> leftrightpairs = obj.LeftRightList(sortedkmerslist, kmerlen);
			int leftrightpairslen = leftrightpairs.size();
			List<Edge> indicesbefore = obj.indexCreateBefore(leftrightpairslen);

			List<String> NewReferenceList = obj.indexReferencenew(leftrightpairs);
			Hashtable<String, Integer> Dictionary = obj.makeDictref(NewReferenceList);
			List<Edge> indicesafter = obj.indexCreateAfter(leftrightpairs, indicesbefore, NewReferenceList, Dictionary);
			System.out.println("\nIndices of De Bruijn Graph : ");
			obj.prinIndices(indicesafter);
			System.out.println("\nDe Bruijn Graph : ");

			obj.printNewGraph(indicesafter, NewReferenceList);

			List<Edge> edgeList = indicesafter;
			int nodes = edgeList.size();
			int[][] adjacencymatrix = obj.AdjacencyMatrix(edgeList, nodes);
			int[] indeg = new int[nodes];
			int[] outdeg = new int[nodes];
			List<Integer> possibleStarts = new ArrayList<>();
			List<Edge> EulerianResult = new ArrayList<>();

			indeg = obj.InDegree(nodes, adjacencymatrix);
			outdeg = obj.OutDegree(nodes, adjacencymatrix);
			possibleStarts = obj.ifEulerPathExit(indeg, outdeg);
			if (possibleStarts != null) {
				System.out.println("\n\nEuler Path Exists");

				if (obj.checkEulerianCycle(indeg, outdeg))
					System.out.println("Graph has Eulerian Cycle\n");
				for (int j = 0; j < 5000; j++) {
					Collections.shuffle(indicesafter);

					Eulerian_Graph_Trail newgraph = new Eulerian_Graph_Trail(indicesafter.size() + 1);
					for (int k = 0; k < possibleStarts.size(); k++) {
						for (int i1 = 0; i1 < indicesafter.size(); i1++) {
							newgraph.addEdge(indicesafter.get(i1).fromnode, indicesafter.get(i1).tonode);
						}

						EulerianResult = newgraph.printEulerTour(possibleStarts.get(k));
						obj.printSequenceGenerated(EulerianResult, NewReferenceList);
					}
				}

				System.out.print("Reference Dictionary    :" + Dictionary);
				System.out.print("\nIn Degree of Each Node  : ");
				obj.printArray(indeg);
				System.out.print("\nOut Degree of Each Node : ");
				obj.printArray(outdeg);
				System.out.print("\n\nPossible Starting Node  : ");
				System.out.print(NewReferenceList.get(possibleStarts.get(0)));
				System.out.println("\n\nLast Iteration Data Member Values :");
				System.out.print("Eulerian Path in Indices :");
				List<Edge> finalindices = obj.indexCreateAfter(leftrightpairs, EulerianResult, NewReferenceList,
						Dictionary);
				obj.printEulerIndices(finalindices);
				List<String> repSequences = obj.repSequences;
				Set<String> AllPossibleSequences = new HashSet<String>(repSequences);
				System.out.print("\n\nNumber of Unique Sequences Obtained : " + AllPossibleSequences.size() + "\n");
				Iterator<String> it = AllPossibleSequences.iterator();
				while (it.hasNext()) {
					System.out.println(it.next());
				}

			} else
				System.out.println("\nEuler Path Does Not Exist");
		}
		long endTime = System.nanoTime();
		long timeElapsed = endTime - startTime;

		System.out.println("Execution time in nanoseconds  : " + timeElapsed);

		System.out.println("Execution time in milliseconds : " + timeElapsed / 1000000);

	}

}

class Edge {
	int fromnode, tonode;

	public Edge(int fromnode, int tonode) {
		this.fromnode = fromnode;
		this.tonode = tonode;
	}
}