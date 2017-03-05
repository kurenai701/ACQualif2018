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
		Sol.curScore = -1000;
		double bestScore = Sol.GetScore();
		double firstScore = bestScore;
		double firstTime = System.currentTimeMillis();
		
		int currentBestNum = BestSolSynchro.getNumSol();
	//	Sys.disp("START");
		
		
		//*************
		
		SubProblems.add(pb);// Currently, no divide and conquer
		double pRestart = 0.9;
		
		for( int nit = 0;nit<NIT;nit++)
		{
		
		
		//******************************************************************************

	
		
			for(int subInd = 0; subInd<SubProblems.size();subInd++)
			{
				Problem subProb = subProblem(pb);
	
			//	Sol= ResolveSubProblem2(subProb,Sol,rand);
				Sol= ResolveSubProblem(subProb,Sol,rand);
				
		//		Sys.disp("bestScore"+bestScore);
				
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
						System.out.println("Better compactness");
						BestSolSynchro.StoreNewBestSolution(Sol);
					}
					bestScore = Sol.GetScore();
	//				bestSol = Common.DeepCopy(Sol);
					Sol.improved =false;
				}else
				{
					if(rand.nextDouble()<pRestart)
					{
						Sol = BestSolSynchro.gestBestSolution();
			//			System.out.print("Restart");
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
	
	
	
	
	// This algorithm tries to compact the server by exchanging 2 videos between servers.
	public Solution ResolveSubProblem2( Problem subProb, Solution Sol,  SplittableRandom rand)
	{
		Sol.curScore = -1000;
		double bestScore = Sol.GetScore();
	//	Sys.disp("initialScore :"+bestScore);
		//Solution subSol = new Solution(subProb);
		Sol.curScore = -1000;
		
		 int NITERATIONS = 30;//100
		 int NSERVPAIRS  = 200;//500
		
		for(int nservpair = 0;nservpair < NSERVPAIRS;nservpair++)
		{
		
		Server serv1 = subProb.ServerList.get(rand.nextInt(subProb.ServerList.size()));
		Server serv2 = subProb.ServerList.get(rand.nextInt(subProb.ServerList.size()));
		
		if(serv1.servID==serv2.servID)
			continue;
		
		double prodSize =  serv1.sizeUsed * serv2.sizeUsed;
		
		Integer[]  Vidlist1 = serv1.VideosCached.toArray(new Integer[serv1.VideosCached.size()]);
		Integer[]  Vidlist2 = serv2.VideosCached.toArray(new Integer[serv2.VideosCached.size()]);
		if(Vidlist1.length==0 || Vidlist2.length==0)
			return Sol;

		// Try exchange of two videos between servers
		for(int niteration = 0;niteration< NITERATIONS;niteration++)
		{
				int vid1 = Vidlist1[rand.nextInt( Vidlist1.length)];
				int vid2 = Vidlist2[rand.nextInt( Vidlist2.length)];
				if(vid1==vid2)
					continue;
				
	
				
				serv1.RemoveVideoFromCache( subProb.VideoList.get(vid1));
				serv2.RemoveVideoFromCache( subProb.VideoList.get( vid2));
				boolean addedin1 = serv1.PutVideoInCache(subProb.VideoList.get(vid2));
				boolean addedin2 = serv2.PutVideoInCache(subProb.VideoList.get(vid1));
				Sol.curScore = -1000;
				double newScore = Sol.GetScore();
				if(newScore>bestScore  )// || (newScore==initialScore)&& serv1.sizeUsed * serv2.sizeUsed <prodSize )// prodsize is used to enforce movements that concentrate videos 
				{
					bestScore = newScore;
					//Sys.disp("newScore :"+newScore);
					System.out.print("x");
					prodSize = serv1.sizeUsed * serv2.sizeUsed;
					Sol.improved = true;
				}else
				{
					if(addedin1)
						serv1.RemoveVideoFromCache( subProb.VideoList.get(vid2));
					if(addedin2)
						serv2.RemoveVideoFromCache( subProb.VideoList.get( vid1));
					serv1.PutVideoInCache(subProb.VideoList.get(vid1));
					serv2.PutVideoInCache(subProb.VideoList.get(vid2));
					
				
				}
//				Sol.curScore = -1000;
//				double outs = Sol.GetScore();
//				if(outs != bestScore)
//					Sys.disp("ERROR");
				
		}
		
			
		}
		
		Sol.curScore = -1000;
		Sys.disp("Score at output :"+ Sol.GetScore());
		return Sol;
	}
	
	//select best videogains from in servers, and add it to cache. Returns Score change
	private double FillWithBestVideoGains( ArrayList<Server>  CurServerList, Problem pb, LinkedList<Integer> TabuList, int Ntabu,double paccept,  SplittableRandom rand)
	{
		double scoreChange = 0.0;
		VideoGain bestVG = null;
		Server bestServ = null;
		for(Server s : CurServerList)
		{
			//VideoGain curVG = s.VideosPriority.peek();
			VideoGain curVG = s.ReturnBestCandidateVideo();
			if( (curVG!=null) && (bestVG == null ||  bestVG.Score <= curVG.Score) && !TabuList.contains(curVG.VID) && paccept>rand.nextDouble()   ) // TODO : add look into tree for best score that fits, and log size
			{
				if(bestVG == null || bestVG.Score < curVG.Score)
				{
					bestVG   = curVG;
					bestServ = s;
				}else
				{
					//Best fit algorithm
					if( s.sizeUsed> bestServ.sizeUsed  )
					{
						bestVG   = curVG;
						bestServ = s;
				//		Sys.disp("Best fitting");
					}
					
					
				}
				
			}
			
		}
	
		
		
		if(bestVG==null)
		{
			TabuList.removeLast();
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
	
	
	

	public Solution ResolveSubProblem( Problem subProb, Solution Sol,  SplittableRandom rand)
	{

		//Solution subSol = new Solution(subProb);
		Sol.curScore = -100;
		
		
		
		// Select 'NServersOpt' servers to optimize
		int NServersOpt = 1;  // 1????
		int NITERATIONS = 2;//3
		int NVIDEOREMOVEDPERSERVER = 30;//20;
		int Ntabu = 1;// rand.nextInt(15);//Tabu list length
		double paccept =1.0;
		
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
				
				double scoreInc = FillWithBestVideoGains( servOptimized, subProb, TabuList,Ntabu,paccept,rand);
				
			
				if(scoreInc == 0)
					break;

			}
		
			
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
			double scoreInc = FillWithBestVideoGains( pb.ServerList, pb,TabuList,0,1,rand);
			
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
