package AC2017Qualif;

public class Request implements Comparable<Request>{

	public final  int ReqID;
	public final Video V ;//the ID of the requested video
	//public int Re;//the ID of the endpoint from which the requests are coming from
	public final EndPoint eP;
	public  long Nreq;//- the number of requests
	
	
	// Updateable
	public int curServer;// Current server providing the service. -1 for global
	public long curLatency;// current latency for request
	
	public Request(Video v, EndPoint eP, long nreq, int curServer, long curLatency, int ReqID) {
		super();
		this.eP = eP;
		this.V = v;
		Nreq = nreq;
		this.curServer = curServer;
		this.curLatency = curLatency;
		this.ReqID= ReqID;
	}

	
	public void UpdateStat()
	{
		// Updates the request : Recomputes current Latency and current server
		
		curLatency = eP.LD;
		this.curServer = -1;
		for(Server s : eP.ServerList)
		{
			if(eP.Latency4ServerList.get(s.servID) <  eP.LD && s.VideosCached.contains(V.ID)  )
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
