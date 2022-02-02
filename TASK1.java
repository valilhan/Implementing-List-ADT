import java.util.*;
import java.io.*;
//Valihan_Ilyasov_BS19_02
//https://codeforces.com/group/3ZU2JJw8vQ/contest/276900/submission/77912968
public class DSA_GRAPH_1 {
	public static void main(String args[]) throws IOException {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in)); //Read the input
		String[] f=br.readLine().trim().split(" ");	//read first line
		Graph<Integer> graph=new Graph<Integer>(); //Create graph
		int nV=Integer.parseInt(f[0]); //the number of the vertices
		for(int i=1;i<=nV;i++) {
			graph.addVertex(i);
		}
		int nE=Integer.parseInt(f[1]); //the number of the edges
		for(int i=0;i<nE;i++) {
			String[] str=br.readLine().trim().split(" ");
			int u=Integer.parseInt(str[0]);	//first
			int v=Integer.parseInt(str[1]); //second
			graph.addEdges(u, v); //add vertices in graph
		}

		 if(graph.DFS().size()==1 || graph.DFS().size()==0){
			 //If all vertices are connected to graph
	        System.out.print("GRAPH IS CONNECTED");
	    }else {
	    	//if we have two vertices which is not connected than show it 
	    	System.out.print("VERTICES "+graph.DFS().get(0)+" AND "+graph.DFS().get(1)+" ARE NOT CONNECTED BY A PATH");
	    }
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
	//O(number of vertices+number of edges)
	public ArrayList<V> DFS() {
		HashMap<V, Status> vertexStatus=new HashMap<V, Status>();//Map with status every vertices
		V s=null;//starting point
		for(V v: mp.keySet()) {
			if(s==null) {
				s=v; // add first vertex into starting point
				vertexStatus.put(v,Status.OPENED); // this vertex visited 
			}else {
				vertexStatus.put(v,Status.NOT_VISITED); //others vertices are not visited
			}
		}
		Queue<V> q= new LinkedList<V>();//queue for dfs
		q.add(s);//add starting point
		while(!q.isEmpty()) {//until queue is not empty do following
			V v=q.poll();//take first vertex
			for(V w: mp.get(v)) {//go through all vertices which incident with this vertex
				if(vertexStatus.get(w)==Status.NOT_VISITED) {//if status of neighborhood vertex is not visited
					vertexStatus.put(w,Status.OPENED);//visit this vertex
					q.add(w);//add in queue
				}
			}
			vertexStatus.put(v,Status.EXITED);//when we visit this vertex we delete from the queue
		}
		
		//Not Visited
		//Visited
		boolean check1=false;
		boolean check2=false;
		ArrayList<V> sb=new ArrayList<V>();
		for(Map.Entry<V,Status> v: vertexStatus.entrySet()) {
			if(check1==false && v.getValue()==Status.EXITED) {//if one vertex is connected to graph
				check1=true;
				sb.add(v.getKey());
			}
			if(check2==false && v.getValue()==Status.NOT_VISITED) {	//another is not connected to graph
				check2=true;
				sb.add(v.getKey());
			}
		}
		return sb; //return answer
	}
	
}
	

