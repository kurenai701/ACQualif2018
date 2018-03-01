package AC2018Qualif;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.SplittableRandom;
import java.util.TreeSet;


public class AlgoInputToOutput implements  Runnable {
	
	
	private  Problem cf_pb;
	private  SplittableRandom cf_rand;
	private  Solution cf_Sol;
	private  BestSolutionSynchro cf_BestSolSynchro ;
	private  int cf_id;
	private int NIT;
	
	public AlgoInputToOutput(Problem cf_pb, SplittableRandom cf_rand, Solution cf_Sol, BestSolutionSynchro cf_BestSolSynchro, int cf_id,  int cf_NIT) {
		super();
		this.cf_id = cf_id;
		this.cf_pb = cf_pb;
		this.cf_rand = cf_rand;
		this.cf_Sol = cf_Sol;
		this.cf_BestSolSynchro = cf_BestSolSynchro;
		this.NIT = cf_NIT;

	}
	
	public AlgoInputToOutput() {
		super();
		
	}


	@Override
	public void run()	{
		DivideAndConquer(cf_pb, cf_rand, cf_Sol,cf_BestSolSynchro,NIT);

	}

	
	
	
	public Solution DivideAndConquer(Problem pb, SplittableRandom rand, Solution Sol,BestSolutionSynchro BestSolSynchro, int NIT )
	{
		
		
		
		// TODO : create sub problems
		ArrayList<Problem> SubProblems = new ArrayList<Problem>();
	//	Solution bestSol = Common.DeepCopy(Sol);
		Sol.curScore = -1000;
		double bestScore = Sol.GetScore();
		double firstScore = bestScore;
		double firstTime = System.currentTimeMillis();
		
		int currentBestNum = BestSolSynchro.getNumSol();
	//	Sys.disp("START");
		
		
		//*************
		
		SubProblems.add(pb);// Currently, no divide and conquer// TODO : If creating subproblem, update this line

		int NITSUB = 60;// Number of iteration for each subproblem
		
		for( int nit = 0;nit<NIT;nit++)
		{
		
		
		//******************************************************************************

	
		
			for(int subInd = 0; subInd<SubProblems.size();subInd++)
			{		
				Problem subProb = subProblem(pb);//, xxx parameters)
				Solution SubSol = new Solution(subProb);
				double initScore = SubSol.GetScore();
				BestSolutionSynchro BestSolSynchroSub = new BestSolutionSynchro(SubSol );
				Sol.curScore = -1000;
				Sys.disp(" Full Sol score :" + Sol.GetScore() + "  subScore : " + SubSol.GetScore());
				if(Sol.GetScore() != SubSol.GetScore())
					Sys.disp("Error in subsol score");
				
				// ***********************  Call optimisation on subproblem *************************************
				SubSol= IterativelyResolveSubProblem(subProb,SubSol,rand,BestSolSynchroSub, NITSUB);
				
				Sol.curScore = -1000;SubSol.curScore=-1000;
				Sys.disp("After iteration : Full Sol score :" + Sol.GetScore() + "  subScore : " + SubSol.GetScore());
				// *********************************************************
				if(SubSol.GetScore() >  initScore)
				{
					Sys.disp("Subproblem improvement from "+ initScore + " to " + SubSol.GetScore() + " => Putting it back in main Solution");
		
					Sol.curScore = -1000;
					if(Sol.GetScore() != SubSol.GetScore())
						Sys.disp("Error in Sol getback score,  expecte : " +SubSol.GetScore(true) + " got " + Sol.GetScore(true));	
				}
				
				
				if(Sol.GetScore()>bestScore ||  (Sol.GetScore()==bestScore && Sol.improved ==true))
				{System.out.print("+");
				
					if(Sol.GetScore()>bestScore+0.5  )//
					{
						
						double sc = Sol.GetScore();
						System.out.println("");
						System.out.println("Id" + cf_id + " Params :" + "new Best Finished complicatedalgo Score : " +sc);
						System.out.println(" Gain for round " + ( sc-firstScore) + " Gain per minute : " + (( sc-firstScore)/( System.currentTimeMillis() - firstTime)  *1000 * 60));
						FullProcess.CheckSolution(Sol);
						BestSolSynchro.StoreNewBestSolution(Sol);
							
					}else
					{
//						System.out.println("Better compactness");
//						BestSolSynchro.StoreNewBestSolution(Sol);
					}
					bestScore = Sol.GetScore();
					Sol.improved =false;
				}else
				{
//					if(rand.nextDouble()<pRestart)// Useful???
//					{
//						Sol = BestSolSynchro.gestBestSolution();
//					}
					if(subInd%50==0)
						System.out.print(".");
				}
			
				// Check with bestScore if another process has not improved
				if(currentBestNum != BestSolSynchro.getNumSol())
				{
					System.out.print("Update" + this.cf_id);
					Sol = BestSolSynchro.gestBestSolution();
					Sol.pb = pb;
					bestScore = Sol.GetScore();
					currentBestNum = BestSolSynchro.getNumSol();
				}
			
				
				
			}
			
		}
		
		return BestSolSynchro.gestBestSolution();
		
	}
			
			
			private Solution IterativelyResolveSubProblem(Problem subProb, Solution subSol, SplittableRandom rand, BestSolutionSynchro BestSolSynchroSub, int NITSUB)
			{
				double pRestart = 0.9;
				
				double bestScore = subSol.GetScore();
				double firstScore = bestScore;
		//		double firstTime = System.currentTimeMillis();
				for( int nit = 0;nit<NITSUB;nit++)
				{
				
				
				subSol= ResolveSubProblem(subProb,subSol,rand);
				
				
				if(subSol.GetScore()>bestScore ||  (subSol.GetScore()==bestScore && subSol.improved ==true))
				{System.out.print("+");
				
					if(subSol.GetScore()>bestScore+0.5  )//
					{
						
						double sc = subSol.GetScore();
						System.out.println("");
						System.out.println("SUB   Id" + cf_id + " Params :" + "new Best Finished complicatedalgo Score : " +sc);
						BestSolSynchroSub.StoreNewBestSolution(subSol,false);
							
					}else
					{
						System.out.println("Better compactness");
						BestSolSynchroSub.StoreNewBestSolution(subSol,false);
					}
					bestScore = subSol.GetScore();
					subSol.improved =false;
				}else
				{
					if(rand.nextDouble()<pRestart)
					{
						subSol = BestSolSynchroSub.gestBestSolution();
			//			System.out.print("Restart");
						//Sol = Common.DeepCopy(bestSol);
					}

				}

			
		}
		
		
		System.out.println("Finisher Divide first "+ firstScore + " new : "+ BestSolSynchroSub.BestSol.GetScore() );
		
		return BestSolSynchroSub.gestBestSolution();
		}
			
			
	
