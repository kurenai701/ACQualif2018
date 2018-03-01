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
	private boolean ConsiderStart;
	
	public AlgoInputToOutput(Problem cf_pb, SplittableRandom cf_rand, Solution cf_Sol, BestSolutionSynchro cf_BestSolSynchro, int cf_id,  int cf_NIT,boolean considerStart) {
		super();
		this.cf_id = cf_id;
		this.cf_pb = cf_pb;
		this.cf_rand = cf_rand;
		this.cf_Sol = cf_Sol;
		this.cf_BestSolSynchro = cf_BestSolSynchro;
		this.NIT = cf_NIT;
		this.ConsiderStart = considerStart;

	}
	
	public AlgoInputToOutput() {
		super();
		
	}


	@Override
	public void run()	{
		DivideAndConquer(cf_pb, cf_rand, cf_Sol,cf_BestSolSynchro,NIT, ConsiderStart);

	}

	
	
	
	public Solution DivideAndConquer(Problem pb, SplittableRandom rand, Solution Sol,BestSolutionSynchro BestSolSynchro, int NIT,boolean ConsiderStart )
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

		int NITSUB = 60000;// Number of iteration for each subproblem
		
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
				SubSol= IterativelyResolveSubProblem(subProb,SubSol,rand,BestSolSynchroSub, NITSUB, ConsiderStart);
				
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
			
			
			private Solution IterativelyResolveSubProblem(Problem subProb, Solution subSol, SplittableRandom rand, BestSolutionSynchro BestSolSynchroSub, int NITSUB,boolean ConsiderStart)
			{
				double pRestart = 1;
				
				double bestScore = subSol.GetScore();
				double firstScore = bestScore;
		//		double firstTime = System.currentTimeMillis();
				for( int nit = 0;nit<NITSUB;nit++)
				{
				
				
				subSol= ResolveSubProblem(subProb,subSol,rand, ConsiderStart);
				
				
				if(subSol.GetScore()>bestScore ||  (subSol.GetScore()==bestScore && subSol.improved ==true))
				{System.out.print("+");
				
					if(subSol.GetScore()>bestScore+0.5  )//
					{
						
						double sc = subSol.GetScore();
						System.out.println("");
						System.out.println("SUB   Id" + cf_id + " Params :" + "new Best Finished complicatedalgo Score : " +sc);
						BestSolSynchroSub.StoreNewBestSolution(subSol,true);
							
					}else
					{
						System.out.println("Better compactness");
						BestSolSynchroSub.StoreNewBestSolution(subSol,true);
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
	
	
	
	
	
	
	

	public Solution ResolveSubProblem( Problem subProb, Solution Sol,  SplittableRandom rand,boolean ConsiderStart)
	{

		//Solution subSol = new Solution(subProb);
		Sol.curScore = -100;
		
		// Remove a list of cars;
		int nCareToRemove = 1;//(int)(subProb.F*0.1);
		boolean removedCar[] = new boolean[subProb.F];
		while(nCareToRemove>0)
		{
			int idx = rand.nextInt(subProb.F);
			if(!removedCar[idx])
			{
				nCareToRemove--;
				removedCar[idx] = true;
				Sol.removeCar(idx);
			}
		}
		
		int nRideToRemove = (int)(subProb.N*0.1);
		if(rand.nextDouble()<0.2)
		{
			nRideToRemove=0;
		}
		
		
		
		boolean[] rideCoveredSave = Sol.RideServed.clone();
		int[] listI = new int[nRideToRemove];
		for(int i =0;i<nRideToRemove;i++)
		{
			int k=rand.nextInt(subProb.N);
			listI[i]=k;
			Sol.RideServed[k] = true;
		}
		
		Sol =  AlgoInit( subProb,  rand, Sol, ConsiderStart);
		for(int i =0;i<nRideToRemove;i++)
		{
			int k = listI[i];
			Sol.RideServed[k] = rideCoveredSave[k];
		}
		
		
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
	public Solution AlgoInit(Problem pb, SplittableRandom rand,boolean ConsiderStart)
	{
		Solution resp = new Solution(pb);
		return AlgoInit( pb,  rand, resp, ConsiderStart);
	}
		
		
	public Solution AlgoInit(Problem pb, SplittableRandom rand,Solution resp, boolean ConsiderStart)
	{
		
	// TODO : Compute initial condition
		
		// Foreach car, try to allocate the ride with the first starting time accessible, then iterate
		// For each case, the car has a score, covered rides and time.
		boolean finished = false	;
		
		
		// Essaye d'allouer les rides
		boolean limitToStart = ConsiderStart;
		while(!finished)
		{ 
			finished = true;

				for(Car c : resp.Cars)
				{
					
					if(!c.finished)
					{
						int rideIdx = FindClosestAccessibleRide(   c, resp.RideServed, pb, limitToStart,resp,ConsiderStart,rand);//up to 10k   Would need better algo
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

			if(finished && limitToStart)// first tried all starts
			{
				finished = false;
				limitToStart = false;
				for(Car c : resp.Cars){
					c.finished = false;
				}
			}
		}
		
		
		
		return resp;
	}

	int FindClosestAccessibleRide(Car c, boolean RideServed[], Problem pb, boolean limitToStart, Solution sol, boolean ConsiderStart,SplittableRandom rand)
	{
		int lastRideIdx = c.RidesServed.get(c.RidesServed.size()-1);
		Point pos ;
		Ride lastRide = pb.Rides.get(lastRideIdx);
		double pbkeep = 0.9;
		int bestDist = Integer.MAX_VALUE;
		int bestRideIdx = 0;
		if(ConsiderStart)
		{
			// FIrst try to match start
			for(Ride ri : pb.Rides )
			{
				if(!sol.RideServed[ri.id] &&  Common.IsStartRidable( lastRide, ri, c.lastRideTime ) )
				{
					if(bestRideIdx == 0 || (pb.Rides.get(bestRideIdx).f > ri.f  && rand.nextDouble()<pbkeep) )
					{
						bestRideIdx = ri.id;
					}
				}
			}
			if(bestRideIdx != 0)
			{
				return bestRideIdx;
			}
		}
		// Else try to match closest
		if(!limitToStart)
		{
			for(Ride ri : pb.Rides )
			{
				if(!sol.RideServed[ri.id] && Common.IsRidable( lastRide, ri, c.lastRideTime ) )
				{
					if(bestRideIdx == 0 || (pb.Rides.get(bestRideIdx).f > ri.f && rand.nextDouble()<pbkeep) )
					{
						bestRideIdx = ri.id;
						bestDist = Common.Dist(ri,lastRide);
					}
				}
			}
		}
		
		return bestRideIdx;
	}
	
	
}
