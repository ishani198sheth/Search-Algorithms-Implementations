import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.*;
import java.util.*;

public class waterFlow {

	
		
		 public static void main(String[] args) throws IOException,NullPointerException,ArrayIndexOutOfBoundsException,NumberFormatException {
			try{
			 PrintWriter writer = new PrintWriter("output.txt", "UTF-8"); 
			 Scanner sc = new Scanner(new File(args[1]));
			
			    sc.useDelimiter("\n");
			   
			    ArrayList<String> information = new ArrayList<String>();
			   
			    
			    while (sc.hasNext()) {
			      String s = sc.next();
			    
			      information.add(s);
			      
			      if (s.trim().isEmpty()) {
			        continue;
			      }
			    
			    }
			    sc.close();
			    			    
	// variable for looping the input scan arraylist
			    int i =0;
			    int count =0;
			    int cases=0;
			    while(i<information.size())
			    {
			    	int totalcases=Integer.valueOf(information.get(0).trim());
			    	cases=cases+1;	
			    	
			    
			    //System.out.println(Integer.valueOf(totalcases));
			   //System.out.println(i);	
			    if(cases<=totalcases)
			    {
			    String algorithm= information.get(i+1).trim();

			    count++;
			    String source= (String) information.get(i+2).trim();

			    count++;
			    String destination= (String) information.get(i+3).trim();

			    count++;
			    String middlenodes = (String) information.get(i+4).trim();

			    count++;
			    int pipes = Integer.valueOf(information.get(i+5).trim());

			    count = count+1+pipes;
			    int starttime = Integer.valueOf(information.get(i+6+pipes).trim());

			    count=count+2;	
			    
			    //getting middle and destination nodes in an array after splitting
			    String[] mnodes = middlenodes.split("\\s+");
			    String[] dnodes = destination.split("\\s+");
			    String[] snodes = source.split("\\s+");		   		  
			    //getting the total number of nodes for the system
			    int sourcenodes=snodes.length;
			    int middlenodes_no=mnodes.length;		    
			    int destinationnodes=dnodes.length;
			    
			    int totalnodes=sourcenodes+destinationnodes+middlenodes_no;
			   	  		    
			    //Array list of name of nodes
			    ArrayList<String> namenodes = new ArrayList<String>();
			    
			    // Adding source to list	   
			    namenodes.add(source.replaceAll("\\s+",""));
			    
			    //Adding destinations to list
			    for(int dest=0;dest<destinationnodes;dest++)
			    {
			    	namenodes.add(dnodes[dest]);
			    }
			    
			    //Adding middle nodes to list
			    for(int mid=0;mid<middlenodes_no;mid++)
			    {
			    	namenodes.add(mnodes[mid]);
			    }
			   
			    
			    int[][] graph = new int[totalnodes][totalnodes];
			    String[][] offtimeperiods = new String[totalnodes][totalnodes];
			    //Reading the pipes information to form the graph
			    if(pipes!=0)
			    {
			    int graphreading=i+6;
			    
			    for(int pipe=0;pipe<pipes;pipe++)
			    {
			    	
			    	String[] splitline = information.get(graphreading).split("\\s+");
			    	ArrayList<Integer> finalofflist = new ArrayList<Integer>();
			    	ArrayList<String> offlist = new ArrayList<String>();
			    	String start = splitline[0];
			    	String end = splitline[1];

			    	int cost = Integer.valueOf(splitline[2].trim());
			    	if(cost==0)
			    	{
			    		cost= -1;
			    	}
			    	int offtime = Integer.valueOf(splitline[3].trim());
			    	for(int period=1;period<=offtime;period++)
			    	{
			    		String offperiodlist = splitline[3+period].replaceAll("-", " ");

			    		offlist.add(offperiodlist);

			    		String[] time = offlist.get(period-1).split(" ") ;
			    		for(int t=Integer.valueOf(time[0]);t<=Integer.valueOf(time[1]);t++)
			    		{
			    			finalofflist.add(t);
			    		}
			    		
			    	}
			    	Set<Integer> distinctofflist = new HashSet<>();
			    	distinctofflist.addAll(finalofflist);
			    	finalofflist.clear();
			    	finalofflist.addAll(distinctofflist);
//					String finalofflists = finalofflist.toString().replaceAll(" ", "");
//					finalofflists=finalofflists.replaceAll(",", " ");
////					finalofflists = finalofflists.replaceAll("[", "");
////					finalofflists = finalofflists.replaceAll("]", "");
			    	graphreading++;
			    	int startpoint = namenodes.indexOf(start) ;
			    	int endpoint = namenodes.indexOf(end);
//			    	System.out.println(finalofflist.size());
			    	graph[startpoint][endpoint] = cost;
			    	if(finalofflist.size()==0)
			    	{
			    	offtimeperiods[startpoint][endpoint]=null;
			    	}
			    	else
			    	{
			    	offtimeperiods[startpoint][endpoint]=finalofflist.toString().trim();
			    	}
			    	//System.out.println(!(Arrays.asList(offtimeperiods[startpoint][endpoint]).contains(null)));
			    	//System.out.println(finalofflist);
			    }
			    }
      

			 //BFS Implementation call
			    if(algorithm.equals("BFS"))
			    {
			    	//System.out.println("inside bfs");
			    	waterFlow  bfselement = new waterFlow();
			    	BFS bfs= bfselement.new BFS();
			    	bfs.breadthfirst(source, destination, graph,namenodes,starttime,writer);
			    	i=count;
			    	
			    	//System.out.println(i);
			    }
			    
			    //DFS Implementation call
			    if(algorithm.equals("DFS"))
			    {
			    	//System.out.println("inside DFS");
			    	waterFlow  dfselement = new waterFlow();
			    	DFS dfs = dfselement.new DFS();
			    	dfs.depthfirst(source, destination, graph,namenodes,starttime,writer);
			    	i=count;
			    	
			    }
			    
			    //UCS Implementation call
			    if(algorithm.equals("UCS"))
			    {
			    	//System.out.println("inside UCS");
			    	waterFlow  ucselement = new waterFlow();
			    	UCS1 ucs = ucselement.new UCS1();
			    	ucs.uniformcost(source, destination, graph,namenodes,starttime,offtimeperiods,writer);
			    	
			    	i=count;
			    	
			    }
			    	
			    }
			    else
			    {
			    	break;
			    }
//			    }
			    }
	writer.close();		
			        
	
		 }
		 catch(NumberFormatException e){
			 
			 System.out.println("Number Format Error");
		 }
			catch(ArrayIndexOutOfBoundsException e){
				 
				 System.out.println("Element not found in nodelist");
			 }
			catch(IOException e){
				 
				 System.out.println("Input Output exception");
			 }
			catch(NullPointerException e){
				 
				 System.out.println("Null Pointer exception");
			 }
		 }	 
// The Node Class		 

public class Node {
	String state;
	Node parent;
	int cost;
	int depth;
	boolean visited;
	
