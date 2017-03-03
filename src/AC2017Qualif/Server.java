package AC2017Qualif;

import java.util.ArrayList;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.PriorityBlockingQueue;

public class Server {
	public Problem pb; 
	public int servID;
	public ArrayList<EndPoint> ServedEndPoint;// All EndPoint served
	public SortedSet<Integer> VideosCached;
	PriorityBlockingQueue<VideoGain> VideosPriority;// contains the videos not yet in server sorted by potential gains
	ArrayList<VideoGain> AllVideoGains;// contains the gain of videos, indexed by video IDs. Used to update VideosPriority;
	
	
	
	public Server(int servID, ArrayList<EndPoint> servedEndPoint, Problem pb) {
		super();
		this.servID = servID;
		ServedEndPoint = servedEndPoint;
		VideosCached =  Collections.synchronizedSortedSet(new TreeSet<Integer>());
		this.sizeUsed = 0;
		AllVideoGains = new ArrayList<VideoGain>();
		VideosPriority = new PriorityBlockingQueue<>();
		this.pb = pb;
		
	}
	public int sizeUsed;
	
	
	
	
	public long EvaluateGainAddingVideo(Video vid)
	{
		long resp = 0;
		// Evaluate Gain by adding the Video vid to this server
		for(Request rq : pb.RequestForVideo.get(vid.ID))// Nendpoint iteration.  Could be optimized by using only the Endpoints with request for this video
		{
			EndPoint ep = rq.eP;
			
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
	public void RemoveVideoFromCache(Video vid)
	{
		// Get videoGain
		VideoGain VG = AllVideoGains.get(vid.ID);
		
		
		// Update size
		if(VideosCached.contains(vid.ID))
			sizeUsed = sizeUsed - vid.size;
		
		// Remove video from server
		VideosCached.remove(vid.ID);
		
		// Update affected requests
		for( Request Rq : VG.ServedRequest  )
		{
			// Update Request
			Rq.UpdateStat();
			
		}
		// Clear affected Request List
				VG.ServedRequest = Collections.synchronizedSortedSet(new TreeSet<Request>());;
				
		// Update all VG scores for video
		 updateAllServersVG( vid);
		
		
		
		
	}
	

	// Put video in server. 
	public void PutVideoInCache(Video vid)
	{
		// Get videoGain
		VideoGain VG = AllVideoGains.get(vid.ID);
		
		// Update size
		if(!VideosCached.contains(vid.ID))
			sizeUsed = sizeUsed + vid.size;
		if(sizeUsed > pb.X)
		{
			Sys.disp("ERROR, server overflow");
			
		}
		
		// Add video to server
		VideosCached.add(vid.ID);
		
		
		// Update affected RequestList
		for(Request Rq : pb.RequestForVideo.get(vid.ID))// Nendpoint iteration.  Could be optimized by using only the Endpoints with request for this video
		{
			
//				if(Rq.V.ID == vid.ID)
//				{
//					VG.ServedRequest.add(Rq);  // TODO : add at creation
//				}
			
//			}
			Rq.curLatency = Math.min( Rq.eP.LD, Rq.eP.Latency4ServerList.get(servID));
			
		}
		
		// Remove from priority queue
		VideosPriority.remove(VG);
		

		// Update all VG scores for video
		 updateAllServersVG( vid);

	}
	
	
	public void updateAllServersVG(Video vid)
	{
		// Update Video Gain Score on all Servers (including this one)
		for(Server s : pb.ServerList )
		{
			if(!s.VideosCached.contains(vid.ID))
			{		
				VideoGain VG2 = s.AllVideoGains.get(vid.ID);
				// compute gain and update priority list
				s.VideosPriority.remove(VG2);
				VG2.Score = s.EvaluateGainAddingVideo(vid);
				s.VideosPriority.put(VG2);
				
				
			}
			
		}
	}
	
	
	
	
}
