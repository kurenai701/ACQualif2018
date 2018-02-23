package AC2018Qualif;

import java.io.Serializable;




// Cette classe ScoreInfo permettra de modeliser de façon plus comlexe

public class ScoreInfo implements Serializable {
	
	

	private static final long serialVersionUID = 43L;
		
	double score;
	
	
	public ScoreInfo(){}	
		
	public ScoreInfo(double score) {
		super();
		this.score = score;
	}
	
	
	
}
