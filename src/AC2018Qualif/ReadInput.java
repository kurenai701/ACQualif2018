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
			pb.R = scIn.nextInt();
			pb.C = scIn.nextInt();
			pb.F = scIn.nextInt();
			pb.N = scIn.nextInt();
			pb.B = scIn.nextInt();
			pb.T = scIn.nextInt();

			for(int i=0; i< pb.N; i++){
				int a = scIn.nextInt();
				int b = scIn.nextInt();
				int x = scIn.nextInt();
				int y = scIn.nextInt();
				int s = scIn.nextInt();
				int f = scIn.nextInt();
				
				pb.Rides.add(new Ride(a,b,x,y,s,f,i));
			}			
			
//			pb.V = scIn.nextInt(); // nb videos
//			pb.E = scIn.nextInt(); // nb endpoints
//			pb.R = scIn.nextInt(); // nb requests
//			pb.C = scIn.nextInt(); // nb cache servers
//			pb.X = scIn.nextInt(); // capacity of each server in MB
//			
//			
//		    // VIDEO LIST
//			for (int i=0; i < pb.V; i++)
//			{
//				int size = scIn.nextInt();
//			    Video vid = new Video(i, size);
//				pb.VideoList.add(vid);
//				pb.RequestForVideo.put(vid.ID, new ArrayList<Request>());
//			}
			
			
			
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
	
	