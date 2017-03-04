package AC2017Qualif;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.function.Predicate;

public class GenerateOutput implements Serializable{

	


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

		Predicate<Server> pred = s -> s.VideosCached.size() > 0;
		int count_used_servers = Common.Count(sol.pb.ServerList, pred);
		writer.println(count_used_servers);
		Sys.disp(count_used_servers);
		
		for(Server s : sol.pb.ServerList)
		{
			String buildLine = "";
			buildLine += s.servID;	
			
			for(Integer id_vid : s.VideosCached)
			{
				buildLine += " " + id_vid;
			}
			buildLine = buildLine.trim();

			writer.println(buildLine);
		//	Sys.disp(buildLine);
		}
		
//		//  !!!!!!!!!!!!!!!!!!! //					
		
	}


	
}