	//Getters and Setters
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}

}

	
// The BFS Implementation Class

public class BFS{
	Queue<Node> q = new LinkedList<Node>();
	
	Node root = new Node();
	
	public void breadthfirst(String source, String destination, int[][] graphmatrix,ArrayList<String> nodelist ,int start_time, PrintWriter writer) throws FileNotFoundException, UnsupportedEncodingException
	{
		
		//Initialization of Root Node
		root.state=source;
		root.cost=start_time;
		root.depth=0;
		root.parent=null;
		int flag=0;
		//Adding the Root node to the Queue
		q.add(root);
		//setting the root node as visited
		root.setVisited(true);
		

		//setting all the nodes to visited=false and root to true
		boolean[] visit = new boolean[nodelist.size()];
		visit[nodelist.indexOf(root.state)] =true;
		
		//checking if the queue is empty and flag is 0 to continue visiting the nodes until destination is reached
		
		while(!q.isEmpty() && flag==0)
		{

			Node n=(Node)q.poll();
			
			// getting the parentindex and defining a new array for adding the children of parents
			int parentindex = nodelist.indexOf(n.getState().trim());
			ArrayList<String> found = new ArrayList<String>();
			
			//setting all the nodes to visited=false
			//boolean[] visit = new boolean[nodelist.size()];
			
			// looping all the nodes to check for edges
			for (int g=0;g<nodelist.size();g++)
				{
								
					if(graphmatrix[parentindex][g]!=0 && visit[g]==false)
					{
						
						String children = nodelist.get(g);
						found.add(children);
						
					}
				}
					Collections.sort(found);
					//System.out.println(found);
					for(int qf=0;qf<found.size();qf++)
					{
						Node child=new Node();
						child.setState(found.get(qf));
						child.setParent(n);
						child.setDepth((n.depth)+1);
						child.setCost(n.getCost()+1);
						//System.out.println(child.getState());
						visit[nodelist.indexOf(child.state)]=true;
						//child.setVisited(true);
						q.add(child);
						
						if(child.getState()!=null)
						{
							if(destination.contains(child.getState()))
							{
								writer.println(child.getState()+" "+child.getCost()%24);
								flag=1;
								
								break;
							}
							
						}

						else{
							continue;
						}
						
						
					}
						
					
				}
if(q.isEmpty())
{
	writer.println("None");
}
		}
		
	
		
	}

