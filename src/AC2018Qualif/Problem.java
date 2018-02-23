package AC2018Qualif;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;


/*
 *  ProblemModel : For Modeling the HashCode Problem
 *  
 */
public class Problem implements Serializable, Cloneable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -711589748598493526L;
	/**
	 * 
	 */
	static double smallOffset = 30;//Parameters to give a small boost to smaller videos
	public int V;// (1 <= V <= 10000) - the number of videos
	public int E;// (1 <= E <= 1000) - the number of endpoints
	public int R;// (1 <= R <= 1000000) - the number of request descriptions
	public int C;// (1 <= C <= 1000) - the number of cache servers
	public int X;// (1 <= X <= 500000) - the capacity of each cache server in megabytes

	public double SR;// Total number of requested Videos
	
	public ArrayList<EndPoint> EndPointList;
	public ArrayList<Video>   VideoList;
	public ArrayList<Server>  ServerList;
	public ArrayList<Request> RequestList;
	public HashMap<Integer, ArrayList<Request>>  RequestForVideo;
	
	
	
	public Problem(){
		this.VideoList = new ArrayList<Video>();
		this.EndPointList = new ArrayList<EndPoint>();
		this.ServerList = new ArrayList<Server>();
		this.RequestList = new ArrayList<Request>();
		this.RequestForVideo = new HashMap<>();
		SR = 0.0;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		
		
		Problem sup = (Problem)super.clone();
		
		sup.EndPointList = (ArrayList<EndPoint>) EndPointList.clone();
		sup.VideoList = (ArrayList<Video>) VideoList.clone();
		sup.ServerList = (ArrayList<Server>) ServerList.clone();
		sup.RequestList = (ArrayList<Request>) RequestList.clone();
		sup.RequestForVideo = (HashMap<Integer, ArrayList<Request>>) RequestForVideo.clone();

		
		sup.V=V;
		sup.E=E;
		sup.R=R;
		sup.C=C;
		sup.X=X;
		
		
		return sup;	
	
	}
	
	// Create a new SubProblem, linked to existing problem
	public Problem(Problem pb2, TreeSet<Server> ServerListSubpb){

		this.V = pb2.V;// (1 <= V <= 10000) - the number of videos
		this.E= pb2.E;// (1 <= E <= 1000) - the number of endpoints
		this.R= pb2.V;// (1 <= R <= 1000000) - the number of request descriptions
		this.C= pb2.C;// (1 <= C <= 1000) - the number of cache servers
		this.X= pb2.X;// (1 <= X <= 500000) - the capacity of each cache server in megabytes

		this.SR = pb2.SR;// Total number of requested Videos
				
		// VideoList****************************
		this.VideoList = pb2.VideoList;// unchanged
		this.ServerList =new ArrayList<Server>();
		
		
		ArrayList<Integer> ConversionServerFull2Sub = new ArrayList<>(pb2.ServerList.size()); // contains Id of new server based on old server id. 
		for(Server s :pb2.ServerList)
		{
			ConversionServerFull2Sub.add(s.servID, -1);
		}
		
		int cnt = 0;
		for(Server s :ServerListSubpb)
		{
			Server SubServ = new Server(cnt, this);
			SubServ.originalServID = s.servID;
			this.ServerList.add(SubServ);
			ConversionServerFull2Sub.set(s.servID , cnt);
			cnt++;
		}
		
		// EndPointList*****************************		
		this.EndPointList = new ArrayList<EndPoint>();
		for(EndPoint eP : pb2.EndPointList)
		{
			ArrayList<Long> latency4ServerList = new ArrayList<>();
			for(Server s:ServerListSubpb)
			{
				latency4ServerList.add(eP.Latency4ServerList.get(s.servID));
			}
			
			//Create new SubProblem EndPoint
			EndPoint SubeP = new EndPoint(eP.getEpID(), eP.LD,  latency4ServerList);
			this.EndPointList.add(SubeP);
		}
		
		//RequestForVideo****************************
		//RequestList****************************
		this.RequestForVideo = new HashMap<>();
		for(Video V : pb2.VideoList)
		{
			this.RequestForVideo.put(V.ID, new ArrayList<Request>());
		}
		
		
		this.RequestList = new ArrayList<Request>();
		for(Request rq : pb2.RequestList)
		{

			Request Subrq = new Request(rq.V, this.EndPointList.get(  rq.eP.getEpID() ) , rq.Nreq, -1, 100000, rq.ReqID);
			//Update latency to external server
			long outLatency = rq.eP.LD;
			for( Server s : pb2.ServerList)
			{
				if(!ServerListSubpb.contains( s))
				{
					if(   s.VideosCached.contains(rq.V.ID))
					{
						outLatency = Math.min(outLatency , rq.eP.Latency4ServerList.get(s.servID));
					}
				}else
				{
		//			Sys.disp("Z");
				}
			}
			Subrq.Latency2ExternalServer = outLatency;
			Subrq.curLatency=outLatency;
			RequestList.add(Subrq);
			this.RequestForVideo.get(rq.V.ID).add(Subrq);
		
		}
		// Now, copy state of current server to subproblem servers
		// First, update all server priority list
		int cntvid=0;
		for(Video vid : this.VideoList)
		{
			for(Server s : this.ServerList)
			{
				VideoGain VG = new VideoGain(vid.ID, -1.0);
				s.AllVideoGains.add(VG);
				s.VideosPriority.add(VG);
				
			}
		//	this.ServerList.get(0).updateAllServersVG(vid);
			cntvid++;
			if(cntvid%1000==0)
			{
				Sys.disp("vid add in Subproblem: " + vid.ID);
			}
		}

		
		// Put existing videos back in cache
		for(Server s : ServerListSubpb)
		{
			int subDserv = ConversionServerFull2Sub.get(s.servID);
			
			for(int prevCachedVideoIndex :s.VideosCached)
			{
				boolean PutOK = this.ServerList.get(subDserv).PutVideoInCache( this.VideoList.get(prevCachedVideoIndex) ,false  );//don't update state, will be done last
				if(!PutOK)
					Sys.disp("ERROR");
			}
		}
		
		// Update all Video Gains
		for(Video vid : this.VideoList)
		{
			this.ServerList.get(0).updateAllServersVG(vid);
		}
		
		
	}


	
	
}
