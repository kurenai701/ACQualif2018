package AC2018Qualif;

public class Ride {

	public int id;
	public int a; // start row
	public int b; // start col

	public int x; // finish row
	public int y; // finish col
	
	public int s; // earliest start time
	public int f; // latest finish time

	public Ride() {}
	
	public Ride(int a, int b, int x, int y, int s, int f, int id)
	{
	    this.a = a;
	    this.b = b;
	    this.x = x;
	    this.y = y;
	    this.s = s;
	    this.f = f;
	    this.id = id;
	}
	
}
