import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Map.Entry;
//https://codeforces.com/group/3ZU2JJw8vQ/contest/276900/submission/78104097
//Valihan_Ilyasov_BS19_02
public class GRAPH_DSA_3 {
	public static void main (String args[]) throws IOException {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));//Read line 
		String[] f=br.readLine().trim().split(" ");//
		Graph<Integer> graph=new Graph<Integer>();//Create graph
		int nV=Integer.parseInt(f[0]);//number of vertices
		for(int i=1;i<=nV;i++) {
			graph.addVertex(i);//add vertices into graph
		}
		int nE=Integer.parseInt(f[1]);//number of edges
		for(int i=0;i<nE;i++) {
			String[] str=br.readLine().trim().split(" ");
			int uI=Integer.parseInt(str[0]);//first vertex
			int vI=Integer.parseInt(str[1]);//second vertex
			int cost=Integer.parseInt(str[2]);//weight
			graph.addEdges(uI, vI,cost);//connect two vertices together
		}
		graph.Prims();
	}
}
 
class node implements Comparable <node>{

	public V sec;//start
	public V dest;//end
	public int cost;//cost
	int i;
	public node(V u,V v, int c, int i) {//O(1)
		sec=u;
		this.i=i;
		dest=v;
		cost=c;
	}
 
	@Override
	//show node
	public String toString() {//O(1)
		if(i==1) {
			return dest+" "+sec+" "+cost;
		}else {
		return sec+" "+dest+" "+cost;
		}
	}
	//Compare two node O(1)
	@Override
	public int compareTo(node cmp) {
		// TODO Auto-generated method stub
		return this.cost-cmp.cost;
	}
	
}
class V<T> {
	public int status;//visited or unvisited
	public T index;//the value of this vertex from the input 
	public V(T value){//O(1)
		this.index=value;
	}
	@Override
	//show two vertex
	public String toString() {
		return index+"";
	}
}
class Graph<T>{
	HashMap<V<T>, LinkedList<node>> mp;//Adjacency list
	HashMap<T, V<T>> key;//set of all vertices 
	public Graph() {
		mp=new HashMap<V<T>, LinkedList<node>>();
		key=new HashMap<T, V<T>>();
	}
	public V<T> addVertex(T value) {//O(n) hash map function 
		V<T> newNode=new V<T>(value);//create vertices with value from the input
		mp.put(newNode,new LinkedList<node>());
		key.put(value,newNode);
		return newNode;
	}
	public void addEdges(T vValue, T uValue, int cost) {//Connect two edges O(n)
		V<T> v;
		V<T> u;
		if(!key.containsKey(vValue)) {//if this vertex doesn't exist than create
			v=addVertex(vValue);
		}else {
			v=key.get(vValue);
		}
		if(!key.containsKey(uValue)) {//if this vertex doesn't exist than create
			u=addVertex(uValue);
		}else {
			u=key.get(uValue);
		}
 
		node tmp1=new node(u,v, cost, 1);
		node tmp2=new node(v,u, cost, 0);
 
		mp.get(u).add(tmp1);//add for each vertex pair into adjacency list
		mp.get(v).add(tmp2);//add for each vertex pair into adjacency list
	}
	@SuppressWarnings("unchecked")
	
	//O(V^2) because of using Priority queue instead of using a binary heap
	public void Prims() {
		PriorityQueue<node>  result=new PriorityQueue<node> ();//result which keep all sec->dest with cost
		for(V<T> v: mp.keySet()) {
				v.status=0;//status of each vertices as not visited
		}
		PriorityQueue<node> sm=new PriorityQueue<node>();//Keep vertex which we visit in order of cost
		
		for(Map.Entry<V<T>, LinkedList<node>> value: mp.entrySet()) {
			for(node elm: value.getValue()) {
				if(elm.dest.status==0 || elm.sec.status==0) { //if two vertices in graph than status equals 1
					elm.dest.status=1;	//
					elm.sec.status=1;
				}
			}
		}
		//
		int i=0;
		String answer="";
		for(Entry<V<T>, LinkedList<node>> v: mp.entrySet()) {
			//for(Map.Entry<V,Status> v: vertexStatus.entrySet()) {
				result.clear();
				//v.getKey() staring point
				if(v.getKey().status==1 || v.getKey().status==0 ) {//if starting point in this graph than do following
					//System.out.println(i);
					HashSet<V<T>> arr=new HashSet<V<T>>();//set of all vertices which we visit
					DFSutil(v.getKey(),result, sm, arr);//call function prims with dfs O(E log V)
					if(result.size()!=0 && v.getKey().status!=0 ) {//if the result not 0
						//keep answer for this starting point
						answer+=(arr.size()+" "+result.peek().dest+"\n");
						while(result.size()!=0) {
							 answer+=result.poll()+"\n";
						}
					}else {
						//if 0 than this two edges are not connected with other
						answer+=""+1+" "+v.getKey()+"\n";
					}
					i++;
				}
		}
		System.out.println(i);
		System.out.print(answer.trim());	
	}
	//O(V^2) because of using Priority queue instead of using a binary heap
	public void DFSutil(V s, PriorityQueue<node>  result,PriorityQueue<node> sm, HashSet<V<T>> arr) {
		while(s.status==1) {//While current vertex is not visited do following
			for(node elm: mp.get(s)) {
				if(elm.dest.status==1) {//if we didn't visit this vetex
						sm.add(elm);//add into priory queue
				}
			}
			s.status=2;//status of new vertex equals 2 as visited	
			while( sm.size()!=0 && sm.peek().dest.status ==2) {//while first vertex is visited in priority queue delete this vertex from priority queue
				sm.poll();
			}
			arr.add(s);//add current vertex in queue
			if(sm.peek()!=null) {//if queue is not empty do following
				node tmp=sm.peek();
				s=sm.poll().dest;//Assign new current elemnt
				result.add(tmp);
				arr.add(s);//add to set of vertices new vertex which we visited
 
			}else {
				break;
			}			
		}
	}
	
	
	
}
 