package AC2018Qualif;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;



public class ReadInput {
	

	
	public Scanner ScannerInputFile(String fileFullPath)
	{
		return Common.FU.ScannerFile(fileFullPath);
	}
	
			
	// From Input file to Input Model
	public Problem ProcessReadInputToModel(Scanner scIn)
	{	
		System.out.println("ProcessReadInputToModel");
		Problem pb = new Problem();
		
		if (scIn != null)
		{
			//  !!!!!!!!!!!!!!!!!!! //
			// TODO	CODE here parsing of file, and saving to model	
			//  !!!!!!!!!!!!!!!!!!! //
					
			pb = new Problem();
			
			pb.V = scIn.nextInt(); // nb videos
			pb.E = scIn.nextInt(); // nb endpoints
			pb.R = scIn.nextInt(); // nb requests
			pb.C = scIn.nextInt(); // nb cache servers
			pb.X = scIn.nextInt(); // capacity of each server in MB
			
			
		    // VIDEO LIST
			for (int i=0; i < pb.V; i++)
			{
				int size = scIn.nextInt();
			    Video vid = new Video(i, size);
				pb.VideoList.add(vid);
				pb.RequestForVideo.put(vid.ID, new ArrayList<Request>());
			}
			
			pb.ServerList = new ArrayList<Server>();
			for (int cache_id = 0; cache_id < pb.C; cache_id++)
			{
				Server s = new Server(cache_id, pb);//ew ArrayList<EndPoint>(),
				pb.ServerList.add(s);
			}		
			
			// ENDPOINTS
			for (int i=0; i < pb.E; i++)
			{
				int ld = scIn.nextInt();
				int k = scIn.nextInt();
				
				// Initialize latencyToServer with IntegerMax for all cache
				
				ArrayList<Long> latencyToServerList = new ArrayList<Long>();
				for (int cache_id = 0; cache_id < pb.C; cache_id++)
				{
					latencyToServerList.add(10000000000L);// Latency 1e10 for not used servers
				}			
								
				ArrayList<Integer> serverToUpdateWithEndpoint = new ArrayList<Integer>();
				
				for (int j = 0; j < k; j++)
				{
					int c = scIn.nextInt();
					long lc = scIn.nextLong();
					
					serverToUpdateWithEndpoint.add(c);
					latencyToServerList.set(c, lc);
				}
				
				EndPoint endp = new EndPoint(i, ld,latencyToServerList);
				
				// Now that the endpoint is created, add endpoint to servers connected.
				for (Integer c: serverToUpdateWithEndpoint)
				{
					Server server = pb.ServerList.get(c);
	//				server.ServedEndPoint.add(endp);
				}
				
				pb.EndPointList.add(endp);
			}
			
			// REQUESTS
			int R2 = 0;
			for (int i = 0; i < pb.R; i++)
			{
				int rv = scIn.nextInt();
				int re = scIn.nextInt();
				int nreq = scIn.nextInt();
				pb.SR += nreq;
                int curServer = -1;
                long curLatency = pb.EndPointList.get(re).LD;
				
				//Request req = new Request(rv, re, nreq, curServer, curLatency);
				
				Video vid = pb.VideoList.get(rv);
				EndPoint ep = pb.EndPointList.get(re);
				
				Request req;
				if(ep.RequestList.containsKey(vid.ID))
				{
					req = ep.RequestList.get(vid.ID);
					req.Nreq += nreq;
					
				}else
				{
					req = new Request(vid, ep, nreq, curServer, curLatency,R2);
					pb.RequestList.add(req);
					ep.RequestList.put(vid.ID, req);
					R2++;
				}
				
				
				
			}
			pb.R = R2;
			for(Request rq : pb.RequestList)
			{
				pb.RequestForVideo.get( rq.V.ID   ).add(rq);
			}
			
			
		Sys.pln("input read done : pb created");
		}
		
		return pb;
	}
	
		
	// If time, and if we want to verify that we have correct modeling
	// From Input Model to Something similar as Input Text File
	public void ProcessProblemModelToVerifFile(Problem pb, String FilePath)
	{
		System.out.println("ProcessProblemModelToVerifFile");
		
		// Writer
		PrintWriter writer = Common.FU.CreateWriterFile(FilePath, "UTF-8");
		
		if (writer != null)
		{
			if (pb != null)
			{	
			//  !!!!!!!!!!!!!!!!!!! //
				// TODO CODE here processing to read the model and write the file
				//  !!!!!!!!!!!!!!!!!!! //
				
				
				// write first line
			
			//	writer.println(pb.testString);	
			
				
				//  !!!!!!!!!!!!!!!!!!! //
		    }				
				
			writer.close();
		}
	}
	

}
	
	