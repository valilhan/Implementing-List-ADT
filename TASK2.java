import java.util.*;
import java.io.*;
//Valihan_Ilyasov_BS19_02
//https://codeforces.com/group/3ZU2JJw8vQ/contest/276900/submission/77916780
public class GRAPH_DSA_2 {
	public static void main(String args[]) throws IOException {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));//Read the input
		String[] f=br.readLine().trim().split(" ");//read first line
		Graph<Integer> graph=new Graph<Integer>();//Create graph
		int nV=Integer.parseInt(f[0]); //the number of the vertices
		for(int i=1;i<=nV;i++) {
			graph.addVertex(i);
		}
		int nE=Integer.parseInt(f[1]);//the number of the edges
		for(int i=0;i<nE;i++) {
			String[] str=br.readLine().trim().split(" ");
			int u=Integer.parseInt(str[0]);//first
			int v=Integer.parseInt(str[1]);//second
			graph.addEdges(u, v);//connects first to second and second to first
		}
		HashMap<Integer, Integer> mp=graph.DFS();
		StringBuilder sb=new StringBuilder();
		for(int i=1;i<=nV;i++) {
			sb.append(mp.get(i)+" ");
		}
		System.out.println(sb.toString().trim());
	}
}

class Graph<V>{
	private Map<V, LinkedList<V>> mp=new HashMap<V, LinkedList<V>>();
	public void addVertex(V newVertex) { //O(n) hash function works for O(n)
		mp.put(newVertex, new LinkedList<V>());
	}
	public void addEdges(V u, V v) { // O(n) 
		if(!mp.containsKey(u)) { //check this graph has this vertex
			addVertex(u);
		}
		if(!mp.containsKey(v)) { //check this graph has this vertex
			addVertex(v);
		}
		mp.get(u).add(v);	//add new vertex into adjacency list
		mp.get(v).add(u); //add new vertex into adjacency list
	}
	@Override
	// Show vertex
	public String toString() {
		String str="";
		for(V v: mp.keySet()) {
			str+=""+v+": ";
			for( V w: mp.get(v)) {
				str+=w+" ";
			}
			str+="\n";
		}
		return str;
	}
	enum Status { NOT_VISITED, OPENED, EXITED }
	//O(the number of edges+the number of vertices)
	public HashMap<V, Integer> DFS() {
		HashMap<V, Status> vertexStatus=new HashMap<V, Status>(); //Map with status every vertices
		HashMap<V, Integer> vertexComponents=new HashMap<V, Integer>(); //Map vertex with such components
		V s=null;//starting point
		for(V v: mp.keySet()) {
				vertexStatus.put(v,Status.NOT_VISITED);//set all vertex as not visited
		}
		
		Queue<V> q= new LinkedList<V>();//queue for dfs
		int i=1;
		for(Map.Entry<V,Status> v: vertexStatus.entrySet()) {
			if(v.getValue()==Status.NOT_VISITED) {//Visit all not visited vertex
				s=v.getKey();//with starting point
				q.add(s);//add in queue
				DFSutil(s,vertexStatus, vertexComponents, i);//work with dfs
				i++;//increase the number of components in this graph
			}			
		}
		return vertexComponents; //return map
	}
	
	public void DFSutil(V s, HashMap<V, Status> vertexStatus,HashMap<V, Integer> vertexComponents, int i ) {
		Queue<V> q= new LinkedList<V>();//queue
		q.add(s);//add starting point
		while(!q.isEmpty()) {//while queue is not empty
			V v=q.poll();//delete first element
			vertexComponents.put(v,i);//map this vertex with components
			for(V w: mp.get(v)) {//go through all vertices which incident with this vertex
				if(vertexStatus.get(w)==Status.NOT_VISITED) {//if status of neighborhood vertex is not visited
					vertexStatus.put(w,Status.OPENED);//visit this vertex
					q.add(w);//add in queue
				}
			}
			vertexStatus.put(v,Status.EXITED);//when we visit this vertex we delete from the queue
		}
	}
	
}
	

