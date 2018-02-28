package AC2018Qualif;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.SplittableRandom;

public class DijkstraExample {
	// Generic Dijkstra code. Will find the min length path. To find the other one, invert the compareTo returned value and initial value.
	
	 // Vertex contains all the points in the path
	  public static class Vertex implements Comparable<Vertex>{
		public double score = Double.MAX_VALUE; // MAX_VALUE assumed to be infinity
		public Vertex previous = null;// predecessor
		//TODO:Add addition information related to problem
		
		
		// Todo : Put Below the unique identifier for the Vertex
		public Vertex(double score, Vertex previous) {
			super();
			this.score = score;
			this.previous = previous;
		}


		
	
		
		@Override
		public int compareTo(Vertex other)
		{
			return Double.compare(score, other.score);
		}
		
		
		// Return a list of neighbours 
		public List<Vertex> getNeighbours()
		{
			ArrayList<Vertex> resp = new ArrayList<>();
			// TODO
			
				return resp;
		}

	}
	  
		/*
		 * Used for unitary test of dijkstra algorithm
		 */
		public static void main(String[] args) 
		{
			int NTESTS = 30;			
			
			double tic0 = System.currentTimeMillis();
			SplittableRandom rand = new SplittableRandom(50);//System.currentTimeMillis());
			for(int ntest = 0;ntest<NTESTS;ntest++)
			{
				dijkstra( 500000, rand);
				
				
			}
			
			Sys.disp("Mean time per iteration  time for xxxx:" + " is " + (System.currentTimeMillis()-tic0)/1000/NTESTS);
			
			 
		}
	  
		
		 public static void dijkstra(int MAXIT, SplittableRandom rand)
		 {
				
			Vertex Orig = new Vertex(0, null);
			Vertex Goal = new Vertex(0, null);
			dijkstra(Orig, Goal,MAXIT);

			
			
		 }	  
	 
	   /** Runs dijkstra using a specified source vertex */ 
	   public static void dijkstra(Vertex Origin, Vertex Goal,int MAXIT) {
	      PriorityQueue<Vertex> q = new PriorityQueue<>();
	      HashMap<ArrayList<Integer>,Vertex> h = new HashMap<>();
	      q.add(Origin);
	      h.put( null, Origin);// TODO : replace null by identifier
	     dijkstra(q,h, Goal,MAXIT);
	   }
	 
	   /** dijkstra with priorityQueue. Result is put back in Goal*/
	   /**
	 * @param q PriorityQueue, contains starting Point(s). Will be updated by function
	 * @param h 
	 * @param Goal
	 * @param MAXIT
	 */
	private static void dijkstra( PriorityQueue<Vertex> q,  HashMap<ArrayList<Integer>,Vertex> h, Vertex Goal,int MAXIT) {      
	      Vertex curVertex;
	      int cntit=0;
	      while (!q.isEmpty()) {
	    	  curVertex = q.poll(); //Vertex with lowest cost from leaf
	    	  
	    	  
	    	  // Check if we reached Goal
	    	  if( curVertex.equals(Goal) )
	    	  {
	        		Goal.score = curVertex.score;
	        		Goal.previous = curVertex.previous;
	        		
	        		 Sys.disp("Dijkstra result found in : " + cntit+ " Score : "+curVertex.score);
	        		return;
	        	}
	    	  
	    	  
	    	  
	    	  cntit++;
	    	  if(cntit%50000 == 0)
	    	  {
	    		  Sys.disp("Dijkstra it : " + cntit+ " Min score possible : "+curVertex.score);
	    	  }
	    	  if(cntit>MAXIT)// Early stop
	    	  {
	    		  Goal.previous = null;
	    		  return;
	    	  }
	    	  
            //look at score to each neighbour
	         List<Vertex> Neighbours = curVertex.getNeighbours();
	         
	         for (Vertex n : Neighbours) {
	        	 
	        	 //double scoreNewPath = n.score;
	        	 //Vertex prePath = h.get(null);// TODO : replace null by identifier
	        	 double newScore = curVertex.score+10;//TODO : replace 10 by dist from curVertex to n.
	        	 
	             if ( newScore < n.score) { // better score to Neighbour n found. Update value in priorityQueue by first removing it
	               q.remove(n);
	               n.score = newScore;
	               n.previous = curVertex;
	               	               
	               q.add(n);
	             //  h.put(null, n);// TODO : replace null by identifier
	            } 
	         }
	      }
	      
	   }
	   
	   
	  //  backtracing function. Output is an Arraylist, with first element Goal and last element associated Starting point
	   public static ArrayList<Vertex> getCellList(Vertex Goal, SplittableRandom rand)
	   {
		   Vertex V = Goal;
		   ArrayList<Vertex> resp = new ArrayList<>();
		   while(V!=null)
		   {
			   resp.add(V);
			   V=V.previous;
		   }
		  
		   return resp;
		   
	   }
	 
	 	
	

}
