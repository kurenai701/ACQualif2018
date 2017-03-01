package AC2017Qualif;

import java.util.ArrayList;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.PriorityBlockingQueue;

public class Server {
	public int servID;
	public ArrayList<EndPoint> ServedEndPoint;
	public SortedSet<Integer> VideosCached;
	PriorityBlockingQueue<VideoGain> VideosPriority;// contains the videos not yet in server sorted by potential gains
	ArrayList<VideoGain> AllVideoGains;// contains the gain of videos, indexed by video IDs. Used to update VideosPriority;
	
	
	
	public Server(int servID, ArrayList<EndPoint> servedEndPoint,  int sizeUsed) {
		super();
		this.servID = servID;
		ServedEndPoint = servedEndPoint;
		VideosCached =  Collections.synchronizedSortedSet(new TreeSet<Integer>());
		this.sizeUsed = sizeUsed;
		
		VideosPriority = new PriorityBlockingQueue<>();
		
		
	}
	public int sizeUsed;
	
	
	
	
	public int EvaluateGainAddingVideo(Video vid)
	{
		int resp = 0;
		// Evaluate Gain by adding the Video vid to this server
		for(EndPoint ep : ServedEndPoint)
		{
			
			//TODO : to optimize with HashMap
			if(ep.RequestList.containsKey(vid.ID))
			{
				Request Rq = (ep.RequestList.get(vid.ID));
			
				if(Rq.V.ID == vid.ID)
				{
					resp +=Math.max( 0,Rq.curLatency-ep.Latency4ServerList.get(servID))*Rq.Nreq; 
				}
			
			}
			
		}
		return resp;
	}
	
	
	// Removes video from server. Does nothing if video not present
	public void RemoveVideo(Video vid)
	{
		// Get videoGain
		VideoGain VG = AllVideoGains.get(vid.ID);
		
		// Remove video from server
		VideosCached.remove(vid.ID);
		
		// Update affected requests
		for( Request Rq : VG.ServedRequest  )
		{
			// Update Request
			Rq.UpdateStat();
			
		}
		// Clear affected Request
		VG.ServedRequest = Collections.synchronizedSortedSet(new TreeSet<Request>());;
		
		
		// compute gain  
		VG.Score = EvaluateGainAddingVideo(vid);
		
		
		
		
	}
	
	
}
