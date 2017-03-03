package AC2017Qualif;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class EndPoint implements Comparable<EndPoint>{

	public final int epID;
	public final long LD;//the latency of serving a video request from the data center to this endpoint, in milliseconds
	public final int K;//the number of cache servers that this endpoint is connected to
	public ArrayList<Server> ServerList; // list of all servers
	public final ArrayList<Long> Latency4ServerList;// Latency to all servers. Put  Integer.MAX_VALUE for server not connected
	public Map<Integer,Request> RequestList;// List of requests, mapped by index of video !!! Need concatenate if 2 requests are from same Endpoint with same video
	
	public EndPoint(int epID, long lD, int k, ArrayList<Long>  latency4ServerList,ArrayList<Server> ServerList) {
		super();
		this.epID = epID;

		this.ServerList=ServerList;
		this.RequestList = Collections.synchronizedSortedMap(new TreeMap<Integer,Request>());
		this.LD = lD;
		this.K = k;
		this.Latency4ServerList = latency4ServerList;
	}

	@Override
	public int compareTo(EndPoint o) {
		return Integer.compare(epID, o.epID);
		
		
	}
	
}
