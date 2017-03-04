package AC2017Qualif;

import java.util.Scanner;

public class OutputBackToSol {

	public Scanner ScannerOutputFile(String fileFullPath) {
		return Common.FU.ScannerFile(fileFullPath);
	}

	// From Output file back to Solution Model
	// Problem should be already recreated through ReadInput
	public Solution ProcessReadOutputBackToSolution(Scanner scOut, Problem pb) {
		Sys.disp("ProcessReadOutputBackToSolution");
		Solution sol = new Solution(pb);

		/////////////// Beginning TAKEN FROM AlgoINIT //////
		
		// First, update all server priority list
		for(Video vid : pb.VideoList)
		{
			for(Server s : pb.ServerList)
			{
				VideoGain VG = new VideoGain(vid.ID, -1.0);
				s.AllVideoGains.add(VG);
				s.VideosPriority.add(VG);
			}			
		}
		/////////////////////////
		
		
		
		if (scOut != null) {
			// In the output file that I generate, even if a server is not used.
			// There is a line for that server with the id. But no Video
			
			// Getting rid of the first line
			String line1 = scOut.nextLine();
	//		Sys.disp("line1 : " + line1);
			
			for (int i = 0; i < pb.C; i++) {
				String line = scOut.nextLine();
//				Sys.disp(line);
				String[] splitline = line.split(" ");
	
				int idServer = Integer.parseInt(splitline[0]);
	//			Sys.disp("idServer" + idServer);
				Server curServer = pb.ServerList.get(idServer);
				for (int j = 1; j < splitline.length; j++) {
					int idVid = Integer.parseInt(splitline[j]);
//					Sys.disp("idVid" + idVid);
					Video curVid = pb.VideoList.get(idVid);
                    
					
					curServer.PutVideoInCache(curVid);
				}
			}
			
		    // Videos have been assigned back to their server
		    // Now reupdate video gains
			
			int cntvid = 0;
			for(Video vid : pb.VideoList)
			{
				pb.ServerList.get(0).updateAllServersVG(vid);
				cntvid++;
				if(cntvid%1000==0)
				{
					Sys.disp("vid add : " + vid.ID);
				}
			}

		
		}
		
		
		Sys.disp("DONE GETTING SOL BACK FROM OUTPUT");
		return sol;
	}

}