	// Return a subProblem 
	public Problem subProblem( Problem pb)//, xxx parameters)//,TODO : Pass parameters for sub problem optimisation
	{
			//TODO :create sub problem
		Problem subP = new Problem(pb);//, xxx parameters)

		return subP;
		
	}
	
	
	
	
	
	
	

	public Solution ResolveSubProblem( Problem subProb, Solution Sol,  SplittableRandom rand)
	{

		//Solution subSol = new Solution(subProb);
		Sol.curScore = -100;
		
		//TODO : implement solution
		
		
		
		
		return Sol;
	}
	
	
	
	
	
	
	
	
	
	
	
	public Solution AlgoComplicatedFromProblem(Problem pb, SplittableRandom rand,Solution startSol, int NIT)
	{
		
		return startSol ;
	}
	
		
	
	
	private Solution iteration(Problem pb, SplittableRandom rand, Solution sol)
	{
			return sol;
	}
	
	
	
	
	// Algorithm used to find initial best solution
	public Solution AlgoInit(Problem pb, SplittableRandom rand)
	{
		Solution resp = new Solution(pb);
	// TODO : Compute initial condition
		
		// Foreach car, try to allocate the ride with the first starting time accessible, then iterate
		// For each case, the car has a score, covered rides and time.
		boolean finished = true;
		
		
		// Essaye d'allouer les rides
		while(!finished)
		{ 
			for(Car c : resp.Cars)
			{
				boolean limitToStart = false;
				if(!c.finished)
				{
					int rideIdx = FindClosestAccessibleRide(   c, resp.RideServed, pb, limitToStart);//up to 10k   Would need better algo
					if(rideIdx!=0)
					{
						c.addRide(rideIdx,pb);
						resp.RideServed[rideIdx]= true;
						finished = false;
					}else
					{
						c.finished=true;
					}
				}
				
			}
		}
		
		
		
		return resp;
	}

	int FindClosestAccessibleRide(Car c, boolean RideServed[], Problem pb, boolean limitToStart)
	{
		int lastRideIdx = c.RidesServed.get(c.RidesServed.size()-1);
		Point pos ;
		Ride lastRide = pb.Rides.get(lastRideIdx);

		int bestRideIdx = 0;
		// FIrst try to match start
		for(Ride ri : pb.Rides )
		{
			if(Common.IsStartRidable( lastRide, ri, c.lastRideTime ) )
			{
				if(bestRideIdx == 0 || pb.Rides.get(bestRideIdx).s > ri.s  )
				{
					bestRideIdx = ri.id;
				}
			}
		}
		if(bestRideIdx != -1)
		{
			return bestRideIdx;
		}
		// Else try to match closest
		if(!limitToStart)
		{
			for(Ride ri : pb.Rides )
			{
				if(Common.IsRidable( lastRide, ri, c.lastRideTime ) )
				{
					if(bestRideIdx == 0 || pb.Rides.get(bestRideIdx).s > ri.s  )
					{
						bestRideIdx = ri.id;
					}
				}
			}
		}
		
		return bestRideIdx;
	}
	
	
}
