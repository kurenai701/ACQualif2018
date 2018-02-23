package AC2018Qualif;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Server implements Serializable, Comparable<Server> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6788409562011863866L;
	public Problem pb; 
	public int servID;
	public int originalServID;
	//public ArrayList<EndPoint> ServedEndPoint;// All EndPoint served
	public SortedSet<Integer> VideosCached;
	TreeSet<VideoGain> VideosPriority;// contains the videos not yet in server sorted by potential gains
	ArrayList<VideoGain> AllVideoGains;// contains the gain of videos, indexed by video IDs. Used to update VideosPriority;
	public int sizeUsed;
	
	private  VideoGain StartPosForSmaller;
	
	public Server(int servID, Problem pb) {//, ArrayList<EndPoint> servedEndPoint
		super();
		this.servID = servID;
//		ServedEndPoint = servedEndPoint;
		VideosCached =  Collections.synchronizedSortedSet(new TreeSet<Integer>());
		this.sizeUsed = 0;
		AllVideoGains = new ArrayList<VideoGain>();
		VideosPriority = new TreeSet<>();
		this.pb = pb;
		StartPosForSmaller = null;
	}
	
	
	
	
	// Return best video that fits the size remaining in server
	public VideoGain ReturnBestCandidateVideo()
	{
		if(StartPosForSmaller==null)
			StartPosForSmaller = VideosPriority.first();
		if(sizeUsed<pb.X)
		{
			Set<VideoGain> K = VideosPriority.tailSet(StartPosForSmaller) ;
			for( VideoGain  curVG :K )
			{
				
				if( pb.VideoList.get(curVG.VID).size +sizeUsed <= pb.X  )
					return curVG;
				
				StartPosForSmaller = curVG;
			//	StartPosForSmaller = VideosPriority.first();
			}
		}
		return null;
	}
	
	
	public double EvaluateGainAddingVideo(Video vid)
	{

		
		double resp = 0;
		// Evaluate Gain by adding the Video vid to this server
		for(Request rq : pb.RequestForVideo.get(vid.ID))// Nendpoint iteration.  Could be optimized by using only the Endpoints with request for this video
		{
			EndPoint ep = rq.eP;
			
//				Request Rq = (ep.RequestList.get(vid.ID));
//			
//				if(Rq.V.ID == vid.ID)
//				{
					double inc = Math.max( 0.0, rq.curLatency-ep.Latency4ServerList.get(servID))*rq.Nreq;
					resp +=inc/(vid.size+Problem.smallOffset); 
//				}
			
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
		{
			sizeUsed = sizeUsed - vid.size;
		
			// Remove video from server
			VideosCached.remove(vid.ID);
		
			// Update affected RequestList
			for(Request Rq : pb.RequestForVideo.get(vid.ID))// Nendpoint iteration.  Could be optimized by using only the Endpoints with request for this video
			{
				// Update Request
				Rq.UpdateStat(pb);
				
			}
					
			// Add to priority queue
				VideosPriority.add(VG);
			//Update pointer. Put it at start of priority queue (since we removed a video, high probability that another one will fit.
				StartPosForSmaller = VideosPriority.first();
			
			
			// Update all VG scores for video
			 updateAllServersVG( vid);
		}
		
		
		
	}
	
	public boolean PutVideoInCache(Video vid)
	{
	 return PutVideoInCache(vid,true);
	}
	

	// Put video in server. 
	public boolean PutVideoInCache(Video vid,boolean updateVG)
	{
		// Get videoGain
		VideoGain VG = AllVideoGains.get(vid.ID);
		
		// Update size
		if(!VideosCached.contains(vid.ID))
		{
			sizeUsed = sizeUsed + vid.size;

			if(sizeUsed > pb.X)
			{
			//	Sys.disp("ERROR, server overflow, refused insertion");
				sizeUsed = sizeUsed - vid.size;
				return false;
			}
		}else
		{
			return false;
		}
		
		// Add video to server
		VideosCached.add(vid.ID);
		
		
		// Update affected RequestList
		for(Request Rq : pb.RequestForVideo.get(vid.ID))// Nendpoint iteration.  Could be optimized by using only the Endpoints with request for this video
		{
			 long thisLatency = Rq.eP.Latency4ServerList.get(servID);
			 long latUpdate= Math.min( Math.min(Rq.Latency2ExternalServer ,Rq.curLatency ),thisLatency);
			 Rq.curLatency = latUpdate;
			 if( latUpdate ==  thisLatency)
			 {
				 Rq.curServer = servID;
			 }
		}
		
		// Remove from priority queue
		VideosPriority.remove(VG);
		

		// Update all VG scores for video
		if(updateVG)
			updateAllServersVG( vid);
		
		return true;
	}
	
	
	public void updateAllServersVG(Video vid)
	{
//		if(vid.ID==1973)
//			Sys.disp("ID seen");
		// Update Video Gain Score on all Servers (including this one)
		for(Server s : pb.ServerList )
		{
			if(!s.VideosCached.contains(vid.ID))
			{		
				VideoGain VG2 = s.AllVideoGains.get(vid.ID);
				// compute gain and update priority list
				
				double newscore = s.EvaluateGainAddingVideo(vid);
				if(newscore != VG2.Score)
				{
					s.VideosPriority.remove(VG2);
					VG2.Score = newscore;
					s.VideosPriority.add(VG2);
					
					// Update lookPointer for small enough Video
					if(  VG2.compareTo(s.StartPosForSmaller)<0  )
					{
						if( pb.VideoList.get(VG2.VID).size + s.sizeUsed <= pb.X)
							s.StartPosForSmaller = VG2;
					}else if(  VG2.compareTo(s.StartPosForSmaller)==0  )
						s.StartPosForSmaller = s.VideosPriority.first();
					
				}
				
				
				
				
				
				
			}
			
		}
	}




	@Override
	public int compareTo(Server o) {
		// TODO Auto-generated method stub
		return Integer.compare(this.servID, o.servID);
	}
	
	
	
	
}
