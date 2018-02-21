package AC2017Qualif;

import java.io.Serializable;

public class VideoGain implements Comparable<VideoGain>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4663119527379423380L;
	public int VID;
	public double Score;
		
	
	
	public VideoGain(int vid, double score) {
		super();
		VID = vid;
		Score = score;
	}



	@Override
	public int compareTo(VideoGain o) {
		if(o==null)
			return -1;
		int c = Double.compare(o.Score,Score);
		if(c==0)
			return Integer.compare(o.VID,VID);
		return c;
		
	}
	
	
	
	
}
