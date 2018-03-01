package AC2018Qualif;

import java.io.Serializable;

public class Ride implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 825431538102847461L;
	public int id;
	public int a; // start row
	public int b; // start col

	public int x; // finish row
	public int y; // finish col
	
	public int s; // earliest start time
	public int f; // latest finish time

	public int T; // pb.T
	
	public Ride() {}
	
	public Ride(int a, int b, int x, int y, int s, int f, int id, int T)
	{
	    this.a = a;
	    this.b = b;
	    this.x = x;
	    this.y = y;
	    this.s = s;
	    this.f = f;
	    this.id = id;
	    this.T= T;
	}
	
	
	public int lastTimeForRideStart()
	{
		return T - Common.Dist(this);
	}
}
