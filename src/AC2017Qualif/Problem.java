package AC2017Qualif;

import java.io.Serializable;
import java.util.ArrayList;


/*
 *  ProblemModel : For Modeling the HashCode Problem
 *  
 */
public class Problem implements Serializable {

	
	/**
	 * 
	 */
	
	public int V;// (1 <= V <= 10000) - the number of videos
	public int E;// (1 <= E <= 1000) - the number of endpoints
	public int R;// (1 <= R <= 1000000) - the number of request descriptions
	public int C;// (1 <= C <= 1000) - the number of cache servers
	public int X;// (1 <= X <= 500000) - the capacity of each cache server in megabytes

	public ArrayList<EndPoint> EndPointList;
	public ArrayList<Video>   VideoList;
	public ArrayList<Server>  ServerList;
	public ArrayList<Request> RequestList;
	
	
	
	public Problem(){
		this.VideoList = new ArrayList<Video>();
		this.EndPointList = new ArrayList<EndPoint>();
		this.ServerList = new ArrayList<Server>();
		this.RequestList = new ArrayList<Request>();		
	}
	


	
	
}
