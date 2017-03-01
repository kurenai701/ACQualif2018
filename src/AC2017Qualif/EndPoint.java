package AC2017Qualif;

import java.util.ArrayList;

public class EndPoint {

	
	public int LD;//the latency of serving a video request from the data center to this endpoint, in milliseconds
	public int K;//the number of cache servers that this endpoint is connected to
	
	public ArrayList<Server> ServerList;
	public ArrayList<Integer> Latency4ServerList;
	
}
