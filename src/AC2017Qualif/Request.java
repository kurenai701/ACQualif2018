package AC2017Qualif;

public class Request {

	
	public int Rv ;//the ID of the requested video
	public int Re;//the ID of the endpoint from which the requests are coming from
	public int Nreq;//- the number of requests
	public Request(int rv, int re, int nreq) {
		super();
		Rv = rv;
		Re = re;
		Nreq = nreq;
	}
	
	
	
}
