package AC2017Qualif;

import java.io.Serializable;

/*
 * This class stores a solution. It is serializable for storage to file.
 *  OutputModel : classe Output
 *  @author : Clemence MEGE
 */
  
public class Solution implements Serializable, Cloneable {
	
	
	private static final long serialVersionUID = 420L;
	
	// for concours : list of ballons and movements

	double curScore; // current score
	// TODO : Put solution HERE ****************************************
	// Solution is stored in problem
		Problem pb;	
					
	//************************************************************
	
	
		

	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Solution sup = (Solution)super.clone();
		
		// TODO : code here what is needed fo deep cloning
//		sup.Cells = new ArrayList<Cell>(Cells );
//	
//		sup.Pizza = this.Pizza.clone();
//		for(int r=0;r<pb.R;r++)
//		{
//			sup.Pizza[r] = (this.Pizza[r]).clone();
//
//		}
		
		sup.curScore = this.curScore;
		return sup;
	}
	
	
	public Solution(Problem pb) {
		super();
		this.pb = pb;	
		this.curScore = -1000;
	}
	
	
	@Override
	public String toString() {
		return "Solution [curScore=" + curScore +"]";// + ", Cells=" + Cells + "]";
	}



	/**
	 * Recomputes score from model
	 * @return score of current solution
	 */
	private ScoreInfo GetScoreModel()
	{

		// *******************************
		// ** Currently, hardcore version, recompute everything	**
		//********************************
		double tempscore = 0;
		for(Request req :pb.RequestList)
		{
			double LD = req.eP.LD;
			double LC = LD;
			for(Server s : pb.ServerList)
			{
				if(s.VideosCached.contains(req.V.ID))
				{
					LC = Math.min(LC, req.eP.Latency4ServerList.get(s.servID));
				}
			}
			
			if(LC!=req.curLatency)
			{
				Sys.disp("Error, curLatency of Endpoint not valid!!!");
			}
			
			
			
			
			tempscore += (LD-LC)*req.Nreq*1000.0/pb.SR;
			
		}
		
		
		
		//*******************************
		
		ScoreInfo scoringInfo = new ScoreInfo(tempscore);
		return scoringInfo;
	}
	
	public double GetScore()
	{
		if(curScore>-100)
			return curScore;
		
		ScoreInfo scoringInfo = this.GetScoreModel();
		curScore = scoringInfo.score;
		return scoringInfo.score;
	}
	
	public double PrintScore()
	{ 	
		double scoreDoub = GetScore();
		
		System.out.println("/////////////////////////");
		System.out.println("EvalScore : " + scoreDoub);
		System.out.println("/////////////////////////");
		
		return scoreDoub;
	}

	
	
	public void SaveSolutionAsRaw(String fileName)
	{			
		Common.FU.SerializeObjectToFile(this, Common.ACFileFolderPath+ Common.InputFileName + fileName);
	}
	
	public void SaveSolutionAsRawToFullPath(String fullFilePath)
	{
		Common.FU.SerializeObjectToFile(this, fullFilePath);
	}
	
	
	
	
		
	
}
