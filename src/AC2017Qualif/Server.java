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
	
	
}
