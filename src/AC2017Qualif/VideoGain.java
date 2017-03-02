package AC2017Qualif;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class VideoGain implements Comparable<VideoGain> {
	public Video V;
	public long Score;
	public SortedSet<Request> ServedRequest;
	
	
	
	public VideoGain(Video v, long score) {
		super();
		V = v;
		Score = score;
		ServedRequest = Collections.synchronizedSortedSet(new TreeSet<Request>());
	}



	@Override
	public int compareTo(VideoGain o) {
		int c = Long.compare(Score,o.Score);
		if(c==0)
			return Integer.compare(V.ID, o.V.ID);
		return c;
		
	}
	
	
	
	
}
