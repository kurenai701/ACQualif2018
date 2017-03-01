package AC2017Qualif;

import java.io.PrintWriter;
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
//			pb.R = scIn.nextInt(); // row
//			pb.C = scIn.nextInt(); // col
//			pb.L = scIn.nextInt(); // minimum number of each ingredient cells in a slice
//			pb.H = scIn.nextInt(); // maximum total number of cells of a slice
//
//			
//			
//			// process pizza
//
//			pb.Pizza= new int[pb.R][pb.C];
//			for (int i = 0; i < pb.R; i++)
//			{	
//				String pizzaLine = "";
//				while(pizzaLine.isEmpty())
//				{
//					pizzaLine = scIn.nextLine();
//				}
//				
//				byte[] PizzaByte = pizzaLine.getBytes();
//				for(int j = 0;j<pb.C;j++)
//					if(PizzaByte[j] == ('T'))
//					{	
//						pb.Pizza[i][j] = -1;
//					}else
//					{
//						pb.Pizza[i][j] = -2;
//					}
//						
//			}
//			
		
		Sys.pln("input read done : pb created");
		

		return pb;
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
	
	