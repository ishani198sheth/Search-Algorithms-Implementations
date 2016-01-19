import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;




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
									System.out.println(nodelist.get(nextNode)+" "+nodelist.get(i)+" "+timeperiods[nextNode][i]+" "+timeperiods[nextNode][i].indexOf((String.valueOf(temp))));
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