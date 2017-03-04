package AC2017Qualif;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.SplittableRandom;


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
		double bestScore = Sol.GetScore();
		double firstScore = bestScore;
		double firstTime = System.currentTimeMillis();
		
		int currentBestNum = BestSolSynchro.getNumSol();
		
		
		
		//*************
		
		SubProblems.add(pb);// Currently, no divide and conquer
		double pRestart = 0.3;
		
		for( int nit = 0;nit<NIT;nit++)
		{
		
		
		//******************************************************************************

	
		
			for(int subInd = 0; subInd<SubProblems.size();subInd++)
			{
				Problem subProb = subProblem(pb);
	
				Sol= ResolveSubProblem(subProb,Sol,rand);
			 
				
				
				if(Sol.GetScore()>bestScore)
				{System.out.print("+");
					
					if(Sol.GetScore()>bestScore+0.5  )// savve every 2s at max
					{
						
						double sc = Sol.GetScore();
						System.out.println("");
						System.out.println("Id" + cf_id + " Params :" + "new Best Finished complicatedalgo Score : " +sc);
						System.out.println(" Gain for round " + ( sc-firstScore) + " Gain per minute : " + (( sc-firstScore)/( System.currentTimeMillis() - firstTime)  *1000 * 60));
						FullProcess.CheckSolution(Sol);
						BestSolSynchro.StoreNewBestSolution(Sol);
							
					}
					bestScore = Sol.GetScore();
	//				bestSol = Common.DeepCopy(Sol);
				}else
				{
					if(rand.nextDouble()<pRestart)
					{
						Sol = BestSolSynchro.gestBestSolution();
						//Sol = Common.DeepCopy(bestSol);
					}
					if(subInd%50==0)
						System.out.print(".");
				}
			
				if(currentBestNum != BestSolSynchro.getNumSol())
				{
					System.out.print("Update" + this.cf_id);
					Sol = BestSolSynchro.gestBestSolution();
					Sol.pb = pb;
					bestScore = Sol.GetScore();
					currentBestNum = BestSolSynchro.getNumSol();
				}
			
			
			
			
		}
		
		
		System.out.println("Finisher Divide"+ BestSolSynchro.BestSol.GetScore());
		}
		return BestSolSynchro.gestBestSolution();
		
		
	}
	
	// Return a subProblem 
	public Problem subProblem( Problem pb)
	{
			//TODO :create sub problem
		//Problem subP = new Problem(pb);
		
		
		
		
		
		
		Problem subP = pb;
		return subP;
		
	}
	
	
	public Solution ResolveSubProblem( Problem subProb, Solution Sol,  SplittableRandom rand)
	{

		//Solution subSol = new Solution(subProb);
		Sol.curScore = -100;
		
		// Resolve subProblem
		double scoreCur = 0.0;
		
		
		// Select 'NServersOpt' servers to optimize
		int NServersOpt = 2;  // 1????
		int NITERATIONS = 1;//3
		int NVIDEOREMOVEDPERSERVER = 100;//20;
		int Ntabu = rand.nextInt(15);//Tabu list length
		
		ArrayList<Server> servOptimized = new ArrayList<>();
		for(int i = 0;i<NServersOpt;i++)
		{
			servOptimized.add(subProb.ServerList.get( rand.nextInt(subProb.ServerList.size())   ));
		}
		
		
		

		
		LinkedList<Integer> TabuList = new LinkedList<>();
		
		
		for(int niteration = 0;niteration< NITERATIONS;niteration++)
		{
			
			// Remove videos from servers at random
			for(Server s :servOptimized )
			{
				
				Integer[]  Vidlist = s.VideosCached.toArray(new Integer[s.VideosCached.size()]);
				if(Vidlist.length>0)
				{
					for(int nvr=0;nvr<NVIDEOREMOVEDPERSERVER;nvr++)
					{
						s.RemoveVideoFromCache( subProb.VideoList.get( Vidlist[rand.nextInt(Vidlist.length)]));
					}
				}
			}
			
			

			
			// Put back best videos
			int nit = 0;
			
			while(true)
			{
				nit++;
				
				double scoreInc = FillWithBestVideoGains( servOptimized, subProb, TabuList,Ntabu);
				
				scoreCur += scoreInc;
				if(scoreInc == 0)
					break;

			}
		
			
		}
		
		
		
		
		return Sol;
	}
	
	//select best videogains from in servers, and add it to cache. Returns Score change
	private double FillWithBestVideoGains( ArrayList<Server>  CurServerList, Problem pb, LinkedList<Integer> TabuList, int Ntabu)
	{
		double scoreChange = 0.0;
		VideoGain bestVG = null;
		Server bestServ = null;
		for(Server s : CurServerList)
		{
			//VideoGain curVG = s.VideosPriority.peek();
			VideoGain curVG = s.ReturnBestCandidateVideo();
			if( (curVG!=null) && (bestVG == null ||  bestVG.Score < curVG.Score) && !TabuList.contains(curVG.VID)    ) // TODO : add look into tree for best score that fits, and log size
			{
				bestVG   = curVG;
				bestServ = s;
				
			}
			
		}
	
		
		if(bestVG==null)
		{
			return scoreChange;
		}else{
			
			TabuList.push(bestVG.VID);
			if(TabuList.size()>Ntabu)
			{
				TabuList.removeLast();
			}
			
			scoreChange=(bestVG.Score * ( pb.VideoList.get(bestVG.VID).size +pb.smallOffset))*1000.0/ pb.SR;
			bestServ.PutVideoInCache(pb.VideoList.get(bestVG.VID));
		}

//		if(nit%10==0)
//		{
//			Sys.disp(" put video :" + bestVG.V.ID + " in server:" + bestServ.servID ) ;
//			Sys.disp(" it " + nit +" score inc :" + scoreCor+ " Score : " + Math.floor(scoreCur));
//		}
		
		
		return scoreChange;
		
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
		int cntvid = 0;
		// First, update all server priority list
		for(Video vid : pb.VideoList)
		{
			for(Server s : pb.ServerList)
			{
				VideoGain VG = new VideoGain(vid.ID, -1.0);
				s.AllVideoGains.add(VG);
				s.VideosPriority.add(VG);
				
			}
			pb.ServerList.get(0).updateAllServersVG(vid);
			cntvid++;
			if(cntvid%100==0)
			{
				Sys.disp("vid add : " + vid.ID);
			}
		}
		
		// Then, find Best VG accross all servers, put it in cache, and iterate until end
		double scoreCur = 0;
		int nit = 0;
		
		while(true)
		{
			nit++;
			LinkedList<Integer> TabuList = new LinkedList<>();
			double scoreInc = FillWithBestVideoGains( pb.ServerList, pb,TabuList,0);
			
			scoreCur += scoreInc;
			if(scoreInc == 0)
				break;

		}
		
		
		Sys.disp(" Final Score : " + (scoreCur));
		Sys.disp("Recomputed score : " + resp.GetScore());
		
		Sys.disp(" Final Score round : " + (long)Math.floor(scoreCur));
		return resp;
	}

	
		
	
}
