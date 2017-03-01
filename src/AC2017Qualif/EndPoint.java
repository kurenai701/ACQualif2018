package AC2017Qualif;

import java.util.ArrayList;

public class EndPoint {

		
	public int LD;//the latency of serving a video request from the data center to this endpoint, in milliseconds
	public int K;//the number of cache servers that this endpoint is connected to
	
	//public ArrayList<Server> ServerList;// removed
	public ArrayList<Integer> Latency4ServerList;// Latency to all servers. Put  Integer.MAX_VALUE for server not connected
	public ArrayList<Request> RequestList;
	
	public EndPoint(int lD, int k, ArrayList<Integer> latency4ServerList) {
		super();
		LD = lD;
		K = k;
		Latency4ServerList = latency4ServerList;
	}
	
}
