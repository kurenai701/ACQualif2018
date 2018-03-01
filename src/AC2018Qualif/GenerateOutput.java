package AC2018Qualif;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.function.Predicate;

public class GenerateOutput implements Serializable{

	

	public static void main(String[] args) {

		Problem pb = new Problem();
        Solution s = new Solution(pb);        
        GenerateOutput go = new GenerateOutput();
        
        go.GenerateOutputFileFromOutputModel(s, "C:\\ACFile\\test.txt");
		
		
	}
	
	
	public void GenerateOutputFileFromOutputModel(Solution sol, String filePath)
	{
		System.out.println("GenerateOutputFileFromOutputModel");
		if (sol == null)
		{
			System.out.println("outModel null");
		}
		else
		{
			PrintWriter writer = Common.FU.CreateWriterFile(filePath, Common.OutputEncoding);
			if (writer != null)
			{				
				WriteOutputFileGenerationLogic(writer, sol);
				writer.close();
			}		
		}
	}
	
	
	public void WriteOutputFileGenerationLogic(PrintWriter writer, Solution sol)
	{
		//  !!!!!!!!!!!!!!!!!!! //
		// TODO CODE HERE Output Generation Logic from Output Model
		//  !!!!!!!!!!!!!!!!!!! //

		Sys.disp("Generating Output");

		
		for (Car c : sol.Cars)
		{
			int nb_rides = c.RidesServed.size();
			String buildLine = "";
			buildLine += nb_rides - 1;
			
			for(Integer r : c.RidesServed)
			{
				if(r> 0)
				{
					r = r-1;
					buildLine += " " + r;
				}
			}
			buildLine = buildLine.trim();

			writer.println(buildLine);
			Sys.disp(buildLine);
		}
		
//		Predicate<Server> pred = s -> s.VideosCached.size() > 0;
//		int count_used_servers = Common.Count(sol.pb.ServerList, pred);
//		writer.println(count_used_servers);
//		Sys.disp(count_used_servers);
//		
//		for(Server s : sol.pb.ServerList)
//		{
//			String buildLine = "";
//			buildLine += s.servID;	
//			
//			for(Integer id_vid : s.VideosCached)
//			{
//				buildLine += " " + id_vid;
//			}
//			buildLine = buildLine.trim();
//
//			writer.println(buildLine);
//		//	Sys.disp(buildLine);
//		}
		
//		//  !!!!!!!!!!!!!!!!!!! //					
		
	}


	
}
