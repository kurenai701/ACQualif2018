package AC2018Qualif;

public class BestSolutionSynchro {
	public Solution BestSol;
	private int NumSol=0;
	
	private double lastBestTime;
	private final double startTime;
	private final double startScore;
	private  double lastSave;
	
	public BestSolutionSynchro(Solution bestSol) {
		super();
		this.lastBestTime = System.currentTimeMillis();
		Solution tmp=null;
//		try {
//			tmp = (Solution) bestSol.clone();
//			tmp.pb = bestSol.pb;
//		} catch (CloneNotSupportedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		tmp = Common.DeepCopy(bestSol);
		
		BestSol = tmp;
		startTime = System.currentTimeMillis();
		lastSave = startTime;
		bestSol.curScore=-1000;
		startScore = bestSol.GetScore();
	}
	
	public synchronized void  StoreNewBestSolution(Solution Sol)
	{
		StoreNewBestSolution( Sol, true);
	}
	
	
	public synchronized void  StoreNewBestSolution(Solution Sol,boolean saveToDisk)
	{
		FullProcess.CheckSolution(Sol);
		double margin = 0.0;
		if(Sol.GetScore() > BestSol.GetScore()+margin ||  (Sol.GetScore()==BestSol.GetScore()+margin && Sol.improved ==true))// todo : to adapt
		{
			double curTime = System.currentTimeMillis();
			System.out.printf("BestSolution update, gain/min from last: %04.2f  || gain/minute total: %04.2f \n" ,(Sol.GetScore() - BestSol.GetScore())/ (curTime-this.lastBestTime)*1000*60  , (Sol.GetScore() - startScore)/ (curTime-this.startTime)*1000*60 );
			
			
			Solution tmp=null;
//			try {
//				tmp = (Solution) Sol.clone();
//				tmp.pb = Sol.pb;
//			} catch (CloneNotSupportedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			tmp = Common.DeepCopy(Sol);
			
			BestSol = tmp;
			if(saveToDisk)
			{
				if((curTime-this.lastSave) > 5*1000)//30*1000)// Was a limit to a save to disk every 30 sec
				{
					Sys.disp("Saving to Disk");
					Sol.SaveSolutionAsRaw("_BestSolutionInProcess.ser");
					FullProcess.ProcessAllBackupOfSolutionToFolder(Sol);
					this.lastSave = curTime;
					
				}
			}
			NumSol++;
			this.lastBestTime = curTime;	
		}
	}
	
	public synchronized Solution  gestBestSolution()
	{
		Solution resp=null;
//		try {
//			resp = (Solution)BestSol.clone();
//		} catch (CloneNotSupportedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		resp.pb = BestSol.pb;
		
		resp = Common.DeepCopy(BestSol);
		return resp;
	}
	public synchronized int  getNumSol()
	{
		return NumSol;
	}
	

}
