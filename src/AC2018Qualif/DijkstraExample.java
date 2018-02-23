package AC2018Qualif;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.SplittableRandom;

public class DijkstraExample {
	// Solve a Pizza problem Greedily. At each iteration, compute the best solution from a condition (// position of leftMost empty cell for each Row)
	// Complexity : depending on number of columns. For the big set, Maximal pizza part size is 14 cells,
	// So the active solutions will include all combination of column numbers with a maximal difference of 14+1 cells (H+1) 
	// For C columns, I have for each cell to update in the slice a maximum of H^(C-1) possible states. For each state, need to compute the valid cells at with upper left point at position : complexity ~ H*min(H,C) ( for 14 :  14+7+4+3+2+2+2+7 = 41)      
	// => for a slice of C*R, complexity ~C*R*(H+1) ^(C-1)*[  H*min(H,C)];
	// For the big problem, assuming R = 1000, complexity is for a slice of heigth C:
	// 
	// C = 1 : 1000 * 14      =     14e3
	// C = 2 : 2*1000*15*21   =   ~630e3
	// C = 3 : 3*1000*15^2*25 =    ~17e6
	// C = 4 : 4*1000*15^3*28 =   ~380e6
	
	//For the full pizza of 1000x1000, by testing all possible heightC slices by
	//
	//
	//
	// C = 1 :  =    ~14e6   => ~0.07 sec at 200M it/s
	// C = 2 :  =   ~630e6   =>  ~3   sec at 200M it/s
	// C = 3 :  =    ~17e9   =>  ~1.5   minutes at 200M it/s   => Short term Goal
	// C = 4 :  =   ~380e9   => ~30   minutes at 200M it/s   => Would need some pruning for efficiency
	
		static  Problem pb;
		static Solution sol;
	
	 
	  public static class Vertex implements Comparable<Vertex>{
		public double score = Double.MAX_VALUE; // MAX_VALUE assumed to be infinity
		public Vertex previous = null;// predecessor
		
		// Todo : Put Below the unique identifier for the Vertex
	//	public ArrayList<Integer> leftMostOccupied; // for each row of the slice, indicate position where all cells on left on same row are occupied 
		
			
		
		public Vertex(double score, Vertex previous) {//, ArrayList<Integer> leftMostOccupied
			super();
			this.score = score;
			this.previous = previous;
			//this.leftMostOccupied = leftMostOccupied;
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
			boolean EXAMPLE = false;
			int R = 4, C = 30, L = 6, H = 14;   
			if(EXAMPLE)
			{
				R = 3; C = 7; L = 1; H = 6;
			}
			
			
			double tic0 = System.currentTimeMillis();
			SplittableRandom rand = new SplittableRandom(50);//System.currentTimeMillis());
			for(int ntest = 0;ntest<NTESTS;ntest++)
			{
				//double tic = System.currentTimeMillis();
				
				
				
				int[][] pizza ={{-1,-1,-1,-1,-1,-1,-1}, { -1,-2,-2,-1,-1,-2,-1},{-1,-1,-1,-1,-1,-1,-1}} ;
				if(!EXAMPLE)
				{
					pizza = new int[R][C];
					for(int r = 0;r<R;r++)
						for(int c=0;c<C;c++)
							pizza[r][c] = -1-rand.nextInt(2);
				}
				
				pb = new Problem(); 
				sol = new Solution(pb);
				
				dijkstra( 500000, rand);
				
				
			}
			
			Sys.disp("Mean time per iteration  time for xxxx:" + " is " + (System.currentTimeMillis()-tic0)/1000/NTESTS);
			
			 
		}
	  
		
		 public static void dijkstra(int MAXIT, SplittableRandom rand)
		 {
			 double origScore = sol.GetScore();
			ArrayList<Integer> leftMOrig = new ArrayList<Integer>();
//			for(int t=0;t<pb.R;t++)// TODO : create Origin with correct identification
//				leftMOrig.add(0);
			
			ArrayList<Integer> leftMGoal = new ArrayList<Integer>();
//			for(int t=0;t<pb.R;t++)// TODO : create Goal with correct identification
//				leftMGoal.add(pb.C);
			
			Vertex Orig = new Vertex(0, null);
			Vertex Goal = new Vertex(0, null);
			dijkstra(Orig, Goal,MAXIT);
//			 ArrayList<Cell> answ = getCellList(Goal, rand);// Backtrack to recover path
//				for(Cell c : answ)
//				{
//					sol.addACell(c);
//				}
				FullProcess.CheckSolution(sol);

			
			
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
	   private static void dijkstra( PriorityQueue<Vertex> q,  HashMap<ArrayList<Integer>,Vertex> h, Vertex Goal,int MAXIT) {      
	      Vertex curVertex;
	      int cntit=0;
	      while (!q.isEmpty()) {
	    	  curVertex = q.poll(); //Vertex with lowest cost from leaf
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
	        	if( true )// TODO => n.leftMostOccupied.equals(Goal.leftMostOccupied))
	        	{
	        		Goal.score = n.score;
	        		Goal.previous = n.previous;
	        		
	        		 Sys.disp("Dijkstra result found in : " + cntit+ " Score : "+curVertex.score);
	        		return;
	        	}
	        		
	        	 
	        	 
	        	 double scoreNewPath = n.score;
	        	 Vertex prePath = h.get(null);// TODO : replace null by identifier
	        	 
	             if ( (prePath==null) ||   prePath.score < n.score) { // better score to Neighbour found
	               q.remove(n);
	               n.score = scoreNewPath;
	               n.previous = curVertex;
	               	               
	               q.add(n);
	               h.put(null, n);// TODO : replace null by identifier
	            } 
	         }
	      }
	      
	   }
	   
	   
	   //TODO: code backtracing function
//	   public static ArrayList<Cell> getCellList(Vertex Goal, SplittableRandom rand)
//	   {
//		   Vertex V = Goal;
//		   ArrayList<Cell> resp = new ArrayList<>();
//		  
//		   return resp;
//		   
//	   }
	 
	 	
	

}
