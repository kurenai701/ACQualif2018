package AC2017Qualif;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


/*
 *  ProblemModel : For Modeling the HashCode Problem
 *  
 */
public class Problem implements Serializable, Cloneable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -711589748598493526L;
	/**
	 * 
	 */
	static double smallOffset = 30;//Parameters to give a small boost to smaller videos
	public int V;// (1 <= V <= 10000) - the number of videos
	public int E;// (1 <= E <= 1000) - the number of endpoints
	public int R;// (1 <= R <= 1000000) - the number of request descriptions
	public int C;// (1 <= C <= 1000) - the number of cache servers
	public int X;// (1 <= X <= 500000) - the capacity of each cache server in megabytes

	public double SR;// Total number of requested Videos
	
	public ArrayList<EndPoint> EndPointList;
	public ArrayList<Video>   VideoList;
	public ArrayList<Server>  ServerList;
	public ArrayList<Request> RequestList;
	public HashMap<Integer, ArrayList<Request>>  RequestForVideo;
	
	
	
	public Problem(){
		this.VideoList = new ArrayList<Video>();
		this.EndPointList = new ArrayList<EndPoint>();
		this.ServerList = new ArrayList<Server>();
		this.RequestList = new ArrayList<Request>();
		this.RequestForVideo = new HashMap<>();
		SR = 0.0;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		
		
		Problem sup = (Problem)super.clone();
		
		sup.EndPointList = (ArrayList<EndPoint>) EndPointList.clone();
		sup.VideoList = (ArrayList<Video>) VideoList.clone();
		sup.ServerList = (ArrayList<Server>) ServerList.clone();
		sup.RequestList = (ArrayList<Request>) RequestList.clone();
		sup.RequestForVideo = (HashMap<Integer, ArrayList<Request>>) RequestForVideo.clone();

		
		sup.V=V;
		sup.E=E;
		sup.R=R;
		sup.C=C;
		sup.X=X;
		
		
		return sup;	
	
	}
	
	
	public Problem(Problem pb2, ArrayList<Server> ServerList2){

		this.V = pb2.V;// (1 <= V <= 10000) - the number of videos
		this.E= pb2.E;// (1 <= E <= 1000) - the number of endpoints
		this.R= pb2.V;// (1 <= R <= 1000000) - the number of request descriptions
		this.C= pb2.C;// (1 <= C <= 1000) - the number of cache servers
		this.X= pb2.X;// (1 <= X <= 500000) - the capacity of each cache server in megabytes

		this.SR = pb2.SR;// Total number of requested Videos
				
		this.VideoList = pb2.VideoList;
		
		
//		this.ServerList = ServerList2;
//		public ArrayList<Request> RequestList;
//		public HashMap<Integer, ArrayList<Request>>  RequestForVideo;
//		
//		
//		this.EndPointList = EndPointList 
//		
		
		
	}


	
	
}
