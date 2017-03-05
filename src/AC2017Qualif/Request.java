package AC2017Qualif;

import java.io.Serializable;

public class Request implements Comparable<Request> , Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9142164069308619146L;
	public final  int ReqID;
	public final Video V ;//the ID of the requested video
	//public int Re;//the ID of the endpoint from which the requests are coming from
	public final EndPoint eP;
	public  long Nreq;//- the number of requests
	
	
	// Updateable
	public int curServer;// Current server providing the service. -1 for global
	public long curLatency;// current latency for request
	public long Latency2ExternalServer;// Latency to main server or server outside subproblem
	
	public Request(Video v, EndPoint eP, long nreq, int curServer, long curLatency, int ReqID) {
		super();
		this.eP = eP;
		this.V = v;
		Nreq = nreq;
		this.curServer = curServer;
		this.curLatency = curLatency;
		this.ReqID= ReqID;
		this.Latency2ExternalServer = eP.LD;
	}

	
	public void UpdateStat(Problem pb)
	{
		// Updates the request : Recomputes current Latency and current server
		
		curLatency = Latency2ExternalServer;
		this.curServer = -1;
		for(Server s : pb.ServerList)//TODO : could improve search by limiting to server with video
		{
			if(eP.Latency4ServerList.get(s.servID) <  curLatency && s.VideosCached.contains(V.ID)  )
			{
				this.curServer  = s.servID;
				this.curLatency = eP.Latency4ServerList.get(s.servID);
			}
		}
				
		
	}


	@Override
	public int compareTo(Request o) {
		// TODO Auto-generated method stub
		return Integer.compare(ReqID, o.ReqID);
	}

}
