package AC2017Qualif;

public class Request {

	
	public final Video V ;//the ID of the requested video
	//public int Re;//the ID of the endpoint from which the requests are coming from
	public final EndPoint eP;
	public  int Nreq;//- the number of requests
	
	
	// Updateable
	public int curServer;// Current server providing the service. -1 for global
	public int curLatency;// current latency for request
	
	public Request(Video v, EndPoint eP, int nreq, int curServer, int curLatency) {
		super();
		this.eP = eP;
		this.V = v;
		Nreq = nreq;
		this.curServer = curServer;
		this.curLatency = curLatency;
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

}
