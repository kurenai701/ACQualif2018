package AC2017Qualif;

public class Request {

	
	public int Rv ;//the ID of the requested video
	public int Re;//the ID of the endpoint from which the requests are coming from
	public int Nreq;//- the number of requests
	
	
	public int curServer;// Current server providing the service. -1 for global
	public int curLatency;// current latency for request
	
	public Request(int rv, int re, int nreq, int curServer, int curLatency) {
		super();
		Rv = rv;
		Re = re;
		Nreq = nreq;
		this.curServer = curServer;
		this.curLatency = curLatency;
	}

	
	
	
	
}
