package AC2018Qualif;

import java.io.Serializable;
import java.util.ArrayList;

public class Car implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2313261149009936617L;
	ArrayList<Integer> RidesServed;
	int lastRideTime;
	boolean finished;
	
	
	public Car(ArrayList ridesServed) {
		super();
		RidesServed = ridesServed;
	}
	
	
	public Car( ) {
		super();
		RidesServed = new ArrayList<Integer>();
		RidesServed.add(0);
		lastRideTime=0;
		finished= false;
	}
	
	void addRide(int rideIdx, Problem pb)
	{
		this.RidesServed.add(rideIdx);

		this.lastRideTime = this.lastRideTime 
							+ Common.Dist(pb.Rides.get(rideIdx))
							+ Common.Dist(pb.Rides.get(RidesServed.get(RidesServed.size()-1)), pb.Rides.get(rideIdx));
	
	}
	
	public int score(Problem pb)
	{
		int sc = 0;
		int ti = 0;
		int a =0;
		int b =0;
		for(Integer r : this.RidesServed)
		{
			
			if(r> 0)
			{
				Ride ri = pb.Rides.get(r);
				int x = ri.a;
				int y = ri.b;
				ti += Math.abs(x - a) + Math.abs(y - b);
				if(ti <= ri.s)
				{
					ti = ri.s;
					sc = sc + pb.B;
				}
				ti += Common.Dist(ri);
				if(ti <= ri.f)
				{
					sc += Common.Dist(ri);
				}
				a = ri.x;
				b = ri.y;
			}
		}
		return sc;
	}
}
