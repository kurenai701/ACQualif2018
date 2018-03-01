package AC2018Qualif;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * This class stores a solution. It is serializable for storage to file.
 *  OutputModel : classe Output
 *  @author : Clemence MEGE
 */
  
public class Solution implements Serializable, Cloneable {
	public boolean improved = false;
	
	private static final long serialVersionUID = 421L;
	

	double curScore; // current score
	// TODO : Put solution HERE ****************************************
	// Solution is stored in problem
		Problem pb;	
		ArrayList<Car> Cars;
		boolean RideServed[];
		
					
	//************************************************************
	
	final double COEFCOMPACT = 0;//1e-11;
		

/*	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Solution sup = (Solution)super.clone();
		
		// TODO : code here what is needed for deep cloning
		sup.pb = (Problem)pb.clone();
		
		sup.curScore = this.curScore;
		return sup;
	}
	
	*/
	public Solution(Problem pb) {
		super();
		this.pb = pb;	
		this.curScore = -1000;
		this.Cars = new ArrayList<Car>();
		for(int i=0;i<pb.F;i++)
		{
			this.Cars.add(new Car(pb.N));
		}
		this.RideServed = new boolean[pb.N];	
		
	}
	
	
	@Override
	public String toString() {
		return "Solution [curScore=" + curScore +"]";// + ", Cells=" + Cells + "]";
	}



	/**
	 * Recomputes score from model
	 * @return score of current solution
	 */
	private ScoreInfo GetScoreModel(boolean detailLog)
	{

		// *******************************
		// ** Recoputes score
		//********************************
		double tempscore = 0;
		// TODO :compute score of solution
			
			
			
		
		
		
		
		//*******************************
		
		ScoreInfo scoringInfo = new ScoreInfo(tempscore);
		return scoringInfo;
	}
	
	public double GetScore()
	{
		return GetScore(false);
	}
	
	public double GetScore(boolean detailLog)
	{
//		if(curScore>-100)
//			return curScore;
		
		ScoreInfo scoringInfo = this.GetScoreModel(detailLog);
		curScore = scoringInfo.score;
		return scoringInfo.score;
	}
	
	
//	//Update score for video
//	public UpdateScore(Video vid)
//	{
//		for(Request req :pb.RequestForVideo.get(vid.ID))
//		{
//			
//			
//		}
//		
//		for(Server s : pb.ServerList)
//		{
//			tempscore = tempscore +1.0/(pb.X-s.sizeUsed+1.0)*COEFCOMPACT;// 1/x pent decrease with higher values
//		}	
//		
//	}
	
	
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
