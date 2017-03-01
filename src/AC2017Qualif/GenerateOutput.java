package AC2017Qualif;

import java.io.PrintWriter;
import java.io.Serializable;

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

	//TODO  =>	writer.println(""+sol.Cells.size());
		String buildLine = "";
		//TODO  =>
//		for (Cell cell : sol.Cells)
//		{
//				buildLine = ""+cell.R1+" "+cell.C1+" "+cell.R2+" "+cell.C2;
//		
//			buildLine = buildLine.trim();
//			writer.println(buildLine);
//		}
		
//		//  !!!!!!!!!!!!!!!!!!! //					
		
	}


	
}