//The DFS Implementation Class
public class DFS {
	Stack<Node> s = new Stack<Node>();
	Node root = new Node();
	//Node child=new Node();
	public void depthfirst(String source, String destination, int[][] graphmatrix,ArrayList<String> nodelist ,int start_time, PrintWriter writer)
	{
		//Initialization of Root Node
				root.state=source;
				root.cost=start_time;
				root.depth=0;
				root.parent=null;
				int flag=0;
		//Pushing the Root to the Stack
		s.push(root);
		root.setVisited(true);
//		ArrayList<String> explored = new ArrayList<String>();
//		explored.add(root.state);
		
		//setting all the nodes to visited=false and root to true
		boolean[] visit = new boolean[nodelist.size()];
		//visit[nodelist.indexOf(root.state)] =true;
		
		while(!s.isEmpty() && flag==0)
		{
			Node n=(Node)s.pop();
			if(n.getState()!=null)
			{
				if(destination.contains(n.getState()))
				{
					writer.println(n.getState()+" "+n.getCost()%24);
					flag=1;
					break;
				}
				
			}

			else{
				continue;
			}
			visit[nodelist.indexOf(n.state)] = true;
			// getting the parentindex and defining a new array for adding the children of parents
			int parentindex = nodelist.indexOf(n.getState().trim());
			ArrayList<String> found = new ArrayList<String>();
			
			
			for (int g=0;g<nodelist.size();g++)
				{
				
				
					if(graphmatrix[parentindex][g]!=0 && visit[g]==false)
					{
						
						String children = nodelist.get(g);
						found.add(children);
						
					}
				}
					Collections.sort(found);
					//System.out.println(found);
					
					//Adding the elements to the stack in reverse order
					for(int qf=found.size()-1;qf>=0;qf--)
					{
						Node child=new Node();
						
						child.setState(found.get(qf));
						child.setParent(n);
						child.setDepth((n.depth)+1);
						child.setCost(n.getCost()+1);
						//System.out.println(child.getState());
						//visit[nodelist.indexOf(child.state)]=true;
						//explored.add(children);
						//child.setVisited(true);
						s.push(child);
						
						
						
					}
						
					
				}

		if(s.isEmpty()&& flag==0)
		{
			writer.println("None");
		}
		}
		
	
		
	}


//The UCS Implementation Class
public class UCS1 {
Queue<Node> pq = new LinkedList<Node>();
	
