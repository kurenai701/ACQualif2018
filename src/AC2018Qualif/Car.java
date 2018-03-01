package AC2018Qualif;

import java.util.ArrayList;

public class Car {
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
}
