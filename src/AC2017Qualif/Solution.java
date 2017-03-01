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
	int MAXid;
	// TODO : Put solution HERE ****************************************
		//ArrayList<Cell> Cells;
					
	//************************************************************
	
	
		
	// problem
	transient Problem pb;
	
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
	
		this.curScore = 0;
		//this.Cells = new ArrayList<Cell>();  <= TODO
		this.MAXid = 0;
		this.curScore = -1000;
	}
	
	
	@Override
	public String toString() {
		return "Solution [curScore=" + curScore + ", MAXid=" + MAXid + "]";// + ", Cells=" + Cells + "]";
	}



	/**
	 * Recomputes score from model
	 * @return score of current solution
	 */
	private ScoreInfo GetScoreModel()
	{

		
		double score = 0;
		// *******************************
		// ** TODO : code score here	**
		//********************************
		
		
		//*******************************
		
		ScoreInfo scoringInfo = new ScoreInfo(score);
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
