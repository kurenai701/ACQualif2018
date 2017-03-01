package AC2017Qualif;

import java.util.ArrayList;

public class Server {
	public int ID;
	public ArrayList<EndPoint> ServedEndPoint;
	public ArrayList<Integer> VideosCached;
	public Server(int iD, ArrayList<EndPoint> servedEndPoint, ArrayList<Integer> videosCached, int sizeUsed) {
		super();
		ID = iD;
		ServedEndPoint = servedEndPoint;
		VideosCached = videosCached;
		this.sizeUsed = sizeUsed;
	}
	public int sizeUsed;
	
	
	
	
	public int EvaluateGainAddingVideo(Video vid)
	{
		int resp = 0;
		// Evaluate Gain by ading the Video vid to this server
		for(EndPoint ep : ServedEndPoint)
		{
			for(Request Rq: ep.RequestList)
			{
				if(Rq.Rv == vid.ID)
				{
					resp +=Math.max( 0,Rq.curLatency-ep.Latency4ServerList.get(ID))*Rq.Nreq; 
				}
			
			}
			
		}
		return resp;
		
		
	}
	
	
	
}
