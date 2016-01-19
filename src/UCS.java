import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;


public class UCS {
Queue<Node> pq = new LinkedList<Node>();
	
	Node root = new Node();
	Node child=new Node();
	
	
	public void uniformcost(String source, String destination, int[][] graphmatrix,ArrayList<String> nodelist ,int start_time, String[][] timeperiods)
	{
		//array for cost
		int[] distance = new int[nodelist.size()];
		String[] explored = new String[nodelist.size()];   
		 //Initialization of Root Node
			root.state=source;
			root.cost=start_time;
			root.depth=0;
			root.parent=null;
			
			//Adding the Root node to the Queue
			pq.add(root);
			root.setVisited(true);
			explored[nodelist.indexOf(root.getState().trim())]=root.getState().trim();
			distance[nodelist.indexOf(root.getState().trim())]=root.getCost();
			while(!pq.isEmpty())
			{
				Node n=(Node)pq.poll();
				//System.out.println(child.getState());
				
					
				
				int parentindex = nodelist.indexOf(n.getState().trim());
				
				//setting all the nodes to visited=false
				boolean[] visit = new boolean[nodelist.size()];
				
				for (int g=0;g<nodelist.size();g++)
					{
					//System.out.println(n.getState().trim());
					
						if(graphmatrix[parentindex][g]!=0 && visit[g]==false)
						{
							//System.out.println("into if");
							//System.out.println(g);
							//String time = String.valueOf(n.getCost());
							if(timeperiods[parentindex][g].contains(String.valueOf(n.getCost())))
							{
								visit[g]=true;
								break;
							}
							String children = nodelist.get(g);
							child.setState(children);
							child.setParent(n);
							child.setDepth((n.depth)+1);
							child.setCost(n.getCost()+graphmatrix[parentindex][g]);
							//System.out.println(child.getState());
							visit[g]=true;
							//child.setVisited(true);
							pq.add(child);
							distance[nodelist.indexOf(child.getState().trim())]= child.getCost();
							explored[nodelist.indexOf(child.getState().trim())]= child.getState().trim();
//							if(child.getState()!=null)
//							{
//								if(destination.contains(child.getState()))
//								{
//									
//									System.out.println("destination "+child.getState()+" Reached with cost "+child.getCost());
//									//break;
//								}
//								
//							}
//
//							else{
//								continue;
//							}
							
							
						}
							

						
					}

				
			}
			
			//System.out.println(destination);
			
			//forming array of destination nodes
			String[] finaldestination = destination.split("\\s+");
			String goal =null;
			double min_cost= Double.POSITIVE_INFINITY;
			for(int dest=0;dest<finaldestination.length;dest++)
			{
				if(Arrays.asList(explored).contains(finaldestination[dest]))
				{
					double cost = distance[nodelist.indexOf(finaldestination[dest])];
					if(cost<min_cost)
					{
						min_cost=cost;
						if (min_cost>=24)
						{
							min_cost=min_cost%24;
						}
						goal = finaldestination[dest];
					}
					else{
						min_cost=cost;
					}
				}
			}
			if(goal!=null)
			{
			System.out.println("destination "+goal+" Reached with cost "+(int)min_cost);
			}
			else
			{
				System.out.println("None");
			}

		}
	

}
