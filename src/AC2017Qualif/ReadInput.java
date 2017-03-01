package AC2017Qualif;

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
			}
			
			// ENDPOINTS
			for (int i=0; i < pb.E; i++)
			{
				int ld = scIn.nextInt();
				int k = scIn.nextInt();
				
				// Initialize latencyToServer with IntegerMax for all cache
				ArrayList<Integer> latencyToServerList = new ArrayList<Integer>();
				for (int cache = 0; cache < pb.C; cache++)
				{
					latencyToServerList.add(Integer.MAX_VALUE);
				}			
								
				for (int j = 0; j < k; j++)
				{
					int c = scIn.nextInt();
					int lc = scIn.nextInt();
					latencyToServerList.set(c, lc);
				}
				
				EndPoint endp = new EndPoint(ld, k, latencyToServerList);
				pb.EndPointList.add(endp);
			}
			
			// REQUESTS
			
			for (int i = 0; i < pb.R; i++)
			{
				int rv = scIn.nextInt();
				int re = scIn.nextInt();
				int nreq = scIn.nextInt();
				
                int curServer = -1;
                int curLatency = Integer.MAX_VALUE;
				
				Request req = new Request(rv, re, nreq, curServer, curLatency);
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
	
	