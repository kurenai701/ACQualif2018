package AC2017Qualif;

import java.awt.Point;
import java.util.ArrayList;
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
		
		
		
		// Todo : create sub problems
		ArrayList<Problem> SubProblems = new ArrayList<Problem>();
		ArrayList<Point> StartPos = new ArrayList<Point>();
		double bestScore = Sol.GetScore();
		double firstScore = bestScore;
		double firstTime = System.currentTimeMillis();
		int currentBestNum = BestSolSynchro.getNumSol();
		
		
		for( int nit = 0;nit<NIT;nit++)
		{
		
		
		//******************************************************************************

		
		
		for(int subInd = 0; subInd<StartPos.size();subInd++)
		{
			Problem subProb = SubProblems.get(subInd);

			Sol= ResolveSubProblem(subProb,Sol,rand);
		 
			
			
			if(Sol.GetScore()>bestScore)
			{System.out.print("+");
				
				if(Sol.GetScore()>bestScore+0.5)
				{
					double sc = Sol.GetScore();
					System.out.println("");
					System.out.println("Id" + cf_id + " Params :" + "new Best Finished complicatedalgo Score : " +sc);
					System.out.println(" Gain for round " + ( sc-firstScore) + " Gain per minute : " + (( sc-firstScore)/( System.currentTimeMillis() - firstTime)  *1000 * 60));
					FullProcess.CheckSolution(Sol);
					BestSolSynchro.StoreNewBestSolution(Sol);

				}
				bestScore = Sol.GetScore();
			
			}else
			{
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
		
		
		System.out.println("Finisher Divide"+ Sol.GetScore());
		}
		return Sol;
		
		
	}
	
	// Return a subProblem 
	public Problem subProblem( Problem pb)
	{
			//TOdo :create sub problem
		
		Problem subP = new Problem( );
		return subP;
		
	}
	
	
	public Solution ResolveSubProblem( Problem subProb, Solution Sol,  SplittableRandom rand)
	{

		Solution subSol = new Solution(subProb);
		
		
		// Resolve subProblem
		
		
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
	
	
	public Solution AlgoInit(Problem pb, SplittableRandom rand)
	{
		Solution resp = new Solution(pb);
		int cntvid = 0;
		// First, update all server priority list
		for(Video vid : pb.VideoList)
		{
			for(Server s : pb.ServerList)
			{
				VideoGain VG = new VideoGain(vid, -1.0);
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
			VideoGain bestVG = null;
			Server bestServ = null;
			for(Server s : pb.ServerList)
			{
				VideoGain curVG = s.VideosPriority.peek();
				if((bestVG == null ||  bestVG.Score < curVG.Score)  && ( s.sizeUsed + curVG.V.size <= pb.X)  ) // TODO : add look into tree for best score that fits, and log size
				{
					bestVG   = s.VideosPriority.peek();
					bestServ = s;
					
				}
				
			}
			
			double scoreCor = 0.0;
			if(bestVG==null)
			{
				break;
			}else{
				scoreCor=(bestVG.Score * (bestVG.V.size+pb.smallOffset))*1000.0/ pb.SR;
				scoreCur += scoreCor;
				bestServ.PutVideoInCache(bestVG.V);
			}
			if(nit%10==0)
			{
				Sys.disp(" put video :" + bestVG.V.ID + " in server:" + bestServ.servID ) ;
				Sys.disp(" it " + nit +" score inc :" + scoreCor+ " Score : " + Math.floor(scoreCur));
			}
		}
		
		
		Sys.disp(" Final Score : " + (scoreCur));
		Sys.disp("Recomputed score : " + resp.GetScore());
		
		Sys.disp(" Final Score round : " + (long)Math.floor(scoreCur));
		return resp;
	}

	
		
	
}