	Node root = new Node();
	Node child=new Node();
	
	
	public void uniformcost(String source, String destination, int[][] graphmatrix,ArrayList<String> nodelist ,int start_time, String[][] timeperiods,PrintWriter writer)
	{
		String destinations[] = destination.split(" ");
//		for(int j=0;j<destinations.length;j++)
//		{
//			writer.println(Arrays.asList(destinations).contains("A"));
//		}
		//array for cost
		int[] distance = new int[nodelist.size()];
		String[] explored = new String[nodelist.size()];
		
		int min = 999999999, nextNode = 0;
		//writer.println(destination);
		
		 //Initialization of Root Node
			root.state=source;
			root.cost=0;
			root.depth=0;
			root.parent=null;
			
			//Adding the Root node to the Queue
			pq.add(root);
			root.setVisited(true);
			explored[nodelist.indexOf(root.state)]=root.state;
			//distance=graphmatrix[nodelist.indexOf(root.getState().trim())];
			//distance[nodelist.indexOf(root.getState().trim())]=root.getCost();
//			while(!pq.isEmpty())
//			{
//				Node n=(Node)pq.poll();
//				//System.out.println(child.getState());
//				
//					
//				
//				int parentindex = nodelist.indexOf(n.getState().trim());
				
				//setting all the nodes to visited=false
				int[] visited = new int[nodelist.size()];
				
				int[] preD= new int[nodelist.size()];
				
				
				
				
				
				for(int i = 0; i < distance.length; i++){
					
					visited[i] = 0; //initialize visited array to zeros
					
				
					preD[i] = 0;
					
					
					for(int j = 0; j < distance.length; j++){
						
						
						
						if(graphmatrix[i][j]==0){
							
							graphmatrix[i][j] = 999999999; // make the zeros as 999
							
						}
						
					}
					
				}

				
				
				distance = graphmatrix[nodelist.indexOf(root.state)]; //initialize the distance array
				for(int i=0;i<preD.length;i++)
				{
					if(distance[i]<min)
					{
						preD[i]=nodelist.indexOf(root.state);
					}
				}
				
				visited[0] = 1; //set the source node as visited
				distance[nodelist.indexOf(root.state)] = 0; //set the distance from source to source to zero which is the starting point
				int finalstart=start_time;
				//int test=0;
				for(int counter = 0; counter < nodelist.size(); counter++){
					Node n = (Node)pq.poll();
					if(destination.indexOf(n.state)>=0)
					{
						//String dest= destination.valueOf(destination.indexOf(n.state));
						//writer.println(dest);
					//	test=1;
					//System.out.println(n.state+" "+Arrays.asList(visited[nodelist.indexOf(n.state)]));
						if(n.cost<0)
						{
							n.cost=0;
						}
						if(Arrays.asList(destinations).contains(n.state))
						{
					writer.println(n.state+" "+(n.cost+start_time)%24);
					break;
						}
					}
					
					min = 999999999;
					
					for(int i = 0; i < nodelist.size(); i++){
						
						if(min > distance[i] && visited[i]!=1){
							int flag1=0;
							if(timeperiods[preD[i]][i]!=null)
							{
								int tempdist = distance[preD[i]]+start_time;
								String myinput = timeperiods[preD[i]][i].replace(" ", "");
								myinput = myinput.replace("[", "");
								myinput = myinput.replace("]", "");
								String arr[]= myinput.split(",");
								ArrayList<String> vals= new ArrayList<String>();
								for(int k=0; k<arr.length;k++)
								{
									vals.add(arr[k]);
								}
							if(vals.contains(String.valueOf(tempdist)))
								{
								
								//System.out.println(n.state+" "+nodelist.get(i)+" "+timeperiods[nodelist.indexOf(n.state)][i]);
								flag1=1;
								}
							}
							
							if(flag1==0)
							{
							min = distance[i];
							nextNode = i;
							explored[i]=nodelist.get(i);
							//System.out.println(n.state);
							}
							
							if(timeperiods[nodelist.indexOf(n.state)][i]!=null)
							{
								int tempdist = n.cost+start_time;
								String myinput = timeperiods[nodelist.indexOf(n.state)][i].replace(" ", "");
								myinput = myinput.replace("[", "");
								myinput =myinput.replace("]", "");
								String arr[]= myinput.split(",");
								ArrayList<String> vals= new ArrayList<String>();
								for(int k=0; k<arr.length;k++)
								{
									vals.add(arr[k]);
								}
							if(vals.contains(String.valueOf(tempdist)))
								{
								
								//System.out.println(n.state+" "+nodelist.get(i)+" "+timeperiods[nodelist.indexOf(n.state)][i]);
								flag1=1;
								}
							}
							
							if(flag1==0)
							{
							min = distance[i];
							nextNode = i;
							explored[i]=nodelist.get(i);
							//System.out.println(n.state+" "+nodelist.get(i));
							}
						}
						
						
						else if(distance[i]==distance[nextNode] && visited[i]!=1)
						{
							String first = nodelist.get(nextNode).trim();
							String second = nodelist.get(i).trim();
							int result = first.compareTo(second);
							if(result>0)
							{
								int flag2=0;
								if(timeperiods[preD[i]][i]!=null)
								{
									int tempdist = distance[preD[i]]+start_time;
									String myinput = timeperiods[preD[i]][i].replace(" ", "");
									myinput = myinput.replace("[", "");
									myinput = myinput.replace("]", "");
									String arr[]= myinput.split(",");
									ArrayList<String> vals= new ArrayList<String>();
									for(int k=0; k<arr.length;k++)
									{
										vals.add(arr[k]);
									}
								if(vals.contains(String.valueOf(tempdist)))
									{
									
									//System.out.println(n.state+" "+nodelist.get(i)+" "+timeperiods[nodelist.indexOf(n.state)][i]);
									flag2=1;
									}
								}
								
								if(flag2==0)
								{
								min = distance[i];
								nextNode = i;
								explored[i]=nodelist.get(i);
								//System.out.println(n.state+" "+nodelist.get(i));
								}
								if(timeperiods[nodelist.indexOf(n.state)][i]!=null)
								{
									int tempdist = n.cost+start_time;
									String myinput = timeperiods[nodelist.indexOf(n.state)][i].replace(" ", "");
									myinput = myinput.replace("[", "");
									myinput = myinput.replace("]", "");
									String arr[]= myinput.split(",");
									ArrayList<String> vals= new ArrayList<String>();
									for(int k=0; k<arr.length;k++)
									{
										vals.add(arr[k]);
									}
								if(vals.contains(String.valueOf(tempdist)))
									{
									//writer.println(n.state+" "+nodelist.get(i)+" "+timeperiods[nodelist.indexOf(n.state)][i]);
									flag2=1;
									}
								}
								
								if(flag2==0)
								{
								min = distance[i];
								nextNode = i;
								explored[i]=nodelist.get(i);
								//writer.println(n.state+" "+nodelist.get(i));
								}
							}
						
						}
						
					}
					//System.out.println(nodelist.get(nextNode));
					visited[nextNode] = 1;
					Node element = new Node();
					element.cost= distance[nextNode];
					element.state=nodelist.get(nextNode);
					pq.add(element);
					
					for(int i = 0; i < nodelist.size(); i++){
						
						if(visited[i]!=1){
							//System.out.println(distance[nextNode]);
							//System.out.println(timeperiods[nextNode][i]);
							int flag=0;
							if(min+graphmatrix[nextNode][i] < distance[i]){
								if(timeperiods[nextNode][i]!=null)
								{
							//distance[i] = min+graphmatrix[nextNode][i];
//									System.out.println(nodelist.get(i));
//									System.out.println(nodelist.indexOf(nextNode));
									//System.out.println(timeperiods[nextNode][i]);
									int temp=(distance[nextNode]+finalstart)%24;
									//System.out.println(temp);
									String myinput = timeperiods[nextNode][i].replace(" ", "");
									myinput = myinput.replace("[", "");
									myinput = myinput.replace("]", "");
									String arr[]= myinput.split(",");
									ArrayList<String> vals= new ArrayList<String>();
									for(int k=0; k<arr.length;k++)
									{
										vals.add(arr[k]);
									}
								if(vals.contains(String.valueOf(temp)))
									{
										//visited[i]=1;
									//System.out.println(timeperiods[nextNode][i].s);
									//System.out.println(nodelist.get(nextNode)+" "+nodelist.get(i)+" "+timeperiods[nextNode][i]+" "+timeperiods[nextNode][i].indexOf((String.valueOf(temp))));
									//explored[i]=nodelist.get(i);
									flag=1;
									//finalstart++;
									//break;
									}
								//break;
								}
								if(min+graphmatrix[nextNode][i] < distance[i] && flag==0){
									
								distance[i] = min+graphmatrix[nextNode][i];
								preD[i] = nextNode;
								//finalstart++;
								}
							}
							
						}
						

						}
					
						
					}
					//System.out.println("for break");
//				}

	
			
			//forming array of destination nodes
			String[] finaldestination = destination.split("\\s+");
			//System.out.println(Arrays.asList(finaldestination));
			String goal =null;
//			if()
			//double min_cost= Double.POSITIVE_INFINITY;
			for(int dest=0;dest<finaldestination.length;dest++)
			{
				if(Arrays.asList(explored).contains(finaldestination[dest]))
				{
//					double cost = distance[nodelist.indexOf(finaldestination[dest])];
//					if(cost<min_cost)
//					{
//						min_cost=cost+start_time;
//						if (min_cost>=24)
//						{
//							min_cost=min_cost%24;
//						}
						goal = finaldestination[dest];
					}
//					else{
//						min_cost=cost;
//					}
			}
			if(goal!=null)
			{
			//System.out.println(goal+" "+(int)min_cost);
			}
			else
			{
				writer.println("None");
			}

		}
	
}
	
}



