package AC2018Qualif;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.SplittableRandom;

public class Car implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2313261149009936617L;
	LinkedList<Integer> RidesServed;
	int lastRideTime;
	boolean finished;
	Ride NextBestRide;
	int NextBestRideStartTime;
	
	
	
	public Car(LinkedList ridesServed) {
		super();
		RidesServed = ridesServed;
	}
	
	
	public Car( ) {
		super();
		RidesServed = new LinkedList<Integer>();
		RidesServed.add(0);
		lastRideTime=0;
		finished= false;
	}
	
	void findBestRide(Problem pb,Solution sol)
	{
		int bestTimeToRide = Integer.MAX_VALUE;
		int bestRideIdx = 0;
		int lastRideIdx = this.RidesServed.get(this.RidesServed.size()-1);
		Ride lastRide = pb.Rides.get(lastRideIdx);
		for(Ride ri : pb.Rides )
		{
			if(!sol.RideServed[ri.id] && Common.IsRidable( lastRide, ri, this.lastRideTime ) )
			{
				int curTimeToRide = Math.max(this.lastRideTime+Common.Dist(lastRide,ri),ri.s);
				if(bestRideIdx == 0 || 
						(bestTimeToRide > curTimeToRide ) )//&& rand.nextDouble()<pbkeep
				{
					bestRideIdx = ri.id;
					bestTimeToRide = curTimeToRide;
				}
			}
		}
		NextBestRide 			= pb.Rides.get(bestRideIdx);
		NextBestRideStartTime 	= bestTimeToRide;
		
		
	}
	
	@SuppressWarnings("unchecked")
	void OptimizeRide(Problem pb, Solution sol,SplittableRandom rand)
	{
		double bestScore = this.score(pb);
		LinkedList<Integer> BestRidesServed = (LinkedList<Integer>) RidesServed.clone();
		int bestLastRideTime = lastRideTime;
		boolean bestRideServedIdx[] = sol.RideServed.clone();
		int Nit = 200;
		double temp= 0;//10000;
		for(int i =0;i<Nit;i++)
		{
			temp = temp*0.95;
			TryRemoveAndInsert(pb,sol,rand);
			if(this.score(pb)>bestScore)
			{
				bestScore = this.score(pb);
				BestRidesServed = (LinkedList<Integer>) RidesServed.clone();
				bestLastRideTime = lastRideTime;
				bestRideServedIdx =  sol.RideServed.clone();
			}else
			{
				//Restore
				if((i==Nit-1 )  || 0.15>rand.nextDouble())
				{
					RidesServed = (LinkedList<Integer>) BestRidesServed.clone();
					lastRideTime = bestLastRideTime;
					 sol.RideServed = bestRideServedIdx.clone();
				}
			}
		}
		
	}
	
	// Tries to remove 1 or more Ride from Car, then replace with other Rides.
	void TryRemoveAndInsert(Problem pb, Solution sol, SplittableRandom rand)
	{
		int PosRemove = rand.nextInt(RidesServed.size());
		if(PosRemove!=0)
		{
			Integer id = RidesServed.remove(PosRemove);
			sol.RideServed[id] = false;
		
		// Add 1 or 2 rides
		int NRideAdd= 0 + rand.nextInt(3);
		for(int i =0;i<NRideAdd;i++)
		{
			int id_insert = 0;
			int Ntest = 100;
			while(id_insert==0 && 0<Ntest--)
			{
				int tmp= rand.nextInt(sol.RideServed.length); 
				if(!sol.RideServed[tmp])
				{
					id_insert = tmp;
				}
			}
			if(id_insert!=0)
			{
				RidesServed.add(PosRemove,id_insert);
				sol.RideServed[id_insert] = true;
			}
		}
		}
		
		
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
