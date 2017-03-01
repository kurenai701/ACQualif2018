package AC2017Qualif;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class EndPoint implements Comparable<EndPoint>{

	public final int epID;
	public final int LD;//the latency of serving a video request from the data center to this endpoint, in milliseconds
	public final int K;//the number of cache servers that this endpoint is connected to
	public ArrayList<Server> ServerList;// removed
	public final ArrayList<Integer> Latency4ServerList;// Latency to all servers. Put  Integer.MAX_VALUE for server not connected
	public Map<Integer,Request> RequestList;// List of requests, mapped by index of video !!! Need concatenate if 2 requests are from same Endpoint with same video
	
	public EndPoint(int epID, int lD, int k, ArrayList<Integer>  latency4ServerList,ArrayList<Server> ServerList) {
		super();
		this.epID = epID;
		LD = lD;
		K = k;
		Latency4ServerList = latency4ServerList;
		this.ServerList=ServerList;
		this.RequestList = Collections.synchronizedSortedMap(new TreeMap<Integer,Request>());
	}

	@Override
	public int compareTo(EndPoint o) {
		return Integer.compare(epID, o.epID);
		
		
	}
	
}
