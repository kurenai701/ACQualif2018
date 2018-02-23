package AC2018Qualif;

import java.io.PrintWriter;
import AC2018Qualif.Sys;

public class UtilReadInput {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String myfilepath = Common.ACFileFolderPath + "clemtest.txt";

		PrintWriter writer = Common.FU.CreateWriterFile(myfilepath, "UTF-8");
		if (writer != null) {

			writer.println("pb = new Problem();");
			
			myScanfLike(writer, "%s%d%d%d%d", "V,E,R,C,X", "Pb.");
			writer.println();
			
			forLoopBasicPrint(writer, 'i', "Pb.V");	
			myScanfLike(writer, "%d%d%d", "LC1,LC2,LC3", "int ");	
			closeForLoop(writer);

			
			forLoopBasicPrint(writer, 'i', "Pb.E");	
			myScanfLike(writer, "%d%d%d", "E1,E2,E3", "int ");	
			closeForLoop(writer);

			
			
			// close writer file
			writer.close();
		}
	}

	public static void myScanfLike(PrintWriter writer, String pattern, String targetVals, String beforeVal) {
		/*
		 * pb = new Problem();
		 * 
		 * pb.V = scIn.nextInt(); // nb videos pb.E = scIn.nextInt(); // nb
		 * endpoints pb.R = scIn.nextInt(); // nb requests pb.C =
		 * scIn.nextInt(); // nb cache servers pb.X = scIn.nextInt(); //
		 * capacity of each server in MB
		 */

		String[] singlepatternlist = pattern.split("%");
        String[] singletargetvallist = targetVals.split(",");
		
		for(int i = 0; i < singlepatternlist.length - 1; i++)
		{
			char[] chArray = singlepatternlist[i+1].toCharArray();
			char c = chArray[0];
			String s = singletargetvallist[i];			
			singleLinePrint(writer, c, s, beforeVal);
		}
		
		String result = "haha";
		Sys.pln(result);

		return;
	}

	public static void singleLinePrint(PrintWriter writer, char charPattern, String targetVal, String beforeVal) {

		switch (charPattern) {
		case 'd':
			writer.println(beforeVal + targetVal + " = ScIn.nextInt();");
			break;
		case 's':
			writer.println(beforeVal + targetVal + " = ScIn.next();");
			break;
		}

	}

	public static void forLoopBasicPrint(PrintWriter writer, char var, String variablelimit)
	{
		String formatted = "for("+var+"=0; " + var + "< " + variablelimit + "; " + var + "++){";	
	    writer.println(formatted);
	}	
	
	public static void closeForLoop(PrintWriter writer){
		writer.println("}");
	}
	
}
