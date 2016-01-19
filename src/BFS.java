import java.util.*;



public class BFS{
	Queue<Node> q = new LinkedList<Node>();
	
	Node root = new Node();
	
	public void breadthfirst(String source, String destination, int[][] graphmatrix,ArrayList<String> nodelist ,int start_time)
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
								System.out.println(child.getState()+" "+child.getCost()%24);
								flag=1;
								break;
							}
							
						}

						else{
							continue;
						}
						
						
					}
						
					
				}
			
		}
		
		
		
	}
		
		


